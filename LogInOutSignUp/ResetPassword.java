package LogInOutSignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResetPassword extends JFrame implements ActionListener, MouseListener {
    private final JButton reset;
    private final JButton signIn;

    ResetPassword(){
        reset= new JButton("RESET");
        signIn= new JButton("Sign in");
        add(title());
        add(message());
        add(pressUsername());
        add(getReset());
        add(waring());
        add(getSignIn());
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(278, 327);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JLabel title(){
        JLabel label= new JLabel("Reset Password");
        label.setBounds(42, 23, 198, 34);
        Font font= new Font("Roboto",Font.PLAIN,26);
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public JLabel message(){
        String text="We will send a new password to<br> the email address register with<br> your username";
        JLabel label= new JLabel("<html><div style='text-align: center;'>"+text+"</html>");
        label.setBounds(34, 66, 215, 48);
        Font font= new Font("Roboto",Font.PLAIN,13);
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public JPanel pressUsername(){
        JPanel panel= new JPanel();
        JTextField user= new JTextField(20);
        JLabel textUser= new JLabel("Username");
        textUser.setForeground(new Color(0x939393));
        panel.setLayout(new BorderLayout());
        panel.setBounds(39,131,201,40);
        panel.add(user);
        user.setLayout(new BorderLayout());
        textUser.setBounds(1,10,73,23);
        user.add(textUser);
        user.setBackground(new Color(0xF4F2F2));
        Font font= new Font("Roboto", Font.PLAIN,14);
        panel.setFont(font);
        user.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyTyped(KeyEvent e) {
                                    if(e.getKeyChar()!=KeyEvent.VK_BACK_SPACE)
                                        textUser.setVisible(false);
                                    else
                                    {
                                        if(user.getText().equals(""))
                                            textUser.setVisible(true);
                                    }
                                }
                            }

        );
        return panel;
    }

    public JButton getReset(){
        reset.addActionListener(this);
        reset.addMouseListener(this);
        reset.setBounds(39,196,201, 37);
        reset.setBackground(new Color(0x3598DC));
        reset.setForeground(new Color(0xFFFFFF));
        reset.setOpaque(true); //display background
        reset.setBorderPainted(false);// remove border button
        Font font2= new Font("Roboto", Font.PLAIN,16);
        reset.setFont(font2);
        return reset;
    }

    public JLabel waring(){
        JLabel war= new JLabel("Username don't exist");
        Font font= new Font("Roboto",Font.ITALIC,13);
        war.setBounds(47,245,185,20);
        war.setFont(font);
        war.setHorizontalAlignment(SwingConstants.CENTER);
        war.setForeground(new Color(0xFF0000));
        return war;
    }

    public JButton getSignIn(){
        Font font= new Font("Roboto", Font.ITALIC, 13);
        signIn.setFont(font);
        signIn.setForeground(new Color(0x3598DC));
        signIn.addActionListener(this);
        signIn.addMouseListener(this);
        signIn.setBounds(5,269, 268,20);
        signIn.setHorizontalAlignment(SwingConstants.CENTER);
        signIn.setBorder(null);
        return signIn;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==reset)
        {
            setVisible(false);
            NewWindow nw= new NewWindow();
        }
        if(e.getSource()==signIn)
        {
            setVisible(false);
            Login li= new Login();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==reset)
            reset.setBackground(new Color(0x4682b4));
        if(e.getSource()==signIn)
            signIn.setForeground(new Color(0x4682b4));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==reset)
            reset.setBackground(new Color(0x3598DC));
        if(e.getSource()==signIn)
            signIn.setForeground(new Color(0x3598DC));
    }
}
