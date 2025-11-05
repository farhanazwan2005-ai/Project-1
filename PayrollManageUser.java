import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.io.*;

public class PayrollManageUser extends JFrame {

    JPanel titlePanel, northPanel, centrePanel, buttonPanel;
    JLabel lblTitle, lblInstruction;
    JMenuBar mnuBarTop;
    JButton btnDownload;
    JTable tblHistory;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;
    DefaultTableModel model;
    private int empId;

    // default for testing
    public PayrollManageUser() {
        this(0);
    }

    // call this after login: new PayrollManageUser(empId)
    public PayrollManageUser(int empId) {
        this.empId = empId;

        setLayout(new BorderLayout());
        setSize(1000, 650);
        setTitle("My Payroll Records");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu();
        loadTop();
        loadCentre();

        loadPayrollHistory();

        setVisible(true);
    }

    void createMenu() {
        mnuBarTop = new JMenuBar();
        mnuOpen = new JMenu("Open");
        mnuEM = new JMenuItem("Employee management");
        mnuLM = new JMenuItem("Leave management");
        mnuPM = new JMenuItem("Payroll management");
        mnuExit = new JMenuItem("Exit");

        // move between user pages and close current
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

    void loadTop() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblTitle = new JLabel("My Monthly Payroll");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 15));

        lblInstruction = new JLabel("Select a record below, then click Download to get the payslip.");
        lblInstruction.setFont(new Font("Sans Serif", Font.PLAIN, 16));

        btnDownload = new JButton("Download Payslip");
        btnDownload.setBackground(new Color(255, 140, 0));
        btnDownload.setForeground(Color.white);
        btnDownload.setFocusable(false);
        btnDownload.setPreferredSize(new Dimension(180, 35));

        btnDownload.addActionListener(e -> downloadPayslip());

        buttonPanel.add(lblInstruction);
        buttonPanel.add(btnDownload);

        northPanel.add(titlePanel);
        northPanel.add(buttonPanel);
        add(northPanel, BorderLayout.NORTH);
    }

    void loadCentre() {
        centrePanel = new JPanel(new BorderLayout());

        JLabel lblHistoryTitle = new JLabel("Payroll History:");
        lblHistoryTitle.setFont(new Font("Sans Serif", Font.BOLD, 18));
        lblHistoryTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        String[] columnNames = {"Record ID", "Month", "Basic Salary", "Allowances", "Deductions", "Net Pay"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblHistory = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblHistory);

        centrePanel.add(lblHistoryTitle, BorderLayout.NORTH);
        centrePanel.add(scrollPane, BorderLayout.CENTER);
        add(centrePanel, BorderLayout.CENTER);
    }

    // ================= BACKEND =================
    private void loadPayrollHistory() {
        model.setRowCount(0);
        if (empId == 0) return;

        // your table has pay_month, pay_year, not month
        String sql =
            "SELECT payroll_id, " +
            "CONCAT(pay_month, '/', pay_year) AS month_text, " +
            "basic_salary, allowances, deductions, net_pay " +
            "FROM payroll WHERE emp_id = ? ORDER BY payroll_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("payroll_id"),
                        rs.getString("month_text"),
                        rs.getDouble("basic_salary"),
                        rs.getDouble("allowances"),
                        rs.getDouble("deductions"),
                        rs.getDouble("net_pay")
                    };
                    model.addRow(row);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading payroll: " + ex.getMessage());
        }
    }

    private void downloadPayslip() {
        int selectedRow = tblHistory.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a payroll record first.");
            return;
        }

        String recordId = model.getValueAt(selectedRow, 0).toString();
        String month = model.getValueAt(selectedRow, 1).toString();
        String basic = model.getValueAt(selectedRow, 2).toString();
        String allow = model.getValueAt(selectedRow, 3).toString();
        String ded = model.getValueAt(selectedRow, 4).toString();
        String net = model.getValueAt(selectedRow, 5).toString();

        try {
            File file = new File("Payslip_" + month.replace("/", "_") + ".txt");
            try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                out.println("==== PAYSLIP ====");
                out.println("Employee ID: " + empId);
                out.println("Record ID: " + recordId);
                out.println("Month: " + month);
                out.println("----------------------------");
                out.println("Basic Salary: RM " + basic);
                out.println("Allowances:   RM " + allow);
                out.println("Deductions:   RM " + ded);
                out.println("----------------------------");
                out.println("Net Pay:      RM " + net);
                out.println("============================");
                out.println("Generated by AURA HR System");
            }
            JOptionPane.showMessageDialog(this, "Payslip downloaded: " + file.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving payslip: " + ex.getMessage());
        }
    }
}
