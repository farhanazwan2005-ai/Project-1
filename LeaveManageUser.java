import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class LeaveManageUser extends JFrame {

    JPanel titlePanel, northPanel, centrePanel, buttonPanel, formPanel;
    JLabel lblTitle, lblLeaveType, lblStartDate, lblEndDate;
    JTextField txtStartDate, txtEndDate;
    JComboBox<String> cmbLeaveType;
    JButton btnSubmit;
    JTable tblHistory;
    JMenuBar mnuBarTop;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    // logged-in user id
    private int empId;
    private DefaultTableModel model;

    // fallback constructor
    public LeaveManageUser() {
        this(0);
    }

    // call this: new LeaveManageUser(empId);
    public LeaveManageUser(int empId) {
        this.empId = empId;

        setLayout(new BorderLayout());
        setSize(900, 600);
        setTitle("Apply for Leave");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu();
        loadTop();
        loadCentre();

        loadLeaveHistory();

        setVisible(true);
    }

    // --- 1. Menu Bar (User) ---
    void createMenu() {
        mnuBarTop = new JMenuBar();
        mnuOpen = new JMenu("Open");
        mnuEM = new JMenuItem("Employee management");
        mnuLM = new JMenuItem("Leave management");
        mnuPM = new JMenuItem("Payroll management");
        mnuExit = new JMenuItem("Exit");

        // wire to user pages, keeping the same empId
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

    // --- 2. Top (title + form) ---
    void loadTop() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblTitle = new JLabel("Apply for Leave");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);

        formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 30, 20));

        lblLeaveType = new JLabel("Leave Type:");
        lblStartDate = new JLabel("Start Date (YYYY-MM-DD):");
        lblEndDate = new JLabel("End Date (YYYY-MM-DD):");

        txtStartDate = new JTextField(10);
        txtEndDate = new JTextField(10);

        String[] leaveTypes = {"Sick", "Emergency", "Annual", "Maternity", "Others"};
        cmbLeaveType = new JComboBox<>(leaveTypes);

        btnSubmit = new JButton("Submit Application");
        btnSubmit.setBackground(java.awt.Color.decode("#4A70A9"));
        btnSubmit.setForeground(Color.white);
        btnSubmit.setPreferredSize(new Dimension(160, 30));

        btnSubmit.addActionListener(e -> submitLeave());

        formPanel.add(lblLeaveType);
        formPanel.add(cmbLeaveType);
        formPanel.add(lblStartDate);
        formPanel.add(txtStartDate);
        formPanel.add(lblEndDate);
        formPanel.add(txtEndDate);
        formPanel.add(btnSubmit);

        northPanel.add(titlePanel);
        northPanel.add(formPanel);
        add(northPanel, BorderLayout.NORTH);
    }

    // --- 3. Centre (history table) ---
    void loadCentre() {
        centrePanel = new JPanel();
        centrePanel.setLayout(new BorderLayout());

        JLabel lblHistory = new JLabel("My Leave History:");
        lblHistory.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        String[] columnNames = {"ID", "Type", "Start Date", "End Date", "Status"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // user should not edit history
            }
        };
        tblHistory = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblHistory);

        centrePanel.add(lblHistory, BorderLayout.NORTH);
        centrePanel.add(scrollPane, BorderLayout.CENTER);

        add(centrePanel, BorderLayout.CENTER);
    }

    // ================= DB METHODS =================

    private void loadLeaveHistory() {
        model.setRowCount(0);

        if (empId == 0) {
            return;
        }

        String sql = "SELECT leave_id, leave_type, start_date, end_date, status " +
                     "FROM leave_applications WHERE emp_id = ? ORDER BY start_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                            rs.getInt("leave_id"),
                            rs.getString("leave_type"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getString("status")
                    };
                    model.addRow(row);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading leave history: " + ex.getMessage());
        }
    }

    private void submitLeave() {
        if (empId == 0) {
            JOptionPane.showMessageDialog(this, "Cannot submit. Employee ID not found from login.");
            return;
        }

        String leaveType = cmbLeaveType.getSelectedItem().toString();
        String start = txtStartDate.getText().trim();
        String end = txtEndDate.getText().trim();

        if (start.isEmpty() || end.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill start and end date.");
            return;
        }

        String sql = "INSERT INTO leave_applications (emp_id, leave_type, start_date, end_date, status) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, leaveType);
            ps.setString(3, start);
            ps.setString(4, end);
            ps.setString(5, "Pending");

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Leave application submitted!");

            // clear fields
            txtStartDate.setText("");
            txtEndDate.setText("");

            // reload history
            loadLeaveHistory();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error submitting leave: " + ex.getMessage());
        }
    }
}
