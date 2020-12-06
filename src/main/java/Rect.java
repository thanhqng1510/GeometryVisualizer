import java.awt.*;


public class Rect extends Shape {

    public Rect(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY, Paint paint) {
        super(paint);
        topLeft = new Point(topLeftX, topLeftY);
        bottomRight = new Point(bottomRightX, bottomRightY);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(int x, int y) {
        topLeft.x = x;
        topLeft.y = y;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(int x, int y) {
        bottomRight.x = x;
        bottomRight.y = y;
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
    public void startDraw(int x, int y) {
        topLeft.x = x;
        topLeft.y = y;

        bottomRight.x = x;
        bottomRight.y = y;
    }

    @Override
    public void onDraw(int x, int y) {
        bottomRight.x = x;
        bottomRight.y = y;
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
    public void scale(int xOrigin, int yOrigin, float scaleFactor) {
        topLeft.x = (int) ((topLeft.x - xOrigin) * scaleFactor + xOrigin);
        topLeft.y = (int) ((topLeft.y - yOrigin) * scaleFactor + yOrigin);

        bottomRight.x = (int) ((bottomRight.x - xOrigin) * scaleFactor + xOrigin);
        bottomRight.y = (int) ((bottomRight.y - yOrigin) * scaleFactor + yOrigin);
    }

    private final Point topLeft;
    private final Point bottomRight;

}