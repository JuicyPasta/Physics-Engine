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
    public void addPiston(){

    }
    public void removePiston(){

    }
    public void update(){
        for (Piston p: pistons){
            p.update();
        }
    }

}
