import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class DrawArea extends JComponent {

    // Image to draw
    private Image image;
    // Graphics 2D object => use to draw on
    private Graphics2D context;
    // Mouse coordinates
    private int curX, curY, oldX, oldY;

    public DrawArea() {
        setDoubleBuffered(true);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                // Save old coordinates when mouse is pressed
                oldX = e.getX();
                oldY = e.getY();
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();

                if (context != null) {
                    context.drawLine(oldX, oldY, curX, curY);
                    repaint();
                    oldX = curX;
                    oldY = curY;
                }
            }

        });
    }

    public void clear() {
        context.setPaint(Color.white);
        context.fillRect(0, 0, getWidth(), getHeight());
        repaint();
        context.setPaint(Color.black);
    }

    public void setColor(Color color) {
        context.setPaint(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getWidth(), getHeight());
            context = (Graphics2D)image.getGraphics();
            context.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

}