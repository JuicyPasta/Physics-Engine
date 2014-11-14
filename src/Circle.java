import java.awt.*;

/**
 * Created by Jackson on 11/13/14.
 */
public class Circle extends Shape {
    int r;

    Circle(int x, int y){
        super(x,y);
        r = (int) (1+Math.random()*10);
    }

    Circle(int x, int y, int r) {
        super(x,y);
        this.r = r;
    }

    @Override
    public double area(){
        return Math.PI * Math.pow(r,2);
    }
    public void draw(Graphics g) {
        g.drawOval(x, y, r, r);
    }

    @Override
    public double lengthToEdge(double angle) {
        return r;
    }
}
