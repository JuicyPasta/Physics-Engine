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

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    double startX, startY;
    int r;
    int state = 0;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    long time = System.currentTimeMillis();
    @Override
    public void mousePressed(MouseEvent e) {

        if (time + 100 < System.currentTimeMillis() && e.getButton() == MouseEvent.BUTTON1){
            time = System.currentTimeMillis();
            switch (state){
                case 0:
                    startX = e.getX()-15;
                    startY = e.getY()-15;
                    state = 2;
                    break;
                case 1:
                    r = (int) (Math.sqrt( Math.pow(startX - e.getX(), 2) +  Math.pow(startY - e.getY(), 2))/2);
                    if (r > 0){
                        state = 2;
                    }
                    break;
                case 2:
                    arr.add(new Piston(new Pair(startX,startY),new Pair(( e.getX()-startX)/100, ( e.getY()-startY )/100),
                            0,0,1, new Circle(15),physics));
                    state = 0;
                    break;
            }
        }

    }




    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
