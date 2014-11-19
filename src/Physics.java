import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kyle on 11/13/2014.
 */
public class Physics{
    double GRAV_CONST = 5;
    ArrayList<Piston> arr; // we wont need this

    Physics(ArrayList<Piston> arr){
        this.arr = arr;
    }
    public Physics () {}

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

    public Pair resolveCollisions(){

        Set<Pair> lines = new HashSet<Pair>();
        for (int i = 0; i < arr.size(); i++){
            Piston a = arr.get(i);

            // creates pairs parallel to the vector from polygon corners to the circle center
            if (a instanceof Circle){
                for (Piston p : arr){
                    if (p instanceof Polygon){
                        Polygon pa = (Polygon)(p);
                        for (int q = 0; q < pa.pts.length; q ++){
                            lines.add(pa.pts[q].getCopy().getDifference(a.position));
                        }
                    }
                }
            }
            // creates pairs normal to the sides of polygons
            if (a instanceof Polygon){
                Polygon pa = (Polygon)(a);
                for (int q = 0; q < pa.pts.length; q ++){
                    lines.add(pa.pts[(i+1)%pa.pts.length].getCopy().getDifference(pa.pts[i]).getNorm());
                }
            }
            // switches the places of the two pistons to avoid redundant code, will be triggered by next if

            // done populating lines

            //project the points of the two pistons onto a line


        }
        System.out.println("~~~~~~~");
        for (Pair pr : lines){
            System.out.println(pr.toString());
        }
        return new Pair (0,0);

    }

    // returns the intersection depth along the projection vector
    public double handleProjection (Pair norm, Piston a, Piston b){
        Pair mma;
        Pair mmb;
        if (a instanceof Circle){
            mma = handleProjCirc (norm, (Circle)a);
        }else{
            mma = handleProjPoly (norm, (Polygon)a);
        }
        if (b instanceof Circle){
            mmb = handleProjCirc (norm, (Circle)b);
        }else{
            mmb = handleProjPoly (norm, (Polygon)b);
        }
        // x is max, y is min
        return Math.abs(Math.min(mma.x - mmb.y, mmb.x - mma.y));
    }

    public Pair handleProjCirc (Pair norm, Circle a){
        return new Pair (a.r, -a.r);
    }
    public Pair handleProjPoly (Pair norm, Polygon b){
        double min = Double.NaN;
        double max = Double.NaN;
        for (int i = 0; i < b.pts.length; i ++){
            Pair origin = b.pts[(i+1)%b.pts.length].getCopy().getDifference(b.pts[i]).convertUnit();
            double x = norm.getCopy().getProjX(origin);
            if (max == Double.NaN || x > max){
                max = x;
            }
            if (min == Double.NaN || x < min) {
                min = x;
            }
        }
        return new Pair (max,min);
    }
    public void update(){
        for (int i = 0; i < arr.size(); i++){
            for (int j = i + 1; j < arr.size(); j++){
                if ((!arr.get(i).ghost && !arr.get(j).ghost) && arr.get(i) instanceof Circle && arr.get(j) instanceof Circle) {
                    Circle a = (Circle) arr.get(i);
                    Circle b = (Circle) arr.get(j);
                    Pair diffPair = a.position.getCopy().getDifference(b.position);
                    if (Math.pow(a.r+b.r,2)>= diffPair.x*diffPair.x + diffPair.y*diffPair.y){


                        //correct formula is new v1 = (v1*(m1-m2) + 2 *m2*v2)/(m1 + m2)
                        //its not working properly
                        //TODO: Fix bouncing

//                        Pair temp = arr.get(i).velocity.convertUnit();
//                        double speed1 = (arr.get(i).velocity.r()*(arr.get(i).mass() - arr.get(j).mass())
//                                + (arr.get(j).velocity.r()*(arr.get(j).mass()*2)))
//                                /(arr.get(i).mass() + arr.get(j).mass());
//
//                        arr.get(i).velocity = arr.get(j).velocity.convertUnit();
//                        double speed2 = (arr.get(j).velocity.r()*(arr.get(j).mass() - arr.get(i).mass())
//                                + (arr.get(i).velocity.r()*(arr.get(i).mass()*2)))
//                                /(arr.get(i).mass() + arr.get(j).mass());
//
//                        arr.get(j).velocity = temp;
//                        arr.get(i).velocity.multiplyScalar(speed1);
//                        arr.get(j).velocity.multiplyScalar(speed2);


                        //assumes they have the same mass
                        double tx = arr.get(i).velocity.x;
                        double ty = arr.get(i).velocity.y;

                        // *.9 is friction/ energy lost
                        arr.get(i).velocity.x = arr.get(j).velocity.x;
                        arr.get(i).velocity.y = arr.get(j).velocity.y;
                        arr.get(j).velocity.x = tx;
                        arr.get(j).velocity.y = ty;
                    }
                }
            }
        }
        //for (int i = 0; i < arr.size(); i++) arr.get(i).update();
    }

}
