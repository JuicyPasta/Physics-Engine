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
                    + Math.pow(a.position.y - b.position.y, 2)) <= a.r + b.r)) {

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

    public void resolveCollisions() {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) instanceof Polygon) ((Polygon) arr.get(i)).updateNormals();
        }
        for (int i = 0; i < arr.size(); i++) {
            Piston a = arr.get(i);
            // creates pairs parallel to the vector from polygon corners to the circle center
            if (a instanceof Circle) {
                for (int z = i + 1; z < arr.size(); z++) {
                    boolean flag = true;

                    if (arr.get(z) instanceof Circle) {
                        Circle c1 = (Circle) a;
                        Circle c2 = (Circle) arr.get(z);
                        Pair contactVect = c1.position.getCopy().getDifference(c2.position.getCopy());

                        double dist = c1.r+c2.r - contactVect.r();
                        if (!c1.ghost && !c2.ghost && dist >= 0){
                            collide(contactVect.convertUnit(),dist,c1,c2);
                        }

                        } else if (arr.get(z) instanceof Polygon) {
                            Polygon p = (Polygon) arr.get(z);
                            Circle c = (Circle) a;
                            ArrayList<Pair> normals = p.getNormals();
                            Pair[] points = p.pts;

                            double length = new Pair(c.position.x - p.position.x, c.position.y - p.position.y).r();
                            for (int q = 0; q < normals.size(); q++) {
                                Pair normal = normals.get(q);

                                double left = 0;
                                for (Pair pair : points) {
                                    double temp = pair.getCopy().getDifference(p.position).projOnTo(normal).r();
                                    //System.out.println(pair.projOnTo(normal).toString());
                                    if (temp > left) left = temp;
                                }
                                if (c.r + left < length) {
                                    flag = false;
                                }
                            }
                        }
                    }
                }
                // creates pairs normal to the sides of polygons
                if (a instanceof Polygon) {
                    Polygon pa = (Polygon) (a);
                    ArrayList<Pair> normals = pa.getNormals();
                    Pair[] points = pa.pts;
                    for (int j = i + 1; j < arr.size(); j++) {
                        if (!(arr.get(j) instanceof Polygon)) break;
                        Polygon b = (Polygon) arr.get(j);
                        ArrayList<Pair> normalsOther = b.getNormals();
                        normalsOther.addAll(normals);
                        Pair[] pointsOther = b.pts;
                        double length = new Pair(b.position.x - pa.position.x, b.position.y - pa.position.y).r();
                        boolean flag = true;
                        for (int q = 0; q < normalsOther.size(); q++) {
                            Pair normal = normalsOther.get(q);
                            double left = 0;
                            for (Pair pair : points) {
                                double temp = pair.getCopy().getDifference(pa.position).projOnTo(normal).r();
                                if (temp > left) left = temp;
                            }
                            double right = 0;
                            for (Pair pair : pointsOther) {
                                double temp = pair.getCopy().getDifference(b.position).projOnTo(normal).r();
                                if (temp > right) right = temp;
                            }
                            if (left + right < length) {
                                flag = false;
                            }

                        }
                        //System.out.println("Collision status:" + flag);
                    }
                }
            }
        }

    double elasticity = .9;
    public void collide (Pair normal, double depth, Piston a, Piston b){
        double ratioA = a.mass()/(a.mass()+b.mass());
        double ratioB = b.mass()/(a.mass()+b.mass());
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
