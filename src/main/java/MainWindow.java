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
                if (e.getSource() == selectCursorBtn) {
                    drawArea.setCursorMode(CursorMode.SELECT);
                    drawArea.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                else if (e.getSource() == clearBtn)
                    drawArea.clearScreen();
                else if (e.getSource() == changeColorBtn) {
                    JDialog dialog = JColorChooser.createDialog(
                            drawArea, "Choose color", false, colorChooser,
                            e1 -> drawArea.setPaint(colorChooser.getColor()), null);
                    dialog.setVisible(true);
                }
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
        drawArea.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                xCoordStatus.setText("<html><b>X: " + e.getX() + "</b></html>");
                yCoordStatus.setText("<html><b>Y: " + e.getY() + "</b></html>");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                xCoordStatus.setText("<html><b>X: " + e.getX() + "</b></html>");
                yCoordStatus.setText("<html><b>Y: " + e.getY() + "</b></html>");
            }

        });
        drawArea.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (!hasFocus())
                    requestFocus();
            }

        });
        container.add(drawArea, BorderLayout.CENTER);

        // Create color choose panel (not button)
        colorChooser = new JColorChooser((Color) drawArea.getPaint());

        // Create toolbar
        toolbar = new JToolBar(JToolBar.HORIZONTAL);
        toolbar.setBackground(toolbarColor);
        toolbar.setRequestFocusEnabled(false);
        toolbar.setBorder(new EmptyBorder(componentBorderSize, componentBorderSize, componentBorderSize, componentBorderSize));

        selectCursorBtn = new JButton(new ImageIcon("./res/selectCursorBtn.png"));
        selectCursorBtn.setBackground(toolbarColor);
        selectCursorBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        selectCursorBtn.addActionListener(toolbarAdapter);

        shapeTypeComboBox = new JComboBox<>(ShapeType.getAllTypes());
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
        shapeTypeComboBox.addActionListener(e -> {
            ShapeType shapeType = (ShapeType) ((JComboBox) e.getSource()).getSelectedItem();
            drawArea.setDrawShapeType(shapeType);
            drawArea.setCursorMode(CursorMode.DRAW);
            drawArea.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        });
        shapeTypeComboBox.setMaximumSize(shapeTypeComboBox.getPreferredSize());

        changeColorBtn = new JButton("Color", new ImageIcon("./res/changeColorBtn.png"));
        changeColorBtn.setBackground(toolbarColor);
        changeColorBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        changeColorBtn.addActionListener(toolbarAdapter);

        clearBtn = new JButton("Clear", new ImageIcon("./res/clearBtn.png"));
        clearBtn.setBackground(toolbarColor);
        clearBtn.setBorder(new EmptyBorder(0, componentBorderSize, 0, componentBorderSize));
        clearBtn.addActionListener(toolbarAdapter);

        toolbar.add(selectCursorBtn);
        toolbar.add(shapeTypeComboBox);
        toolbar.add(changeColorBtn);
        toolbar.add(clearBtn);

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
    private final JButton selectCursorBtn;
    private final JComboBox<ShapeType> shapeTypeComboBox;
    private final JButton changeColorBtn;
    private final JColorChooser colorChooser;
    private final JButton clearBtn;
    private final DrawArea drawArea;
    private final JLabel xCoordStatus;
    private final JLabel yCoordStatus;

    private final ActionListener toolbarAdapter;
    private final KeyAdapter keyAdapter;

}
