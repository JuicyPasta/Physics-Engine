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

    }
    public void basicAdd(Pair other){
        this.x += other.x;
        this.y += other.y;
    }


}