import java.awt.*;

/**
 * Created by Jackson on 11/13/14.
 */

//This is a generic engine object, all other objects should extend this
public class Piston {
    double x,y,vx,vy,ax,ay,rx,density;
    Shape shape;
    Engine eng;
    public Piston (Engine eng, double x, double y, double vx, double vy, double ax, double ay, double rx, int density, Shape shape){
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        this.ax=ax;
        this.ay=ay;
        this.rx=rx;
        this.shape=shape;
        this.eng=eng;
        this.density = density;
    }
    public Piston (double x, double y, double vx, double vy, double ax, double ay, double rx, int density, Shape shape){
        this(null,x,y,vx,vy,ax,ay,rx,density,shape);
    }
    public boolean isBound(){
        return eng != null;
    }
    public void update(){
        if (isBound()){
            x += vx;
            y += vy;
            vx += ax;
            vy += vy;
            //Handle the next move with eng.physics.XXX();
        }else{
            System.out.println("this piston is not bound to an engine :(");
        }
    }
    public void bindEngine(Engine eng){
        this.eng=eng;
    }
    public double mass(){

        return shape.area()*density;
    }
    public void draw(Graphics g){
        shape.draw(g);
    }


}
