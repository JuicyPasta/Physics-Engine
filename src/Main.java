import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kyle on 11/13/2014.
 *
 *
 * Notes section:
 *
 * TODO: thread safe array conversion
 * friction?
 * removing pistons - need to switch array to hashmap/2d array of Pair object (neighbors)
 * polygons, hows to
 */
public class Main {
    public static int SIZE = 800;
    static GraphicEngine ge;
    static Engine e;
    static Physics phy;
    public static void main(String[] args) {
        ArrayList<Piston> arr = new SafeArrayList<Piston>();



        phy = new Physics(arr);
        Piston p = new Circle(new Pair(380,200),new Pair(0,0),0,0,1,15,false,true);
        Piston earth = new Circle(new Pair(400,2000000),new Pair(0,0),0,0,10000,15,false,true);
        Piston p1 = new Circle(new Pair(200,310),new Pair(4,0),0,0,1,15,false,true);
        //Piston p3 = new Polygon(new Pair(410,300), new Pair(0,0),1.7,0,true,false,4,50);
        //Piston p2 = new Piston(new Pair (201,150),new Pair(.4,.1),0,1,1,new Circle(25),arr,phy);
        Piston p4 = new Polygon(new Pair(380,340), new Pair(0,0),0,0,true,false,4,50);
        arr.add(p);
        //arr.add(p1);
        //arr.add(p3);
        //arr.add(p2);
        //arr.add(p4);
        //arr.add(earth);

        ge = new GraphicEngine(arr,phy);
        e = new Engine(arr);

        // this seems jank, fix later
        MouseListener mouseListener = new MasterListener(e,ge,arr,phy);
        KeyListener keyListener = new MasterListener(e,ge,arr,phy);

        ge.addMouseListener(mouseListener);
        ge.addKeyListener(keyListener);
        Thread t1 = new Thread(ge);
        Thread t2 = new Thread(e);
        t1.start();
        t2.start();

    }

}
