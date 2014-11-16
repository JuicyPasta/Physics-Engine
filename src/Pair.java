/**
 * Created by Jackson on 11/14/14.
 * meant to function a a point or a vector
 * handle vector math here.
 */

public class Pair {
    double x,y;
    public Pair (double x, double y){
        this.x=x;
        this.y=y;
    }
    public Pair (){
        this.x=0;
        this.y=0;
    }
    public void basicAdd(Pair other){
        this.x += other.x;
        this.y += other.y;
    }
    public void convertUnit(){
        double r = Math.sqrt(x*x+y*y);
        x /= r;
        y /= r;
    }
    public void multiplyScalar(double scal){
        x *= scal;
        y *= scal;
    }
    public void divideScalar(double scal){
        x /= scal;
        y /= scal;
    }
    public String toString(){
        return ("x: " + x + " y: " + y);
    }

}