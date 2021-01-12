import java.awt.*;
import java.awt.geom.Ellipse2D;


public class Ellipse extends Ellipse2D.Double implements MyShape {

    public Ellipse(int topLeftX, int topLeftY, int width, int height, Paint paint) {
        super(topLeftX, topLeftY, width, height);
        anchorX = topLeftX;
        anchorY = topLeftY;
        this.paint = paint;
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

        g2d.drawOval((int) x, (int) y, (int) width, (int) height);

        g2d.setPaint(oldPaint);
    }

    @Override
    public void begin(double x, double y) {
        this.x = x;
        this.y = y;
        anchorX = (int) x;
        anchorY = (int) y;
        width = 1;
        height = 1;
    }

    @Override
    public void update(double x, double y) {
        this.x = Math.min(x, anchorX);
        this.y = Math.min(y, anchorY);

        width = Math.abs(x - anchorX) + 1;
        height = Math.abs(y - anchorY) + 1;
    }

    @Override
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
        anchorX = (int) x;
        anchorY = (int) y;
    }

    @Override
    public void scale(double xOrigin, double yOrigin, double scaleFactor) {
        x = (int) ((x - xOrigin) * scaleFactor + xOrigin);
        y = (int) ((y - yOrigin) * scaleFactor + yOrigin);

        anchorX = (int) x;
        anchorY = (int) y;
        width *= scaleFactor;
        height *= scaleFactor;
    }

    private int anchorX;
    private int anchorY;
    private final Paint paint;
    private boolean selected;

}