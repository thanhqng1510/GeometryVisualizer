package LogInOutSignUp;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUp extends JFrame implements ActionListener, MouseListener {
    private final JButton signUp;
    private final JButton signIn;

//    private SingUpService singUpService = new SingUpServiceImpl();
    SignUp(){
        signIn= new JButton("Sign In");
        signUp= new JButton("SIGN UP");
        add(title());
        add(pressUsername());
        add(pressEmail());
        add(pressPassword());
        add(getSignUp());
        add(waring());
        add(getSignIn());
        setLayout(null);
        setSize(278, 390);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JLabel title(){
        JLabel label= new JLabel("Sign Up");
        label.setBounds(80, 23, 118, 34);
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
        panel.setBounds(39,77,201,40);
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

    public JPanel pressEmail(){
        JPanel panel= new JPanel();
        JTextField user= new JTextField(20);
        JLabel textUser= new JLabel("Email");
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

    public JPanel pressPassword(){
        JPanel panel= new JPanel();
        JPasswordField password= new JPasswordField(20);
        JLabel textPass= new JLabel("Password");
        textPass.setForeground(new Color(0x939393));
        panel.setLayout(new BorderLayout());
        panel.setBounds(39,186,201,40);
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

    public JButton getSignUp(){
        signUp.addActionListener(this);
        signUp.addMouseListener(this);
        signUp.setBounds(39,253 ,201, 37);
        signUp.setBackground(new Color(0x3598DC));
        signUp.setForeground(new Color(0xFFFFFF));
        signUp.setOpaque(true); //display background
        signUp.setBorderPainted(false);// remove border button
        Font font= new Font("Roboto", Font.PLAIN,16);
        signUp.setFont(font);
        return signUp;
    }

    public JLabel waring(){
        JLabel war= new JLabel("Username or email already exist");
        Font font= new Font("Roboto",Font.ITALIC,13);
        war.setBounds(32,303,215,20);
        war.setFont(font);
        war.setHorizontalAlignment(SwingConstants.CENTER);
        war.setForeground(new Color(0xFF0000));
        return war;
    }

    public JButton getSignIn(){
        signIn.setLayout(new BorderLayout());
        Font font= new Font("Roboto", Font.ITALIC, 13);
        signIn.setFont(font);
        signIn.setForeground(new Color(0x3598DC));
        signIn.addActionListener(this);
        signIn.addMouseListener(this);
        signIn.setBounds(5,328, 268,20);
        signIn.setHorizontalAlignment(SwingConstants.CENTER);
        signIn.setBorder(null);
        return signIn;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==signUp)
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
        if(e.getSource()==signUp)
            signUp.setBackground(new Color(0x4682b4));
        if(e.getSource()==signIn)
            signIn.setForeground(new Color(0x4682b4));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()== signUp)
            signUp.setBackground(new Color(0x3598DC));
        if(e.getSource()==signIn)
            signIn.setForeground(new Color(0x3598DC));
    }
}
