import javax.swing.*;
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
        super("Geometry Visualizer");

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Add draw area to the main window
        drawArea = new DrawArea();
        container.add(drawArea, BorderLayout.CENTER);

        // Create control bar
        JPanel control_bar = new JPanel();

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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
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
