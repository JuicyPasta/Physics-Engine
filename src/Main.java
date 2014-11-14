import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Kyle on 11/13/2014.
 */
public class Main {
    public static int SIZE = 500;
    public static void main(String[] args) {
        ArrayList<Piston> arr = new ArrayList<Piston>();


        GraphicEngine ge = new GraphicEngine(arr);
        Engine e = new Engine(arr);
        Thread t1 = new Thread(ge);
        Thread t2 = new Thread(e);
        t1.start();
        t2.start();

        Piston p = new Piston(10,10,1,.5,0,0,0,1,new Circle());
        e.addPiston(p);
        ge.addPiston(p);
    }
}
