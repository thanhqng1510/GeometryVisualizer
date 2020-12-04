import java.awt.*;
import java.awt.event.MouseEvent;


public class Oval extends Shape {

    public Oval(Point topLeft, int width, int height, Paint paint) {
        super(paint);
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void drawOn(Graphics2D g2d) {
        Paint oldPaint = g2d.getPaint();

        g2d.setPaint(getPaint());
        g2d.drawOval(topLeft.x, topLeft.y, width, height);

        g2d.setPaint(oldPaint);
    }

    @Override
    public void startDraw(MouseEvent e) {
        topLeft = new Point(e.getX(), e.getY());
        width = 0;
        height = 0;
    }

    @Override
    public void onDraw(MouseEvent e) {
        width = Math.abs(e.getX() - topLeft.x);
        height = Math.abs(e.getY() - topLeft.y);
    }

    @Override
    public Shape endDraw() {
        return this;
    }

    private Point topLeft;
    private int width;
    private int height;

}