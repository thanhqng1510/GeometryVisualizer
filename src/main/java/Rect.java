import java.awt.*;


public class Rect extends Shape {

    public Rect(Point topLeft, Point bottomRight, Paint paint) {
        super(paint);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    @Override
    public void drawOn(Graphics2D g2d) {
        Paint oldPaint = g2d.getPaint();

        g2d.setPaint(getPaint());
        g2d.drawLine(topLeft.x, topLeft.y, bottomRight.x, topLeft.y);
        g2d.drawLine(bottomRight.x, topLeft.y, bottomRight.x, bottomRight.y);
        g2d.drawLine(bottomRight.x, bottomRight.y, topLeft.x, bottomRight.y);
        g2d.drawLine(topLeft.x, bottomRight.y, topLeft.x, topLeft.y);

        g2d.setPaint(oldPaint);
    }

    @Override
    public void startDraw(Point location) {
        topLeft = location;
        bottomRight = location;
    }

    @Override
    public void onDraw(Point location) {
        bottomRight = location;
    }

    @Override
    public Shape endDraw() {
        return this;
    }

    @Override
    public void translate(int dx, int dy) {
        topLeft.translate(dx, dy);
        bottomRight.translate(dx, dy);
    }

    @Override
    public void scale(Point origin, float scaleFactor) {
        topLeft.x = (int) ((topLeft.x - origin.x) * scaleFactor + origin.x);
        topLeft.y = (int) ((topLeft.y - origin.y) * scaleFactor + origin.y);

        bottomRight.x = (int) ((bottomRight.x - origin.x) * scaleFactor + origin.x);
        bottomRight.y = (int) ((bottomRight.y - origin.y) * scaleFactor + origin.y);
    }

    private Point topLeft;
    private Point bottomRight;

}