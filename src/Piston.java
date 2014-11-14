import java.awt.*;

/**
 * Created by Jackson on 11/13/14.
 */

//This is a generic engine object, all other objects should extend this
public class Piston {
    double x,y,vx,vy,ax,ay,rx;
    int mass;
    Shape shape;
    Engine eng;
    public Piston (Engine eng, double x, double y, double vx, double vy, double ax, double ay, double rx, int mass, Shape shape){
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        this.ax=ax;
        this.ay=ay;
        this.rx=rx;
        this.mass=mass;
        this.shape=shape;
        this.eng=eng;
    }
    public Piston (double x, double y, double vx, double vy, double ax, double ay, double rx, int mass, Shape shape){
        this (null,x,y,vx,vy,ax,ay,rx,mass,shape);
    }
    public boolean isBound(){
        return eng != null;
    }
    public void bindEngine (Engine eng){
        this.eng = eng;
    }
    public void update(){
        if (isBound()){
            //use eng.physics.XXX(); to calculate the next position.
            x += vx;
            y += vy;
            vx += ax;
            vy += ay;
        }else {
            System.out.println("this object is not bound to an engine");
        }
    }
    public void draw(Graphics g){
        shape.draw(g);
    }
}
