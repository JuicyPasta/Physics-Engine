/**
 * Created by Jackson on 11/13/14.
 */
public class Circle extends Shape {
    int r;

    Circle(){
        r = (int) (1+Math.random()*10);
    }

    Circle(int x, int y, int dx, int dy, int r) {
        this.r = r;
    }

    @Override
    public double mass(){
        return Math.PI * Math.pow(r,2);
    }
}
