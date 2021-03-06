import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
class Dataframe extends JFrame implements ActionListener
{
 Frame f;
 String years[] = {"2013","2014","2015","2016","2017","2018","2019","2020"};
 String months[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
 
 JComboBox m;
 JComboBox y;
 JButton search;
 public Dataframe(Frame f)
 {
 this.f = f;
 }
 public void Run() 
 {
  JPanel p1 = new JPanel();
  y = new JComboBox(years);
  m = new JComboBox(months);
  search = new JButton("목록찾기");
  search.addActionListener(this);
  //버튼이벤트
  
  p1.add(new JLabel("년도 : "));p1.add(y);p1.add(new JLabel("월 : "));p1.add(m);p1.add(search);
  add(p1,BorderLayout.CENTER);
  setLocation(400,100);  
  setSize(400,100);
  setVisible(true);
  setTitle("월간 내역보기");
 }
 public void actionPerformed(ActionEvent e)
 {
 Loaddata ld = new Loaddata(this,years[y.getSelectedIndex()],months[m.getSelectedIndex()]);
 ld.loadDatas();
 }
}
 
class Loaddata extends JFrame  implements ActionListener 
{
Frame f;
String year;
String month;
String title[] = {"등록번호","년","월","일","지출내용","지출금액","메모"};
JTable table;
JScrollPane sp;
Dialog dialog;
Button close;
Panel dialogP1;
Panel dialogP2;
Panel dialogP3;
 public Loaddata(Frame f,String year,String month)
 {
 this.f = f;
 this.year = year;
 this.month = month;
 }
 public void loadDatas()
 {
 dialog = new Dialog(f);
 close = new Button("close");
 dialogP1 = new Panel();  // Panel 3개 생성 "선택 결과"가 배치
 dialogP2 = new Panel();  //     TextArea가 배치
 dialogP3 = new Panel();  //     close 버튼이 배치
 TextArea textArea = new TextArea(50,50); // TextArea 10은 줄 38은 너비
 textArea.setEditable(false);    // TextArea 수정 불가(Read Only)
  
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
   
   String strSql = "select * from cashdiary WHERE  year ='"+year+"' AND month = '"+month+"';";
   JOptionPane.showMessageDialog(null, strSql);
   ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL 창에 입력하는 명령어를 적어넣을수있다. 
   ResultSetMetaData rmdata = result.getMetaData();
   int cols = rmdata.getColumnCount();
   while(result.next())
   {
   textArea.append("\n등록번호 : " + result.getString(1));
   textArea.append("\n날      짜 : " + result.getString(2)+"년"+result.getString(3)+"월"+result.getString(4)+"일");
   textArea.append("\n지출내용 : " + result.getString(5));
   textArea.append("\n지출금액 : " + result.getString(6));
   textArea.append("\n메      모 : " + result.getString(7));
   textArea.append("\n--------------------------------");
   textArea.append("\n                                ");
   }

   st.close();
   con.close();
   }catch(SQLException e){System.out.println("SQLException: "+e.getMessage());}
 
  close.addActionListener(this);// 다이얼로그 닫기
  dialog.setLocation(400,200);  // 다이얼로그 위치는 프로그램의(Frame)과 동일한 위치에서 시작
  dialog.setSize(400,600);     // 다이얼로그 사이즈
  dialog.setTitle(year+"년"+month+"월 지출내역");    // 다이얼로그 제목
  dialog.setLayout(new BorderLayout());  // 다이얼로그 Layout
  
  dialogP1.add(new Label("선택 사항"));  // Panel1에 선택사항이라는 레이블 배치
  dialogP2.add(textArea);      // Panel2에 textArea 배치
  dialogP3.add(close);      // Panel3에 close버튼 배치
  dialog.add(dialogP1,BorderLayout.NORTH); // Panel1을 다이얼로그의 상단에 배치
  dialog.add(dialogP2,BorderLayout.CENTER); // Panel2를 다이얼로그의 가운데에 배치
  dialog.add(dialogP3,BorderLayout.SOUTH); // Panel3을 다이얼로그의 하단에 배치
  dialog.show();        // 다이얼로그 보여줘!!
  
  }
   public void actionPerformed (ActionEvent  e) 
 {
  dialog.hide();  // dialog를 종료 해준다.
 }
}