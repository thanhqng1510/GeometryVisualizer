import java.awt.*;


public interface MyShape {

    Paint getPaint();

    Paint getBlendPaint();

    void setBlendPaint(Paint paint);

    boolean selected();

    void setSelected(boolean selected);

    void draw(Graphics2D g2d);

    void begin(double x, double y);

    void update(double x, double y);

    void translate(double dx, double dy);

    void scale(double xOrigin, double yOrigin, double scaleFactor);

    boolean contain(double x, double y);

}
