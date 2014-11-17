import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jackson on 11/13/14.
 */

//This is a generic engine object, all other objects should extend this
public class Piston {
    private double x,y; //left top corner
    Pair position;
    Pair velocity;
    Pair acc;
    double rot;
    double vrot;
    int density;
    Shape shape;
    boolean ghost;
    boolean showLine;
    //ArrayList<Piston> others;
    Physics phy;
    int id;
    public Piston (Pair position, Pair velocity, double rot, double vrot, int density, Physics phy){
        this (position, velocity, rot, vrot, density, phy, false, false);
    }
    public Piston (Pair position, Pair velocity, double rot, double vrot, int density, Physics phy, boolean ghost, boolean showLine){
        this.position=position;
        this.velocity=velocity;
        this.rot=rot;
        this.vrot=vrot;
        //this.others=others;
        this.density = density;
        this.phy=phy;
        this.id=hashCode();
        acc = new Pair(0,0);
        this.ghost = ghost;
        this.showLine = showLine;

    }

    public synchronized Pair getPos() {
        return position;
    }

    // If possible synchronize the pair add method?
    private synchronized void addPair(Pair a,Pair b) {
        a.basicAdd(b);
    }

    public void update(){
        //Pair gravAcc = phy.getGrav(this,others);
        //System.out.println(id + " " + gravAcc);
        //addPair(velocity, gravAcc); // thread safe
        addPair(velocity, acc); // thread safe
        acc.multiplyScalar(0);
        addPair(position, velocity); // thread safe

    }

    public double mass(){
        return density;
    }
    public void draw(Graphics g){

    }

    public void switchGhost(){
        ghost = !ghost;
    }
    public void switchShowLine(){
        showLine = !showLine;
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
