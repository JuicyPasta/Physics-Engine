import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Kyle on 11/16/2014.
 */
public class MasterListener implements KeyListener, MouseListener {
    Engine e;
    GraphicEngine g;
    ArrayList<Piston> arr;
    Physics physics;

    public MasterListener(Engine e, GraphicEngine g, ArrayList<Piston> arr, Physics physics) {
        this.e = e;
        this.g = g;
        this.arr = arr;
        this.physics = physics;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // toggle gravity lines
        if (e.getKeyChar() == '1'){
            this.g.graphicLinesOn = !this.g.graphicLinesOn;
        }
        // toggle gravity
        if (e.getKeyChar() == '2'){
            this.e.gravityOn = !this.e.gravityOn;
        }
        // toggles velocity lines
        if (e.getKeyChar() == '3'){

        }
        if (e.getKeyChar() == 's'){
            for (Piston p : arr){
                p.velocity.x = 0;
                p.velocity.y = 0;
            }
        }
        if (e.getKeyChar() == 'l'){
            for (Piston p : arr){
                p.showLine=!p.showLine;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    Pair mouseStart;
    int r;
    int state = 0;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    long time = System.currentTimeMillis();
    double radius = 15;

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON2){
            //remove item
        }

        if (time + 100 < System.currentTimeMillis() && e.getButton() == MouseEvent.BUTTON1){
            time = System.currentTimeMillis();
            switch (state){
                case 0:
                    mouseStart = new Pair(e.getX(),e.getY());
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        arr.add(new Circle(mouseStart, new Pair (0,0), 0, 0, 1, radius, true, true));
                    }
                    if (e.getButton() == MouseEvent.BUTTON3){
                        arr.add(new Circle(mouseStart, new Pair (0,0), 0, 0, 1, radius*2, true, true));
                    }
                    state = 1;
                    break;

            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (state){
            case 1:
                Pair difference = mouseStart.getCopy();
                difference.getDifference(new Pair(e.getX(),e.getY())).divideScalar(50);

                arr.remove(arr.size()-1);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    arr.add(new Circle(mouseStart, difference, 0, 0, 1, radius, false, true));
                }
                if (e.getButton() == MouseEvent.BUTTON3){
                    arr.add(new Circle(mouseStart, difference, 0, 0, 1, radius*2, false, true));
                }
                state = 0;
                this.e.refreshCouples();
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
