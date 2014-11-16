import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jackson on 11/13/14.
 */

//This is a generic engine object, all other objects should extend this
public class Piston {
    private double x,y; //TODO: ensure thread-safe
    Pair position;
    Pair velocity;
    double rot;
    double vrot;
    int density;
    Shape shape;
    ArrayList<Piston> others;
    Physics phy;
    int id;
    public Piston (Pair position, Pair velocity, double rot, double vrot, int density, Shape shape, ArrayList<Piston> others, Physics phy, int id){
        this.position=position;
        this.velocity=velocity;
        this.rot=rot;
        this.vrot=vrot;
        this.shape=shape;
        this.shape.setPos(position);
        this.others=others;
        this.density = density;
        this.phy=phy;
        this.id=id;

    }

    public synchronized Pair getPos() {
        return position;
    }

    // If possible synchronize the pair add method?
    private synchronized void addPair(Pair a,Pair b) {
        a.basicAdd(b);
    }

    public void update(){
        if (position.x + velocity.x - shape.lengthToEdge(Math.PI) < 0 || position.x + velocity.x + shape.lengthToEdge(0) >= Main.SIZE){
            velocity.x *= -1;
        }
        if (position.y + velocity.y - shape.lengthToEdge(Math.PI/2) < 0 || position.y + velocity.y + shape.lengthToEdge(3*Math.PI/2) >= Main.SIZE){
            velocity.y *= -1;
        }

        Pair gravAcc = phy.getGrav(this,others);
        System.out.println(id + " " + gravAcc);
        addPair(velocity, gravAcc); // thread safe
        addPair(position, velocity); // thread safe

    }

    public double mass(){
        return shape.area()*density;
    }
    public void draw(Graphics g){
        shape.setPos(position);
        shape.draw(g);
    }

    @Override
    public String toString() {
        return "Piston{" +
                "density=" + density +
                ", x=" + position.x +
                ", y=" + position.y +
                ", vx=" + velocity.x +
                ", vy=" + velocity.y +
                ", rot=" + rot +
                ", vrot=" + vrot +
                '}';
    }
}
