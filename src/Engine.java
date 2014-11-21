import java.awt.*;

/**
 * Created by Jackson on 11/13/14.
 */

import java.util.*;
public class Engine implements Runnable{
    ArrayList <Piston> pistons;
    Physics phys;
    boolean gravityOn;
    public Engine(ArrayList<Piston> pistons){
        this.pistons = pistons;
        this.phys = new Physics(pistons);
        gravityOn = false;
    }

    public synchronized void update(){
        phys.resolveCollisions();
        if (gravityOn) {
            phys.updateGrav(pistons);
        }
        for (Piston p: pistons){
            p.update();
        }
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        while (true){
            try {

                //TODO: better way to do this is to define speed as a pixels/ms and pass in (msSinceLastPass), that way you get max refresh rate

                //1000/60 - time it took to complete last cycle
                long diff = System.currentTimeMillis() - lastTime;
                Thread.sleep(1000/60); //catch negative
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastTime = System.currentTimeMillis();
            update();
        }
    }
}
