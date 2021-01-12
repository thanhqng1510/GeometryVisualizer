import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class DrawArea extends JPanel{
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
        mousePos = null;
        strokeThickness = 3;
        streamData = new ArrayList<>();
        streamIdx = -1;

        setDoubleBuffered(true);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                switch (cursorMode) {
                    case SELECT:
                        // TODO: add more features
                        break;
                    case ZOOM_IN:
                        zoomIn(5);
                        break;
                    case ZOOM_OUT:
                        zoomOut(5);
                        break;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (cursorMode == CursorMode.DRAW) {
                    data.add(userCurShape);
                    isDrawing = false;

                    streamData.subList(streamIdx + 1, streamData.size()).clear();
                    streamData.add(new ArrayList<>(data));
                    ++streamIdx;
                }
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mousePos = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                Point oldMousePos = new Point(mousePos);
                mousePos = new Point(e.getX(), e.getY());

                switch (cursorMode) {
                    case MOVE:
                        for (MyShape s : data) s.translate(mousePos.x - oldMousePos.x, mousePos.y - oldMousePos.y);
                        break;
                    case ZOOM_IN:
                        zoomIn(1);
                        break;
                    case ZOOM_OUT:
                        zoomOut(1);
                        break;
                    case DRAW:
                        if (!isDrawing) {
                            userCurShape = drawShapeType.genInstance(paintColor);
                            userCurShape.begin(mousePos.x, mousePos.y);
                            isDrawing = true;
                        }
                        else userCurShape.update(mousePos.x, mousePos.y);
                        break;
                }
            }

        });

    }

    public void clearScreen() {
        data.clear();
        clear();
    }

    public void undo(){
        if (streamIdx >= 0) {
            --streamIdx;
            if (streamIdx >= 0) data = new ArrayList<>(streamData.get(streamIdx));
            else data = new ArrayList<>();
        }
    }

    public void redo(){
        if (streamIdx < streamData.size() - 1) {
            ++streamIdx;
            data = new ArrayList<>(streamData.get(streamIdx));
        }
    }

    public void delete() {
        data.removeIf(MyShape::selected);
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
        if (cursorMode != CursorMode.DRAW) stopDrawing();
        this.cursorMode = cursorMode;
    }

    public void stopDrawing() {
        isDrawing = false;
        repaint();
    }

    public void toggleGridView() {
        shouldDrawGrid = !shouldDrawGrid;
    }

    public void zoomIn(double mul) {
        for (MyShape s : data) s.scale(mousePos.x, mousePos.y, 1 + scaleFactor * mul);
    }

    public void zoomOut(double mul) {
        for (MyShape s : data) s.scale(mousePos.x, mousePos.y, 1 - scaleFactor * mul);
    }

    private Graphics2D g2d;
    private ShapeType drawShapeType;
    private CursorMode cursorMode;
    private boolean shouldDrawGrid;
    private Paint paintColor;
    private ArrayList<MyShape> data;
    private final float scaleFactor;
    private final int strokeThickness;
    private Point mousePos;

    private MyShape userCurShape;
    private boolean isDrawing;

    private final ArrayList<ArrayList<MyShape>> streamData;
    private int streamIdx;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        clear();

        if (shouldDrawGrid) drawGrid();

        doPainting();
    }

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

    private void drawGrid() {
        int w = getWidth();
        int h = getHeight();
        int rows = h / 40;
        int cols = w / 40;

        g2d.setPaint(Color.decode("#bababa"));

        int rowHeight = h / (rows != 0 ? rows : 1);
        for (int i = 0; i < rows + 1; ++i)
            g2d.drawLine(0, i * rowHeight, w, i * rowHeight);

        int colWidth = w / (cols != 0 ? cols : 1);
        for (int i = 0; i < cols + 1; ++i)
            g2d.drawLine(i * colWidth, 0, i * colWidth, h);

        g2d.setPaint(paintColor);
    }

    private void doPainting() {
        g2d.setStroke(new BasicStroke(strokeThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

        if (isDrawing) userCurShape.draw(g2d);

        for (MyShape s : data) s.draw(g2d);

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
