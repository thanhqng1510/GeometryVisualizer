import javax.swing.*;
import java.awt.*;


public class Application {

    public Application() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        themeColor = Color.decode("#3598DC");
    }

    public void start() {
        new MainWindow(themeColor);
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }

    private final Color themeColor;

}
