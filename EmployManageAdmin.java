import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class EmployManageAdmin extends JFrame{

    JPanel titlePanel, northPanel, containerPanel, centrePanel, buttonPanel, itemPanel;
    JLabel lblTitle, lblName, lblIC, lblDept, lblPosition, lblSalary;
    JTextField txtName, txtIC, txtDept, txtPosition, txtSalary;
    JMenuBar mnuBarTop;
    JButton btnUpdate, btnDelete, btnAdd, btnView, btnSubmit;
    JTable tblView;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    DefaultTableModel model;

    public EmployManageAdmin(){
        setLayout(new BorderLayout());

        setSize(1200,800);
        setTitle("Employee management (Admin)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu();
        loadTop();
        loadCentre();

        // load data from database at startup
        loadEmployees();

        // table click to fill text fields
        tblView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblView.getSelectedRow();
                if (row != -1) {
                    txtName.setText(model.getValueAt(row, 1).toString());
                    txtIC.setText(model.getValueAt(row, 2).toString());
                    txtDept.setText(model.getValueAt(row, 3).toString());
                    txtPosition.setText(model.getValueAt(row, 4).toString());
                    txtSalary.setText(model.getValueAt(row, 5).toString());
                }
            }
        });

        // button actions
        btnView.addActionListener(e -> loadEmployees());
        btnAdd.addActionListener(e -> addEmployee());
        btnSubmit.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());

        setVisible(true);
    }

    void loadTop(){
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblTitle = new JLabel("Employee Management (Admin)");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);

        itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        lblName = new JLabel("Name:");
        lblIC = new JLabel("IC Number:");
        lblDept = new JLabel("Department:");
        lblPosition = new JLabel("Position:");
        lblSalary = new JLabel("Salary:");

        txtName = new JTextField(12);
        txtIC = new JTextField(12);
        txtDept = new JTextField(12);
        txtPosition = new JTextField(12);
        txtSalary = new JTextField(12);

        itemPanel.add(lblName);
        itemPanel.add(txtName);
        itemPanel.add(lblIC);
        itemPanel.add(txtIC);
        itemPanel.add(lblDept);
        itemPanel.add(txtDept);
        itemPanel.add(lblPosition);
        itemPanel.add(txtPosition);
        itemPanel.add(lblSalary);
        itemPanel.add(txtSalary);

        northPanel.add(titlePanel);
        northPanel.add(itemPanel);

        add(northPanel, BorderLayout.NORTH);
    }

    void loadCentre(){
        centrePanel = new JPanel();
        centrePanel.setLayout(new BorderLayout());

        String[] columnNames = {"ID","Name", "IC Number", "Department", "Position", "Basic Salary"};

        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblView = new JTable(model);
        tblView.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tblView);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));

        // hide ID column
        tblView.getColumnModel().getColumn(0).setMinWidth(0);
        tblView.getColumnModel().getColumn(0).setMaxWidth(0);
        tblView.getColumnModel().getColumn(0).setWidth(0);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        btnAdd = new JButton("Add New");
        btnAdd.setBackground(java.awt.Color.decode("#1055C9"));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusable(false);
        btnAdd.setPreferredSize(new Dimension(150, 30));

        btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(java.awt.Color.decode("#41A67E"));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusable(false);
        btnUpdate.setPreferredSize(new Dimension(150, 30));

        btnDelete = new JButton("Delete Employee");
        btnDelete.setBackground(java.awt.Color.decode("#BF092F"));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusable(false);
        btnDelete.setPreferredSize(new Dimension(150, 30));

        btnView = new JButton("View All");
        btnView.setBackground(java.awt.Color.decode("#132440"));
        btnView.setForeground(Color.WHITE);
        btnView.setFocusable(false);
        btnView.setPreferredSize(new Dimension(150, 30));

        btnSubmit = new JButton("Save");
        btnSubmit.setBackground(java.awt.Color.decode("#016B61"));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusable(false);
        btnSubmit.setPreferredSize(new Dimension(150, 30));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);
        buttonPanel.add(btnSubmit);

        centrePanel.add(scrollPane, BorderLayout.CENTER);
        centrePanel.add(buttonPanel, BorderLayout.SOUTH);

        add(centrePanel, BorderLayout.CENTER);
    }

    void createMenu(){
        mnuBarTop = new JMenuBar();
        mnuOpen = new JMenu("Open");
        mnuEM = new JMenuItem("Employee management");
        mnuLM = new JMenuItem("Leave management");
        mnuPM = new JMenuItem("Payroll management");
        mnuExit = new JMenuItem("Exit");

        // wire menu items
       mnuEM.addActionListener(e -> {
    new EmployManageAdmin();
    dispose();
});
mnuLM.addActionListener(e -> {
    new LeaveManageAdmin();
    dispose();
});
mnuPM.addActionListener(e -> {
    new PayrollManageAdmin();
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

    // ================== DB METHODS =====================

    private void loadEmployees() {
        model.setRowCount(0);
        String sql = "SELECT emp_id, name, ic_no, department, position, basic_salary FROM employee";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("ic_no"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getBigDecimal("basic_salary")
                };
                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading employees: " + ex.getMessage());
        }
    }

    private void addEmployee() {
        String name = txtName.getText().trim();
        String ic = txtIC.getText().trim();
        String dept = txtDept.getText().trim();
        String pos = txtPosition.getText().trim();
        String salaryText = txtSalary.getText().trim();

        if (name.isEmpty() || ic.isEmpty() || dept.isEmpty() || pos.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            double salary = Double.parseDouble(salaryText);

            String email = name.toLowerCase().replace(" ", "") + "@company.com";

            String sql = "INSERT INTO employee (name, ic_no, department, position, basic_salary, email, password, role, status) " +
                         "VALUES (?,?,?,?,?,?,?,?,?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, ic);
                ps.setString(3, dept);
                ps.setString(4, pos);
                ps.setDouble(5, salary);
                ps.setString(6, email);
                ps.setString(7, "123456");   // default password
                ps.setString(8, "user");     // default role
                ps.setString(9, "active");

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Employee added.");
                clearForm();
                loadEmployees();
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Salary must be a number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage());
        }
    }

    private void updateEmployee() {
        int row = tblView.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee from the table.");
            return;
        }

        int empId = (int) model.getValueAt(row, 0);

        String name = txtName.getText().trim();
        String ic = txtIC.getText().trim();
        String dept = txtDept.getText().trim();
        String pos = txtPosition.getText().trim();
        String salaryText = txtSalary.getText().trim();

        if (name.isEmpty() || ic.isEmpty() || dept.isEmpty() || pos.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            double salary = Double.parseDouble(salaryText);

            String sql = "UPDATE employee SET name=?, ic_no=?, department=?, position=?, basic_salary=? WHERE emp_id=?";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, ic);
                ps.setString(3, dept);
                ps.setString(4, pos);
                ps.setDouble(5, salary);
                ps.setInt(6, empId);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Employee updated.");
                clearForm();
                loadEmployees();
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Salary must be a number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + ex.getMessage());
        }
    }

    private void deleteEmployee() {
        int row = tblView.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee from the table.");
            return;
        }

        int empId = (int) model.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM employee WHERE emp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee deleted.");
            clearForm();
            loadEmployees();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting employee: " + ex.getMessage());
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtIC.setText("");
        txtDept.setText("");
        txtPosition.setText("");
        txtSalary.setText("");
    }
}
