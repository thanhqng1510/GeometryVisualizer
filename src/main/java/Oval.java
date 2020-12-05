import java.awt.*;


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
    public void startDraw(Point location) {
        topLeft = location;
        anchorX = topLeft.x;
        anchorY = topLeft.y;
        width = 1;
        height = 1;
    }

    @Override
    public void onDraw(Point location) {
        int mouseX = location.x;
        int mouseY = location.y;

        topLeft.x = Math.min(mouseX, anchorX);
        topLeft.y = Math.min(mouseY, anchorY);

        width = Math.abs(mouseX - anchorX) + 1;
        height = Math.abs(mouseY - anchorY) + 1;
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
    public void scale(Point origin, float scaleFactor) {
        topLeft.x = (int) ((topLeft.x - origin.x) * scaleFactor + origin.x);
        topLeft.y = (int) ((topLeft.y - origin.y) * scaleFactor + origin.y);

        anchorX = topLeft.x;
        anchorY = topLeft.y;
        width *= scaleFactor;
        height *= scaleFactor;
    }

    private Point topLeft;
    private int anchorX;
    private int anchorY;
    private int width;
    private int height;

}