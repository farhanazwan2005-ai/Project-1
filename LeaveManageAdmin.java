import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class LeaveManageAdmin extends JFrame {

    JPanel titlePanel, northPanel, centrePanel, buttonPanel, itemPanel;
    JLabel lblTitle, lblEmpID, lblLeaveType, lblStartDate, lblEndDate, lblStatus;
    JTextField txtEmpID, txtStartDate, txtEndDate;
    JComboBox<String> cmbLeaveType, cmbStatus;
    JMenuBar mnuBarTop;
    JButton btnApprove, btnReject, btnAdd, btnDelete, btnUpdate, btnView;
    JTable tblView;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    DefaultTableModel model;

    public LeaveManageAdmin() {
        setLayout(new BorderLayout());
        setSize(1300, 800);
        setTitle("Leave Management (Admin/HR)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu();
        loadTop();
        loadCentre();

        loadLeaves();

        // table click: fill form with selected record
        tblView.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblView.getSelectedRow();
                if (row != -1) {
                    txtEmpID.setText(model.getValueAt(row, 1).toString());
                    cmbLeaveType.setSelectedItem(model.getValueAt(row, 2).toString());
                    txtStartDate.setText(model.getValueAt(row, 3).toString());
                    txtEndDate.setText(model.getValueAt(row, 4).toString());
                    // table has 6 columns (0..5), status is col 5
                    cmbStatus.setSelectedItem(model.getValueAt(row, 5).toString());
                }
            }
        });

        // button wiring
        btnView.addActionListener(e -> loadLeaves());
        btnAdd.addActionListener(e -> addLeave());
        btnUpdate.addActionListener(e -> updateLeave());
        btnDelete.addActionListener(e -> deleteLeave());
        btnApprove.addActionListener(e -> changeStatus("Approved"));
        btnReject.addActionListener(e -> changeStatus("Rejected"));

        setVisible(true);
    }

    // --- Menu Bar ---
    void createMenu() {
        mnuBarTop = new JMenuBar();
        mnuOpen = new JMenu("Open");
        mnuEM = new JMenuItem("Employee management");
        mnuLM = new JMenuItem("Leave management");
        mnuPM = new JMenuItem("Payroll management");
        mnuExit = new JMenuItem("Exit");

        // wire them like other admin screens
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

    // --- Top Form ---
    void loadTop() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        lblTitle = new JLabel("Leave Application Management");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);

        itemPanel = new JPanel(new GridBagLayout());
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblEmpID = new JLabel("Employee ID:");
        lblLeaveType = new JLabel("Leave Type:");
        lblStartDate = new JLabel("Start Date (YYYY-MM-DD):");
        lblEndDate = new JLabel("End Date (YYYY-MM-DD):");
        lblStatus = new JLabel("Status:");

        txtEmpID = new JTextField(15);
        txtStartDate = new JTextField(15);
        txtEndDate = new JTextField(15);

        String[] leaveTypes = {"Sick", "Emergency", "Annual", "Maternity", "Others"};
        cmbLeaveType = new JComboBox<>(leaveTypes);

        String[] statuses = {"Pending", "Approved", "Rejected"};
        cmbStatus = new JComboBox<>(statuses);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        itemPanel.add(lblEmpID, gbc);
        gbc.gridx = 1; itemPanel.add(txtEmpID, gbc);

        gbc.gridx = 2; itemPanel.add(lblLeaveType, gbc);
        gbc.gridx = 3; itemPanel.add(cmbLeaveType, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        itemPanel.add(lblStartDate, gbc);
        gbc.gridx = 1; itemPanel.add(txtStartDate, gbc);

        gbc.gridx = 2; itemPanel.add(lblEndDate, gbc);
        gbc.gridx = 3; itemPanel.add(txtEndDate, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        itemPanel.add(lblStatus, gbc);
        gbc.gridx = 1; itemPanel.add(cmbStatus, gbc);

        northPanel.add(titlePanel);
        northPanel.add(itemPanel);
        add(northPanel, BorderLayout.NORTH);
    }

    // --- Centre (Table + Buttons) ---
    void loadCentre() {
        centrePanel = new JPanel(new BorderLayout());

        // your table has 6 cols
        String[] columnNames = {"Leave ID", "Emp. ID", "Type", "Start Date", "End Date", "Status"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblView = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblView);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        btnApprove = new JButton("Approve");
        btnReject = new JButton("Reject");
        btnAdd = new JButton("Add New");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnView = new JButton("Refresh");

        for (JButton btn : new JButton[]{btnApprove, btnReject, btnAdd, btnUpdate, btnDelete, btnView}) {
            btn.setBackground(java.awt.Color.decode("#4A70A9"));
            btn.setForeground(Color.white);
            btn.setFocusable(false);
            btn.setPreferredSize(new Dimension(150, 30));
        }

        btnApprove.setBackground(new Color(0, 150, 0));
        btnReject.setBackground(new Color(200, 0, 0));

        buttonPanel.add(btnApprove);
        buttonPanel.add(btnReject);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);

        centrePanel.add(scrollPane, BorderLayout.CENTER);
        centrePanel.add(buttonPanel, BorderLayout.SOUTH);

        add(centrePanel, BorderLayout.CENTER);
    }

    // ================= BACKEND ===================

    private void loadLeaves() {
        model.setRowCount(0);
        String sql = "SELECT leave_id, emp_id, leave_type, start_date, end_date, status FROM leave_applications";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("leave_id"),
                        rs.getInt("emp_id"),
                        rs.getString("leave_type"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading leaves: " + ex.getMessage());
        }
    }

    private void addLeave() {
        String empId = txtEmpID.getText().trim();
        String type = cmbLeaveType.getSelectedItem().toString();
        String start = txtStartDate.getText().trim();
        String end = txtEndDate.getText().trim();
        String status = cmbStatus.getSelectedItem().toString();

        if (empId.isEmpty() || start.isEmpty() || end.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.");
            return;
        }

        String sql = "INSERT INTO leave_applications (emp_id, leave_type, start_date, end_date, status) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(empId));
            ps.setString(2, type);
            ps.setString(3, start);
            ps.setString(4, end);
            ps.setString(5, status);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Leave added successfully.");
            clearForm();
            loadLeaves();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding leave: " + ex.getMessage());
        }
    }

    private void updateLeave() {
        int row = tblView.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a leave record first.");
            return;
        }

        int leaveId = (int) model.getValueAt(row, 0);

        String type = cmbLeaveType.getSelectedItem().toString();
        String start = txtStartDate.getText().trim();
        String end = txtEndDate.getText().trim();
        String status = cmbStatus.getSelectedItem().toString();

        String sql = "UPDATE leave_applications SET leave_type=?, start_date=?, end_date=?, status=? WHERE leave_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, type);
            ps.setString(2, start);
            ps.setString(3, end);
            ps.setString(4, status);
            ps.setInt(5, leaveId);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Leave updated successfully.");
            clearForm();
            loadLeaves();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating leave: " + ex.getMessage());
        }
    }

    private void deleteLeave() {
        int row = tblView.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a leave record to delete.");
            return;
        }

        int leaveId = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM leave_applications WHERE leave_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, leaveId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Leave deleted successfully.");
            clearForm();
            loadLeaves();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting leave: " + ex.getMessage());
        }
    }

    private void changeStatus(String newStatus) {
        int row = tblView.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a leave to " + newStatus.toLowerCase() + ".");
            return;
        }

        int leaveId = (int) model.getValueAt(row, 0);

        String sql = "UPDATE leave_applications SET status=? WHERE leave_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, leaveId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Leave " + newStatus.toLowerCase() + ".");
            loadLeaves();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating status: " + ex.getMessage());
        }
    }

    private void clearForm() {
        txtEmpID.setText("");
        txtStartDate.setText("");
        txtEndDate.setText("");
        cmbLeaveType.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
    }
}
