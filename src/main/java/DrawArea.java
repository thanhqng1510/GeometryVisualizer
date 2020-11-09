import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class DrawArea extends JComponent {

    // Image to draw
    private Image image;
    // Graphics 2D object => use to draw on
    private Graphics2D g2d;
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

                if (g2d != null) {
                    g2d.drawLine(oldX, oldY, curX, curY);
                    repaint();
                    oldX = curX;
                    oldY = curY;
                }
            }

        });
    }

    public void clear() {
        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        repaint();
        g2d.setPaint(Color.black);
    }

    public void setColor(Color color) {
        g2d.setPaint(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getWidth(), getHeight());
            g2d = (Graphics2D)image.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

}
