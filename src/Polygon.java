import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jackson and Kyle on 11/13/14.
 * Starting with regular polygons with n-sides
 */
public class Polygon extends Piston {

    int sides;
    double sideLength;
    double radius;
    Pair[] pts;
    ArrayList<Pair> normals;

    public Polygon(Pair position, Pair velocity, double rot, double vrot, boolean ghost, boolean showLine, int sides, double sideLength) {
        super(position, velocity, rot, vrot, 1, ghost, showLine);
        this.sides = sides;
        this.sideLength = sideLength;
        radius = sideLength/(2*Math.sin(Math.PI/sides));

        pts = new Pair[sides];
        coordsUpdate();
    }

    public void update(){
        for (int i = 0; i < pts.length; i++){
            if (pts[i].x + velocity.x < 0 || pts[i].x + velocity.x  >= Main.SIZE){
                velocity.x *= -1;
            }
            if (pts[i].y + velocity.y < 0 || pts[i].y + velocity.y >= Main.SIZE){
                velocity.y *= -1;
            }
        }

        super.update();
    }

    private void coordsUpdate() {
        double angle = rot;

        for (int i = 0; i < sides; i++){
            Pair temp = new Pair (position.x + radius*Math.cos(angle), position.y + radius*Math.sin(angle));
            pts[i] = temp;
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
        int[] x = new int [pts.length];
        int[] y = new int [pts.length];
        for (int i = 0; i < pts.length; i++){
            x[i] = (int)pts[i].x;
            y[i] = (int)pts[i].y;
        }
        g.drawPolygon(x,y,sides);
    }

    @Override
    public double mass() {
        return 0;//replace w area formula
    }

    public ArrayList<Pair> getNormals() {
        return normals;
    }

    public void updateNormals(){
        ArrayList <Pair> lines = new ArrayList <Pair> ();
        for (int q = 0; q < pts.length; q ++){
            lines.add(pts[(q+1)%pts.length].getCopy().getDifference(pts[q]).getNorm().convertUnit());
        }
        normals = lines;
    }
}
