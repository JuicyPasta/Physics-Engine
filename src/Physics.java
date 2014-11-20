import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Physics{
    double GRAV_CONST = 5;
    ArrayList<Piston> arr; // we wont need this

    Physics(ArrayList<Piston> arr){
        this.arr = arr;
    }

    public void updateGrav (ArrayList <Piston> pistons){
        for (int i = 0; i < pistons.size(); i++)
            for (int j = i +1; j < pistons.size();j++) {
                Circle a = (Circle)arr.get(i);
                Circle b = (Circle)arr.get(j);
                if ((!a.ghost && !b.ghost) && !(Math.sqrt(Math.pow(a.position.x - b.position.x + a.r - b.r, 2)
                        + Math.pow(arr.get(i).position.y - arr.get(j).position.y + a.r - b.r, 2)) < a.r + b.r)) {
                    double distance = distance(pistons.get(i), pistons.get(j));
                    double force = GRAV_CONST * pistons.get(i).mass() * pistons.get(j).mass() / (distance * distance);
                    // Makes a pair that points toward the other piston
                    Pair direction = new Pair(pistons.get(j).position.x - pistons.get(i).position.x, pistons.get(j).position.y
                            - pistons.get(i).position.y);
                    // Converts the pair into a unit pair
                    direction.convertUnit();
                    // Multiplies the unit by the force
                    direction.multiplyScalar(force);
                    Pair dir2 = new Pair(-direction.x, -direction.y);
                    // Converts the force into acceleration
                    direction.divideScalar(pistons.get(i).mass());
                    dir2.divideScalar(pistons.get(j).mass());

                    pistons.get(i).acc.basicAdd(direction);
                    pistons.get(j).acc.basicAdd(dir2);
            }
        }
    }

    public void drawGrav(Graphics g){
        for (int i = 0; i < Main.SIZE; i+=4){
            for (int j = 0; j < Main.SIZE; j+=4){
                Pair force = new Pair(0,0);
                for (int q = 0; q < arr.size(); q++) {
                    Pair temp = new Pair(i, j).getDifference(arr.get(q).position);
                    double magnitude = GRAV_CONST * arr.get(q).mass() / Math.pow(temp.r(), 2);
                    force.basicAdd(temp.multiplyScalar(magnitude));
                }
                double var1 = 2*Math.cos(force.theta());
                double var2 = 2*Math.sin(force.theta());
                int color = 255- (int) (force.r());
                if (color < 0) color = 0;
                g.setColor(new Color(color,color,color));
                g.fillRect((int)(i - 2),(int)( j - 2),4,4);
                g.setColor(Color.BLACK);
                g.drawLine((int)(i - var2),(int)( j - var1),(int)(i + var2),(int)( j + var1));
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
        for (int i = 0; i < arr.size(); i++){
            Piston a = arr.get(i);
            // creates pairs parallel to the vector from polygon corners to the circle center
            if (a instanceof Circle){
                for (int z = i + 1; z < arr.size(); z++){
                    boolean flag = true;

                    if (arr.get(z) instanceof Circle) {
                        Circle c1 = (Circle) a;
                        Circle c2 = (Circle) arr.get(z);
                        Pair contactVect = c1.position.getCopy().getDifference(c2.position.getCopy());
                        double dist = c1.r+c2.r - contactVect.r();
                        if (!c1.ghost && !c2.ghost && dist >= 0){
                            collide(contactVect.convertUnit(),dist,c1,c2);
                        }

                    }else if (arr.get(z) instanceof Polygon){
                        Polygon p = (Polygon) arr.get(z);
                        Circle c = (Circle) a;
                        ArrayList <Pair> normals = p.getNormals();
                        Pair[] points = p.pts;

                        double length = new Pair(c.position.x-p.position.x,c.position.y - p.position.y).r();
                        for (int q = 0; q < normals.size(); q++){
                            Pair normal = normals.get(q);

                            double left = 0;
                            for (Pair pair : points){
                                double temp = pair.getCopy().getDifference(p.position).projOnTo(normal).r();
                                //System.out.println(pair.projOnTo(normal).toString());
                                if (temp > left) left = temp;
                            }
                            if ( c.r + left < length ){
                                flag = false;
                            }
                        }

                    }
                    //System.out.println("Circle-Poly status: " + flag);


                }

            }
            // creates pairs normal to the sides of polygons
            if (a instanceof Polygon){
                Polygon pa = (Polygon)(a);
                ArrayList <Pair> normals = pa.getNormals();
                Pair[] points = pa.pts;
                for (int j = i + 1; j < arr.size(); j++){
                    if (!(arr.get(j) instanceof Polygon)) break;
                    Polygon b = (Polygon) arr.get(j);
                    ArrayList<Pair> normalsOther = b.getNormals();
                    normalsOther.addAll(normals);
                    Pair[] pointsOther = b.pts;
                    double length = new Pair(b.position.x-pa.position.x,b.position.y - pa.position.y).r();
                    boolean flag = true;
                    for (int q = 0; q < normalsOther.size(); q++){
                        Pair normal = normalsOther.get(q);
                        double left = 0;
                        for (Pair pair : points){
                            double temp = pair.getCopy().getDifference(pa.position).projOnTo(normal).r();
                            //System.out.println(pair.projOnTo(normal).toString());
                            if (temp > left) left = temp;
                        }
                        double right = 0;
                        for (Pair pair : pointsOther){
                            double temp = pair.getCopy().getDifference(b.position).projOnTo(normal).r();
                            if (temp > right) right = temp;
                        }
                        //System.out.println("left " + left + " right " + right + " length " + length + " normal " + normal);
                        if ( left + right < length ){
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
        Pair relativeVelocity = a.velocity.getCopy().getDifference(b.velocity);
        double cosine = relativeVelocity.dotProduct(normal);
        if(cosine > 0)
            return;
        cosine *= -elasticity;
        double j = (-(1+elasticity) * cosine) / (1/a.mass() + 1/b.mass());
        Pair impulse = normal.getCopy().multiplyScalar(j);
        a.velocity = a.velocity.add(impulse.getCopy().divideScalar(a.mass()));
        b.velocity = b.velocity.subtract(impulse.getCopy().divideScalar(b.mass()));
        // calculate what percent of the radius half the pen depth represents
        // multilply the percent by r and then multiply that by the normal
        // back them out by the value you found in ^^

    }
}
