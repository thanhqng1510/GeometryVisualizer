import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class DrawArea extends JPanel {

    public DrawArea() {
        g2d = null;
        drawShapeType = ShapeType.LINE;
        cursorMode = CursorMode.SELECT;
        paintColor = Color.BLACK;
        data = new ArrayList<>();
        userCurShape = null;
        isDrawing = false;

        setDoubleBuffered(true);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                switch (cursorMode) {
                    case DRAW:
                        if (!isDrawing) {
                            userCurShape = drawShapeType.getInstance(paintColor);
                            assert userCurShape != null;
                            userCurShape.startDraw(e);
                        }
                        else {
                            data.add(userCurShape.endDraw());
                        }

                        isDrawing = !isDrawing;
                        break;
                    case SELECT:
                        // TODO: add more features
                        break;
                }
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                switch (cursorMode) {
                    case DRAW:
                        if (isDrawing)
                            userCurShape.onDraw(e);
                        break;
                    case SELECT:
                        // TODO: add more features
                        break;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                switch (cursorMode) {
                    case DRAW:
                        if (isDrawing) {
                            userCurShape.onDraw(e);
                        }
                        break;
                    case SELECT:
                        // TODO: add more features
                        break;
                }


            }

        });
    }

    public void clearScreen() {
        data.clear();
        clear();
    }

    public void setPaint(Paint paint) {
        paintColor = paint;
        g2d.setPaint(paintColor);
    }

    public Paint getPaint() {
        return paintColor;
    }

    public void setDrawShapeType(ShapeType shapeType) {
        drawShapeType = shapeType;
    }

    public void setCursorMode(CursorMode cursorMode) {
        this.cursorMode = cursorMode;
    }

    public void stopDrawing() {
        isDrawing = false;
        repaint();
    }

    private Graphics2D g2d;
    private ShapeType drawShapeType;
    private CursorMode cursorMode;
    private Paint paintColor;
    private final ArrayList<Shape> data;

    private Shape userCurShape;
    private boolean isDrawing;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        clear();

        drawGrid(g);
        doPainting(g);
    }

    private void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();
        int rows = h / 25;
        int cols = w / 25;

        g2d.setPaint(Color.decode("#b6b6b6"));

        int rowHeight = h / rows;
        for (int i = 0; i < rows; ++i)
            g.drawLine(0, i * rowHeight, w, i * rowHeight);

        int colWidth = w / cols;
        for (int i = 0; i < cols; ++i)
            g.drawLine(i * colWidth, 0, i * colWidth, h);

        g2d.setPaint(paintColor);
    }

    private void doPainting(Graphics g) {
        if (isDrawing) {
            userCurShape.drawOn(g2d);
        }

        for (Shape s : data) {
            s.drawOn(g2d);
        }
    }

    private void clear() {
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        repaint();

        g2d.setPaint(paintColor);
    }

}
