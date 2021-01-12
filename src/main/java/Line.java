import java.awt.*;
import java.awt.geom.Line2D;


public class Line extends Line2D.Double implements MyShape {

    public Line(double xStart, double yStart, double xEnd, double yEnd, Paint paint) {
        super(xStart, yStart, xEnd, yEnd);
        this.paint = paint;
        this.selected = false;
    }

    @Override
    public Paint getPaint() {
        return paint;
    }

    @Override
    public boolean selected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Paint oldPaint = g2d.getPaint();
        g2d.setPaint(getPaint());

        g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

        g2d.setPaint(oldPaint);
    }

    @Override
    public void begin(double x, double y) {
        x1 = x2 = x;
        y1 = y2 = y;
    }

    @Override
    public void update(double x, double y) {
        x2 = x;
        y2 = y;
    }

    @Override
    public void translate(double dx, double dy) {
        x1 += dx;
        y1 += dy;

        x2 += dx;
        y2 += dy;
    }

    @Override
    public void scale(double xOrigin, double yOrigin, double scaleFactor) {
        x1 = (x1 - xOrigin) * scaleFactor + xOrigin;
        y1 = (y1 - yOrigin) * scaleFactor + yOrigin;

        x2 = (x2 - xOrigin) * scaleFactor + xOrigin;
        y2 = (y2 - yOrigin) * scaleFactor + yOrigin;
    }

    private final Paint paint;
    private boolean selected;

}