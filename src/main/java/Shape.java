import java.awt.*;
import java.awt.event.MouseEvent;


public abstract class Shape {

    public Shape(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public abstract void drawOn(Graphics2D g2d);

    public abstract void startDraw(int x, int y);

    public abstract void onDraw(int x, int y);

    public abstract Shape endDraw();

    public abstract void translate(int dx, int dy);

    public abstract void scale(int xOrigin, int yOrigin, float scaleFactor);

    public abstract boolean contain(int x,int y);


    private boolean click;
    private Paint paint;

}
