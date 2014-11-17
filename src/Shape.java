import java.awt.*;
/**
 * Created by Jackson on 11/13/14.
 */

//Every piston has a shape, shapes is also designed to be a parent class
public abstract class Shape {
    Pair position;
    boolean showLine;
    public void setPos(Pair position){
        this.position = position;
    }
    public abstract double area();
    public abstract void draw(Graphics g);

    public abstract double lengthToEdge(double angle);
}
