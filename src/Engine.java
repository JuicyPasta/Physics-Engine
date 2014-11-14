import java.awt.*;

/**
 * Created by Jackson on 11/13/14.
 */

import java.util.*;
public class Engine {
    ArrayList <Piston> pistons;
    Physics phys;
    public Engine(){
        this.pistons = new ArrayList <Piston> ();
        this.phys = new Physics();
    }
    public void addPiston(Piston p) {
        p.bindEngine(this);
        pistons.add(p);
    }
    public void update(){
        for (Piston p: pistons){
            p.update();
        }
    }
    public void draw(Graphics g){
        for (Piston p: pistons){
            p.draw(g);
        }
    }

}
