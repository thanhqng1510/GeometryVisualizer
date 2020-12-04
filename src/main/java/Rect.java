import java.awt.*;
import java.awt.event.MouseEvent;


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
    public void startDraw(MouseEvent e) {
        topLeft = new Point(e.getX(), e.getY());
        bottomRight = (Point) topLeft.clone();
    }

    @Override
    public void onDraw(MouseEvent e) {
        bottomRight = new Point(e.getX(), e.getY());
    }

    @Override
    public Shape endDraw() {
        return this;
    }

    private Point topLeft;
    private Point bottomRight;

}