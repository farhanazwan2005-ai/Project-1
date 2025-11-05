import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame {
    JPanel topPanel, centrePanel, centPanel, buttonPanel;
    JLabel topTitle, lblUser, lblPass;
    JTextField txtUser, txtPass;
    JButton btLogin, btReset;

    public Login() {
        setSize(400,500);
        setTitle("Log In");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);

        loadTop();
        loadCentre();

        // login button
        btLogin.addActionListener(e -> doLogin());

        // enter key on password
        txtPass.addActionListener(e -> doLogin());

        // reset button
        btReset.addActionListener(e -> {
            // open reset page
            new ResetPassword();
            // you can dispose() here if you want only 1 window
            // dispose();
        });

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

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

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

        // new reset button
        btReset = new JButton("Reset");
        btReset.setBackground(Color.LIGHT_GRAY);
        btReset.setForeground(Color.BLACK);
        btReset.setFocusable(false);
        btReset.setPreferredSize(new Dimension(80, 30));

        centrePanel.add(lblUser);
        centrePanel.add(txtUser);
        centrePanel.add(lblPass);
        centrePanel.add(txtPass);

        buttonPanel.add(btLogin);
        buttonPanel.add(btReset);

        centPanel.add(userIcon, BorderLayout.NORTH);
        centPanel.add(centrePanel);
        centPanel.add(buttonPanel);

        add(centPanel);
    }

    private void doLogin() {
        String username = txtUser.getText().trim();
        String password = txtPass.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Cannot connect to database.");
                return;
            }

            String sql = "SELECT emp_id, name, role FROM employee " +
                         "WHERE (email = ? OR name = ?) AND password = ? AND status = 'active' LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int empId = rs.getInt("emp_id");
                String empName = rs.getString("name");
                String role = rs.getString("role");

                JOptionPane.showMessageDialog(this, "Login successful. Welcome " + empName);

                if ("admin".equalsIgnoreCase(role)) {
                    new MenuAdmin(empName);
                } else {
                    new MenuUser(empId, empName);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials or inactive user.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
