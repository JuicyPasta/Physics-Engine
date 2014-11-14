/**
 * Created by Kyle on 11/13/2014.
 */
public class Physics{
    static double GRAV_CONST = 1;

    public static void gravityUpdate(Piston[] arr){
        for (int i = 0; i < arr.length; i++){
            for (int j = i + 1; j < arr.length; j++) {
                double force = GRAV_CONST * arr[i].mass() * arr[j].mass() / distance(arr[i], arr[j]);
                double angle = angle(arr[i],arr[j]);
                applyForce(arr[i], force, angle);
                applyForce(arr[j], force, angle + Math.PI);
            }
        }
    }
    public static double distance(Piston a, Piston b){
        return Math.sqrt( Math.pow(a.x - b.x, 2) +  Math.pow(a.y - b.y, 2));
    }


    public static double angle(Piston a, Piston b){
        return Math.atan((a.y - b.y)/(a.x - b.x));
    }

    public static void applyForce(Piston b, double force, double angle){
        b.ax += force * Math.cos(angle) / b.mass();
        b.ay += force * Math.sin(angle) / b.mass();
    }
}
