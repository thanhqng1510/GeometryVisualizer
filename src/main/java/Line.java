import java.awt.*;


public class Line extends Shape {

    public Line(Point start, Point end, Paint paint) {
        super(paint);
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    public void drawOn(Graphics2D g2d) {
        Paint oldPaint = g2d.getPaint();

        g2d.setPaint(getPaint());
        g2d.drawLine(start.x, start.y, end.x, end.y);

        g2d.setPaint(oldPaint);
    }

    @Override
    public void startDraw(Point location) {
        start = location;
        end = location;
    }

    @Override
    public void onDraw(Point location) {
        end = location;
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
    public void scale(Point origin, float scaleFactor) {
        start.x = (int) ((start.x - origin.x) * scaleFactor + origin.x);
        start.y = (int) ((start.y - origin.y) * scaleFactor + origin.y);

        end.x = (int) ((end.x - origin.x) * scaleFactor + origin.x);
        end.y = (int) ((end.y - origin.y) * scaleFactor + origin.y);
    }

    private Point start;
    private Point end;

}