import java.awt.*;

/**
 * Created by Jackson and Kyle on 11/13/14.
 * Starting with regular polygons with n-sides
 */
public class Polygon extends Piston {


    int sides;
    double sideLength;

    public Polygon(Pair position, Pair velocity, double rot, double vrot, boolean ghost, boolean showLine, int sides, double sideLength) {
        super(position, velocity, rot, vrot, 1, ghost, showLine);
        this.sides = sides;
        this.sideLength = sideLength;
    }

    //relevent formula:  Side = 2 × Radius × sin(π/n)
    //where radius is distance from center to vertex and n is num of sides
    //exterior angle = 360/numOfSides



    public void draw(Graphics g){

    }

    @Override
    public double mass() {
        return 0;
    }

}
