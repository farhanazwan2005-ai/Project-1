import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel; 

public class LeaveManageUser extends JFrame {

    // Components for the Frame
    JPanel titlePanel, northPanel, centrePanel, buttonPanel, formPanel;
    JLabel lblTitle, lblLeaveType, lblStartDate, lblEndDate;
    JTextField txtStartDate, txtEndDate;
    JComboBox<String> cmbLeaveType; 
    JButton btnSubmit; // Submit the application
    JTable tblHistory; // Shows only the employee's history
    JMenuBar mnuBarTop;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    public LeaveManageUser() {
        setLayout(new BorderLayout()); 
        setSize(900, 600); 
        setTitle("Apply for Leave");
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

    // --- 2. Load the NORTH Panel (Title Bar and Application Form) ---
    void loadTop() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS)); 

        // 2a. Title Bar Panel
        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblTitle = new JLabel("Apply for Leave"); 
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);
        
        // 2b. Leave Application Form Panel (FlowLayout for a simple horizontal form)
        formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Horizontal flow
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 30, 20));
        
        // Initialize Components
        lblLeaveType = new JLabel("Leave Type:");
        lblStartDate = new JLabel("Start Date (YYYY-MM-DD):");
        lblEndDate = new JLabel("End Date (YYYY-MM-DD):");

        txtStartDate = new JTextField(10);
        txtEndDate = new JTextField(10);
        
        // JComboBox for Leave Type (Required by specs)
        String[] leaveTypes = {"Sick", "Emergency", "Annual", "Maternity", "Others"};
        cmbLeaveType = new JComboBox<>(leaveTypes);
        
        btnSubmit = new JButton("Submit Application"); 
        btnSubmit.setBackground(java.awt.Color.decode("#4A70A9"));
        btnSubmit.setForeground(Color.white);
        btnSubmit.setPreferredSize(new Dimension(160, 30));

        // Add action for submitting the form
        btnSubmit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Leave application submitted! (DB integration required)");
            // In a real application, you would validate fields and insert into the database here.
        });


        // Add components to the panel in one row
        formPanel.add(lblLeaveType);
        formPanel.add(cmbLeaveType);
        formPanel.add(lblStartDate);
        formPanel.add(txtStartDate);
        formPanel.add(lblEndDate);
        formPanel.add(txtEndDate);
        formPanel.add(btnSubmit);

        // Assemble and Add to Frame
        northPanel.add(titlePanel);
        northPanel.add(formPanel);
        add(northPanel, BorderLayout.NORTH);
    }
    
    // --- 3. Load the CENTER Panel (Leave History Table) ---
    void loadCentre() {
        centrePanel = new JPanel();
        centrePanel.setLayout(new BorderLayout());
        
        // Label for the history table
        JLabel lblHistory = new JLabel("My Leave History:");
        lblHistory.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // JTable Setup (Only shows the employee's history)
        String[] columnNames = {"ID", "Type", "Start Date", "End Date", "Days", "Status", "HR Comment"};
        // Dummy data (This would be filtered by the logged-in user's ID)
        Object[][] data = {
            {"102", "Annual", "2025-08-10", "2025-08-12", "3", "Approved", "Enjoy your break."},
            {"105", "Sick", "2025-11-05", "2025-11-05", "1", "Pending", ""}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblHistory = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblHistory);
        
        // Assemble Centre Panel
        centrePanel.add(lblHistory, BorderLayout.NORTH);
        centrePanel.add(scrollPane, BorderLayout.CENTER); 

        add(centrePanel, BorderLayout.CENTER);
    }
}