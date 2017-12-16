import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import javax.swing.table.DefaultTableModel;

import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.*;
import java.text.*;

class CalendarDataManager extends JFrame{ // 6*7배열에 나타낼 달력 값을 구하는 class
	static final int CAL_WIDTH = 7;
	final static int CAL_HEIGHT = 6;
	int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
	int calYear;
	int calMonth;
	int calDayOfMon;
	final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
	int calLastDate;
	Calendar today = Calendar.getInstance();
	Calendar cal;
	
	
	public CalendarDataManager(){ 
		setToday(); 
	}
	public void setToday(){
		calYear = today.get(Calendar.YEAR); 
		calMonth = today.get(Calendar.MONTH);
		calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
		makeCalData(today);
	}
	private void makeCalData(Calendar cal){
		// 1일의 위치와 마지막 날짜를 구함 
		int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
		if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
		else calLastDate = calLastDateOfMonth[calMonth];
		// 달력 배열 초기화
		for(int i = 0 ; i<CAL_HEIGHT ; i++){
			for(int j = 0 ; j<CAL_WIDTH ; j++){
				calDates[i][j] = 0;
			}
		}
		// 달력 배열에 값 채워넣기
		for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
			if(i == 0) k = calStartingPos;
			else k = 0;
			for(int j = k ; j<CAL_WIDTH ; j++){
				if(num <= calLastDate) calDates[i][j]=num++;
			}
		}
	}
	private int leapCheck(int year){ // 윤년인지 확인하는 함수
		if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
		else return 0;
	}
	public void moveMonth(int mon){ // 현재달로 부터 n달 전후를 받아 달력 배열을 만드는 함수(1년은 +12, -12달로 이동 가능)
		calMonth += mon;
		if(calMonth>11) while(calMonth>11){
			calYear++;
			calMonth -= 12;
		} else if (calMonth<0) while(calMonth<0){
			calYear--;
			calMonth += 12;
		}
		cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
		makeCalData(cal);
	}
}

public class calendon extends CalendarDataManager implements ActionListener, ItemListener { // CalendarDataManager의 GUI + 메모기능 + 시계
	// 창 구성요소와 배치도	
	
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rss;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/localhost";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "ded3507**";
	Connection conn = null;
	Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;   // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )
	
	private static final long serialVersionUID = 1L;
    private JTable table;    
    private JScrollPane scrollPane;
	
	
	
	
	JFrame mainFrame;
		ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
	
	JPanel calOpPanel;
		JButton todayBut;
		JLabel todayLab;
		JButton lYearBut;
		JButton lMonBut;
		JLabel curMMYYYYLab;
		JButton nMonBut;
		JButton nYearBut;
		ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();
	
	JPanel calPanel;
		JButton weekDaysName[];
		JButton dateButs[][] = new JButton[6][7];
		listenForDateButs lForDateButs = new listenForDateButs(); 
	
	JPanel infoPanel;
		JLabel infoClock;
	
	JPanel memoPanel;
		JLabel selectedDate;
		JLabel sqllist;
		JTextArea memoArea;
		JPanel memoSubPanel;
		JButton saveBut; 	
		
		JPanel buttonPanel;
		JPanel tablePanel;
		JButton update, delete;
		String fieldname[]= {"구분", "분류", "메모", "수입", "지출"};
		DefaultTableModel dt = new DefaultTableModel(fieldname, 0);
//		dt.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableModel model;
		Container pane;
		JPanel dataPanel;
		
		String datess;
//		JTable table;
		JScrollPane scroll;
		Dialog dialog;
		Panel dialogP1;
		Panel dialogP2;
		Panel dialogP3;
		
	JPanel p0;
	JPanel nextPanel;
	JPanel nextPanel2;
	 JTextField codeField;
	 JTextField memo;
	 String strSql;
	 JButton save;
	 JButton show;
	 JLabel buyLabel1;
	 JLabel buyLabel2;
	 JLabel memotitle;
	 JLabel incomexpendLable;
	 JComboBox buySelect;
	 JComboBox incomexpend;
	 String select[] = {"선택", "수입", "지출"}; 
		String income[] = {"용돈", "월급", "금융소득", "부수입", "기타"};
		String expend[] = {"식비", "미용/패션", "교통", "생활", "건강", "기타"};
		 int incomming;
		 int expending;
		 String memocontents;
		 String incomexpending;
		 int year;
		 int month;
		 int day;
		 
		JPanel selectPanel;
		
	JPanel frameBottomPanel;
		JLabel bottomInfo = new JLabel("Welcome to Memo Calendar!");
	//상수, 메세지
	final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
	final String title = "캘린-돈";

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new calendon();
			}
		});
	}
		
	public calendon (){ //구성요소 순으로 정렬되어 있음. 각 판넬 사이에 빈줄로 구별
		mainFrame = new JFrame(title);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1400,600);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setIconImage(icon.getImage());
		try{
			UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//LookAndFeel Windows 스타일 적용
			SwingUtilities.updateComponentTreeUI(mainFrame) ;
		}catch(Exception e){
			bottomInfo.setText("ERROR : LookAndFeel setting failed");
		}		
		
		
				
		calOpPanel = new JPanel();
			todayBut = new JButton("Today");
			todayBut.setToolTipText("Today");
			todayBut.addActionListener(lForCalOpButtons);
			todayLab = new JLabel(today.get(Calendar.YEAR)+"년 "+(today.get(Calendar.MONTH) + 1)+"월 "+today.get(Calendar.DAY_OF_MONTH)+"일");
			lYearBut = new JButton("<<");
			lYearBut.setToolTipText("Previous Year");
			lYearBut.addActionListener(lForCalOpButtons);
			lMonBut = new JButton("<");
			lMonBut.setToolTipText("Previous Month");
			lMonBut.addActionListener(lForCalOpButtons);
			curMMYYYYLab = new JLabel("<html><table width=200><tr><th>"+calYear+"년 "+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+"월 "+"</th></tr></table></html>");
			curMMYYYYLab.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			todayLab.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			nMonBut = new JButton(">");
			nMonBut.setToolTipText("Next Month");
			nMonBut.addActionListener(lForCalOpButtons);
			nYearBut = new JButton(">>");
			nYearBut.setToolTipText("Next Year");
			nYearBut.addActionListener(lForCalOpButtons);
			calOpPanel.setLayout(new GridBagLayout());
			GridBagConstraints calOpGC = new GridBagConstraints();
			calOpGC.gridx = 1;
			calOpGC.gridy = 1;
			calOpGC.gridwidth = 2;
			calOpGC.gridheight = 1;
			calOpGC.weightx = 1;
			calOpGC.weighty = 1;
			calOpGC.insets = new Insets(5,5,0,0);
			calOpGC.anchor = GridBagConstraints.WEST;
			calOpGC.fill = GridBagConstraints.NONE;
			calOpPanel.add(todayBut,calOpGC);
			calOpGC.gridwidth = 3;
			calOpGC.gridx = 2;
			calOpGC.gridy = 1;
			calOpPanel.add(todayLab,calOpGC);
			calOpGC.anchor = GridBagConstraints.CENTER;
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 1;
			calOpGC.gridy = 2;
			calOpPanel.add(lYearBut,calOpGC);
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 2;
			calOpGC.gridy = 2;
			calOpPanel.add(lMonBut,calOpGC);
			calOpGC.gridwidth = 2;
			calOpGC.gridx = 3;
			calOpGC.gridy = 2;
			calOpPanel.add(curMMYYYYLab,calOpGC);
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 5;
			calOpGC.gridy = 2;
			calOpPanel.add(nMonBut,calOpGC);
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 6;
			calOpGC.gridy = 2;
			calOpPanel.add(nYearBut,calOpGC);
		
		calPanel=new JPanel();
			weekDaysName = new JButton[7];
			for(int i=0 ; i<CAL_WIDTH ; i++){
				weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);
				weekDaysName[i].setBorderPainted(false);
				weekDaysName[i].setContentAreaFilled(false);
				weekDaysName[i].setForeground(Color.WHITE);
				if(i == 0) weekDaysName[i].setBackground(new Color(200, 50, 50));
				else if (i == 6) weekDaysName[i].setBackground(new Color(50, 100, 200));
				else weekDaysName[i].setBackground(new Color(150, 150, 150));
				weekDaysName[i].setOpaque(true);
				weekDaysName[i].setFocusPainted(false);
				calPanel.add(weekDaysName[i]);
			}
			for(int i=0 ; i<CAL_HEIGHT ; i++){
				for(int j=0 ; j<CAL_WIDTH ; j++){
					dateButs[i][j]=new JButton();
					dateButs[i][j].setBorderPainted(false);
					dateButs[i][j].setContentAreaFilled(false);
					dateButs[i][j].setBackground(Color.WHITE);
					dateButs[i][j].setOpaque(true);
					dateButs[i][j].addActionListener(lForDateButs);
					calPanel.add(dateButs[i][j]);
				}
			}
			calPanel.setLayout(new GridLayout(0,7,2,2));
			calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			calPanel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			showCal(); // 달력을 표시
						
		infoPanel = new JPanel();
			infoPanel.setLayout(new BorderLayout());
			infoClock = new JLabel("", SwingConstants.RIGHT);
			infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			infoPanel.add(infoClock, BorderLayout.NORTH);
			selectedDate = new JLabel(today.get(Calendar.YEAR)+"년 "+(today.get(Calendar.MONTH)+1)+"월 "+today.get(Calendar.DAY_OF_MONTH)+"일 ", SwingConstants.LEFT);
			selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
			selectedDate.setFont(new Font("맑은 고딕", Font.BOLD, 17));
						
		memoPanel=new JPanel();
			memoPanel.setBorder(BorderFactory.createTitledBorder(""));
			p0=new JPanel();
			int row=500;
			int rowHeight=300;
			
			tablePanel = new JPanel();
			buttonPanel = new JPanel();
			model = new DefaultTableModel(fieldname, 0);
			table = new JTable(model);
//			JTable.setRowHeight(int row, int rowHeight);
			update = new JButton("수정");
			delete = new JButton("삭제");
			delete.addActionListener(this);
			scroll = new JScrollPane(table);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//			add(scrollPane); 
			pane = new JPanel();
			pane.setLayout(new BorderLayout());
			
			tablePanel.add(scroll);
//			tablePanel.setPreferredSize(new Dimension(800, 500));
			buttonPanel.add(update);
			buttonPanel.add(delete);

			pane.add(tablePanel, BorderLayout.CENTER);
			pane.add(buttonPanel, BorderLayout.SOUTH);

			nextPanel =new JPanel();	
			nextPanel2 = new JPanel();
			
			nextPanel = new JPanel();
			buyLabel1 = new JLabel("구분 :");
			buySelect = new JComboBox(select);
		  
			buySelect.addItemListener(this);
			incomexpendLable = new JLabel("  분류 :");
			incomexpend = new JComboBox();
			incomexpend.addItemListener(this);
			incomexpend.setEnabled(false);
		  
			memotitle = new JLabel("  메모 :");
			memo = new JTextField(13);
		  
			buyLabel2 = new JLabel("  금액 :");
			codeField = new JTextField(8);
			save = new JButton("입력");
			save.addActionListener(this);
			show = new JButton("목록");
			show.addActionListener(this);
		  		  
			  memoPanel.add(selectedDate);
  
			  nextPanel.add(buyLabel1);
			  nextPanel.add(buySelect);
			  nextPanel.add(incomexpendLable);
			  nextPanel.add(incomexpend);
			  nextPanel.add(memotitle);
			  nextPanel.add(memo);
			  nextPanel.add(buyLabel2);
			  nextPanel.add(codeField);
			  nextPanel.add(save);
//			  nextPanel.add(show);
			  nextPanel.setFont(new Font("맑은 고딕", Font.BOLD, 17));


		//calOpPanel, calPanel을  frameSubPanelWest에 배치
		JPanel frameSubPanelWest = new JPanel();
		Dimension calOpPanelSize = calOpPanel.getPreferredSize();
		calOpPanelSize.height = 90;
		calOpPanel.setPreferredSize(calOpPanelSize);
		frameSubPanelWest.setLayout(new BorderLayout());
		frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
		frameSubPanelWest.add(calPanel,BorderLayout.CENTER);
		


		//infoPanel, memoPanel을  frameSubPanelEast에 배치
		JPanel frameSubPanelEast = new JPanel();
		frameSubPanelEast.setLayout(new GridLayout(6,1));
		Dimension infoPanelSize=infoPanel.getPreferredSize();
		infoPanel.setPreferredSize(infoPanelSize);
		frameSubPanelEast.add(p0);
		frameSubPanelEast.add(memoPanel);
		frameSubPanelEast.add(nextPanel);
		frameSubPanelEast.add(pane);
		Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
		frameSubPanelWestSize.width = 800;
		frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);
		
		//뒤늦게 추가된 bottom Panel..
		frameBottomPanel = new JPanel();
		frameBottomPanel.add(bottomInfo);
		
		//frame에 전부 배치
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
		mainFrame.add(frameSubPanelEast, BorderLayout.CENTER);
		mainFrame.add(frameBottomPanel, BorderLayout.SOUTH);
		mainFrame.setVisible(true);

		
//		dao.userSelectAll(dt);
		
		
		focusToday(); //현재 날짜에 focus를 줌 (mainFrame.setVisible(true) 이후에 배치해야함)
		
		//Thread 작동(시계, bottomMsg 일정시간후 삭제)
		ThreadConrol threadCnl = new ThreadConrol();
		threadCnl.start();	
	}
	
	
	 public void itemStateChanged (ItemEvent e) {		// 동적 콤보박스
			String income[] = {"용돈", "월급", "금융소득", "부수입", "기타"};
			String expend[] = {"식비", "미용/패션", "교통", "생활", "건강", "기타"};

			if (e.getSource() == buySelect) {
				if (buySelect.getSelectedItem().equals("선택")) {
					incomexpend.setEnabled(false);
					incomming=0;
					expending=0;
				} else if (buySelect.getSelectedItem().equals("수입")) {
					incomexpend.setEnabled(true);
					incomexpend.removeAllItems();
					String src = codeField.getText();
					expending=0;
					for (int i = 0; i < income.length; i++) {
						incomexpend.addItem(income[i]);
					}
				} else if (buySelect.getSelectedItem().equals("지출")) {
					incomexpend.setEnabled(true);
					incomexpend.removeAllItems();
					incomming=0;
					for (int i = 0; i < expend.length; i++) {
						incomexpend.removeItem(expend[i]);
						incomexpend.addItem(expend[i]);
					}
				}
			}
		}

	private void focusToday(){
		if(today.get(Calendar.DAY_OF_WEEK) == 1)
			dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
		else
			dateButs[today.get(Calendar.WEEK_OF_MONTH)-1][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
	}
	

	public void actionPerformed(ActionEvent e)
	 {
		if(e.getSource()==save)//이벤트가 발생한 컴포넌트가 save이면 실행
		{
			   boolean number = false; //숫자만 일때 판별 bool
			   boolean character = false; //문자만 일때 판별 bool
			   String src = codeField.getText();
			   memocontents = new String(memo.getText());
			   incomexpending = (String) (incomexpend.getSelectedItem());
			   for(int i=0 ; i< src.length() ; i++){ //입력받은 값을 한글자씩 검색
			   char result = src.substring(i,i+1).charAt(0);
			   if((int)result<48 || (int)result>57){ //만약 숫자외의 문자라면...
			   character = true;
			   }else{ //만약 숫자라면...
			   number = true;
			   }
			   }
			   if(character == true && number == true){
			   JOptionPane.showMessageDialog(null, "문자와 숫자가 혼용되어있습니다 숫자만입력하세요");
			   }else if(character == true && number == false){
			   JOptionPane.showMessageDialog(null, "문자열입니다 숫자만 입력하세요");
			   }else if(character == false && number == true){
			   
			   int c = Integer.parseInt(codeField.getText());
			   
				if (buySelect.getSelectedItem().equals("수입")) {
					incomming=c;
					} else if (buySelect.getSelectedItem().equals("지출")) {
					expending=c;
				}
				String date=(calYear + "년 " + (calMonth+1) + "월 " + calDayOfMon + "일");
				
		   Save s = new Save(this,date,select[buySelect.getSelectedIndex()],incomexpending, memocontents, incomming, expending,calYear,(calMonth+1),calDayOfMon);
		   s.saveData();
		   }
		  } else if(e.getSource()==delete) {
			  System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
              DefaultTableModel model2 = (DefaultTableModel)table.getModel();
              int row = table.getSelectedRow();
              if(row<0) return; // 선택이 안된 상태면 -1리턴
              String query = "delete from cashdiary where memo= ?";

              try{
                  Class.forName(JDBC_DRIVER);  // 드라이버 로딩
                  con = DriverManager.getConnection(DB_URL,USER, PASS); // DB 연결
                  pstmt = con.prepareStatement(query);   
                  
                  // 물음표가 1개 이므로 4개 각각 입력해줘야한다.
                  pstmt.setString(1, (String) model2.getValueAt(row, 2));
//                  pstmt.setString(1, (String) model2.getValueAt(row, 3));
//                  pstmt.setString(1, (String) model2.getValueAt(row, 4));
                  int cnt = pstmt.executeUpdate();
                  //pstmt.executeUpdate(); create insert update delete 
                  //pstmt.executeQuery(); select 
              }catch(Exception eeee){
                  System.out.println(eeee.getMessage());
              }finally{
                  try {
                      pstmt.close();con.close();
                  } catch (Exception e2) {}
              }
              model2.removeRow(row);    // 테이블 상의 한줄 삭제
          }
	 }
                
	
	class Save
	{
	Frame f;
	String date=(calYear + "년 " + (calMonth+1) + "월 " + calDayOfMon + "일");
	String item;
	int c;
	String memocontents;
	int incomming;
	int expending;
	String incomexpending;
	int year;
	int month;
	int day;

	 public Save(calendon calendon, String date, String item, String incomexpending, String memocontents, int incomming, int expending, int year, int month, int day) {
		 this.f = f;
		 this.date=date;
		 this.item = item;
		 this.incomexpending=incomexpending;
		 this.memocontents=memocontents;
		 this.incomming=incomming;
		 this.expending=expending;
		 this.year=year;
		 this.month=month;
		 this.day=day;
	 } 

	public void saveData()
	 {
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");
	 }catch(ClassNotFoundException e){System.out.println("JDBC driver loading error:");}
	  try{
	   Connection con;
	   String jdbcUrl = "jdbc:mysql://localhost:3306/localhost";
	   String db_id = "root";
	   String db_pw = "ded3507**"; // 사용자 설정 비밀번호.
	   con = (Connection) DriverManager.getConnection(jdbcUrl, db_id, db_pw);

	   Statement st=con.createStatement();
	   
	   String strSql = "insert into cashdiary (date,item,incomexpending,memo,income,expend,year,month,day) values('"+date+"','"+item+"','"+incomexpending+"','"+memocontents+"',"+incomming+","+expending+","+calYear+","+(calMonth+1)+","+calDayOfMon+");";
	   st.executeUpdate(strSql);
	   
	   st.close();
	   con.close();
	   }catch(SQLException e){System.out.println("SQLException: "+e.getMessage());}
	  try {
          // 연결
          Connection con;
   	   String jdbcUrl = "jdbc:mysql://localhost:3306/localhost";
   	   String db_id = "root";
   	   String db_pw = "ded3507**"; // 사용자 설정 비밀번호.
   	   con = (Connection) DriverManager.getConnection(jdbcUrl, db_id, db_pw);
   	   Statement st=con.createStatement();
      	
      	
		st = con.createStatement();
      rs = st.executeQuery("select * from cashdiary where year = "+calYear+" and month = " +(calMonth+1)+ " and day = " +calDayOfMon+";");

      // DefaultTableModel에 있는 기존 데이터 지우기
      
      for (int i = 0; i < model.getRowCount();) {
          model.removeRow(0);
      }

      while (rs.next()) {
          Object data[] = { rs.getString(2), rs.getString(3),
                  rs.getString(4), rs.getInt(5), rs.getInt(6) };

          model.addRow(data); //DefaultTableModel에 레코드 추가
      }
   }catch (SQLException e1) {
			e1.printStackTrace();
		}
	  }
	}
	
	public void userSelectAll(String show) {
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from cashdiary");
 
            // DefaultTableModel에 있는 기존 데이터 지우기
            
 
            while (rs.next()) {
                Object data[] = { rs.getString(1), rs.getString(2),
                        rs.getInt(3), rs.getString(4) };
 
                model.addRow(data); //DefaultTableModel에 레코드 추가
            }
 
        } catch (SQLException e) {
            System.out.println(e + "=> userSelectAll fail");
        }
    }

	private void showCal(){
		for(int i=0;i<CAL_HEIGHT;i++){
			for(int j=0;j<CAL_WIDTH;j++){
				String fontColor="black";
				if(j==0) fontColor="red";
				else if(j==6) fontColor="blue";
				
				File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDates[i][j]<10?"0":"")+calDates[i][j]+".txt");
				if(f.exists()){
					dateButs[i][j].setText("<html><b><font color="+fontColor+">"+calDates[i][j]+"</font></b></html>");
				}
				else dateButs[i][j].setText("<html><font color="+fontColor+">"+calDates[i][j]+"</font></html>");

				JLabel todayMark = new JLabel("<html><font color=green>*</html>");
				dateButs[i][j].removeAll();
				if(calMonth == today.get(Calendar.MONTH) &&
						calYear == today.get(Calendar.YEAR) &&
						calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)){
					dateButs[i][j].add(todayMark);
					dateButs[i][j].setToolTipText("Today");
				}
				
				if(calDates[i][j] == 0) dateButs[i][j].setVisible(false);
				else dateButs[i][j].setVisible(true);
			}
		}
	}
	
	private class ListenForCalOpButtons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == todayBut){
				setToday();
				lForDateButs.actionPerformed(e);
				focusToday();
			}
			else if(e.getSource() == lYearBut) moveMonth(-12);
			else if(e.getSource() == lMonBut) moveMonth(-1);
			else if(e.getSource() == nMonBut) moveMonth(1);
			else if(e.getSource() == nYearBut) moveMonth(12);
			
			

			curMMYYYYLab.setText("<html><table width=200><tr><th>"+calYear + "년 "+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+"월 "+"</th></tr></table></html>");
			showCal();
		}
	}
	private class listenForDateButs implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int k=0,l=0;
			for(int i=0 ; i<CAL_HEIGHT ; i++){
				for(int j=0 ; j<CAL_WIDTH ; j++){
					if(e.getSource() == dateButs[i][j]){ 
						k=i;
						l=j;
					}
				}
			}
	
			if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; //today버튼을 눌렀을때도 이 actionPerformed함수가 실행되기 때문에 넣은 부분

			cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
			
			String dDayString = new String();
			int dDay=((int)((cal.getTimeInMillis() - today.getTimeInMillis())/1000/60/60/24));
			if(dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) 
					&& (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
					&& (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today"; 
			else if(dDay >=0) dDayString = "D-"+(dDay+1);
			else if(dDay < 0) dDayString = "D+"+(dDay)*(-1);
			
						
			selectedDate.setText(calYear + "년 " + (calMonth+1)+"월 "+calDayOfMon+"일 ");
			
			try {
                // 연결
                Connection con;
         	   String jdbcUrl = "jdbc:mysql://localhost:3306/localhost";
         	   String db_id = "root";
         	   String db_pw = "ded3507**"; // 사용자 설정 비밀번호.
         	   con = (Connection) DriverManager.getConnection(jdbcUrl, db_id, db_pw);
         	   Statement st=con.createStatement();
            	
            	
			st = con.createStatement();
            rs = st.executeQuery("select * from cashdiary where year = "+calYear+" and month = " +(calMonth+1)+ " and day = " +calDayOfMon+";");
 
            
            for (int i = 0; i < model.getRowCount();) {
                model.removeRow(0);
            }
            // DefaultTableModel에 있는 기존 데이터 지우기
            
            for (int i = 0; i < dt.getRowCount();) {
                dt.removeRow(0);
            }
 
            while (rs.next()) {
                Object data[] = { rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6) };
 
                model.addRow(data); //DefaultTableModel에 레코드 추가
            }
         }catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	private class ThreadConrol extends Thread{
		public void run(){
			
			
			boolean msgCntFlag = false;
			int num = 0;
			String curStr = new String();
			while(true){
				try{
					today = Calendar.getInstance();
					String amPm = (today.get(Calendar.AM_PM)==0?"AM":"PM");
					String hour;
							if(today.get(Calendar.HOUR) == 0) hour = "12"; 
							else if(today.get(Calendar.HOUR) == 12) hour = " 0";
							else hour = (today.get(Calendar.HOUR)<10?" ":"")+today.get(Calendar.HOUR);
					String min = (today.get(Calendar.MINUTE)<10?"0":"")+today.get(Calendar.MINUTE);
					String sec = (today.get(Calendar.SECOND)<10?"0":"")+today.get(Calendar.SECOND);
					infoClock.setText(amPm+" "+hour+":"+min+":"+sec);

					sleep(1000);
					String infoStr = bottomInfo.getText();
					
					if(infoStr != " " && (msgCntFlag == false || curStr != infoStr)){
						num = 5;
						msgCntFlag = true;
						curStr = infoStr;
					}
					else if(infoStr != " " && msgCntFlag == true){
						if(num > 0) num--;
						else{
							msgCntFlag = false;
							bottomInfo.setText(" ");
						}
					}		
				}
				catch(InterruptedException e){
					System.out.println("Thread:Error");
				}
			}
		}
	}
}
