import java.awt.*;
import java.awt.geom.Ellipse2D;


public class Oval extends Shape {

    public Oval(int topLeftX, int topLeftY, int width, int height, Paint paint) {
        super(paint);
        topLeft = new Point(topLeftX, topLeftY);
        anchorX = topLeft.x;
        anchorY = topLeft.y;
        this.width = width;
        this.height = height;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(int x, int y) {
        topLeft.x = x;
        topLeft.y = y;
        anchorX = topLeft.x;
        anchorY = topLeft.y;
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
    public void startDraw(int x, int y) {
        topLeft.x = x;
        topLeft.y = y;

        anchorX = topLeft.x;
        anchorY = topLeft.y;
        width = 1;
        height = 1;
    }

    @Override
    public void onDraw(int x, int y) {
        topLeft.x = Math.min(x, anchorX);
        topLeft.y = Math.min(y, anchorY);

        width = Math.abs(x - anchorX) + 1;
        height = Math.abs(y - anchorY) + 1;
    }

    @Override
    public Shape endDraw() {
        return this;
    }

    @Override
    public void translate(int dx, int dy) {
        topLeft.translate(dx, dy);
        anchorX = topLeft.x;
        anchorY = topLeft.y;
    }

    @Override
    public void scale(int xOrigin, int yOrigin, float scaleFactor) {
        topLeft.x = (int) ((topLeft.x - xOrigin) * scaleFactor + xOrigin);
        topLeft.y = (int) ((topLeft.y - yOrigin) * scaleFactor + yOrigin);

        anchorX = topLeft.x;
        anchorY = topLeft.y;
        width *= scaleFactor;
        height *= scaleFactor;
    }

    @Override
    public boolean contain(int x,int y){
        Ellipse2D e= new Ellipse2D.Double(topLeft.x, topLeft.y, width, height);
        return e.contains(x, y);
    }


    private final Point topLeft;
    private int anchorX;
    private int anchorY;
    private int width;
    private int height;

}