/**
 * Created by Kyle on 11/13/2014.
 */
public class BallManager {
    Ball arr[];
    public BallManager(int sizeOfField, int numBalls){
        Ball.FIELD_SIZE = sizeOfField;
        arr = new Ball[numBalls];
    }
    public void update(){
        for (int i = 0; i < arr.length; i++){
            for (int j = i + 1; j < arr.length; j++){
                if (Math.sqrt( Math.pow(arr[i].x - arr[j].x, 2) +  Math.pow(arr[i].y - arr[j].y, 2)) < arr[i].r + arr[j].r){
                    int tx = arr[i].dx;
                    int ty = arr[i].dy;
                    arr[i].dx = arr[j].dx;
                    arr[i].dy = arr[j].dy;
                    arr[j].dx = tx;
                    arr[j].dy = ty;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) arr[i].update();
    }
}
class Ball{
    static int FIELD_SIZE; // treat as final
    int x;
    int y;
    int dx;
    int dy;
    int r;
    public void update(){
        if (x + dx - r < 0 || x + dx + r >= FIELD_SIZE){
            dx *= -1;
        }
        x += dx;
        y += dy;
    }
}