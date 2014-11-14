/**
 * Created by Jackson on 11/13/14.
 */

import java.util.*;
public class Engine {
    ArrayList <Piston> pistons = new ArrayList <Piston> ();
    public Engine(){
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
