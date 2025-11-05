import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployManageUser extends JFrame {

    JPanel titlePanel, northPanel, centrePanel, formPanel, buttonPanel;
    JLabel lblTitle, lblName, lblIC, lblDept, lblPosition, lblSalary;
    JTextField txtName, txtIC, txtDept, txtPosition, txtSalary;
    JButton btnUpdate;
    JMenuBar mnuBarTop;
    JMenu mnuOpen;
    JMenuItem mnuExit, mnuEM, mnuLM, mnuPM;

    // logged-in user id
    private int empId;

    // default (if someone still calls without id)
    public EmployManageUser() {
        this(0);
    }

    // this is the one to call from MenuUser
    public EmployManageUser(int empId) {
        this.empId = empId;

        setSize(800, 450);
        setTitle("Employee Information (User View)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createMenu();
        loadNorthPanel();
        loadCentrePanel();

        loadUserData();

        setVisible(true);
    }

    void createMenu(){
        mnuBarTop = new JMenuBar();
        mnuOpen = new JMenu("Open");
        mnuEM = new JMenuItem("Employee management");
        mnuLM = new JMenuItem("Leave management");
        mnuPM = new JMenuItem("Payroll management");
        mnuExit = new JMenuItem("Exit");

        // wire them
        mnuEM.addActionListener(e -> {
    new EmployManageUser(empId);
    dispose();
});
mnuLM.addActionListener(e -> {
    new LeaveManageUser(empId);
    dispose();
});
mnuPM.addActionListener(e -> {
    new PayrollManageUser(empId);
    dispose();
});
mnuExit.addActionListener(e -> dispose());

        mnuOpen.add(mnuEM);
        mnuOpen.add(mnuLM);
        mnuOpen.add(mnuPM);
        mnuOpen.addSeparator();
        mnuOpen.add(mnuExit);

        mnuBarTop.add(mnuOpen);

        setJMenuBar(mnuBarTop);
    }

    void loadNorthPanel() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#05339C"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        lblTitle = new JLabel("My Information");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 24));
        titlePanel.add(lblTitle);
        northPanel.add(titlePanel);

        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 50));

        lblName = new JLabel("Name:");
        lblIC = new JLabel("IC Number:");
        lblDept = new JLabel("Department:");
        lblPosition = new JLabel("Position:");
        lblSalary = new JLabel("Basic Salary:");

        txtName = new JTextField(15);
        txtIC = new JTextField(15);
        txtDept = new JTextField(15);
        txtPosition = new JTextField(15);
        txtSalary = new JTextField(15);

        setEditable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(lblName, gbc);
        gbc.gridx = 1;
        formPanel.add(txtName, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(lblIC, gbc);
        gbc.gridx = 1;
        formPanel.add(txtIC, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(lblDept, gbc);
        gbc.gridx = 1;
        formPanel.add(txtDept, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(lblPosition, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPosition, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(lblSalary, gbc);
        gbc.gridx = 1;
        formPanel.add(txtSalary, gbc);

        northPanel.add(formPanel);
        add(northPanel, BorderLayout.NORTH);
    }

    void loadCentrePanel() {
        centrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));

        btnUpdate = new JButton("Update My Information");
        btnUpdate.setBackground(java.awt.Color.decode("#05339C"));
        btnUpdate.setForeground(Color.white);
        btnUpdate.setPreferredSize(new Dimension(200, 35));

        btnUpdate.addActionListener(new ActionListener() {
            private boolean editing = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editing) {
                    setEditable(true);
                    btnUpdate.setText("Save");
                    editing = true;
                } else {
                    saveUserData();
                    setEditable(false);
                    btnUpdate.setText("Update My Information");
                    editing = false;
                }
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.add(btnUpdate);
        centrePanel.add(buttonPanel);

        add(centrePanel, BorderLayout.CENTER);
    }

    private void setEditable(boolean b) {
        txtName.setEditable(b);
        txtIC.setEditable(b);
        txtDept.setEditable(b);
        txtPosition.setEditable(b);
        // salary should not be edited by user
        txtSalary.setEditable(false);
    }

    // ================= DB PART ===================

    void loadUserData() {
        if (empId == 0) {
            txtName.setText("Unknown user");
            return;
        }

        String sql = "SELECT name, ic_no, department, position, basic_salary FROM employee WHERE emp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    txtName.setText(rs.getString("name"));
                    txtIC.setText(rs.getString("ic_no"));
                    txtDept.setText(rs.getString("department"));
                    txtPosition.setText(rs.getString("position"));
                    txtSalary.setText(
                            rs.getBigDecimal("basic_salary") != null
                                    ? rs.getBigDecimal("basic_salary").toString()
                                    : "0.00"
                    );
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading user data: " + ex.getMessage());
        }
    }

    void saveUserData() {
        if (empId == 0) {
            JOptionPane.showMessageDialog(this, "Cannot update. Employee ID missing.");
            return;
        }

        String name = txtName.getText().trim();
        String ic = txtIC.getText().trim();
        String dept = txtDept.getText().trim();
        String pos = txtPosition.getText().trim();

        if (name.isEmpty() || ic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and IC cannot be empty.");
            return;
        }

        String sql = "UPDATE employee SET name = ?, ic_no = ?, department = ?, position = ? WHERE emp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, ic);
            ps.setString(3, dept);
            ps.setString(4, pos);
            ps.setInt(5, empId);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Your data has been updated.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating data: " + ex.getMessage());
        }
    }
}
