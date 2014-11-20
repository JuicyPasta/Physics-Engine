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
        for (int i = 0; i < Main.SIZE; i+=10){
            for (int j = 0; j < Main.SIZE; j+=10){
                Pair force = new Pair(0,0);
                for (int q = 0; q < arr.size(); q++) {
                    Pair temp = new Pair(i-15, j-15).getDifference(arr.get(q).position);
                    double magnitude = GRAV_CONST * arr.get(q).mass() / Math.pow(temp.r(), 2);
                    force.basicAdd(temp.multiplyScalar(magnitude));
                }
                double var1 = 5*Math.cos(force.theta());
                double var2 = 5*Math.sin(force.theta());
                g.drawLine((int)(i - var1),(int)( j - var2),(int)(i + var1),(int)( j + var2));
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
                for (Piston p : arr){
                    if (p instanceof Polygon){
                        Polygon pa = (Polygon)(p);
                        ArrayList <Pair> lines = new ArrayList <Pair> ();
                        for (int q = 0; q < pa.pts.length; q ++){
                            lines.add(a.position.getCopy().getDifference(pa.pts[q]).convertUnit());
                        }
                        //code
                    }
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
                    System.out.println("Collision status:" + flag);
                }
            }
        }
    }
}
