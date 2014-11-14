import javax.swing.*;

/**
 * Created by Kyle on 11/13/2014.
 */
public class Main {
    public static void main(String[] args) {

        GraphicEngine ge = new GraphicEngine();
        Thread t = new Thread(ge);
        t.start();
    }
}
