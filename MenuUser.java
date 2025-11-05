import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuUser extends JFrame {
    JPanel topPanel, centrePanel, containerPanel, buttonPanel;
    JLabel lblTitle, lblName;
    JButton btnEM, btnLM, btnPM;
    JMenuBar mnuBarTop;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    // to store name from login
    private String userName;
    // to store emp_id from login (so user pages can load their own data)
    private int empId;

    // old code can still call this
    public MenuUser() {
        this(0, "User");
    }

    // constructor with name only
    public MenuUser(String userName) {
        this(0, userName);
    }

    // recommended: call this from Login when you know both
    public MenuUser(int empId, String userName) {
        this.empId = empId;
        this.userName = userName;

        setSize(700, 400);
        setTitle("Menu (User)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createMenu();
        loadCentre();
        loadTop();
        setVisible(true);
    }

    void loadTop() {
        topPanel = new JPanel();
        topPanel.setBackground(java.awt.Color.decode("#05339C"));
        topPanel.setPreferredSize(new Dimension(1500, 60));

        lblTitle = new JLabel();
        lblTitle.setText("User");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 36));

        topPanel.add(lblTitle);

        add(topPanel, BorderLayout.NORTH);
    }

    void loadCentre() {
        centrePanel = new JPanel();
        centrePanel.setLayout(new GridLayout(2, 1, 0, 0));
        centrePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        containerPanel = new JPanel();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 60, 80));

        lblName = new JLabel();
        lblName.setText("Welcome! " + userName);
        lblName.setFont(new Font("Sans Serif", Font.BOLD, 58));

        btnEM = new JButton("Employee management");
        btnEM.setBackground(java.awt.Color.decode("#05339C"));
        btnEM.setForeground(Color.white);
        btnEM.setFocusable(false);

        btnLM = new JButton("Leave management");
        btnLM.setBackground(java.awt.Color.decode("#05339C"));
        btnLM.setForeground(Color.white);
        btnLM.setFocusable(false);

        btnPM = new JButton("Payroll management");
        btnPM.setBackground(java.awt.Color.decode("#05339C"));
        btnPM.setForeground(Color.white);
        btnPM.setFocusable(false);

        // button actions (now passing empId)
        btnEM.addActionListener(e -> {
            new EmployManageUser(empId);
            dispose();
        });
        btnLM.addActionListener(e -> {
            new LeaveManageUser(empId);
            dispose();
        });
        btnPM.addActionListener(e -> {
            new PayrollManageUser(empId);
            dispose();
        });

        containerPanel.add(lblName);

        buttonPanel.add(btnEM);
        buttonPanel.add(btnLM);
        buttonPanel.add(btnPM);

        centrePanel.add(containerPanel);
        centrePanel.add(buttonPanel);

        add(centrePanel, BorderLayout.CENTER);
    }

    void createMenu() {
        mnuBarTop = new JMenuBar();
        mnuOpen = new JMenu("Open");
        mnuEM = new JMenuItem("Employee management");
        mnuLM = new JMenuItem("Leave management");
        mnuPM = new JMenuItem("Payroll management");
        mnuExit = new JMenuItem("Exit");

        // menu actions (same as buttons, pass empId, close current)
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
}
