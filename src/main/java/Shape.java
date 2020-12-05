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

    public abstract void startDraw(Point location);

    public abstract void onDraw(Point location);

    public abstract Shape endDraw();

    public abstract void translate(int dx, int dy);

    public abstract void scale(Point origin, float scaleFactor);

    private Paint paint;

}
