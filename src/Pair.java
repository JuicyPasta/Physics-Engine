/**
 * Created by Jackson on 11/14/14.
 * meant to function a a point or a vector
 * handle vector math here.
 */

public class Pair{
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
    public double getRad (){
        return Math.sqrt(x*x+y*y);
    }
    public Pair convertUnit(){
        double r = getRad();
        if (x != 0) {
            x /= r;
        }
        if (y != 0) {
            y /= r;
        }
        return this;
    }
    public Pair multiplyScalar(double scal){
        x *= scal;
        y *= scal;
        return this;
    }
    public Pair divideScalar(double scal){
        if (scal==0){
            System.out.println("you tried to divide by 0 in divideScalar()");
            return this;
        }
        x /= scal;
        y /= scal;
        return this;
    }
    public Pair addScalar(double scal){
        x += scal;
        y += scal;
        return this;
    }
    public Pair subtractScalar(double scal){
        x -= scal;
        y -= scal;
        return this;
    }
    public Pair getDifference(Pair other){
        this.x = other.x - this.x;
        this.y = other.y - this.y;
        return this;
    }
    public Pair getNorm(){
        double temp = this.x;
        this.x = this.y;
        this.y = -temp;
        return this;
    }


    public Pair getCopy(){
        return new Pair (this.x,this.y);
    }

    public String toString(){
        return ("x: " + x + " y: " + y);
    }

    public double r(){
        return Math.sqrt( x * x +  y * y);
    }

    public double theta(){ return Math.atan(y/x); }

    public double dotProduct(Pair b){
        return this.x*b.x + this.y*b.y;
    }

    //THIS MUST BE A UNIT VECTOR (for optimization purposes)
    public double getProjX (Pair projectee){
        return this.dotProduct(projectee) * this.x;
    }
    public double getProjY (Pair projectee){ return this.dotProduct(projectee) * this.y; }

    public int hashCode(){
        return (((int)x) >> 16) | ((int)y);
    }
}