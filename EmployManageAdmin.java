import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class EmployManageAdmin extends JFrame{

    JPanel titlePanel, northPanel, containerPanel, centrePanel, buttonPanel, itemPanel;
    JLabel lblTitle, lblName, lblIC, lblDept, lblPosition, lblSalary;
    JTextField txtName, txtIC, txtDept, txtPosition, txtSalary;
    JMenuBar mnuBarTop;
    JButton btnUpdate, btnDelete, btnAdd, btnView, btnSubmit;
    JTable tblView;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    public EmployManageAdmin(){
        setLayout(new BorderLayout()); 
        
        setSize(1200,800);
        setTitle("Employee management (Admin)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        createMenu(); 
        loadTop();
        loadCentre();
        
        setVisible(true);
    }

    void loadTop(){
        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS)); 

        titlePanel = new JPanel();
        titlePanel.setBackground(java.awt.Color.decode("#4A70A9"));

        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblTitle = new JLabel("Employee Management (Admin)"); 
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 28));
        titlePanel.add(lblTitle);
        
        itemPanel = new JPanel();

        itemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); 
        itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        lblName = new JLabel("Name:");
        lblIC = new JLabel("IC Number:");
        lblDept = new JLabel("Department:");
        lblPosition = new JLabel("Position:");
        lblSalary = new JLabel("Salary:");

        txtName = new JTextField(12); 
        txtIC = new JTextField(12);
        txtDept = new JTextField(12);
        txtPosition = new JTextField(12);
        txtSalary = new JTextField(12);

        itemPanel.add(lblName);
        itemPanel.add(txtName);
        itemPanel.add(lblIC);
        itemPanel.add(txtIC);
        itemPanel.add(lblDept);
        itemPanel.add(txtDept);
        itemPanel.add(lblPosition);
        itemPanel.add(txtPosition);
        itemPanel.add(lblSalary);
        itemPanel.add(txtSalary);
        
        northPanel.add(titlePanel);
        northPanel.add(itemPanel);
        
        add(northPanel, BorderLayout.NORTH); 
    }
    
    void loadCentre(){
        centrePanel = new JPanel();
        centrePanel.setLayout(new BorderLayout());
        
        // 2a. JTable Setup (Required for data display)
        String[] columnNames = {"Name", "IC Number", "Department", "Position", "Basic Salary"};
        // Dummy data for visualization
        Object[][] data = {
            {"John Doe", "901234-01-5678", "IT", "Technician", "3500.00"},
            {"Jane Smith", "850101-14-1234", "HR", "Manager", "6000.00"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblView = new JTable(model);
        tblView.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tblView);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        btnAdd = new JButton("Add New");
        btnAdd.setBackground(java.awt.Color.decode("#1055C9"));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusable(false);
        btnAdd.setPreferredSize(new Dimension(150, 30));


        btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(java.awt.Color.decode("#41A67E"));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusable(false);
        btnUpdate.setPreferredSize(new Dimension(150, 30));


        btnDelete = new JButton("Delete Employee");
        btnDelete.setBackground(java.awt.Color.decode("#BF092F"));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusable(false);
        btnDelete.setPreferredSize(new Dimension(150, 30));


        btnView = new JButton("View All");
        btnView.setBackground(java.awt.Color.decode("#132440"));
        btnView.setForeground(Color.WHITE);
        btnView.setFocusable(false);
        btnView.setPreferredSize(new Dimension(150, 30));

        btnSubmit = new JButton("Save");
        btnSubmit.setBackground(java.awt.Color.decode("#016B61"));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusable(false);
        btnSubmit.setPreferredSize(new Dimension(150, 30));
    
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);
        buttonPanel.add(btnSubmit);
        
        centrePanel.add(scrollPane, BorderLayout.CENTER);
        centrePanel.add(buttonPanel, BorderLayout.SOUTH);

        add(centrePanel, BorderLayout.CENTER);
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
}