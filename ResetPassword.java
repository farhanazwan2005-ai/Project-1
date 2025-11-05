import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ResetPassword extends JFrame {

    JPanel topPanel, centrePanel, containerPanel, buttonPanel;
    JLabel lblTitle, lblUser, lblNewPass;
    JTextField txtUser;
    JPasswordField txtNewPass;
    JButton btnSave, btnCancel;

    public ResetPassword() {
        setSize(400,400);
        setTitle("Reset Password");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setUndecorated(false);  // you can make it true if you like suffering

        loadTop();
        loadCentre();

        setVisible(true);
    }

    void loadTop() {
        topPanel = new JPanel();
        topPanel.setBackground(java.awt.Color.decode("#4A70A9"));
        topPanel.setPreferredSize(new Dimension(400,60));

        lblTitle = new JLabel("Reset Password");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));

        topPanel.add(lblTitle);
        add(topPanel, BorderLayout.NORTH);
    }

    void loadCentre() {
        centrePanel = new JPanel();
        centrePanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 0, 60));
        centrePanel.setLayout(new GridLayout(4,1,0,10));

        lblUser = new JLabel("Username / Email");
        txtUser = new JTextField(20);

        lblNewPass = new JLabel("New Password");
        txtNewPass = new JPasswordField(20);

        centrePanel.add(lblUser);
        centrePanel.add(txtUser);
        centrePanel.add(lblNewPass);
        centrePanel.add(txtNewPass);

        buttonPanel = new JPanel();
        btnSave = new JButton("Save");
        btnSave.setBackground(java.awt.Color.decode("#4A70A9"));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusable(false);
        btnSave.setPreferredSize(new Dimension(120,35));

        btnCancel = new JButton("Cancel");
        btnCancel.setFocusable(false);
        btnCancel.setPreferredSize(new Dimension(120,35));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // actions
        btnSave.addActionListener(e -> doReset());
        btnCancel.addActionListener(e -> dispose());

        add(centrePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void doReset() {
        String user = txtUser.getText().trim();
        String newPass = new String(txtNewPass.getPassword()).trim();

        if (user.isEmpty() || newPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill both fields.");
            return;
        }

        String sql = "UPDATE employee SET password = ? WHERE (email = ? OR name = ?) AND status = 'active'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPass);
            ps.setString(2, user);
            ps.setString(3, user);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Password reset successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "User not found or inactive.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
