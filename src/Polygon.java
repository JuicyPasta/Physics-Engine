import java.awt.*;

/**
 * Created by Jackson and Kyle on 11/13/14.
 * Starting with regular polygons with n-sides
 */
public class Polygon extends Piston {

    int sides;
    double sideLength;
    double radius;
    int[] x;
    int[] y;

    public Polygon(Pair position, Pair velocity, double rot, double vrot, boolean ghost, boolean showLine, int sides, double sideLength) {
        super(position, velocity, rot, vrot, 1, ghost, showLine);
        this.sides = sides;
        this.sideLength = sideLength;
        radius = sideLength/(2*Math.sin(Math.PI/sides));

        x = new int[sides];
        y = new int[sides];
        coordsUpdate();
    }

    public void update(){
        for (int i = 0; i < x.length; i++){
            if (x[i] + velocity.x < 0 || x[i] + velocity.x  >= Main.SIZE){
                velocity.x *= -1;
            }
            if (y[i] + velocity.y < 0 || y[i] + velocity.y >= Main.SIZE){
                velocity.y *= -1;
            }
        }

        super.update();
    }

    private void coordsUpdate() {
        double angle = rot;

        for (int i = 0; i < sides; i++){
            x[i] = (int) (position.x + radius*Math.cos(angle));
            y[i] = (int) (position.y + radius*Math.sin(angle));
            angle += 2*Math.PI/sides;
        }
        rot += vrot;
        rot %= Math.PI*2;
    }

    //relevent formula:  Side = 2 × Radius × sin(π/n)
    //where radius is distance from center to vertex and n is num of sides
    //exterior angle = 2PI/numOfSides



    public void draw(Graphics g){


        coordsUpdate();


        g.drawPolygon(x,y,sides);
    }

    @Override
    public double mass() {
        return 0;//replace w area formula
    }

}
