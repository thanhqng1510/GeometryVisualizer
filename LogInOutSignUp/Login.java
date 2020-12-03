package LogInOutSignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener, MouseListener {
    private final JButton forgotPass;
    private final JButton draw;
    private final JButton signup;

    Login(){
        forgotPass= new JButton("Forgot password?");
        draw= new JButton("LET'S DRAW");
        signup= new JButton("Sign up now");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(278, 327);
        setResizable(false);
        add(title());
        add(pressUsername());
        add(pressPassword());
        add(forgotPassword());
        add(letDraw());
        add(waring());
        add(textFooter());
        add(signUp());
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public JLabel title(){
        JLabel label= new JLabel("Login");
        label.setBounds(80, 20, 118, 34);
        Font font= new Font("Roboto",Font.PLAIN,26);
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
        panel.setBounds(39,59,201,40);
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

    public JPanel pressPassword(){
        JPanel panel= new JPanel();
        JPasswordField password= new JPasswordField(20);
        JLabel textPass= new JLabel("Password");
        textPass.setForeground(new Color(0x939393));
        panel.setLayout(new BorderLayout());
        panel.setBounds(39,113,201,40);
        panel.add(password);
        password.setLayout(new BorderLayout());
        textPass.setBounds(1,10,73,23);
        password.add(textPass);
        password.setBackground(new Color(0xF4F2f2));
        Font font= new Font("Roboto", Font.PLAIN,14);
        panel.setFont(font);
        password.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyTyped(KeyEvent e) {
                                        if(e.getKeyChar()!=KeyEvent.VK_BACK_SPACE)
                                            textPass.setVisible(false);
                                        else
                                        {
                                            if(password.getText().equals(""))
                                                textPass.setVisible(true);
                                        }
                                    }
                                }

        );
        return panel;
    }

    public JButton forgotPassword(){
        forgotPass.addActionListener(this);
        forgotPass.addMouseListener(this);
        forgotPass.setBounds(41,159,115,22);
        forgotPass.setForeground(new Color(0x3598DC));
        forgotPass.setLayout(new BorderLayout());
        forgotPass.setHorizontalAlignment(SwingConstants.LEFT);
        Font font1= new Font("Roboto", Font.PLAIN,13);
        forgotPass.setFont(font1);
        forgotPass.setBorder(null);
        return forgotPass;
    }

    public JButton letDraw(){
        draw.addActionListener(this);
        draw.addMouseListener(this);
        draw.setBounds(39,198,201, 37);
        draw.setBackground(new Color(0x3598DC));
        draw.setForeground(new Color(0xFFFFFF));
        draw.setOpaque(true); //display background
        draw.setBorderPainted(false);// remove border button
        Font font2= new Font("Roboto", Font.PLAIN,16);
        draw.setFont(font2);
        return draw;
    }

    public JLabel waring(){
        JLabel war= new JLabel("Wrong username or password");
        Font font= new Font("Roboto",Font.ITALIC,13);
        war.setBounds(45,246,189,20);
        war.setFont(font);
        war.setHorizontalAlignment(SwingConstants.CENTER);
        war.setForeground(new Color(0xFF0000));
        return war;
    }

    public JLabel textFooter(){
        JLabel label= new JLabel("Didn't have an account? ");
        Font font= new Font("Roboto", Font.PLAIN, 13);
        label.setFont(font);
        label.setBounds(25,271,169,20);
        return label;
    }

    public JButton signUp(){
        signup.addActionListener(this);
        signup.addMouseListener(this);
        signup.setBounds(175,271, 80,20);
        signup.setBorder(null);
        Font font= new Font("Roboto", Font.ITALIC, 13);
        signup.setFont(font);
        signup.setForeground(new Color(0x3598DC));
        return signup;
    }

//    public JPanel footer(){
//        JPanel panel= new JPanel();
//        panel.setBounds(25,271, 248,20);
//        panel.setLayout(new BorderLayout());
//        panel.add(textFooter(),BorderLayout.WEST);
//        panel.add(signUp());
//        signUp().setHorizontalAlignment(SwingConstants.LEFT);
//        return panel;
//    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==forgotPass)
        {
            setVisible(false);
            ResetPassword rp= new ResetPassword();
        }
        else if(e.getSource()==draw)
        {
            setVisible(false);
            NewWindow nw= new NewWindow();
        }
        else if(e.getSource()==signup)
        {
            setVisible(false);
            SignUp su= new SignUp();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==forgotPass)
            forgotPass.setForeground(new Color(0x4682b4));
        if(e.getSource()==draw)
            draw.setBackground(new Color(0x4682b4));
        if(e.getSource()==signup)
            signup.setForeground(new Color(0x4682b4));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==forgotPass)
            forgotPass.setForeground(new Color(0x3598DC));
        if(e.getSource()==draw)
            draw.setBackground(new Color(0x3598DC));
        if(e.getSource()==signup)
            signup.setForeground(new Color(0x3598DC));
    }
}
