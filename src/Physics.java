import java.util.ArrayList;

/**
 * Created by Kyle on 11/13/2014.
 */
public class Physics{
    double GRAV_CONST = .005;
    ArrayList<Piston> arr; // we wont need this

    Physics(ArrayList<Piston> arr){
        this.arr = arr;
    }
    public Physics () {}

    public void updateGrav (ArrayList <Piston> pistons){
        for (int i = 0; i < pistons.size(); i++)
            for (int j = i +1; j < pistons.size();j++) {
                Circle c1 = (Circle) arr.get(i).shape;
                Circle c2 = (Circle) arr.get(j).shape;
                if ((!arr.get(i).ghost && !arr.get(j).ghost) && !(Math.sqrt(Math.pow(arr.get(i).position.x - arr.get(j).position.x + c1.r - c2.r, 2)
                        + Math.pow(arr.get(i).position.y - arr.get(j).position.y + c1.r - c2.r, 2)) < c1.r + c2.r)) {
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

    public void update(){
        for (int i = 0; i < arr.size(); i++){
            for (int j = i + 1; j < arr.size(); j++){
                if ((!arr.get(i).ghost && !arr.get(j).ghost) && arr.get(i).shape instanceof Circle && arr.get(j).shape instanceof Circle) {
                    Circle c1 = (Circle) arr.get(i).shape;
                    Circle c2 = (Circle) arr.get(j).shape;
                    if (Math.sqrt(Math.pow(arr.get(i).position.x - arr.get(j).position.x + c1.r - c2.r, 2)
                            + Math.pow(arr.get(i).position.y - arr.get(j).position.y + c1.r - c2.r, 2)) < c1.r + c2.r) {


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
                        arr.get(i).velocity.x = arr.get(j).velocity.x*.9;
                        arr.get(i).velocity.y = arr.get(j).velocity.y*.9;
                        arr.get(j).velocity.x = tx*.9;
                        arr.get(j).velocity.y = ty*.9;
                    }
                }
            }
        }
        //for (int i = 0; i < arr.size(); i++) arr.get(i).update();
    }

}
