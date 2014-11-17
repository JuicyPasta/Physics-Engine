import java.awt.*;

/**
 * Created by Jackson on 11/13/14.
 */
public class Circle extends Piston {
    double r;

    Circle(Pair position, Pair velocity, double rot, double vrot, int density, double r, Physics phy, boolean ghost, boolean showLine){
        super(position, velocity, rot, vrot, density, phy, ghost, showLine);
        this.r = r;
    }
    Circle(Pair position, Pair velocity, double rot, double vrot, int density, double r, Physics phy){
        super(position, velocity, rot, vrot, density, phy);
        this.r = r;
    }

    public double area(){
        return Math.PI * Math.pow(r,2);
    }
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval((int)position.x,(int)position.y,2*(int)r,2*(int)r);
        if(showLine){
            Pair posCop = position.getCopy();
            Pair velCop = velocity.getCopy().multiplyScalar(100).addScalar(r);
            Pair end = posCop.basicAdd(velCop);
            g.drawLine((int)(position.x+r),(int)(position.y+r),(int)end.x,(int)end.y);
        }
    }

    public double lengthToEdge(double angle) {
        return r;
    }

    public void update(){
        if (position.x + velocity.x < 0 || position.x + velocity.x + 2 * lengthToEdge(0) >= Main.SIZE){
            velocity.x *= -1;
        }
        if (position.y + velocity.y < 0 || position.y + velocity.y + 2 * lengthToEdge(3 * Math.PI / 2) >= Main.SIZE){
            velocity.y *= -1;
        }

        super.update();

    }

    public double mass(){
        return area()*density;
    }
}
