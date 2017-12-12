
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Calendar;
import java.text.*;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
 
public class CalendarFrame extends JFrame {
 
    private JPanel contentPane;
    private JTable dayViewTable;
    private JLabel label;
    private JLabel today;
    private JButton leftButton;
    private JButton rightButton;
    private JTable weekBarTable;
    private JButton todayButton;
    private JButton addButton;
 
    private boolean focus = false;
    
    JLabel todaydate;

    
    int todayY;
    int todayM;
    int todayD;
    int serve=1;
    
 
    Calendar calendar = Calendar.getInstance();
 
    DefaultTableModel weekTable = new DefaultTableModel(0, 7);
    DefaultTableModel dayTable = new DefaultTableModel(6, 7);
    private JLabel console;
 
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CalendarFrame frame = new CalendarFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });		
        
    }
    
    public CalendarFrame() {
//        setResizable(false);
        setFont(new Font("����", Font.PLAIN, 12));
        setTitle("Ķ��-��");
		setSize(860, 600);
		setLocation(45, 40);
		
//		JComboBox comboBox;
//		
//		comboBox = new JComboBox();
//		
//		comboBox.addItem("����");
//		comboBox.addItem("����");
//		add(comboBox, "Center");

		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 700, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent arg0) {
                do_contentPane_mouseWheelMoved(arg0);
            }
        });
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
//        JPanel p0 = new JPanel();
//        JPanel p2 = new JPanel();
//        
//        buyLabel1 = new JLabel("���⳻�� :");
//        buySelect = new JComboBox(select);
//        
//        p0.add(new JLabel(""));
//        p2.add(buyLabel1);
//        p2.add(buySelect); 
//        
//        add(p0);add(p2);
//        
        setVisible(true);
 
        // ���� ǥ�� ���̺�
        String[] weekColumns = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        weekTable.addRow(weekColumns);
        
        // ��� ���� renderer
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
 
        // �Ͽ��� ������
        DefaultTableCellRenderer sunRed = new DefaultTableCellRenderer();
        sunRed.setForeground(Color.RED);
        sunRed.setHorizontalAlignment(JLabel.CENTER);
 
        // ����� �Ķ���
        DefaultTableCellRenderer satBlue = new DefaultTableCellRenderer();
        satBlue.setForeground(Color.BLUE);
        satBlue.setHorizontalAlignment(JLabel.CENTER);
 
        label = new JLabel("");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("���� ���", Font.BOLD, 26));
        label.setBounds(70, 10, 660, 54);					// x�� ����
        contentPane.add(label);
        
 
        DefaultTableCellRenderer top = new DefaultTableCellRenderer();
        top.setVerticalAlignment(SwingConstants.TOP);
 
        // ��¥ �Ͽ��� ������
        DefaultTableCellRenderer sunDateRed = new DefaultTableCellRenderer();
        sunDateRed.setForeground(Color.RED);
        sunDateRed.setVerticalAlignment(SwingConstants.TOP);
 
        // ��¥ ����� �Ķ���
        DefaultTableCellRenderer satDateBlue = new DefaultTableCellRenderer();
        satDateBlue.setForeground(Color.BLUE);
        satDateBlue.setVerticalAlignment(SwingConstants.TOP);
        
        leftButton = new JButton("<");
        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_leftButton_actionPerformed(e);
            }
        });
        leftButton.setFont(new Font("����", Font.PLAIN, 20));
        leftButton.setBounds(12, 17, 450, 46);
        
        // ��ư ����ȭ
        leftButton.setOpaque(false);
        leftButton.setContentAreaFilled(false);
        leftButton.setBorderPainted(false);
        leftButton.setFocusable(false);
        contentPane.add(leftButton);
 
        rightButton = new JButton(">");
        rightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_rightButton_actionPerformed(e);
            }
        });
        rightButton.setFont(new Font("����", Font.PLAIN, 20));
        rightButton.setBounds(466, 17, 210, 46);
        rightButton.setOpaque(false);
        rightButton.setContentAreaFilled(false);
        rightButton.setBorderPainted(false);
        rightButton.setFocusable(false);
        contentPane.add(rightButton);
 
//        todayButton = new JButton("\uC624\uB298");
//        todayButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                do_todayButton_actionPerformed(e);
//            }
//        });
//        todayButton.setBackground(SystemColor.control);
//        todayButton.setFont(new Font("���� ���", Font.PLAIN, 12));
//        todayButton.setBounds(377, 61, 79, 23);
//        todayButton.setOpaque(false);
//        todayButton.setBorderPainted(false);
//        todayButton.setFocusable(false);
//        contentPane.add(todayButton);
// 
//        addButton = new JButton("\uFF0B");
//        addButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                do_addButton_actionPerformed(e);
//            }
//        });
//        addButton.setBackground(SystemColor.control);
//        addButton.setFont(new Font("���� ���", Font.PLAIN, 14));
//        addButton.setBounds(452, 61, 60, 23);
//        addButton.setOpaque(false);
//        addButton.setFocusable(false);
//        addButton.setBorderPainted(false);
//        contentPane.add(addButton);
 
        weekBarTable = new JTable(weekTable) {
            private static final long serialVersionUID = 1L;
 
            public boolean isCellEditable(int row, int column) {
                return false;
            };
 
        };
 
        weekBarTable.setEnabled(false);
        weekBarTable.setRowSelectionAllowed(false);
        weekBarTable.setFont(new Font("���� ���", Font.BOLD, 12));
        weekBarTable.setBackground(new Color(220, 220, 220));
        weekBarTable.setRowHeight(25);
        weekBarTable.setFillsViewportHeight(true);
        weekBarTable.setBorder(new LineBorder(new Color(0, 0, 0)));
        weekBarTable.setBounds(12, 85, 799, 25);						 // ���� ���� ���� ĭ ũ��
 
        weekBarTable.getColumnModel().getColumn(1).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(2).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(3).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(4).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(5).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(0).setCellRenderer(sunRed);
        weekBarTable.getColumnModel().getColumn(6).setCellRenderer(satBlue);
        contentPane.add(weekBarTable);
        
                // ��¥ ǥ�� ���̺�
                dayViewTable = new JTable(dayTable) {
                    private static final long serialVersionUID = 1L;
        
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    };
        
                };
                dayViewTable.setCellSelectionEnabled(true);
                dayViewTable.setColumnSelectionAllowed(true);
                dayViewTable.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        do_dayViewTable_mouseClicked(e);
                    }
                });
                dayViewTable.setFont(new Font("���� ���", Font.PLAIN, 12));
                dayViewTable.setBorder(new LineBorder(new Color(0, 0, 0)));
                dayViewTable.setRowHeight(70);							// �޷� ��¥ ���� �� ĭ ����
                dayViewTable.setFillsViewportHeight(true);
                dayViewTable.setBounds(12, 109, 799, 420);				// �޷�  ��¥ ���� ĭ ũ��
                dayViewTable.getColumnModel().getColumn(0).setCellRenderer(top);
                dayViewTable.getColumnModel().getColumn(1).setCellRenderer(top);
                dayViewTable.getColumnModel().getColumn(2).setCellRenderer(top);
                dayViewTable.getColumnModel().getColumn(3).setCellRenderer(top);
                dayViewTable.getColumnModel().getColumn(4).setCellRenderer(top);
                dayViewTable.getColumnModel().getColumn(5).setCellRenderer(top);
                dayViewTable.getColumnModel().getColumn(6).setCellRenderer(top);
                dayViewTable.getColumnModel().getColumn(0).setCellRenderer(sunDateRed);
                dayViewTable.getColumnModel().getColumn(6).setCellRenderer(satDateBlue);
                contentPane.add(dayViewTable);
                
                console = new JLabel("");
                console.setFont(new Font("���� ���", Font.PLAIN, 12));
                console.setHorizontalAlignment(SwingConstants.RIGHT);
                console.setBounds(12, 472, 750, 15);
                contentPane.add(console);
 
        todayY = calendar.get(Calendar.YEAR);
        todayM = calendar.get(Calendar.MONTH);
        todayD = calendar.get(Calendar.DATE);
 
        
        
        // ����� ��¥ �Ѹ��鼭 ���÷� ��Ŀ��
        refresh();
        todayFocus();
 
    }
 
    // �޷� �ҽ�
    protected void refresh() {
 
        label.setText((calendar.get(Calendar.YEAR) + "��, ") + (calendar.get(Calendar.MONTH) + 1) + "��");
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
 
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                dayViewTable.setValueAt("", i, j);
            }
        }
 
        for (int day = 1, row = 0, col = dayWeek - 1; day < endDay + 1; day++, col++) {
            if (col % 7 == 0) {
                col = 0;
                row += 1;
            }
            
            dayViewTable.setValueAt(" " + day, row, col);
 
//                String sqlYear = calendar.get(Calendar.YEAR) + "";
//                String sqlMonth = (calendar.get(Calendar.MONTH) + 1) + "";
//                String sqlDay = day + "";
//                if (sqlMonth.length() == 1) {sqlMonth = "0" + sqlMonth;}
//                if (sqlDay.length() == 1) {sqlDay = "0" + sqlDay;}
//
//            String sqlInput = sqlYear + sqlMonth + sqlDay;
//            
//            try {int result = searchAgenda.Search(sqlInput);
//            
//            if (result != 0) {
//                
//            }
//            }
//            
//            catch (ClassNotFoundException | SQLException e) {e.printStackTrace();}
        
        }
    }
    
    
 
    
    
    // < ��ư �Է½�
    protected void do_leftButton_actionPerformed(ActionEvent e) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, calendar.get(Calendar.DATE));
        refresh();
    }
 
    // > ��ư �Է½�
    protected void do_rightButton_actionPerformed(ActionEvent e) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
        refresh();
    }
 
    // ���콺 �� ����
    protected void do_contentPane_mouseWheelMoved(MouseWheelEvent arg0) {
        int notches = arg0.getWheelRotation();
        if (notches < 0) {
            do_leftButton_actionPerformed(null);
        } else {
            do_rightButton_actionPerformed(null);
        }
    }
 
    // ���� ��ư Ŭ��
    protected void do_todayButton_actionPerformed(ActionEvent e) {
        calendar.set(todayY, todayM, todayD);
        refresh();
//        setSize(1300, 600);
        
//        Dataframe df = new Dataframe(this);
//        df.Run();
        todayFocus();
    }
 
    // ���÷� ��Ŀ�� �ϱ�
    protected void todayFocus() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (dayViewTable.getValueAt(i, j).equals(" " + todayD)) {
                    dayViewTable.changeSelection(i, j, false, false);
                    dayViewTable.requestFocus();
                }
            }
        }
 
    }
    
 
    // ����Ŭ�� �̺�Ʈ
    protected void do_dayViewTable_mouseClicked(MouseEvent e) {
        
        focus = true;
        if (e.getClickCount() == 2) {				// �� �� Ŭ���ϸ� Ŀ��
//            setSize(1300, 600);
//            JFrame f = new JFrame(" ");
//            JFrame panel=new JFrame();
//            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            f.getContentPane().add(panel, "Center");
//            f.setVisible(true);
//        	  serve++;
        	  Doubleclickframe df = new Doubleclickframe(this);
        	  df.Run();
            addAgenda();
        }
        else if (e.getClickCount()==3) {			// �� �� Ŭ���ϸ� ����ũ��
            setSize(860, 600);
            addAgenda();        	
        }
    }


 
    // �����߰� ��ư
    protected void do_addButton_actionPerformed(ActionEvent e) {
        if (focus == true) {
            addAgenda();
        }
    }
 
    // �����߰� �޼���
    protected void addAgenda() {
 
        int row = dayViewTable.getSelectedRow();
        int col = dayViewTable.getSelectedColumn();
 
        String day = dayViewTable.getValueAt(row, col).toString();
 
        String sqlYear = calendar.get(Calendar.YEAR) + "";
        String sqlMonth = (calendar.get(Calendar.MONTH) + 1) + "";
        String sqlDay = day.trim();
        
        if (sqlMonth.length() == 1) {
            sqlMonth = "0" + sqlMonth;
        }
        if (sqlDay.length() == 1) {
            sqlDay = "0" + sqlDay;
        }
                
        String sqlInput = sqlYear + sqlMonth + sqlDay;
 
//        if (!day.equals("")) {
//            AgendaDB addAgenda = new AgendaDB(this, ModalityType.APPLICATION_MODAL, sqlInput);
//            addAgenda.setLocationRelativeTo(this);
//            addAgenda.setVisible(true);
//        }
    }
    
}