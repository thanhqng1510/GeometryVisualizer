package LogInOutSignUp;

import javax.swing.*;

public class NewWindow {
    NewWindow(){
        JFrame frame= new JFrame();
        frame.setTitle("New Window");
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
