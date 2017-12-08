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
  search = new JButton("���ã��");
  search.addActionListener(this);
  //��ư�̺�Ʈ
  
  p1.add(new JLabel("�⵵ : "));p1.add(y);p1.add(new JLabel("�� : "));p1.add(m);p1.add(search);
  add(p1,BorderLayout.CENTER);
  setLocation(400,100);  
  setSize(400,100);
  setVisible(true);
  setTitle("���� ��������");
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
String title[] = {"��Ϲ�ȣ","��","��","��","���⳻��","����ݾ�","�޸�"};
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
 dialogP1 = new Panel();  // Panel 3�� ���� "���� ���"�� ��ġ
 dialogP2 = new Panel();  //     TextArea�� ��ġ
 dialogP3 = new Panel();  //     close ��ư�� ��ġ
 TextArea textArea = new TextArea(50,50); // TextArea 10�� �� 38�� �ʺ�
 textArea.setEditable(false);    // TextArea ���� �Ұ�(Read Only)
  
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
   
   String strSql = "select * from cashdiary WHERE  year ='"+year+"' AND month = '"+month+"';";
   JOptionPane.showMessageDialog(null, strSql);
   ResultSet result=st.executeQuery(strSql); //st.executeQuery SQL â�� �Է��ϴ� ��ɾ ����������ִ�. 
   ResultSetMetaData rmdata = result.getMetaData();
   int cols = rmdata.getColumnCount();
   while(result.next())
   {
   textArea.append("\n��Ϲ�ȣ : " + result.getString(1));
   textArea.append("\n��      ¥ : " + result.getString(2)+"��"+result.getString(3)+"��"+result.getString(4)+"��");
   textArea.append("\n���⳻�� : " + result.getString(5));
   textArea.append("\n����ݾ� : " + result.getString(6));
   textArea.append("\n��      �� : " + result.getString(7));
   textArea.append("\n--------------------------------");
   textArea.append("\n                                ");
   }

   st.close();
   con.close();
   }catch(SQLException e){System.out.println("SQLException: "+e.getMessage());}
 
  close.addActionListener(this);// ���̾�α� �ݱ�
  dialog.setLocation(400,200);  // ���̾�α� ��ġ�� ���α׷���(Frame)�� ������ ��ġ���� ����
  dialog.setSize(400,600);     // ���̾�α� ������
  dialog.setTitle(year+"��"+month+"�� ���⳻��");    // ���̾�α� ����
  dialog.setLayout(new BorderLayout());  // ���̾�α� Layout
  
  dialogP1.add(new Label("���� ����"));  // Panel1�� ���û����̶�� ���̺� ��ġ
  dialogP2.add(textArea);      // Panel2�� textArea ��ġ
  dialogP3.add(close);      // Panel3�� close��ư ��ġ
  dialog.add(dialogP1,BorderLayout.NORTH); // Panel1�� ���̾�α��� ��ܿ� ��ġ
  dialog.add(dialogP2,BorderLayout.CENTER); // Panel2�� ���̾�α��� ����� ��ġ
  dialog.add(dialogP3,BorderLayout.SOUTH); // Panel3�� ���̾�α��� �ϴܿ� ��ġ
  dialog.show();        // ���̾�α� ������!!
  
  }
   public void actionPerformed (ActionEvent  e) 
 {
  dialog.hide();  // dialog�� ���� ���ش�.
 }
}