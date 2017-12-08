import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.text.*;
public class Buyframe extends JFrame implements ActionListener
{
 String select[] = {"��  ��","��  ��","��  ��","��  ��","��  ȭ","��  ��","��  ��","��  Ÿ"};
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
  super("�ڹ� ����� ���α׷�");
  setSize(300,250);
  setLayout(new GridLayout(6,1));
 
  //�� �⺻����
  JPanel p0 = new JPanel();
  JPanel p1 = new JPanel();
  JPanel p2 = new JPanel();
  JPanel p3 = new JPanel();
  JPanel p4 = new JPanel();
  //�гμ���
  t = new Todays();
  
  //��¥ Ŭ���� ����
  JMenuBar menuBar = new JMenuBar();
  JMenu datas = new JMenu("��������");
  JMenu deld = new JMenu("���ڵ����");
  menuItem1 = new JMenuItem("��������");
  menuItem2 = new JMenuItem("�ڵ�˻�����");
  alldel = new JMenuItem("��緹�ڵ����");
  datas.add(menuItem1);
  deld.add(menuItem2);
  deld.add(alldel);
  menuBar.add(datas);menuBar.add(deld);
  add(menuBar);
  //�޴��� ����
  todayy = new JLabel("8");
  todayy.setText("�� ¥ : "+t.gety());
  todaym = new JLabel("8");
  todaym.setText(" "+t.getm());
  todayd = new JLabel("8");
  todayd.setText(" "+t.getd());
  
  buyLabel1 = new JLabel("���⳻�� :");
  buySelect = new JComboBox(select); 
  buyLabel2 = new JLabel("    ��  �� :");
  buyTextField = new JTextField(8);
  buyLabel3 = new JLabel("��        �� :");
     buyMemo =  new JTextField(19);
  save = new JButton("��  ��");
  cancel = new JButton("�����");
  //���̺귯��
  menuItem2.addActionListener(this);
  menuItem1.addActionListener(this);
  alldel.addActionListener(this);
  save.addActionListener(this);
  cancel.addActionListener(this);
  //��ư�̺�Ʈ
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
  //�гο� ���̺귯�� ��ġ
  add(p0);add(p1);add(p2);add(p3);add(p4);
  //�����ӿ� �г� ��ġ
  setLocation(100,100);
  setVisible(true);
 }
 public void actionPerformed(ActionEvent e)
 {
  if (e.getSource()==menuItem1)
  {
   JOptionPane.showMessageDialog(null, "������������");
   Dataframe df = new Dataframe(this);
   df.Run();
  }
  else if (e.getSource()==menuItem2)
  {
   JOptionPane.showMessageDialog(null, "���ڵ����");
   Delectframe delf = new Delectframe(this);
   delf.Run();
  }
  else if (e.getSource()==cancel)//�̺�Ʈ�� �߻��� ������Ʈ�� cancel�̸� ����
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
  else if(e.getSource()==save)//�̺�Ʈ�� �߻��� ������Ʈ�� save�̸� ����
  {
   boolean number = false; //���ڸ� �϶� �Ǻ� bool
   boolean character = false; //���ڸ� �϶� �Ǻ� bool
   String src = buyTextField.getText();
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
   
   int c = Integer.parseInt(buyTextField.getText());
   Save s = new Save(this,t.gety(),t.getm(),t.getd(),select[buySelect.getSelectedIndex()],c,buyMemo.getText());
   s.saveData();

   }
  }
  
 }
 public static void main(String[] args) 
 {
  Buyframe bf = new Buyframe();
  bf.setDefaultCloseOperation(EXIT_ON_CLOSE); //X������ ����
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
   String db_pw = "ded3507**"; // ����� ���� ��й�ȣ.
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