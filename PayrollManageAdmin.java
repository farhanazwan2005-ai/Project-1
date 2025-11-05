import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class PayrollManageAdmin extends JFrame {

    JPanel titlePanel, northPanel, centrePanel, buttonPanel, itemPanel;
    JLabel lblTitle, lblEmpID, lblBasic, lblAllowances, lblDeductions, lblNetPay;
    JTextField txtEmpID, txtBasic, txtAllowances, txtDeductions, txtNetPay;
    JMenuBar mnuBarTop;
    JButton btnCalculate, btnStore, btnAdd, btnDelete, btnUpdate, btnView;
    JTable tblView;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    DefaultTableModel model;

    public PayrollManageAdmin() {
        setLayout(new BorderLayout());
        setSize(1300, 800);
        setTitle("Payroll Management (Admin/HR)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu();
        loadTop();
        loadCentre();

        loadPayrollData();

        // table click: fill form
        tblView.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblView.getSelectedRow();
                if (row != -1) {
                    txtEmpID.setText(model.getValueAt(row, 1).toString());
                    // col 2 is Month (text), we ignore
                    txtBasic.setText(model.getValueAt(row, 3).toString());
                    txtAllowances.setText(model.getValueAt(row, 4).toString());
                    txtDeductions.setText(model.getValueAt(row, 5).toString());
                    txtNetPay.setText(model.getValueAt(row, 6).toString());
                }
            }
        });

        // buttons
        btnCalculate.addActionListener(e -> calculatePayroll());
        btnAdd.addActionListener(e -> addPayroll());
        btnStore.addActionListener(e -> addPayroll()); // same as add
        btnUpdate.addActionListener(e -> updatePayroll());
        btnDelete.addActionListener(e -> deletePayroll());
        btnView.addActionListener(e -> loadPayrollData());

        setVisible(true);
    }

    void createMenu() {
        mnuBarTop = new JMenuBar();
        mnuOpen = new JMenu("Open");
        mnuEM = new JMenuItem("Employee management");
        mnuLM = new JMenuItem("Leave management");
        mnuPM = new JMenuItem("Payroll management");
        mnuExit = new JMenuItem("Exit");

        // open new, close this
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

    void loadTop() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblTitle = new JLabel("Payroll Calculation and Records");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);

        itemPanel = new JPanel();
        itemPanel.setLayout(new GridBagLayout());
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblEmpID = new JLabel("Employee ID:");
        lblBasic = new JLabel("Basic Salary:");
        lblAllowances = new JLabel("Allowances:");
        lblDeductions = new JLabel("Deductions:");
        lblNetPay = new JLabel("Net Pay:");

        txtEmpID = new JTextField(12);
        txtBasic = new JTextField(12);
        txtAllowances = new JTextField(12);
        txtDeductions = new JTextField(12);
        txtNetPay = new JTextField(12);
        txtNetPay.setEditable(false);

        btnCalculate = new JButton("Calculate Net Pay");
        btnCalculate.setBackground(new Color(0, 150, 0));
        btnCalculate.setForeground(Color.white);
        btnCalculate.setFocusable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        itemPanel.add(lblEmpID, gbc);
        gbc.gridx = 1; itemPanel.add(txtEmpID, gbc);
        gbc.gridx = 2; itemPanel.add(lblBasic, gbc);
        gbc.gridx = 3; itemPanel.add(txtBasic, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        itemPanel.add(lblAllowances, gbc);
        gbc.gridx = 1; itemPanel.add(txtAllowances, gbc);
        gbc.gridx = 2; itemPanel.add(lblDeductions, gbc);
        gbc.gridx = 3; itemPanel.add(txtDeductions, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        itemPanel.add(btnCalculate, gbc);
        gbc.gridx = 2; itemPanel.add(lblNetPay, gbc);
        gbc.gridx = 3; itemPanel.add(txtNetPay, gbc);

        northPanel.add(titlePanel);
        northPanel.add(itemPanel);
        add(northPanel, BorderLayout.NORTH);
    }

    void loadCentre() {
        centrePanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Record ID", "Emp. ID", "Month", "Basic Salary", "Allowances", "Deductions", "Net Pay"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // user edits via form, not table
            }
        };
        tblView = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblView);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        btnStore = new JButton("Store/Save Payroll");
        btnAdd = new JButton("Add Record");
        btnDelete = new JButton("Delete Record");
        btnUpdate = new JButton("Update Record");
        btnView = new JButton("View All/Refresh");

        for (JButton btn : new JButton[]{btnStore, btnAdd, btnDelete, btnUpdate, btnView}) {
            btn.setBackground(java.awt.Color.decode("#4A70A9"));
            btn.setForeground(Color.white);
            btn.setFocusable(false);
            btn.setPreferredSize(new Dimension(160, 30));
        }

        btnStore.setBackground(new Color(255, 140, 0));

        buttonPanel.add(btnStore);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);

        centrePanel.add(scrollPane, BorderLayout.CENTER);
        centrePanel.add(buttonPanel, BorderLayout.SOUTH);

        add(centrePanel, BorderLayout.CENTER);
    }

    // ================= CALCULATION =================
    private void calculatePayroll() {
        try {
            double basic = Double.parseDouble(txtBasic.getText());
            double allowances = Double.parseDouble(txtAllowances.getText());
            double deductions = Double.parseDouble(txtDeductions.getText());
            double netPay = basic + allowances - deductions;
            txtNetPay.setText(String.format("%.2f", netPay));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            txtNetPay.setText("");
        }
    }

    // ================= DB METHODS =================
    private void loadPayrollData() {
        model.setRowCount(0);
        // concat pay_month/pay_year so table still shows "Month"
        String sql = "SELECT payroll_id, emp_id, CONCAT(pay_month, '/', pay_year) AS month, " +
                     "basic_salary, allowances, deductions, net_pay FROM payroll ORDER BY payroll_id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("payroll_id"),
                        rs.getInt("emp_id"),
                        rs.getString("month"),
                        rs.getDouble("basic_salary"),
                        rs.getDouble("allowances"),
                        rs.getDouble("deductions"),
                        rs.getDouble("net_pay")
                };
                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading payroll: " + ex.getMessage());
        }
    }

    private void addPayroll() {
        String empId = txtEmpID.getText().trim();
        String basic = txtBasic.getText().trim();
        String allow = txtAllowances.getText().trim();
        String ded = txtDeductions.getText().trim();
        String net = txtNetPay.getText().trim();

        if (empId.isEmpty() || basic.isEmpty() || allow.isEmpty() || ded.isEmpty() || net.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields and calculate net pay.");
            return;
        }

        // you can later replace 11 and 2025 with current month/year
        String sql = "INSERT INTO payroll (emp_id, pay_month, pay_year, basic_salary, allowances, deductions, net_pay) " +
                     "VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(empId));
            ps.setInt(2, 11);       // demo: November
            ps.setInt(3, 2025);     // demo: 2025
            ps.setDouble(4, Double.parseDouble(basic));
            ps.setDouble(5, Double.parseDouble(allow));
            ps.setDouble(6, Double.parseDouble(ded));
            ps.setDouble(7, Double.parseDouble(net));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payroll record added.");
            clearForm();
            loadPayrollData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding payroll: " + ex.getMessage());
        }
    }

    private void updatePayroll() {
        int row = tblView.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a record to update.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String sql = "UPDATE payroll SET emp_id=?, basic_salary=?, allowances=?, deductions=?, net_pay=? WHERE payroll_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtEmpID.getText()));
            ps.setDouble(2, Double.parseDouble(txtBasic.getText()));
            ps.setDouble(3, Double.parseDouble(txtAllowances.getText()));
            ps.setDouble(4, Double.parseDouble(txtDeductions.getText()));
            ps.setDouble(5, Double.parseDouble(txtNetPay.getText()));
            ps.setInt(6, id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record updated.");
            clearForm();
            loadPayrollData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating payroll: " + ex.getMessage());
        }
    }

    private void deletePayroll() {
        int row = tblView.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a record to delete.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this payroll record?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM payroll WHERE payroll_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payroll record deleted.");
            clearForm();
            loadPayrollData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting payroll: " + ex.getMessage());
        }
    }

    private void clearForm() {
        txtEmpID.setText("");
        txtBasic.setText("");
        txtAllowances.setText("");
        txtDeductions.setText("");
        txtNetPay.setText("");
    }
}
