
import javax.swing.*;
import java.awt.*;


public class Application {
    private String userEmail;
    public Application(String userEmail) {
        this.userEmail = userEmail;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        themeColor = Color.decode("#3598DC");
    }

    public void start() {
        MainWindow main_window = new MainWindow(themeColor,userEmail);
    }

    public void exit() {

    }

    public static void main(String[] args) {
        Login sa = new Login();
    }

    private final Color themeColor;

}