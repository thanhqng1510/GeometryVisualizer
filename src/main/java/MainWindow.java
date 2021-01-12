import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;


public class MainWindow extends JFrame implements ActionListener{
    private String userEmail;
    private Vector<String> cloud;
    public MainWindow(Color themeColor,String userEmail) {
        super("Untitled*");
        this.userEmail = userEmail;
        // TODO: work-around

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JMenuBar menuBar= new JMenuBar();
        //System.setProperty("apple.laf.useScreenMenuBar", "true");

        JMenu file= new JMenu("File");
        JMenuItem fileNew= new JMenuItem("New");
        //fileNew.setMnemonic(KeyEvent.VK_N);
        fileNew.setActionCommand("New");
        fileNew.addActionListener(this);

        JMenuItem fileOpen= new JMenuItem("Open");
        fileOpen.setActionCommand("Open");
        fileOpen.addActionListener(this);

        JMenuItem fileOpenProJect = new JMenuItem("Open Cloud");
        fileOpenProJect.setActionCommand("OpenCloud");
        fileOpenProJect.addActionListener(this);

        JMenuItem fileSave= new JMenuItem("Save");
        fileSave.setActionCommand("Save");
        fileSave.addActionListener(this);

        JMenuItem fileSaveAs= new JMenuItem("Save as");
        fileSaveAs.setActionCommand("Save as");
        fileSaveAs.addActionListener(this);

        JMenuItem fileClose= new JMenuItem("Close");
        fileClose.setActionCommand("Close");
        fileClose.addActionListener(this);

        JMenu file2 = new JMenu("Open Cloud");

        MongoDb db = new MongoDb(userEmail);
        cloud = MongoDb.retrieveimage();

        Vector<JMenuItem> cloudItem = new Vector<>();

        for (int i=0;i<cloud.size();i++){
            JMenuItem tmp = new JMenuItem(cloud.get(i));
            tmp.setActionCommand("cloud-" + i);
            tmp.addActionListener(this);
            file2.add(tmp);
        }

        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileOpenProJect);
        file.add(fileSave);
        file.add(fileSaveAs);
        file.add(fileClose);

        menuBar.add(file);
        menuBar.add(file2);
        setJMenuBar(menuBar);

        container.add(menuBar, BorderLayout.NORTH);

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
            if (e.getWheelRotation() < 0) drawArea.zoomIn(1);
            else drawArea.zoomOut(1);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "New"){
            drawArea.clearScreen();
        }else if (e.getActionCommand() == "Close"){
            System.exit(0);
        }else
        if (e.getActionCommand() == "Save"){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                drawArea.ImageTofile(fileToSave.getAbsolutePath() + ".jpg");
                MongoDb db = new MongoDb(userEmail);
                db.uploadimage(fileToSave.getAbsolutePath() + ".jpg", fileToSave.getName());
            }
        }else if (e.getActionCommand() == "Save as"){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileTode = fileChooser.getSelectedFile();
                if (fileTode.delete()){
                    File fileToSave = fileChooser.getSelectedFile();
                    drawArea.ImageTofile(fileToSave.getAbsolutePath() + ".jpg");
                    MongoDb db = new MongoDb(userEmail);
                    db.uploadimage(fileToSave.getAbsolutePath() + ".jpg", fileToSave.getName());
                }
            }
        }
        else if (e.getActionCommand() == "Open"){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to Open");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println(fileToSave);
                try {
                    Image ip = ImageIO.read(fileToSave);
                    drawArea.setImage(ip);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        else if (e.getActionCommand().contains("cloud")) {
            int index = Integer.parseInt(e.getActionCommand().split("-")[1]);
            String name = "C:/Users/hoang/GV/" + this.userEmail + "-" + cloud.get(index) + ".jpg";
            File fileToOpen = new File(name);
            System.out.println(name);
            try {
                Image ip = ImageIO.read(fileToOpen);
                drawArea.setImage(ip);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
