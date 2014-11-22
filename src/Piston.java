import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jackson on 11/13/14.
 */

//This is a generic engine object, all other objects should extend this
public abstract class Piston {
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

    //Physics phy;
    int id;
    public Piston (Pair position, Pair velocity, double rot, double vrot, int density){
        this (position, velocity, rot, vrot, density, false, false);
    }
    public Piston (Pair position, Pair velocity, double rot, double vrot, int density, boolean ghost, boolean showLine){
        this.position=position;
        this.velocity=velocity;
        this.rot=rot;
        this.vrot=vrot;

        this.density = density;

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
        addPair(velocity, acc); // thread safe
        acc.multiplyScalar(0);
        addPair(position, velocity); // thread safe
    }

    public abstract double mass();

    public abstract void draw(Graphics g);

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
