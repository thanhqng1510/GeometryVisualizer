import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindow extends JFrame implements ActionListener {

    private final JButton clearBtn;
    private final JButton redBtn;
    private final JButton greenBtn;
    private final JButton blueBtn;
    private final JButton blackBtn;
    private final JButton magentaBtn;
    private final DrawArea drawArea;

    public MainWindow() {
        super("Untitled*");
        // TODO: work-around

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Add draw area to the main window
        drawArea = new DrawArea();
        container.add(drawArea, BorderLayout.CENTER);

        // Create control bar
        JPanel control_bar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        control_bar.setBackground(Color.decode("#3598DC"));
        control_bar.setBorder(new EmptyBorder(5, 5, 5, 5));

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
    }

}