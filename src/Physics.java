import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Physics{
    double GRAV_CONST = 1;
    ArrayList<Piston> arr;
    ArrayList <Couple> couples;
    Physics(ArrayList<Piston> arr){
        this.arr = arr;
        this.couples = new ArrayList<Couple> ();
    }
    public void refreshCouples (ArrayList <Piston> others){
        if (others.size() > 1){
            couples.clear();
            for (int i = 0; i < others.size(); i ++){
                for (int j = i+1; j < others.size(); j++){
                    if (i != j) {
                        couples.add(new Couple(others.get(i), others.get(j)));
                    }
                }
            }
        }
    }

    public void updateGrav (){
        for (Couple coup : couples) {
            Circle a = (Circle) coup.a;
            Circle b = (Circle) coup.b;
            if ((!a.ghost && !b.ghost) && !(Math.sqrt(Math.pow(a.position.x - b.position.x, 2)
                    + Math.pow(a.position.y - b.position.y, 2))-2 <= a.r + b.r)) {

                double distance = distance(a, b);
                double force = GRAV_CONST * a.mass() * b.mass() / (distance * distance);
                // Makes a pair that points toward the other piston
                Pair direction = new Pair(b.position.x - a.position.x, b.position.y - a.position.y);
                // Converts the pair into a unit pair
                direction.convertUnit();
                // Multiplies the unit by the force
                direction.multiplyScalar(force);
                Pair dir2 = new Pair(-direction.x, -direction.y);
                // Converts the force into acceleration
                direction.divideScalar(a.mass());
                dir2.divideScalar(a.mass());

                a.acc.basicAdd(direction);
                b.acc.basicAdd(dir2);
            }
        }
    }

    public void drawGrav(Graphics g){
        for (int i = 0; i < Main.SIZE; i+=10){
            for (int j = 0; j < Main.SIZE; j+=10){
                Pair force = new Pair(0,0);
                for (int q = 0; q < arr.size(); q++) {
                    Pair temp = new Pair(i, j).getDifference(arr.get(q).position);
                    double magnitude = GRAV_CONST * arr.get(q).mass() / Math.pow(temp.r(), 2);
                    force.basicAdd(temp.multiplyScalar(magnitude));
                }
                double var1 = 5*Math.cos(force.theta());
                double var2 = 5*Math.sin(force.theta());
                int color = 255- (int) (force.r());
                if (color < 0) color = 0;
                g.setColor(new Color(color,color,color));
                g.fillRect((int)(i - 5),(int)( j - 5),10,10);
                g.setColor(Color.BLACK);

//                g.drawLine((int)(i - var2),(int)( j + var1),(int)(i + var2),(int)( j - var1));
                g.drawLine((int)(i + var2),(int)( j - var1),(int)(i - var2),(int)( j + var1));
            }
        }
    }

    public double distance(Piston a, Piston b){
        return Math.sqrt(Math.pow(a.position.x - b.position.x, 2) + Math.pow(a.position.y - b.position.y, 2));
    }

    public double angle(Piston a, Piston b){
        return Math.atan((a.position.y - b.position.y)/(a.position.x - b.position.x));
    }

    public Pair getAcceleration(Piston b, double force, double angle){
        Pair v = new Pair();
        v.x += force * Math.cos(angle) / b.mass();
        v.y += force * Math.sin(angle) / b.mass();
        return v;
    }

    public void resolveCollisions(){
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) instanceof Polygon) ((Polygon) arr.get(i)).updateNormals();
        }
        for (Couple coup : couples){
            Piston pista = coup.a;
            Piston pistb = coup.b;

            // CIRCLE - CIRCLE
            if (pista instanceof Circle && pistb instanceof Circle){
                Circle a = (Circle) pista;
                Circle b = (Circle) pistb;
                Pair contactVect = a.position.getCopy().getDifference(b.position.getCopy());
                double dist = a.r+b.r - contactVect.r();
                if (!a.ghost && !b.ghost && dist >= 0){
                    collide(contactVect.convertUnit(),dist,a,b);
                }
            }
            // POLYGON - CIRCLE --> CIRCLE - POLYGON
            if (pista instanceof Polygon && pistb instanceof Circle){
                Piston copy = pista;
                pista = pistb;
                pistb = copy;
            }
            // CIRCLE - POLYGON
            if (pista instanceof Circle && pistb instanceof Polygon){
                Circle a = (Circle) pista;
                Polygon b = (Polygon) pistb;

                ArrayList<Pair> normals = b.getNormals();
                Pair[] points = b.pts;


                // Find the axis of least penetration
                double minPen = Double.MAX_VALUE;
                Pair finNormal = null;
                for (int q = 0; q < normals.size(); q++) {
                    Pair normal = normals.get(q);
                    // checks to see if the circle is moving towards normal
                    if (a.velocity.dotProduct(normal) <= 0) {
                        double circPt = a.position.getCopy().projOnTo(normal).r();
                        double min = Double.MAX_VALUE;
                        double max = Double.MIN_VALUE;
                        double cmin = circPt - a.r;
                        double cmax = circPt + a.r;

                        for (Pair pair : points) {

                            double projection = pair.getCopy().projOnTo(normal).r();
                            if (projection > max) max = projection;
                            if (projection < min) min = projection;

                        }
                        double penDepth = (Math.min(max - cmin, cmax - min));
                        // if the pen depth is negative, were done
                        if (penDepth < 0){
                            finNormal = null;
                            break;
                        }
                        if (penDepth > 0 && penDepth < minPen) {
                            minPen = penDepth;
                            finNormal = normal;
                        }

                    }

                }

                if (finNormal != null) {
                    collide(finNormal, minPen, a, b);
                    //System.out.println(finNormal.toString());
                    //System.out.println(minPen);
                }
                //Collision handling goes here

            }
            // POLYGON - POLYGON
            if (pista instanceof Polygon && pistb instanceof Polygon){
                Polygon a = (Polygon) pista;
                Polygon b = (Polygon) pistb;

                ArrayList<Pair> normals = a.getNormals();
                normals.addAll(b.getNormals());

                Pair[] points = a.pts;
                Pair[] pointsOther = b.pts;
                double length = new Pair(b.position.x - a.position.x, b.position.y - a.position.y).r();
                boolean flag = true;
                for (int q = 0; q < normals.size(); q++) {
                    Pair normal = normals.get(q);
                    double left = 0;
                    for (Pair pair : points) {
                        double temp = pair.getCopy().getDifference(a.position).projOnTo(normal).r();
                        if (temp > left) left = temp;
                    }
                    double right = 0;
                    for (Pair pair : pointsOther) {
                        double temp = pair.getCopy().getDifference(b.position).projOnTo(normal).r();
                        if (temp > right) right = temp;
                    }
                    if (left + right > length) {
                        // method for collision goes here
                    }

                }
            }

        }
    }

    double elasticity = .9;
    public void collide (Pair normal, double depth, Piston a, Piston b){
        //b.velocity = new Pair (5,5);

        double ratioB = a.mass()/(a.mass()+b.mass());
        double ratioA = b.mass()/(a.mass()+b.mass());
        a.position = a.position.subtract(normal.getCopy().multiplyScalar(ratioA*depth));
        b.position = b.position.add(normal.getCopy().multiplyScalar(ratioB*depth));

        Pair relativeVelocity = a.velocity.getCopy().getDifference(b.velocity);
        double cosine = relativeVelocity.dotProduct(normal);
        if(cosine > 0)
            return;
        cosine *= -elasticity;
        double j = (-(1+elasticity) * cosine) / (1/a.mass() + 1/b.mass());
        Pair impulse = normal.getCopy().multiplyScalar(j);
        a.velocity = a.velocity.add(impulse.getCopy().divideScalar(a.mass()));
        b.velocity = b.velocity.subtract(impulse.getCopy().divideScalar(b.mass()));
    }
}
