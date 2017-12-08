import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.text.*;
public class Buyframe extends JFrame implements ActionListener
{
 String select[] = {"식  비","교  통","통  신","쇼  핑","문  화","의  료","세  금","기  타"};
 JComboBox buySelect;
 JLabel buyLabel1;
 JLabel buyLabel2;
 JLabel buyLabel3;
 JLabel todayy;
 JLabel todaym;
 JLabel todayd;
 JTextField buyTextField;
 JTextField buyMemo;
 JButton save;
 JButton cancel;
 String nowdate;
 Todays t;
 JMenuItem menuItem1;
 JMenuItem menuItem2;
 JMenuItem alldel;
 String strSql;
 public Buyframe()
 {
  super("자바 가계부 프로그램");
  setSize(300,250);
  setLayout(new GridLayout(6,1));
 
  //폼 기본설정
  JPanel p0 = new JPanel();
  JPanel p1 = new JPanel();
  JPanel p2 = new JPanel();
  JPanel p3 = new JPanel();
  JPanel p4 = new JPanel();
  //패널설정
  t = new Todays();
  
  //날짜 클래스 선언
  JMenuBar menuBar = new JMenuBar();
  JMenu datas = new JMenu("내역보기");
  JMenu deld = new JMenu("레코드삭제");
  menuItem1 = new JMenuItem("월간내역");
  menuItem2 = new JMenuItem("코드검색삭제");
  alldel = new JMenuItem("모든레코드삭제");
  datas.add(menuItem1);
  deld.add(menuItem2);
  deld.add(alldel);
  menuBar.add(datas);menuBar.add(deld);
  add(menuBar);
  //메뉴바 설정
  todayy = new JLabel("8");
  todayy.setText("날 짜 : "+t.gety());
  todaym = new JLabel("8");
  todaym.setText(" "+t.getm());
  todayd = new JLabel("8");
  todayd.setText(" "+t.getd());
  
  buyLabel1 = new JLabel("지출내용 :");
  buySelect = new JComboBox(select); 
  buyLabel2 = new JLabel("    금  액 :");
  buyTextField = new JTextField(8);
  buyLabel3 = new JLabel("메        모 :");
     buyMemo =  new JTextField(19);
  save = new JButton("입  력");
  cancel = new JButton("지우기");
  //라이브러리
  menuItem2.addActionListener(this);
  menuItem1.addActionListener(this);
  alldel.addActionListener(this);
  save.addActionListener(this);
  cancel.addActionListener(this);
  //버튼이벤트
  p0.add(new JLabel(""));
  p1.add(todayy);
  p1.add(todaym);
  p1.add(todayd);
  p2.add(buyLabel1);
  p2.add(buySelect); 
  p2.add(buyLabel2);
  p2.add(buyTextField);  
  p3.add(buyLabel3);
  p3.add(buyMemo);
  p4.add(save);
  p4.add(cancel);
  //패널에 라이브러리 배치
  add(p0);add(p1);add(p2);add(p3);add(p4);
  //프레임에 패널 배치
  setLocation(100,100);
  setVisible(true);
 }
 public void actionPerformed(ActionEvent e)
 {
  if (e.getSource()==menuItem1)
  {
   JOptionPane.showMessageDialog(null, "월간내역보기");
   Dataframe df = new Dataframe(this);
   df.Run();
  }
  else if (e.getSource()==menuItem2)
  {
   JOptionPane.showMessageDialog(null, "레코드삭제");
   Delectframe delf = new Delectframe(this);
   delf.Run();
  }
  else if (e.getSource()==cancel)//이벤트가 발생한 컴포넌트가 cancel이면 실행
  {
   buyTextField.setText("");
   buyMemo.setText("");
  }
   else if(e.getSource()==alldel)
  {
   strSql = "Delete from cashdiary;";
   Delectdata dd = new Delectdata(this,strSql);
   dd.delDatas();
  
  }
  else if(e.getSource()==save)//이벤트가 발생한 컴포넌트가 save이면 실행
  {
   boolean number = false; //숫자만 일때 판별 bool
   boolean character = false; //문자만 일때 판별 bool
   String src = buyTextField.getText();
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
   
   int c = Integer.parseInt(buyTextField.getText());
   Save s = new Save(this,t.gety(),t.getm(),t.getd(),select[buySelect.getSelectedIndex()],c,buyMemo.getText());
   s.saveData();

   }
  }
  
 }
 public static void main(String[] args) 
 {
  Buyframe bf = new Buyframe();
  bf.setDefaultCloseOperation(EXIT_ON_CLOSE); //X눌르면 종료
 }
}
class Save
{
Frame f;
String year;
String month;
String day;
String item;
int cost;
String memo;
 public Save(Frame f,String year,String month,String day,String item,int cost,String memo)
 {
 this.f = f;
 this.year = year;
 this.month = month;
 this.day = day;
 this.item = item;
 this.cost = cost;
 this.memo = memo;
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
   
   String strSql = "insert into cashdiary (year,month,day,item,cost,memo) values('"+year+"','"+month+"','"+day+"','"+item+"',"+cost+",'"+memo+"');";
   JOptionPane.showMessageDialog(null, strSql);
   st.executeUpdate(strSql);
   
   st.close();
   con.close();
   }catch(SQLException e){System.out.println("SQLException: "+e.getMessage());}
  }
}

class Todays 
{
 Calendar cal;
 SimpleDateFormat sdfy;
 SimpleDateFormat sdfm;
 SimpleDateFormat sdfd;
  public Todays()
 {
  cal = Calendar.getInstance();
  sdfy = new SimpleDateFormat();
  sdfm = new SimpleDateFormat();
  sdfd = new SimpleDateFormat();
  sdfy.applyPattern("yyyy");
  sdfm.applyPattern("MM");
  sdfd.applyPattern("dd");
 }
 public String gety(){
  String yyyy = sdfy.format(cal.getTime());
  return yyyy;
 }
 public String getm(){
  String mm = sdfm.format(cal.getTime());
  return  mm;
 }
 public String getd(){
  String dd = sdfd.format(cal.getTime());
  return dd;
 }
}