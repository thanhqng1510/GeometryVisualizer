import java.awt.*;


public class Line extends Shape {

    public Line(int xStart, int yStart, int xEnd, int yEnd, Paint paint) {
        super(paint);
        start = new Point(xStart, yStart);
        end = new Point(xEnd, yEnd);
    }

    public Point getStart() {
        return start;
    }

    public void setStart(int x, int y) {
        start.x = x;
        start.y = y;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(int x, int y) {
        end.x = x;
        end.y = y;
    }

    @Override
    public void drawOn(Graphics2D g2d) {
        Paint oldPaint = g2d.getPaint();
        g2d.setPaint(getPaint());

        g2d.drawLine(start.x, start.y, end.x, end.y);

        g2d.setPaint(oldPaint);
    }

    @Override
    public void startDraw(int x, int y) {
        start.x = x;
        start.y = y;

        end.x = x;
        end.y = y;
    }

    @Override
    public void onDraw(int x, int y) {
        end.x = x;
        end.y = y;
    }

    @Override
    public Shape endDraw() {
        return this;
    }

    @Override
    public void translate(int dx, int dy) {
        start.translate(dx, dy);
        end.translate(dx, dy);
    }

    @Override
    public void scale(int xOrigin, int yOrigin, float scaleFactor) {
        start.x = (int) ((start.x - xOrigin) * scaleFactor + xOrigin);
        start.y = (int) ((start.y - yOrigin) * scaleFactor + yOrigin);

        end.x = (int) ((end.x - xOrigin) * scaleFactor + xOrigin);
        end.y = (int) ((end.y - yOrigin) * scaleFactor + yOrigin);
    }

    private final Point start;
    private final Point end;

}