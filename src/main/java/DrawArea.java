import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class DrawArea extends JPanel {

    public DrawArea() {
        g2d = null;
        drawShapeType = ShapeType.LINE;
        cursorMode = CursorMode.SELECT;
        shouldDrawGrid = true;
        paintColor = Color.BLACK;
        data = new ArrayList<>();
        scaleFactor = .02f;
        userCurShape = null;
        isDrawing = false;

        setDoubleBuffered(true);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Point mouseLocation = new Point(e.getX(), e.getY());

                switch (cursorMode) {
                    case SELECT:
                        // TODO: add more features
                        break;
                    case ZOOM_IN:
                        if (userCurShape != null)
                            userCurShape.scale(mouseLocation, 1 + scaleFactor);
                        for (Shape s : data)
                            s.scale(mouseLocation, 1 + scaleFactor);
                        break;
                    case ZOOM_OUT:
                        if (userCurShape != null)
                            userCurShape.scale(mouseLocation, 1 - scaleFactor);
                        for (Shape s : data)
                            s.scale(mouseLocation, 1 - scaleFactor);
                        break;
                    case DRAW:
                        if (!isDrawing) {
                            userCurShape = drawShapeType.getInstance(paintColor);
                            assert userCurShape != null;
                            userCurShape.startDraw(mouseLocation);
                        }
                        else
                            data.add(userCurShape.endDraw());

                        isDrawing = !isDrawing;
                        break;
                }
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                Point mouseLocation = new Point(e.getX(), e.getY());

                switch (cursorMode) {
                    case SELECT:
                        // TODO: add more features
                        break;
                    case DRAW:
                        if (isDrawing)
                            userCurShape.onDraw(mouseLocation);
                        break;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                Point mouseLocation = new Point(e.getX(), e.getY());

                switch (cursorMode) {
                    case SELECT:
                        // TODO: add more features
                        break;
                    case MOVE_AROUND:
                        if (userCurShape != null)
                            userCurShape.translate(1, 1);
                        for (Shape s : data)
                            s.translate(1, 1);
                        break;
                    case ZOOM_IN:
                        if (userCurShape != null)
                            userCurShape.scale(mouseLocation, 1 + scaleFactor);
                        for (Shape s : data)
                            s.scale(mouseLocation, 1 + scaleFactor);
                        break;
                    case ZOOM_OUT:
                        if (userCurShape != null)
                            userCurShape.scale(mouseLocation, 1 - scaleFactor);
                        for (Shape s : data)
                            s.scale(mouseLocation, 1 - scaleFactor);
                        break;
                    case DRAW:
                        if (isDrawing)
                            userCurShape.onDraw(mouseLocation);
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

    public void toggleGridView() {
        shouldDrawGrid = !shouldDrawGrid;
    }

    private Graphics2D g2d;
    private ShapeType drawShapeType;
    private CursorMode cursorMode;
    private boolean shouldDrawGrid;
    private Paint paintColor;
    private final ArrayList<Shape> data;
    private final float scaleFactor;

    private Shape userCurShape;
    private boolean isDrawing;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        clear();

        if (shouldDrawGrid)
            drawGrid();

        doPainting();
    }

    private void drawGrid() {
        int w = getWidth();
        int h = getHeight();
        int rows = h / 30;
        int cols = w / 30;

        g2d.setPaint(Color.decode("#bababa"));

        int rowHeight = h / rows;
        for (int i = 0; i < rows + 1; ++i)
            g2d.drawLine(0, i * rowHeight, w, i * rowHeight);

        int colWidth = w / cols;
        for (int i = 0; i < cols + 1; ++i)
            g2d.drawLine(i * colWidth, 0, i * colWidth, h);

        g2d.setPaint(paintColor);
    }

    private void doPainting() {
        if (isDrawing)
            userCurShape.drawOn(g2d);

        for (Shape s : data)
            s.drawOn(g2d);
    }

    private void clear() {
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        repaint();

        g2d.setPaint(paintColor);
    }

}
