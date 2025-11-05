import javax.swing.*;
import java.awt.*;

public class MenuAdmin extends JFrame {

    JPanel topPanel, centrePanel, containerPanel, buttonPanel;
    JLabel lblTitle, lblName;
    JButton btnEM, btnLM, btnPM;
    JMenuBar mnuBarTop;
    JMenu mnuOpen;
    JMenuItem mnuEM, mnuLM, mnuPM, mnuExit;

    public MenuAdmin(){
        setSize(700,400);
        setTitle("Menu (Admin)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu();
        loadCentre();
        loadTop();
        setVisible(true);
    }

    void loadTop(){
        topPanel = new JPanel();
        topPanel.setBackground(java.awt.Color.decode("#4A70A9"));
        topPanel.setPreferredSize(new Dimension(1500,60));

        lblTitle = new JLabel();
        lblTitle.setText("Admin");
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 36));

        topPanel.add(lblTitle);

        add(topPanel, BorderLayout.NORTH);
    }

    void loadCentre(){
        centrePanel = new JPanel();
        centrePanel.setLayout(new GridLayout(2,1,0,0));
        centrePanel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

        containerPanel = new JPanel();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,3,10,10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40,80,60,80));

        lblName = new JLabel();
        lblName.setText("Welocome!");
        lblName.setFont(new Font("Sans Serif", Font.BOLD, 58));

        btnEM = new JButton("Employee management");
        btnEM.setBackground(java.awt.Color.decode("#4A70A9"));
        btnEM.setForeground(Color.white);
        btnEM.setFocusable(false);

        btnLM = new JButton("Leave management");
        btnLM.setBackground(java.awt.Color.decode("#4A70A9"));
        btnLM.setForeground(Color.white);
        btnLM.setFocusable(false);

        btnPM = new JButton("Payroll management");
        btnPM.setBackground(java.awt.Color.decode("#4A70A9"));
        btnPM.setForeground(Color.white);
        btnPM.setFocusable(false);

        containerPanel.add(lblName);

        buttonPanel.add(btnEM);
        buttonPanel.add(btnLM);
        buttonPanel.add(btnPM);

        centrePanel.add(containerPanel, BorderLayout.NORTH);
        centrePanel.add(buttonPanel, BorderLayout.CENTER);

        add(centrePanel);
        
    }

    void createMenu(){
        mnuBarTop = new JMenuBar();
        mnuOpen = new JMenu("Open");
        mnuEM = new JMenuItem("Employee management");
        mnuLM = new JMenuItem("Leave management");
        mnuPM = new JMenuItem("Payroll management");
        mnuExit = new JMenuItem("Exit");

        mnuOpen.add(mnuEM);
        mnuOpen.add(mnuLM);
        mnuOpen.add(mnuPM);
        mnuOpen.addSeparator();
        mnuOpen.add(mnuExit);

        mnuBarTop.add(mnuOpen);

       
        setJMenuBar(mnuBarTop);
    }
    
}
