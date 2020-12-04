import java.awt.*;
import java.awt.event.MouseEvent;


public class Oval extends Shape {

    public Oval(Point topLeft, int width, int height, Paint paint) {
        super(paint);
        this.topLeft = topLeft;
        this.anchorX = topLeft.x;
        this.anchorY = topLeft.y;
        this.width = width;
        this.height = height;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
        this.anchorX = topLeft.x;
        this.anchorY = topLeft.y;
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
        anchorX = topLeft.x;
        anchorY = topLeft.y;
        width = 1;
        height = 1;
    }

    @Override
    public void onDraw(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        topLeft.x = Math.min(mouseX, anchorX);
        topLeft.y = Math.min(mouseY, anchorY);

        width = Math.abs(mouseX - anchorX) + 1;
        height = Math.abs(mouseY - anchorY) + 1;
    }

    @Override
    public Shape endDraw() {
        return this;
    }

    private Point topLeft;
    private int anchorX;
    private int anchorY;
    private int width;
    private int height;

}