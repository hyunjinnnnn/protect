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
 String select[] = {"����", "����", "����"}; 
	String income[] = {"�뵷", "����", "�����ҵ�", "�μ���", "��Ÿ"};
	String expend[] = {"�ĺ�", "�̿�/�м�", "����", "��Ȱ", "�ǰ�", "��Ÿ"};
 
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
  
  buyLabel1 = new JLabel("���� :");
  buySelect = new JComboBox(select);
  
  buySelect.addItemListener(this);
  incomexpendLable = new JLabel("  ���� : ");
  incomexpend = new JComboBox();
  incomexpend.addItemListener(this);
  incomexpend.setEnabled(false);
  
  buyLabel2 = new JLabel("  �ݾ� : ");
  codeField = new JTextField(8);
  save = new JButton("�Է�");
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
 
 public void itemStateChanged(ItemEvent e) {		// ���� �޺��ڽ�
		String income[] = {"�뵷", "����", "�����ҵ�", "�μ���", "��Ÿ"};
		String expend[] = {"�ĺ�", "�̿�/�м�", "����", "��Ȱ", "�ǰ�", "��Ÿ"};

		if (e.getSource() == buySelect) {
			if (buySelect.getSelectedItem().equals("����")) {
				incomexpend.setEnabled(false);
			} else if (buySelect.getSelectedItem().equals("����")) {
				incomexpend.setEnabled(true);
				incomexpend.removeAllItems();
				for (int i = 0; i < income.length; i++) {
					incomexpend.addItem(income[i]);
				}
			} else if (buySelect.getSelectedItem().equals("����")) {
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
	if(e.getSource()==save)//�̺�Ʈ�� �߻��� ������Ʈ�� save�̸� ����
	{
		   boolean number = false; //���ڸ� �϶� �Ǻ� bool
		   boolean character = false; //���ڸ� �϶� �Ǻ� bool
		   String src = codeField.getText();
		   for(int i=0 ; i< src.length() ; i++){ //�Է¹��� ���� �ѱ��ھ� �˻�
		   char result = src.substring(i,i+1).charAt(0);
		   if((int)result<48 || (int)result>57){ //���� ���ڿ��� ���ڶ��...
		   character = true;
		   }else{ //���� ���ڶ��...
		   number = true;
		   }
		   }
		   if(character == true && number == true){
		   JOptionPane.showMessageDialog(null, "���ڿ� ���ڰ� ȥ��Ǿ��ֽ��ϴ� ���ڸ��Է��ϼ���");
		   }else if(character == true && number == false){
		   JOptionPane.showMessageDialog(null, "���ڿ��Դϴ� ���ڸ� �Է��ϼ���");
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
   String db_pw = "ded3507**"; // ����� ���� ��й�ȣ.
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