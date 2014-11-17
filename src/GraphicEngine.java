import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Kyle on 11/13/2014.
 */
public class GraphicEngine extends JFrame implements Runnable {

    BufferStrategy bs;
    ArrayList<Piston> arr;

    public GraphicEngine(ArrayList<Piston> pistons){
        this.arr = pistons;
        setSize(Main.SIZE,Main.SIZE);
        setBackground(Color.WHITE);
        setTitle("GraphicV12");
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void paintUpdate(){
        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        paintNow(g);
        g.dispose();
        bs.show();
    }

    private synchronized void paintNow(Graphics g){
        //do all drawing here
        g.setColor(Color.WHITE);
        g.fillRect(0,0,Main.SIZE,Main.SIZE);
        g.setColor(Color.BLACK);
        for (int i = 0; i < arr.size(); i++){
            arr.get(i).draw(g);
        }
    }

    @Override
    public void run() {
        createBufferStrategy(2);
        bs = getBufferStrategy();

        while (true) {
            try {
                Thread.sleep(1000 / 60); // 1000/fps
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            paintUpdate();
        }
    }
}
