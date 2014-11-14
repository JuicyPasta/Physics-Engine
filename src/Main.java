import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Kyle on 11/13/2014.
 */
public class Main {
    public static int SIZE = 500;
    static GraphicEngine ge;
    static Engine e;
    public static void main(String[] args) {
        ArrayList<Piston> arr = new ArrayList<Piston>();

        Piston p = new Piston(arr,100,100,.2,.5,0,0,0,1,new Circle(15));
        Piston p1 = new Piston(arr,201,100,-.1,.3,0,0,0,1,new Circle(15));
        Piston p2 = new Piston(arr,201,150,.4,.1,0,0,0,1,new Circle(25));

        arr.add(p);
        arr.add(p1);
        arr.add(p2);

        ge = new GraphicEngine(arr);
        e = new Engine(arr);
        Thread t1 = new Thread(ge);
        Thread t2 = new Thread(e);
        t1.start();
        t2.start();

    }

}
