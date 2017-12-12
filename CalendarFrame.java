
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
        setFont(new Font("굴림", Font.PLAIN, 12));
        setTitle("캘린-돈");
		setSize(860, 600);
		setLocation(45, 40);
		
//		JComboBox comboBox;
//		
//		comboBox = new JComboBox();
//		
//		comboBox.addItem("수입");
//		comboBox.addItem("지출");
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
//        buyLabel1 = new JLabel("지출내용 :");
//        buySelect = new JComboBox(select);
//        
//        p0.add(new JLabel(""));
//        p2.add(buyLabel1);
//        p2.add(buySelect); 
//        
//        add(p0);add(p2);
//        
        setVisible(true);
 
        // 요일 표시 테이블
        String[] weekColumns = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        weekTable.addRow(weekColumns);
        
        // 가운데 정렬 renderer
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
 
        // 일요일 빨간색
        DefaultTableCellRenderer sunRed = new DefaultTableCellRenderer();
        sunRed.setForeground(Color.RED);
        sunRed.setHorizontalAlignment(JLabel.CENTER);
 
        // 토요일 파란색
        DefaultTableCellRenderer satBlue = new DefaultTableCellRenderer();
        satBlue.setForeground(Color.BLUE);
        satBlue.setHorizontalAlignment(JLabel.CENTER);
 
        label = new JLabel("");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        label.setBounds(70, 10, 660, 54);					// x월 글자
        contentPane.add(label);
        
 
        DefaultTableCellRenderer top = new DefaultTableCellRenderer();
        top.setVerticalAlignment(SwingConstants.TOP);
 
        // 날짜 일요일 빨간색
        DefaultTableCellRenderer sunDateRed = new DefaultTableCellRenderer();
        sunDateRed.setForeground(Color.RED);
        sunDateRed.setVerticalAlignment(SwingConstants.TOP);
 
        // 날짜 토요일 파란색
        DefaultTableCellRenderer satDateBlue = new DefaultTableCellRenderer();
        satDateBlue.setForeground(Color.BLUE);
        satDateBlue.setVerticalAlignment(SwingConstants.TOP);
        
        leftButton = new JButton("<");
        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_leftButton_actionPerformed(e);
            }
        });
        leftButton.setFont(new Font("굴림", Font.PLAIN, 20));
        leftButton.setBounds(12, 17, 450, 46);
        
        // 버튼 투명화
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
        rightButton.setFont(new Font("굴림", Font.PLAIN, 20));
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
//        todayButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
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
//        addButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
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
        weekBarTable.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        weekBarTable.setBackground(new Color(220, 220, 220));
        weekBarTable.setRowHeight(25);
        weekBarTable.setFillsViewportHeight(true);
        weekBarTable.setBorder(new LineBorder(new Color(0, 0, 0)));
        weekBarTable.setBounds(12, 85, 799, 25);						 // 위에 요일 적힌 칸 크기
 
        weekBarTable.getColumnModel().getColumn(1).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(2).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(3).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(4).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(5).setCellRenderer(center);
        weekBarTable.getColumnModel().getColumn(0).setCellRenderer(sunRed);
        weekBarTable.getColumnModel().getColumn(6).setCellRenderer(satBlue);
        contentPane.add(weekBarTable);
        
                // 날짜 표시 테이블
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
                dayViewTable.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
                dayViewTable.setBorder(new LineBorder(new Color(0, 0, 0)));
                dayViewTable.setRowHeight(70);							// 달력 날짜 나온 한 칸 높이
                dayViewTable.setFillsViewportHeight(true);
                dayViewTable.setBounds(12, 109, 799, 420);				// 달력  날짜 나온 칸 크기
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
                console.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
                console.setHorizontalAlignment(SwingConstants.RIGHT);
                console.setBounds(12, 472, 750, 15);
                contentPane.add(console);
 
        todayY = calendar.get(Calendar.YEAR);
        todayM = calendar.get(Calendar.MONTH);
        todayD = calendar.get(Calendar.DATE);
 
        
        
        // 실행시 날짜 뿌리면서 오늘로 포커싱
        refresh();
        todayFocus();
 
    }
 
    // 달력 소스
    protected void refresh() {
 
        label.setText((calendar.get(Calendar.YEAR) + "년, ") + (calendar.get(Calendar.MONTH) + 1) + "월");
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
    
    
 
    
    
    // < 버튼 입력시
    protected void do_leftButton_actionPerformed(ActionEvent e) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, calendar.get(Calendar.DATE));
        refresh();
    }
 
    // > 버튼 입력시
    protected void do_rightButton_actionPerformed(ActionEvent e) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
        refresh();
    }
 
    // 마우스 휠 구현
    protected void do_contentPane_mouseWheelMoved(MouseWheelEvent arg0) {
        int notches = arg0.getWheelRotation();
        if (notches < 0) {
            do_leftButton_actionPerformed(null);
        } else {
            do_rightButton_actionPerformed(null);
        }
    }
 
    // 오늘 버튼 클릭
    protected void do_todayButton_actionPerformed(ActionEvent e) {
        calendar.set(todayY, todayM, todayD);
        refresh();
//        setSize(1300, 600);
        
//        Dataframe df = new Dataframe(this);
//        df.Run();
        todayFocus();
    }
 
    // 오늘로 포커싱 하기
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
    
 
    // 더블클릭 이벤트
    protected void do_dayViewTable_mouseClicked(MouseEvent e) {
        
        focus = true;
        if (e.getClickCount() == 2) {				// 두 번 클릭하면 커짐
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
        else if (e.getClickCount()==3) {			// 세 번 클릭하면 원래크기
            setSize(860, 600);
            addAgenda();        	
        }
    }


 
    // 일정추가 버튼
    protected void do_addButton_actionPerformed(ActionEvent e) {
        if (focus == true) {
            addAgenda();
        }
    }
 
    // 일정추가 메서드
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