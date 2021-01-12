import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


public class MainWindow extends JFrame {

    public MainWindow(Color themeColor) {
        super("Untitled*");
        // TODO: work-around

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        this.themeColor = themeColor;
        this.toolbarColor = Color.decode("#e9e9e9");
        this.componentBorderSize = 5;
        this.normalFontSize = 14;

        this.toolbarAdapter = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == selectBtn) {
                    drawArea.setCursorMode(CursorMode.SELECT);
                    drawArea.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                else if (e.getSource() == moveBtn) {
                    drawArea.setCursorMode(CursorMode.MOVE);
                    drawArea.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }
                else if (e.getSource() == zoomInBtn) {
                    drawArea.setCursorMode(CursorMode.ZOOM_IN);
                    Image cursorImage = new ImageIcon("./res/zoomInBtn.png").getImage();
                    drawArea.setCursor(getToolkit().createCustomCursor(cursorImage, new Point(), null));
                }
                else if (e.getSource() == zoomOutBtn) {
                    drawArea.setCursorMode(CursorMode.ZOOM_OUT);
                    Image cursorImage = new ImageIcon("./res/zoomOutBtn.png").getImage();
                    drawArea.setCursor(getToolkit().createCustomCursor(cursorImage, new Point(), null));
                }
                else if (e.getSource() == shapeTypeComboBox) {
                    ShapeType shapeType = (ShapeType) ((JComboBox) e.getSource()).getSelectedItem();
                    drawArea.setDrawShapeType(shapeType);
                    drawArea.setCursorMode(CursorMode.DRAW);
                    drawArea.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

                }
                else if (e.getSource() == toggleGridBtn) drawArea.toggleGridView();
                else if (e.getSource() == changeColorBtn) {
                    JDialog dialog = JColorChooser.createDialog(
                            drawArea, "Choose color", false, colorChooser,
                            e1 -> drawArea.setPaint(colorChooser.getColor()), null);
                    dialog.setVisible(true);
                }
                else if (e.getSource() == clearBtn) drawArea.clearScreen();
                else if(e.getSource() == undoBtn) drawArea.undo();
                else if(e.getSource() == redoBtn) drawArea.redo();
                else if(e.getSource() == deleteBtn) drawArea.delete();
            }

        };

        this.keyAdapter = new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    drawArea.stopDrawing();
            }

        };
        this.addKeyListener(keyAdapter);

        // Add draw area to the main window
        drawArea = new DrawArea();
        drawArea.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (!hasFocus()) requestFocus();
            }

        });
        drawArea.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                xCoordStatus.setText("<html><b>X: " + e.getX() + "</b></html>");
                yCoordStatus.setText("<html><b>Y: " + e.getY() + "</b></html>");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                xCoordStatus.setText("<html><b>X: " + e.getX() + "</b></html>");
                yCoordStatus.setText("<html><b>Y: " + e.getY() + "</b></html>");
            }

        });
        drawArea.addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) drawArea.zoomIn();
            else drawArea.zoomOut();
        });
        container.add(drawArea, BorderLayout.CENTER);

        // Create color choose panel (not button)
        colorChooser = new JColorChooser((Color) drawArea.getPaint());

        // Create toolbar
        toolbar = new JToolBar(JToolBar.HORIZONTAL);
        toolbar.setBackground(toolbarColor);
        toolbar.setRequestFocusEnabled(false);
        toolbar.setBorder(new EmptyBorder(componentBorderSize, componentBorderSize, componentBorderSize, componentBorderSize));

        selectBtn = new JButton(new ImageIcon("./res/selectCursorBtn.png"));
        selectBtn.setBackground(toolbarColor);
        selectBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        selectBtn.addActionListener(toolbarAdapter);

        moveBtn = new JButton(new ImageIcon("./res/moveAroundBtn.png"));
        moveBtn.setBackground(toolbarColor);
        moveBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        moveBtn.addActionListener(toolbarAdapter);

        zoomInBtn = new JButton(new ImageIcon("./res/zoomInBtn.png"));
        zoomInBtn.setBackground(toolbarColor);
        zoomInBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        zoomInBtn.addActionListener(toolbarAdapter);

        zoomOutBtn = new JButton(new ImageIcon("./res/zoomOutBtn.png"));
        zoomOutBtn.setBackground(toolbarColor);
        zoomOutBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        zoomOutBtn.addActionListener(toolbarAdapter);

        shapeTypeComboBox = new JComboBox<>(ShapeType.getAllTypes());
        shapeTypeComboBox.setBackground(toolbarColor);
        shapeTypeComboBox.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        shapeTypeComboBox.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                    ShapeType shapeType = (ShapeType) value;
                    label.setText(shapeType.toString());
                    label.setIcon(new ImageIcon(shapeType.getIconPath()));

                    return label;
                }

        });
        shapeTypeComboBox.addActionListener(toolbarAdapter);
        shapeTypeComboBox.setMaximumSize(shapeTypeComboBox.getPreferredSize());

        toggleGridBtn = new JButton(new ImageIcon("./res/toggleGridBtn.png"));
        toggleGridBtn.setBackground(toolbarColor);
        toggleGridBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        toggleGridBtn.addActionListener(toolbarAdapter);

        changeColorBtn = new JButton("Color", new ImageIcon("./res/changeColorBtn.png"));
        changeColorBtn.setBackground(toolbarColor);
        changeColorBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        changeColorBtn.addActionListener(toolbarAdapter);

        clearBtn = new JButton("Clear", new ImageIcon("./res/clearBtn.png"));
        clearBtn.setBackground(toolbarColor);
        clearBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        clearBtn.addActionListener(toolbarAdapter);

        undoBtn = new JButton("Undo");
        undoBtn.setBackground(toolbarColor);
        undoBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        undoBtn.addActionListener(toolbarAdapter);

        redoBtn = new JButton("Redo");
        redoBtn.setBackground(toolbarColor);
        redoBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        redoBtn.addActionListener(toolbarAdapter);

        deleteBtn= new JButton("Delete");
        deleteBtn.setBackground(toolbarColor);
        deleteBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        deleteBtn.addActionListener(toolbarAdapter);

        toolbar.add(selectBtn);
        toolbar.add(moveBtn);
        toolbar.add(zoomInBtn);
        toolbar.add(zoomOutBtn);
        toolbar.add(shapeTypeComboBox);
        toolbar.add(toggleGridBtn);
        toolbar.add(changeColorBtn);
        toolbar.add(clearBtn);
        toolbar.add(undoBtn);
        toolbar.add(redoBtn);
        toolbar.add(deleteBtn);

        // Add toolbar to the main window
        container.add(toolbar, BorderLayout.NORTH);

        // Create status bar
        JPanel status_bar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status_bar.setBackground(themeColor);
        status_bar.setBorder(new EmptyBorder(componentBorderSize, componentBorderSize, componentBorderSize, componentBorderSize));

        xCoordStatus = new JLabel("<html><b>X: 0</b></html>");
        xCoordStatus.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, normalFontSize));
        xCoordStatus.setForeground(Color.white);

        yCoordStatus = new JLabel("<html><b>Y: 0</b></html>");
        yCoordStatus.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, normalFontSize));
        yCoordStatus.setForeground(Color.white);

        status_bar.add(xCoordStatus);
        status_bar.add(new JLabel("  "));
        status_bar.add(yCoordStatus);

        // Add status bar to the main window
        container.add(status_bar, BorderLayout.SOUTH);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        setMaximizedBounds(env.getMaximumWindowBounds());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private final Color themeColor;
    private final Color toolbarColor;
    private final int componentBorderSize;
    private final int normalFontSize;

    private final JToolBar toolbar;
    private final JButton selectBtn;
    private final JButton moveBtn;
    private final JButton zoomInBtn;
    private final JButton zoomOutBtn;
    private final JComboBox<ShapeType> shapeTypeComboBox;
    private final JButton toggleGridBtn;
    private final JButton changeColorBtn;
    private final JColorChooser colorChooser;
    private final JButton clearBtn;
    private final DrawArea drawArea;
    private final JLabel xCoordStatus;
    private final JLabel yCoordStatus;

    private final JButton undoBtn;
    private final JButton redoBtn;
    private final JButton deleteBtn;

    private final ActionListener toolbarAdapter;
    private final KeyAdapter keyAdapter;

}
