import javax.swing.*;
import java.awt.*;

public class EmployManageUser extends JFrame {

    // Components needed for the User view
    JPanel titlePanel, northPanel, centrePanel, formPanel, buttonPanel;
    JLabel lblTitle, lblName, lblIC, lblDept, lblPosition, lblSalary;
    JTextField txtName, txtIC, txtDept, txtPosition, txtSalary;
    JButton btnUpdate;
    JMenuBar mnuBarTop;
    JMenu mnuOpen;
    JMenuItem mnuExit, mnuEM, mnuLM, mnuPM; // User needs a way to exit/log out

    public EmployManageUser() {
        // Use a slightly smaller frame size as the content is simpler
        setSize(800, 450); 
        setTitle("Employee Information (User View)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Set the main layout

        createMenu();
        loadNorthPanel(); // Loads the Title and Form
        loadCentrePanel(); // Loads the single action button
        
        // Load initial data (This is where you'd fetch the user's data from the DB)
        loadUserData(); 

        setVisible(true);
    }

    // --- 1. Menu Bar (Simplified) ---
    void createMenu(){
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

    // --- 2. North Panel (Title and Form) ---
    void loadNorthPanel() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        // 2a. Title Bar (Simplified Title)
        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        lblTitle = new JLabel("My Information");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 24));
        titlePanel.add(lblTitle);
        northPanel.add(titlePanel);

        // 2b. Form Input Panel (Using GridBagLayout for a clean form)
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // Using GridBag for better alignment
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // Initialize components (fields are NOT editable until update is pressed, by default)
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

        // Set all fields to read-only initially (User can only view)
        // Only specific fields (like Name, Dept) might be editable, depending on your rules
        txtName.setEditable(false); 
        txtIC.setEditable(false); 
        txtDept.setEditable(false); 
        txtPosition.setEditable(false); 
        txtSalary.setEditable(false); 

        // Layout the form (Using simple 2-column GridBag for alignment)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Placing components in 2 columns
        formPanel.add(lblName, gbc); gbc.gridx = 1; formPanel.add(txtName, gbc); gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(lblIC, gbc); gbc.gridx = 1; formPanel.add(txtIC, gbc); gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(lblDept, gbc); gbc.gridx = 1; formPanel.add(txtDept, gbc); gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(lblPosition, gbc); gbc.gridx = 1; formPanel.add(txtPosition, gbc); gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(lblSalary, gbc); gbc.gridx = 1; formPanel.add(txtSalary, gbc); 

        northPanel.add(formPanel);
        add(northPanel, BorderLayout.NORTH);
    }
    
    // --- 3. Center Panel (Action Button) ---
    void loadCentrePanel() {
        centrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        
        btnUpdate = new JButton("Update My Information");
        btnUpdate.setBackground(java.awt.Color.decode("#4A70A9"));
        btnUpdate.setForeground(Color.white);
        btnUpdate.setPreferredSize(new Dimension(200, 35));


        buttonPanel = new JPanel(); // A container to hold the button
        buttonPanel.add(btnUpdate);
        centrePanel.add(buttonPanel);

        add(centrePanel, BorderLayout.CENTER);
    }
    
    // Placeholder method to simulate loading data
    void loadUserData() {
        // Example: In a real app, you'd get the user ID, query the DB, and populate fields.
        txtName.setText("User Name");
        txtIC.setText("User IC");
        txtDept.setText("IT");
        txtPosition.setText("Technician");
        txtSalary.setText("3500.00");
    }
}