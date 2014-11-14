import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Kyle on 11/13/2014.
 */
public class Main {
    public static int SIZE = 500;
    public static void main(String[] args) {
        ArrayList<Piston> arr = new ArrayList<Piston>();


        GraphicEngine ge = new GraphicEngine();
        Engine e = new Engine();
        Thread t1 = new Thread(ge);
        Thread t2 = new Thread(e);
        t1.start();
        t2.start();

        Piston p = new Piston(100,100,1,1,0,0,0,1,new Circle());
        e.addPiston(p);
        ge.addPiston(p);
    }
}
