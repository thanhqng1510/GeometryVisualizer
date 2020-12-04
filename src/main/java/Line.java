import java.awt.*;
import java.awt.event.MouseEvent;


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
    public void startDraw(MouseEvent e) {
        start = new Point(e.getX(), e.getY());
        end = (Point) start.clone();
    }

    @Override
    public void onDraw(MouseEvent e) {
        end = new Point(e.getX(), e.getY());
    }

    @Override
    public Shape endDraw() {
        return this;
    }

    private Point start;
    private Point end;

}