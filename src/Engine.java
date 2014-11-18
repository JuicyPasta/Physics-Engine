import java.awt.*;

/**
 * Created by Jackson on 11/13/14.
 */

import java.util.*;
public class Engine implements Runnable{
    ArrayList <Piston> pistons;
    Physics phys;
    public Engine(ArrayList<Piston> pistons){
        this.pistons = pistons;
        this.phys = new Physics(pistons);
    }

    public synchronized void update(){
        phys.update();
        phys.updateGrav(pistons);
        for (Piston p: pistons){
            p.update();
        }
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        while (true){
            try {
                //1000/60 - time it took to complete last cycle
                long diff = System.currentTimeMillis() - lastTime;
                Thread.sleep(1000/60-diff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastTime = System.currentTimeMillis();
            update();
        }
    }
}
