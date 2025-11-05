import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class PayrollManageAdmin extends JFrame {

    // Components for the Frame
    JPanel titlePanel, northPanel, centrePanel, buttonPanel, itemPanel;
    JLabel lblTitle, lblEmpID, lblBasic, lblAllowances, lblDeductions, lblNetPay;
    JTextField txtEmpID, txtBasic, txtAllowances, txtDeductions, txtNetPay;
    JMenuBar mnuBarTop;
    JButton btnCalculate, btnStore, btnAdd, btnDelete, btnUpdate, btnView;
    JTable tblView;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    public PayrollManageAdmin() {
        setLayout(new BorderLayout()); 
        setSize(1300, 800); 
        setTitle("Payroll Management (Admin/HR)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu(); 
        loadTop();
        loadCentre();
        
        setVisible(true);
    }
    
    // --- 1. Menu Bar ---
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

    // --- 2. Load the NORTH Panel (Title Bar and Calculation Form) ---
    void loadTop() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS)); 

        // 2a. Title Bar Panel
        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblTitle = new JLabel("Payroll Calculation and Records"); 
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);
        
        // 2b. Calculation/Form Panel (GridBagLayout for structure)
        itemPanel = new JPanel();
        itemPanel.setLayout(new GridBagLayout()); 
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Initialize Components
        lblEmpID = new JLabel("Employee ID:");
        lblBasic = new JLabel("Basic Salary:");
        lblAllowances = new JLabel("Allowances:");
        lblDeductions = new JLabel("Deductions:");
        lblNetPay = new JLabel("Net Pay:"); // Result field

        txtEmpID = new JTextField(12);
        txtBasic = new JTextField(12);
        txtAllowances = new JTextField(12);
        txtDeductions = new JTextField(12);
        txtNetPay = new JTextField(12);
        txtNetPay.setEditable(false); // Net Pay is calculated, not input

        btnCalculate = new JButton("Calculate Net Pay");
        btnCalculate.setBackground(new Color(0, 150, 0));
        btnCalculate.setFocusable(false); // Green button
        btnCalculate.setForeground(Color.white);
        
        // Add calculation logic
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePayroll();
            }
        });


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Row 0: ID and Basic Salary
        gbc.gridy = row; itemPanel.add(lblEmpID, new GBC(0, row)); itemPanel.add(txtEmpID, new GBC(1, row));
        gbc.gridx = 2; itemPanel.add(lblBasic, new GBC(2, row)); gbc.gridx = 3; itemPanel.add(txtBasic, new GBC(3, row)); row++;
        
        // Row 1: Allowances and Deductions
        gbc.gridy = row; itemPanel.add(lblAllowances, new GBC(0, row)); itemPanel.add(txtAllowances, new GBC(1, row));
        gbc.gridx = 2; itemPanel.add(lblDeductions, new GBC(2, row)); gbc.gridx = 3; itemPanel.add(txtDeductions, new GBC(3, row)); row++;
        
        // Row 2: Calculation Button and Net Pay Result
        gbc.gridy = row; gbc.gridwidth = 2; itemPanel.add(btnCalculate, new GBC(0, row)); gbc.gridwidth = 1;
        gbc.gridx = 2; itemPanel.add(lblNetPay, new GBC(2, row)); gbc.gridx = 3; itemPanel.add(txtNetPay, new GBC(3, row));

        // Assemble and Add to Frame
        northPanel.add(titlePanel);
        northPanel.add(itemPanel);
        add(northPanel, BorderLayout.NORTH);
    }
    
    // Helper method to perform the calculation logic
    private void calculatePayroll() {
        try {
            double basic = Double.parseDouble(txtBasic.getText());
            double allowances = Double.parseDouble(txtAllowances.getText());
            double deductions = Double.parseDouble(txtDeductions.getText());
            
            // Formula: Net Pay = Basic Salary + Allowances – Deductions
            double netPay = basic + allowances - deductions;
            
            // Display result formatted to two decimal places
            txtNetPay.setText(String.format("%.2f", netPay));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for salary components.", "Input Error", JOptionPane.ERROR_MESSAGE);
            txtNetPay.setText("");
        }
    }
    
    // --- 3. Load the CENTER Panel (JTable and Buttons) ---
    void loadCentre() {
        centrePanel = new JPanel();
        centrePanel.setLayout(new BorderLayout());
        
        // 3a. JTable Setup (Display all payroll data)
        String[] columnNames = {"Record ID", "Emp. ID", "Month", "Basic Salary", "Allowances", "Deductions", "Net Pay"};
        // Dummy data 
        Object[][] data = {
            {"P001", "1002", "Nov 2025", "3500.00", "500.00", "200.00", "3800.00"},
            {"P002", "1005", "Nov 2025", "6000.00", "1200.00", "550.00", "6650.00"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblView = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblView);
        
        // 3b. Button Panel (CRUD + Store)
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        
        btnStore = new JButton("Store/Save Payroll"); // Store monthly payroll
        btnAdd = new JButton("Add Record");
        btnDelete = new JButton("Delete Record");
        btnUpdate = new JButton("Update Record");
        btnView = new JButton("View All/Refresh"); 

        // Style buttons
        for (JButton btn : new JButton[] {btnStore, btnAdd, btnDelete, btnUpdate, btnView}) {
            btn.setBackground(java.awt.Color.decode("#4A70A9"));
            btn.setForeground(Color.white);
            btn.setFocusable(false);
            btn.setPreferredSize(new Dimension(160, 30));
        }
        
        btnStore.setBackground(new Color(255, 140, 0)); // Orange color for Save/Store

        // Add buttons
        buttonPanel.add(btnStore);
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL)); 
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
        // Adjust gridx to properly align items across 4 columns
        this.anchor = (gridx % 2 == 0) ? EAST : WEST;
        this.weightx = (gridx % 2 == 1) ? 1.0 : 0.0;
    }
}