import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Kyle on 11/13/2014.
 */
public class Main {
    public static int SIZE = 500;
    static GraphicEngine ge;
    static Engine e;
    static Physics phy;
    public static void main(String[] args) {
        ArrayList<Piston> arr = new ArrayList<Piston>();
        phy = new Physics();
        Piston p = new Piston(new Pair(100,100),new Pair(0,0),0,1,1,new Circle(15),arr,phy,0);
        Piston p1 = new Piston(new Pair(201,100),new Pair(0,0),0,1,1,new Circle(15),arr,phy,1);
        //Piston p2 = new Piston(new Pair (201,150),new Pair(.4,.1),0,1,1,new Circle(25),arr,phy,2);

        arr.add(p);
        arr.add(p1);
        //arr.add(p2);

        ge = new GraphicEngine(arr);
        e = new Engine(arr);
        Thread t1 = new Thread(ge);
        Thread t2 = new Thread(e);
        t1.start();
        t2.start();

    }

}
