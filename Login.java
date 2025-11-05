import java.awt.*;
import javax.swing.*;


public class Login extends JFrame {
    JPanel topPanel, centrePanel, centPanel, buttonPanel;
    JLabel topTitle, lblUser, lblPass;
    JTextField txtUser, txtPass;
    JButton btLogin;
   public Login() {
    setSize(400,500);
    setTitle("Log In");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setUndecorated(true);

    loadTop();
    loadCentre();

    setVisible(true);
   }

   void loadTop(){
    topPanel = new JPanel();
    topPanel.setBackground(java.awt.Color.decode("#4A70A9"));
    
    topTitle = new JLabel();
    topTitle.setText("Log In");
    topTitle.setFont(new Font("Arial", Font.BOLD, 38));
    topTitle.setForeground(Color.WHITE);
    topPanel.add(topTitle);

    add(topPanel, BorderLayout.NORTH);
   }

   void loadCentre(){
    centrePanel = new JPanel();
    centrePanel.setBorder(BorderFactory.createEmptyBorder(30, 120, 0, 120));
    centrePanel.setLayout(new GridLayout(5,1,0,0));

    centPanel = new JPanel();
    centPanel.setBorder(BorderFactory.createEmptyBorder(60,0,0,0));
    centPanel.setLayout(new FlowLayout());

    buttonPanel = new JPanel();

    ImageIcon icon = new ImageIcon("user.png");
    JLabel userIcon = new JLabel();
    userIcon.setIcon(icon);

    lblUser = new JLabel("Username");
    lblPass = new JLabel("Password");

    txtUser = new JTextField(20);
    txtPass = new JTextField(20);

    btLogin = new JButton("Log In");
    btLogin.setBackground(java.awt.Color.decode("#4A70A9"));
    btLogin.setForeground(Color.white);
    btLogin.setFocusable(false);
    btLogin.setPreferredSize(new Dimension(150, 40));

    centrePanel.add(lblUser);
    centrePanel.add(txtUser);
    centrePanel.add(lblPass);
    centrePanel.add(txtPass);
    buttonPanel.add(btLogin);

    centPanel.add(userIcon, BorderLayout.NORTH);
    centPanel.add(centrePanel);
    centPanel.add(buttonPanel);

    add(centPanel);
   }

   
}
