import java.awt.*;
import java.awt.geom.Rectangle2D;


public class Rect extends Rectangle2D.Double implements MyShape {

    public Rect(double topLeftX, double topLeftY, double width, double height, Paint paint) {
        super(topLeftX, topLeftY, width, height);
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

        g2d.drawLine((int) x, (int) y, (int) (x + width), (int) y);
        g2d.drawLine((int) (x + width), (int) y, (int) (x + width), (int) (y + height));
        g2d.drawLine((int) (x + width), (int) (y + height), (int) x, (int) (y + height));
        g2d.drawLine((int) x, (int) (y + height), (int) x, (int) y);

        g2d.setPaint(oldPaint);
    }

    @Override
    public void begin(double x, double y) {
        this.x = x;
        this.y = y;
        width = 0;
        height = 0;
    }

    @Override
    public void update(double x, double y) {
        width = x - this.x;
        height = y - this.y;
    }

    @Override
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void scale(double xOrigin, double yOrigin, double scaleFactor) {
        double botX = x + width, botY = y + height;

        x = (x - xOrigin) * scaleFactor + xOrigin;
        y = (y - yOrigin) * scaleFactor + yOrigin;

        botX = (botX - xOrigin) * scaleFactor + xOrigin;
        botY = (botY - yOrigin) * scaleFactor + yOrigin;

        width = botX - x;
        height = botY - y;
    }

    private final Paint paint;
    private boolean selected;

}