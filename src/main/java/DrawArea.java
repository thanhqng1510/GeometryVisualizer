import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class DrawArea extends JPanel {

    private BufferedImage bi;
    private Image img = null;
    public DrawArea() {
        g2d = null;
        drawShapeType = ShapeType.LINE;
        cursorMode = CursorMode.SELECT;
        shouldDrawGrid = true;
        paintColor = Color.BLACK;
        data = new ArrayList<>();
        scaleFactor = .025f;
        userCurShape = null;
        isDrawing = false;
        curMousePos = null;
        strokeThickness = 3;

        setDoubleBuffered(true);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                switch (cursorMode) {
                    case SELECT:
                        // TODO: add more features
                        break;
                    case ZOOM_IN:
                        zoomIn();
                        break;
                    case ZOOM_OUT:
                        zoomOut();
                        break;
                    case DRAW:
                        if (!isDrawing) {
                            userCurShape = drawShapeType.getInstance(paintColor);
                            assert userCurShape != null;
                            userCurShape.startDraw(curMousePos.x, curMousePos.y);
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

                curMousePos = new Point(e.getX(), e.getY());

                switch (cursorMode) {
                    case SELECT:
                        // TODO: add more features
                        break;
                    case DRAW:
                        if (isDrawing)
                            userCurShape.onDraw(curMousePos.x, curMousePos.y);
                        break;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                Point oldMousePos = curMousePos;
                curMousePos = new Point(e.getX(), e.getY());

                switch (cursorMode) {
                    case SELECT:
                        // TODO: add more features
                        break;
                    case MOVE_AROUND:
                        for (Shape s : data)
                            s.translate(curMousePos.x - oldMousePos.x, curMousePos.y - oldMousePos.y);
                        break;
                    case ZOOM_IN:
                        zoomIn();
                        break;
                    case ZOOM_OUT:
                        zoomOut();
                        break;
                    case DRAW:
                        if (isDrawing)
                            userCurShape.onDraw(curMousePos.x, curMousePos.y);
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
        if (cursorMode != CursorMode.DRAW)
            stopDrawing();
        this.cursorMode = cursorMode;
    }

    public void stopDrawing() {
        isDrawing = false;
        repaint();
    }

    public void toggleGridView() {
        shouldDrawGrid = !shouldDrawGrid;
    }

    public void zoomIn() {
        for (Shape s : data)
            s.scale(curMousePos.x, curMousePos.y, 1 + scaleFactor);
    }

    public void zoomOut() {
        for (Shape s : data)
            s.scale(curMousePos.x, curMousePos.y, 1 - scaleFactor);
    }

    private Graphics2D g2d;
    private ShapeType drawShapeType;
    private CursorMode cursorMode;
    private boolean shouldDrawGrid;
    private Paint paintColor;
    private final ArrayList<Shape> data;
    private final float scaleFactor;
    private final int strokeThickness;

    private Shape userCurShape;
    private boolean isDrawing;
    private Point curMousePos;

    public void paint( Graphics g )
    {
        if (img != null){
            g2d = (Graphics2D) g;
            g2d.drawImage(img,0,0,null);
            img = null;
        }else{
            super.paint(g);
        }

    }

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
        int rows = h / 40;
        int cols = w / 40;

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
        g2d.setStroke(new BasicStroke(strokeThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

        if (isDrawing)
            userCurShape.drawOn(g2d);

        for (Shape s : data)
            s.drawOn(g2d);

        g2d.setStroke(new BasicStroke());
    }

    private void clear() {
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        repaint();

        g2d.setPaint(paintColor);
    }

    public void setImage(Image image){
        img = image;
        repaint();
        // Draw the image on to the buffered imag
    }

    public void ImageTofile(String dst){
        try {
            bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            Graphics g = bi.getGraphics();
            this.printAll(g);
            ImageIO.write(bi, "jpg", new File(dst));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
