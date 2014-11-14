import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Kyle on 11/13/2014.
 */
public class GraphicEngine extends JFrame implements Runnable {

    BufferStrategy bs;
    final int SIZE = 500;
    BallManager bm;

    public GraphicEngine(){

        bm = new BallManager(SIZE,10);
        setSize(SIZE,SIZE);
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
        paintNow(g);//
        g.dispose();
        bs.show();
    }

    private void paintNow(Graphics g){
        //do all drawing here
        g.setColor(Color.WHITE);
        g.fillRect(0,0,SIZE,SIZE);
        g.setColor(Color.BLACK);
        bm.draw(g);
    }

    @Override
    public void run() {
        createBufferStrategy(2);
        bs = getBufferStrategy();

        while (true) {
            bm.update();
            try {
                Thread.sleep(1000 / 60); // 1000/fps
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            paintUpdate();
            bm.update();
        }
    }
}
