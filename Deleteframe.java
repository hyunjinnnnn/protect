import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
class Delectframe extends JFrame implements ActionListener
{
 Frame f;
 JTextField codeField;
 String strSql;
 JButton del;
 
 public Delectframe(Frame f)
 {
 this.f = f;
 }
 public void Run() 
 {
  JPanel p1 = new JPanel();
  del = new JButton("���ڵ����");
  codeField = new JTextField(8);
  del.addActionListener(this);
  
  //��ư�̺�Ʈ
  
  p1.add(new JLabel("�����ҵ���ڵ��Է� : "));p1.add(codeField);p1.add(del);
  
  add(p1,BorderLayout.CENTER);
  setLocation(400,100);   
  setSize(400,100);
  setVisible(true);
  setTitle("���ڵ� ����");
 }
 public void actionPerformed(ActionEvent e)
 {
  strSql = "Delete from cashdiary WHERE number ="+codeField.getText()+";";
  Delectdata dd = new Delectdata(this,strSql);
  dd.delDatas();
 }
}

class Delectdata extends JFrame
{
Frame f;
String strSql;
 public Delectdata(Frame f,String strSql)
 {
 this.f = f;
 this.strSql = strSql;
 }
 public void delDatas()
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
   
   JOptionPane.showMessageDialog(null, strSql);
   st.executeUpdate(strSql);
  
   st.close();
   con.close();
   }catch(SQLException e){System.out.println("SQLException: "+e.getMessage());}
      
 }
}