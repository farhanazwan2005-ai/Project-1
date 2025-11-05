import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel; 

public class LeaveManageAdmin extends JFrame {

    // Components for the Frame
    JPanel titlePanel, northPanel, centrePanel, buttonPanel, itemPanel;
    JLabel lblTitle, lblEmpID, lblLeaveType, lblStartDate, lblEndDate, lblStatus;
    JTextField txtEmpID, txtStartDate, txtEndDate;
    JComboBox<String> cmbLeaveType, cmbStatus; // JComboBox for fixed choices
    JMenuBar mnuBarTop;
    JButton btnApprove, btnReject, btnAdd, btnDelete, btnUpdate, btnView;
    JTable tblView;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    public LeaveManageAdmin() {
        setLayout(new BorderLayout()); 
        setSize(1300, 800); 
        setTitle("Leave Management (Admin/HR)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu(); 
        loadTop();
        loadCentre();
        
        setVisible(true);
    }
    
    // --- 1. Menu Bar ---
    void createMenu() {
        // Menu bar setup is the same as previous admin screens
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

    // --- 2. Load the NORTH Panel (Title Bar and Leave Form) ---
    void loadTop() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS)); 

        // 2a. Title Bar Panel
        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9")); // Reusing your blue color
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblTitle = new JLabel("Leave Application Management"); 
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);
        
        // 2b. Leave Input/Form Panel (GridBagLayout for neatness)
        itemPanel = new JPanel();
        itemPanel.setLayout(new GridBagLayout()); 
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Initialize Components
        lblEmpID = new JLabel("Employee ID:");
        lblLeaveType = new JLabel("Leave Type:");
        lblStartDate = new JLabel("Start Date:");
        lblEndDate = new JLabel("End Date:");
        lblStatus = new JLabel("Status:"); // Admin can set status

        txtEmpID = new JTextField(15);
        txtStartDate = new JTextField(15);
        txtEndDate = new JTextField(15);
        
        // JComboBox for Leave Type (Required by specs)
        String[] leaveTypes = {"Sick", "Emergency", "Annual", "Maternity", "Others"};
        cmbLeaveType = new JComboBox<>(leaveTypes);
        
        // JComboBox for Status (Admin/HR approval)
        String[] statuses = {"Pending", "Approved", "Rejected"};
        cmbStatus = new JComboBox<>(statuses);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Row 0: Employee ID and Leave Type
        gbc.gridy = row; itemPanel.add(lblEmpID, new GBC(0, row)); itemPanel.add(txtEmpID, new GBC(1, row));
        gbc.gridx = 2; itemPanel.add(lblLeaveType, new GBC(2, row)); gbc.gridx = 3; itemPanel.add(cmbLeaveType, new GBC(3, row)); row++;
        
        // Row 1: Start Date and End Date
        gbc.gridy = row; itemPanel.add(lblStartDate, new GBC(0, row)); gbc.gridx = 1; itemPanel.add(txtStartDate, new GBC(1, row));
        gbc.gridx = 2; itemPanel.add(lblEndDate, new GBC(2, row)); gbc.gridx = 3; itemPanel.add(txtEndDate, new GBC(3, row)); row++;
        
        // Row 2: Status (for HR/Admin to set)
        gbc.gridy = row; itemPanel.add(lblStatus, new GBC(0, row)); gbc.gridx = 1; itemPanel.add(cmbStatus, new GBC(1, row));

        // Assemble and Add to Frame
        northPanel.add(titlePanel);
        northPanel.add(itemPanel);
        add(northPanel, BorderLayout.NORTH);
    }
    
    // --- 3. Load the CENTER Panel (JTable and Buttons) ---
    void loadCentre() {
        centrePanel = new JPanel();
        centrePanel.setLayout(new BorderLayout());
        
        // 3a. JTable Setup (Required for all applications)
        String[] columnNames = {"ID", "Emp. ID", "Type", "Start Date", "End Date", "Days", "Status", "HR Comment"};
        // Dummy data 
        Object[][] data = {
            {"101", "1002", "Annual", "2025-11-20", "2025-11-22", "3", "Pending", ""},
            {"102", "1005", "Sick", "2025-11-10", "2025-11-10", "1", "Approved", "Need MC"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblView = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblView);
        
        // 3b. Button Panel (Approve/Reject + CRUD)
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        
        // HR Action Buttons
        btnApprove = new JButton("Approve");
        btnReject = new JButton("Reject");
        
        // CRUD Buttons
        btnAdd = new JButton("Add New");
        btnDelete = new JButton("Delete");
        btnUpdate = new JButton("Update");
        btnView = new JButton("Refresh"); 

        // Style buttons
        for (JButton btn : new JButton[] {btnApprove, btnReject, btnAdd, btnDelete, btnUpdate, btnView}) {
            btn.setBackground(java.awt.Color.decode("#4A70A9"));
            btn.setForeground(Color.white);
            btn.setFocusable(false);
            btn.setPreferredSize(new Dimension(150, 30));
        }
        
        // Highlighting key HR actions
        btnApprove.setBackground(new Color(0, 150, 0)); // Green
        btnReject.setBackground(new Color(200, 0, 0)); // Red

        // Add buttons
        buttonPanel.add(btnApprove);
        buttonPanel.add(btnReject);
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL)); // Visual separator
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);
        
        // Assemble Centre Panel
        centrePanel.add(scrollPane, BorderLayout.CENTER); 
        centrePanel.add(buttonPanel, BorderLayout.SOUTH); 

        add(centrePanel, BorderLayout.CENTER);
    }
}

// Helper class for GridBagConstraints (used in loadTop)
class GBC extends GridBagConstraints {
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.insets = new Insets(5, 10, 5, 10);
        this.fill = HORIZONTAL;
        this.anchor = (gridx % 2 == 0) ? EAST : WEST; // Align labels right, fields left
        this.weightx = (gridx % 2 == 1) ? 1.0 : 0.0;
    }
}