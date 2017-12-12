import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.*;
import java.util.*;
import java.text.*;

class Doubleclickframe extends JFrame implements ActionListener, ItemListener{
	
 Frame f;
 JTextField codeField;
 String strSql;
 JButton save;
 JLabel buyLabel1;
 JLabel buyLabel2;
 JLabel incomexpendLable;
 
 Todays t; 
 
 JComboBox buySelect;
 JComboBox incomexpend;
 String select[] = {"선택", "수입", "지출"}; 
	String income[] = {"용돈", "월급", "금융소득", "부수입", "기타"};
	String expend[] = {"식비", "미용/패션", "교통", "생활", "건강", "기타"};
 
 public Doubleclickframe(Frame f)
 {
 this.f = f;
 setSize(450,600);
 setLayout(new GridLayout(6,1));
 }

 
 public void Run() 
 {
	 
  JPanel p0 = new JPanel();
  JPanel p1 = new JPanel();
  JPanel p2 = new JPanel();
  JPanel p3 = new JPanel();
  JPanel p4 = new JPanel();
  
  t = new Todays();
  
  buyLabel1 = new JLabel("구분 :");
  buySelect = new JComboBox(select);
  
  buySelect.addItemListener(this);
  incomexpendLable = new JLabel("  내용 : ");
  incomexpend = new JComboBox();
  incomexpend.addItemListener(this);
  incomexpend.setEnabled(false);
  
  buyLabel2 = new JLabel("  금액 : ");
  codeField = new JTextField(8);
  save = new JButton("입력");
  save.addActionListener(this);
  
  p0.add(new JLabel(""));
  p1.add(buyLabel1);
  p1.add(buySelect);
  p1.add(incomexpendLable);
  p1.add(incomexpend);
  p1.add(buyLabel2);
  p1.add(codeField);
  p1.add(save);
  
  add(p0);
  add(p1);
  setLocation(900,40);   
  setVisible(true);
  setTitle(" ");
 }
 
 public void itemStateChanged(ItemEvent e) {		// 동적 콤보박스
		String income[] = {"용돈", "월급", "금융소득", "부수입", "기타"};
		String expend[] = {"식비", "미용/패션", "교통", "생활", "건강", "기타"};

		if (e.getSource() == buySelect) {
			if (buySelect.getSelectedItem().equals("선택")) {
				incomexpend.setEnabled(false);
			} else if (buySelect.getSelectedItem().equals("수입")) {
				incomexpend.setEnabled(true);
				incomexpend.removeAllItems();
				for (int i = 0; i < income.length; i++) {
					incomexpend.addItem(income[i]);
				}
			} else if (buySelect.getSelectedItem().equals("지출")) {
				incomexpend.setEnabled(true);
				incomexpend.removeAllItems();
				for (int i = 0; i < expend.length; i++) {
					incomexpend.removeItem(expend[i]);
					incomexpend.addItem(expend[i]);
				}
			}
		}
	}
 
public void actionPerformed(ActionEvent e)
 {
	if(e.getSource()==save)//이벤트가 발생한 컴포넌트가 save이면 실행
	{
		   boolean number = false; //숫자만 일때 판별 bool
		   boolean character = false; //문자만 일때 판별 bool
		   String src = codeField.getText();
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
	  
	   Save s = new Save(this,t.gety(),t.getm(),t.getd(),select[buySelect.getSelectedIndex()],c);
	   s.saveData();
	   }
	  }
	
	
	
	
	
	
//  strSql = "Delete from cashdiary WHERE number ="+codeField.getText()+";";
//  Delectdata dd = new Delectdata(this,strSql);
//  dd.delDatas();
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
 public Save(Frame f,String year,String month,String day,String item,int cost)
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
   
   String strSql = "insert into cashdiary (year,month,day,item,cost) values('"+year+"','"+month+"','"+day+"','"+item+"',"+cost+");";
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
}