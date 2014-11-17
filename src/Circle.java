import java.awt.*;

/**
 * Created by Jackson on 11/13/14.
 */
public class Circle extends Shape {
    double r;

    Circle(){

        r = (int) (1+Math.random()*10);
    }

    Circle(double r) {
        this.r = r;
    }

    @Override
    public double area(){
        return Math.PI * Math.pow(r,2);
    }
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval((int)position.x,(int)position.y,2*(int)r,2*(int)r);
    }

    @Override
    public double lengthToEdge(double angle) {
        return r;
    }
}
