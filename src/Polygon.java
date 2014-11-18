import java.awt.*;

/**
 * Created by Jackson and Kyle on 11/13/14.
 * Starting with regular polygons with n-sides
 */
public class Polygon extends Piston {


    int sides;
    double sideLength;
    double radius;

    public Polygon(Pair position, Pair velocity, double rot, double vrot, boolean ghost, boolean showLine, int sides, double sideLength) {
        super(position, velocity, rot, vrot, 1, ghost, showLine);
        this.sides = sides;
        this.sideLength = sideLength;
        radius = sideLength/(2*Math.sin(Math.PI/sides));
    }

    //relevent formula:  Side = 2 × Radius × sin(π/n)
    //where radius is distance from center to vertex and n is num of sides
    //exterior angle = 2PI/numOfSides



    public void draw(Graphics g){
        int[] x = new int[sides];
        x[0] = (int) (position.x - radius);
        int[] y = new int[sides];
        y[0] = (int) (position.y - radius);
        double angle = Math.PI/2;

        for (int i = 1; i < sides; i++){
            x[i] = (int) (x[i-1] + sideLength*Math.cos(angle));
            y[i] = (int) (y[i-1] + sideLength*Math.sin(angle));
            angle += 2*Math.PI/sides;
        }
        g.drawPolygon(x,y,sides);
    }

    @Override
    public double mass() {
        return 0;//replace w area formula
    }

}
