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

    public abstract void startDraw(MouseEvent e);

    public abstract void onDraw(MouseEvent e);

    public abstract Shape endDraw();

    private Paint paint;

}
