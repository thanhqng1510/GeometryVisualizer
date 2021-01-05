import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame implements ActionListener {

    private final JButton clearBtn;
    private final JButton redBtn;
    private final JButton greenBtn;
    private final JButton blueBtn;
    private final JButton blackBtn;
    private final JButton magentaBtn;
    private final DrawArea drawArea;

    private String userEmail = null;

    public MainWindow(String email) {
        super("Untitled*");
        // TODO: work-around
        this.userEmail = email;
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Add draw area to the main window
        drawArea = new DrawArea();
        container.add(drawArea, BorderLayout.CENTER);
        //Create control bar
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

        JMenuItem fileOR= new JMenuItem("Open recent");
        fileOR.setActionCommand("Open recent");
        fileOR.addActionListener(this);

        JMenuItem fileSave= new JMenuItem("Save");
        fileSave.setActionCommand("Save");
        fileSave.addActionListener(this);

        JMenuItem fileSaveAs= new JMenuItem("Save as");
        fileSaveAs.setActionCommand("Save as");
        fileSaveAs.addActionListener(this);

        JMenuItem fileClose= new JMenuItem("Close");
        fileClose.setActionCommand("Close");
        fileClose.addActionListener(this);

        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileOpenProJect);
        file.add(fileOR);
        file.add(fileSave);
        file.add(fileSaveAs);
        file.add(fileClose);

        menuBar.add(file);
        setJMenuBar(menuBar);

        container.add(menuBar, BorderLayout.NORTH);
        // Create control bar
        JPanel control_bar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        control_bar.setBackground(Color.decode("#3598DC"));
        control_bar.setBorder(new EmptyBorder(25, 5, 5, 5));

        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);

        redBtn = new JButton("Red");
        redBtn.addActionListener(this);

        greenBtn = new JButton("Green");
        greenBtn.addActionListener(this);

        blueBtn = new JButton("Blue");
        blueBtn.addActionListener(this);

        blackBtn = new JButton("Black");
        blackBtn.addActionListener(this);

        magentaBtn = new JButton("Magenta");
        magentaBtn.addActionListener(this);

        control_bar.add(clearBtn);
        control_bar.add(redBtn);
        control_bar.add(greenBtn);
        control_bar.add(blueBtn);
        control_bar.add(blackBtn);
        control_bar.add(magentaBtn);

        // Add control bar to the main window
        container.add(control_bar, BorderLayout.NORTH);

        // Create status bar
        JPanel status_bar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status_bar.setBackground(Color.decode("#3598DC"));
        status_bar.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel x_coord = new JLabel("X: 100");
        x_coord.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        x_coord.setForeground(Color.white);

        JLabel y_coord = new JLabel("Y: 100");
        y_coord.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        y_coord.setForeground(Color.white);

        status_bar.add(x_coord);
        status_bar.add(new JLabel("\t"));
        status_bar.add(y_coord);

        // Add status bar to the main window
        container.add(status_bar, BorderLayout.SOUTH);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        setMaximizedBounds(env.getMaximumWindowBounds());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if (e.getSource() == clearBtn)
            drawArea.clear();
        else if (e.getSource() == redBtn)
            drawArea.setColor(Color.red);
        else if (e.getSource() == greenBtn)
            drawArea.setColor(Color.green);
        else if (e.getSource() == blueBtn)
            drawArea.setColor(Color.blue);
        else if (e.getSource() == blackBtn)
            drawArea.setColor(Color.black);
        else if (e.getSource() == magentaBtn)
            drawArea.setColor(Color.magenta);
        else if (e.getActionCommand() == "Save"){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                drawArea.ImageTofile(fileToSave.getAbsolutePath() + ".jpg");
                MongoDb db = new MongoDb(userEmail);
                db.uploadimage(fileToSave.getAbsolutePath() + ".jpg",fileToSave.getName());
            }
        }
        else if (e.getActionCommand() == "Open"){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    Image ip = ImageIO.read(fileToSave);
                    drawArea.setImage(ip);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        else if (e.getActionCommand() == "OpenCloud"){
            MongoDb db = new MongoDb(userEmail);
            MongoDb.retrieveimage();
        }
    }

}