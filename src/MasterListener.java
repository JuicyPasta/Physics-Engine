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


    Pair mouseStart;
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
                    mouseStart = new Pair(e.getX()-15,e.getY()-15);
                    arr.add(new Piston(mouseStart, new Pair (0,0), 0, 0, 1, new Circle(15), physics, true, true));

                    state = 1;
                    break;
//                case 2:
//                    r = (int) (Math.sqrt( Math.pow(startX - e.getX(), 2) +  Math.pow(startY - e.getY(), 2))/2);
//                    if (r > 0){
//                        state = 2;
//                    }
//                    break;

            }
        }

    }




    @Override
    public void mouseReleased(MouseEvent e) {
        switch (state){
            case 1:
                Pair difference = mouseStart.getCopy();
                difference.getDifference(new Pair(e.getX()-15,e.getY()-15)).divideScalar(500);
                // I will never know why this works
                arr.add(new Piston(mouseStart, difference, 0, 0, 1, new Circle(15), physics));

                state = 0;
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
