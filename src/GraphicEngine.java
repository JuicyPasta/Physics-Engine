import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Kyle on 11/13/2014.
 */
public class GraphicEngine extends JFrame implements Runnable {

    BufferStrategy bs;
    public GraphicEngine(){

        setSize(500,500);
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
        g.setColor(Color.BLACK);
        g.drawRect(100,100,300,300);
        g.drawRect(99,99,302,302);//here
        g.setColor(Color.BLUE);
        g.fillRect(101,101,299,299);
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
