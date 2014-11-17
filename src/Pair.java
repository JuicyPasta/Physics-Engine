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
    public Pair basicAdd(Pair other){
        this.x += other.x;
        this.y += other.y;
        return this;
    }
    public Pair convertUnit(){
        double r = Math.sqrt(x*x+y*y);
        x /= r;
        y /= r;
        return this;
    }
    public Pair multiplyScalar(double scal){
        x *= scal;
        y *= scal;
        return this;
    }
    public Pair divideScalar(double scal){
        x /= scal;
        y /= scal;
        return this;
    }
    public String toString(){
        return ("x: " + x + " y: " + y);
    }

    public double r(){
        return Math.sqrt( x * x +  y * y);
    }
    public double theta(){
        return Math.atan(y/x);
    }
}