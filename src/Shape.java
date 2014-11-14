import java.awt.*;
/**
 * Created by Jackson on 11/13/14.
 */

//Every piston has a shape, shapes is also designed to be a parent class
public abstract class Shape {
    public abstract double mass();
    public abstract void draw(Graphics g);
}
