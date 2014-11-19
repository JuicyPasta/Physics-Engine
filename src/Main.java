import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kyle on 11/13/2014.
 *
 *
 * Notes section:
 *
 * we need to adjust code so piston.position = center of object
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
        ArrayList<Piston> arr = new ArrayList<Piston>();

        HashMap<Pair,Piston> map = new HashMap<Pair, Piston>();
        //or
        Piston[][] arrList;
        arrList = new Piston[SIZE][SIZE]; // cast each position to int and put it in here

        phy = new Physics();
        //Piston p = new Circle(new Pair(200,200),new Pair(0,0),0,0,1,15,false,true);
        Piston p1 = new Circle(new Pair(200,300),new Pair(0,0),0,0,1,15,false,true);
        Piston p3 = new Polygon(new Pair(400,300), new Pair(0,0),0,0,true,false,5,55);
        //Piston p2 = new Piston(new Pair (201,150),new Pair(.4,.1),0,1,1,new Circle(25),arr,phy);

        //arr.add(p);
        arr.add(p1);
        arr.add(p3);
        //arr.add(p2);

        ge = new GraphicEngine(arr);
        e = new Engine(arr);
        MouseListener mouseListener = new MasterListener(e,ge,arr,phy);
        ge.addMouseListener(mouseListener);
        Thread t1 = new Thread(ge);
        Thread t2 = new Thread(e);
        t1.start();
        t2.start();

    }

}
