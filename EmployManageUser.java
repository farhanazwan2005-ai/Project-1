import javax.swing.*;
import java.awt.*;

public class EmployManageUser extends JFrame {

    JPanel titlePanel, northPanel, centrePanel, formPanel, buttonPanel;
    JLabel lblTitle, lblName, lblIC, lblDept, lblPosition, lblSalary;
    JTextField txtName, txtIC, txtDept, txtPosition, txtSalary;
    JButton btnUpdate;
    JMenuBar mnuBarTop;
    JMenu mnuOpen;
    JMenuItem mnuExit, mnuEM, mnuLM, mnuPM; 

    public EmployManageUser() {
        setSize(800, 450); 
        setTitle("Employee Information (User View)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); 

        createMenu();
        loadNorthPanel(); 
        loadCentrePanel(); 
        
        loadUserData(); 

        setVisible(true);
    }

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

    void loadNorthPanel() {
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#05339C"));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        lblTitle = new JLabel("My Information");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 24));
        titlePanel.add(lblTitle);
        northPanel.add(titlePanel);

        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); 
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 50));

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

        txtName.setEditable(false); 
        txtIC.setEditable(false); 
        txtDept.setEditable(false); 
        txtPosition.setEditable(false); 
        txtSalary.setEditable(false); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        formPanel.add(lblName, gbc); gbc.gridx = 1; formPanel.add(txtName, gbc); gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(lblIC, gbc); gbc.gridx = 1; formPanel.add(txtIC, gbc); gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(lblDept, gbc); gbc.gridx = 1; formPanel.add(txtDept, gbc); gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(lblPosition, gbc); gbc.gridx = 1; formPanel.add(txtPosition, gbc); gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(lblSalary, gbc); gbc.gridx = 1; formPanel.add(txtSalary, gbc); 

        northPanel.add(formPanel);
        add(northPanel, BorderLayout.NORTH);
    }
    
    void loadCentrePanel() {
        centrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        
        btnUpdate = new JButton("Update My Information");
        btnUpdate.setBackground(java.awt.Color.decode("#05339C"));
        btnUpdate.setForeground(Color.white);
        btnUpdate.setPreferredSize(new Dimension(200, 35));

        buttonPanel = new JPanel(); 
        buttonPanel.add(btnUpdate);
        centrePanel.add(buttonPanel);

        add(centrePanel, BorderLayout.CENTER);
    }
    
    void loadUserData() {
        txtName.setText("User Name");
        txtIC.setText("User IC");
        txtDept.setText("IT");
        txtPosition.setText("Technician");
        txtSalary.setText("3500.00");
    }
}