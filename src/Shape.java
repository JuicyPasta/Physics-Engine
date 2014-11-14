import java.awt.*;
/**
 * Created by Jackson on 11/13/14.
 */

//Every piston has a shape, shapes is also designed to be a parent class
public abstract class Shape {
    double x,y;
    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
    }
    public abstract double area();
    public abstract void draw(Graphics g);

    public abstract double lengthToEdge(double angle);
}
