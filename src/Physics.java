import java.util.ArrayList;

/**
 * Created by Kyle on 11/13/2014.
 */
public class Physics{
    double GRAV_CONST = 1;
    ArrayList<Piston> arr; // we wont need this

    Physics(ArrayList<Piston> arr){
        this.arr = arr;
    }
    public Physics () {}

    // returns the acceration of gravity acting on a piston
    public Pair getGrav (Piston self, ArrayList <Piston> others){
        Pair acc = new Pair();
        for (Piston p : others){
            if (p.id != self.id) { // so we don't calculate the gravity to itself
                double distance = distance(self, p);
                double force = GRAV_CONST * self.mass() * p.mass() / (distance * distance);
                // Makes a pair that points toward the other piston
                Pair direction = new Pair (p.position.x-self.position.x,p.position.y-self.position.y);
                // Converts the pair into a unit pair
                direction.convertUnit();
                // Multiplies the unit by the force
                direction.multiplyScalar(force);
                // Converts the force into acceleration
                direction.divideScalar(self.mass());
                acc.basicAdd(direction);
            }
        }
        return acc;
    }
    public double distance(Piston a, Piston b){
        return Math.sqrt( Math.pow(a.position.x - b.position.x, 2) +  Math.pow(a.position.y - b.position.y, 2));
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
                if (arr.get(i).shape instanceof Circle && arr.get(j).shape instanceof Circle) {
                    Circle c1 = (Circle) arr.get(i).shape;
                    Circle c2 = (Circle) arr.get(j).shape;
                    if (Math.sqrt(Math.pow(arr.get(i).position.x - arr.get(j).position.x, 2) + Math.pow(arr.get(i).position.y - arr.get(j).position.y, 2)) < c1.r + c2.r) {
                        double tx = arr.get(i).velocity.x;
                        double ty = arr.get(i).velocity.y;
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
