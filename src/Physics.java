import java.util.ArrayList;

/**
 * Created by Kyle on 11/13/2014.
 */
public class Physics{
    double GRAV_CONST = 1;
    ArrayList<Piston> arr;

    Physics(ArrayList<Piston> arr){
        this.arr = arr;
    }

    public void gravityUpdate(){
        for (int i = 0; i < arr.size(); i++){
            for (int j = i + 1; j < arr.size(); j++) {
                double force = GRAV_CONST * arr.get(i).mass() * arr.get(j).mass() / distance(arr.get(i), arr.get(j));
                double angle = angle(arr.get(i),arr.get(j));
                applyForce(arr.get(i), force, angle);
                applyForce(arr.get(j), force, angle + Math.PI);
            }
        }
    }
    public double distance(Piston a, Piston b){
        return Math.sqrt( Math.pow(a.getX() - b.getX(), 2) +  Math.pow(a.getY() - b.getY(), 2));
    }


    public double angle(Piston a, Piston b){
        return Math.atan((a.getY() - b.getY())/(a.getX() - b.getX()));
    }

    public void applyForce(Piston b, double force, double angle){
        b.ax += force * Math.cos(angle) / b.mass();
        b.ay += force * Math.sin(angle) / b.mass();
    }
    public void update(){
        for (int i = 0; i < arr.size(); i++){
            for (int j = i + 1; j < arr.size(); j++){
                if (arr.get(i).shape instanceof Circle && arr.get(j).shape instanceof Circle) {
                    Circle c1 = (Circle) arr.get(i).shape;
                    Circle c2 = (Circle) arr.get(j).shape;
                    if (Math.sqrt(Math.pow(arr.get(i).getX() - arr.get(j).getX(), 2) + Math.pow(arr.get(i).getY() - arr.get(j).getY(), 2)) < c1.r + c2.r) {
                        double tx = arr.get(i).vx;
                        double ty = arr.get(i).vy;
                        arr.get(i).vx = arr.get(j).vx;
                        arr.get(i).vy = arr.get(j).vy;
                        arr.get(j).vx = tx;
                        arr.get(j).vy = ty;
                    }
                }
            }
        }
        //for (int i = 0; i < arr.size(); i++) arr.get(i).update();
    }
}
