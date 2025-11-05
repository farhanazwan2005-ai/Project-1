import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class PayrollManageUser extends JFrame {

    // Components for the Frame
    JPanel titlePanel, northPanel, centrePanel, buttonPanel;
    JLabel lblTitle, lblInstruction;
    JMenuBar mnuBarTop;
    JButton btnDownload;
    JTable tblHistory; // Shows only the employee's payroll history
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    public PayrollManageUser() {
        setLayout(new BorderLayout()); 
        setSize(1000, 650); 
        setTitle("My Payroll Records");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu(); 
        loadTop();
        loadCentre();
        
        setVisible(true);
    }
    
    // --- 1. Menu Bar (Standard) ---
    void createMenu() {
        mnuBarTop = new JMenuBar();
        mnuOpen = new JMenu("Open");
        mnuEM = new JMenuItem("Employee management");
        mnuLM = new JMenuItem("Leave management");
        mnuPM = new JMenuItem("Payroll management");
        mnuExit = new JMenuItem("Exit");
        
        mnuExit.addActionListener(e -> System.exit(0)); 

        mnuOpen.add(mnuEM);
        mnuOpen.add(mnuLM);
        mnuOpen.add(mnuPM);
        mnuOpen.addSeparator();
        mnuOpen.add(mnuExit);

        mnuBarTop.add(mnuOpen);
        setJMenuBar(mnuBarTop);
    }

    // --- 2. Load the NORTH Panel (Title Bar and Instructions) ---
    void loadTop() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS)); 

        // 2a. Title Bar Panel
        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblTitle = new JLabel("My Monthly Payroll"); 
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);
        
        // 2b. Download Button and Instructions Panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 15));
        
        lblInstruction = new JLabel("Select a record below, then click Download to get the payslip.");
        lblInstruction.setFont(new Font("Sans Serif", Font.PLAIN, 16));

        btnDownload = new JButton("Download Payslip"); 
        btnDownload.setBackground(new Color(255, 140, 0)); // Orange color
        btnDownload.setForeground(Color.white);
        btnDownload.setFocusable(false);
        btnDownload.setPreferredSize(new Dimension(180, 35));
        
        // Add action for downloading the selected record
        btnDownload.addActionListener(e -> {
            int selectedRow = tblHistory.getSelectedRow();
            if (selectedRow != -1) {
                String month = (String) tblHistory.getValueAt(selectedRow, 1);
                JOptionPane.showMessageDialog(this, "Downloading payslip for: " + month + " (File saving logic required)", "Download Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Please select a payroll record to download.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Assemble button panel components
        buttonPanel.add(lblInstruction);
        buttonPanel.add(btnDownload);

        // Assemble and Add to Frame
        northPanel.add(titlePanel);
        northPanel.add(buttonPanel); // Button panel below the title bar
        add(northPanel, BorderLayout.NORTH);
    }
    
    // --- 3. Load the CENTER Panel (Payroll History Table) ---
    void loadCentre() {
        centrePanel = new JPanel();
        centrePanel.setLayout(new BorderLayout());
        
        // Label for the table
        JLabel lblHistoryTitle = new JLabel("Payroll History:");
        lblHistoryTitle.setFont(new Font("Sans Serif", Font.BOLD, 18));
        lblHistoryTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // JTable Setup (Shows only the employee's payroll history)
        String[] columnNames = {"Record ID", "Month", "Basic Salary", "Allowances", "Deductions", "Net Pay"};
        // Dummy data 
        Object[][] data = {
            {"P002", "Nov 2025", "6000.00", "1200.00", "550.00", "6650.00"},
            {"P001", "Oct 2025", "6000.00", "1100.00", "550.00", "6550.00"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblHistory = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblHistory);
        
        // Assemble Centre Panel
        centrePanel.add(lblHistoryTitle, BorderLayout.NORTH);
        centrePanel.add(scrollPane, BorderLayout.CENTER); 

        add(centrePanel, BorderLayout.CENTER);
    }
}