import java.awt.*;

/**
 * Created by Jackson on 11/13/14.
 */
public class Circle extends Piston {


    Circle(Pair position, Pair velocity, double rot, double vrot, int density, double r, boolean ghost, boolean showLine){
        super(position, velocity, rot, vrot, density, ghost, showLine);
        this.r = r;
    }
    Circle(Pair position, Pair velocity, double rot, double vrot, int density, double r){
        super(position, velocity, rot, vrot, density);
        this.r = r;
    }

    public double area(){
        return Math.PI * Math.pow(r,2);
    }
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval((int)(position.x-r),(int)(position.y-r),2*(int)r,2*(int)r);
        g.setColor(Color.WHITE);
        //g.fillOval((int)(position.x-r-1),(int)(position.y-r-1),2*(int)r-1,2*(int)r-1);
        if(showLine){
            g.setColor(Color.BLACK);

            Pair posCop = position.getCopy();
            Pair velCop = velocity.getCopy().multiplyScalar(10);
            Pair end = posCop.basicAdd(velCop);
            g.drawLine((int)(position.x),(int)(position.y),(int)end.x,(int)end.y);
        }
    }

    public double lengthToEdge(double angle) {
        return r;
    }

    public void update(){
        if ( position.x + velocity.x - r < 0 || position.x + velocity.x + 2 * lengthToEdge(0) >= Main.SIZE){
            velocity.x *= -1;
        }
        if (position.y + velocity.y - r < 0 || position.y + velocity.y + 2 * lengthToEdge(3 * Math.PI / 2) >= Main.SIZE){
            velocity.y *= -1;
        }

        super.update();

    }

    public double mass(){
        return area()*density;
    }
}
