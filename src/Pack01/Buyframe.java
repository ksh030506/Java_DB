package Pack01;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Buyframe extends JFrame implements ActionListener {
	private static final Component SENTER = null;
	String select[] = { "��  ��", "��  ��", "��  ��", "��  ��", "��  ȭ", "��  ��", "��  ��", " �� �� ��", "�� �� ��", "�� ��", "�� �� ��",
			"�� ��", "�� �� �� ǰ", "�� �� ��", "�� Ÿ"};
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
	JMenuItem menuItem3;
	JMenuItem menuItem4;
	JMenuItem alldel;

	public Buyframe() {
		super("�ڹ� ����� ���α׷�");
		setSize(500, 500);
		setLayout(new GridLayout(6, 1));
		setLocationRelativeTo(SENTER);

		// �гμ���
		JPanel p0 = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();

		t = new Todays(); // ��¥ Ŭ����
		JMenuBar menuBar = new JMenuBar();
		JMenu datas = new JMenu("��������");
		JMenu deld = new JMenu("���ڵ����");
		JMenu da = new JMenu("��Ÿ");
		//�޴� ������
		menuItem1 = new JMenuItem("��������");
		menuItem2 = new JMenuItem("�ڵ�˻�����");
		menuItem3 = new JMenuItem("���п� ����");
		menuItem4 = new JMenuItem("�̴ϰ���");
		alldel = new JMenuItem("��緹�ڵ����");
		datas.add(menuItem1);
		deld.add(menuItem2);
		da.add(menuItem3);
		da.add(menuItem4);
		deld.add(alldel);
		menuBar.add(datas);
		menuBar.add(deld);
		menuBar.add(da);
		add(menuBar);

		//��¥ �����ִ� ��
		todayy = new JLabel("8");
		todayy.setText("�� ¥ : " + t.gety());
		todaym = new JLabel("8");
		todaym.setText(" " + t.getm());
		todayd = new JLabel("8");
		todayd.setText(" " + t.getd());

		buyLabel1 = new JLabel("���⳻�� :");
		buySelect = new JComboBox(select);
		buyLabel2 = new JLabel("    ��  �� :");
		buyTextField = new JTextField(8);
		buyLabel3 = new JLabel("�ڵ�     ��� :");
		buyMemo = new JTextField(19);
		save = new JButton("��  ��");
		cancel = new JButton("�����");

		//�̺�Ʈ �߰�
		menuItem4.addActionListener(this);
		menuItem3.addActionListener(this);
		menuItem2.addActionListener(this);
		menuItem1.addActionListener(this);
		alldel.addActionListener(this);
		save.addActionListener(this);
		cancel.addActionListener(this);

		// ��ư
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

		add(p0);
		add(p1);
		add(p2);
		add(p3);
		add(p4);

		setLocation(700, 300);
		setVisible(true);
	}

	//�޴��� �����ϱ�
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItem1) {
			JOptionPane.showMessageDialog(null, "������������");
			Dataframe df = new Dataframe(this);
			df.Run();
		} else if (e.getSource() == menuItem2) {
			JOptionPane.showMessageDialog(null, "���ڵ����");
			Delectframe delf = new Delectframe(this);
			delf.Run();
		} else if (e.getSource() == menuItem3) {
			JOptionPane.showMessageDialog(null, "���п� ����");
			Main del = new Main();
			del.Run();
		} 
		else if (e.getSource() == menuItem4) {
			JOptionPane.showMessageDialog(null, "�̴ϰ���");
			Gaim g = new Gaim();
			g.Run();
		}else if (e.getSource() == cancel)     //cancel�̸� ����
		{
			buyTextField.setText("");
			buyMemo.setText("");
		} else if (e.getSource() == alldel) {
			try {
			String strSql = "Delete from cashdiary;";
			Delectdata dd = new Delectdata(this, strSql);
			dd.delDatas();
			JOptionPane.showMessageDialog(null, "��� �����Ͽ����ϴ�. ");
			} catch(Exception e2) {
				JOptionPane.showMessageDialog(null, "��� �������� ���Ͽ����ϴ�.");
			}

		} else if (e.getSource() == save)      //save�̸� ����
		{
			boolean number = false;     //����  boolean
			boolean character = false;  //���� boolean
			String src = buyTextField.getText();

			for (int i = 0; i < src.length(); i++) {    //�Է¹��� ���� �ѱ��ھ� �˻�
				char result = src.substring(i, i + 1).charAt(0);
				if ((int) result < 48 || (int) result > 57) {     //���� ���ڰ� �ƴ϶��
					character = true;
				} else {        //���� ���ڶ��
					number = true;
				}
			}
			if (character == true && number == true) {    //�Ѵ� ���Ͻ�
				JOptionPane.showMessageDialog(null, "���ڿ� ���ڰ� ȥ��Ǿ��ֽ��ϴ� ���ڸ��Է��ϼ���");
			} else if (character == true && number == false) {    //���ڴ� ��, ���ڴ� �����϶�
				JOptionPane.showMessageDialog(null, "���ڿ��Դϴ� ���ڸ� �Է��ϼ���");
			} else if (character == false && number == true) {    //���ڴ� ����, ���ڰ� �����϶� �����Ѵ�.

				int c = Integer.parseInt(buyTextField.getText());

				Save s = new Save(this, t.gety(), t.getm(), t.getd(), t.geta(), select[buySelect.getSelectedIndex()], c,
						buyMemo.getText());
				s.saveData();

			}
		}

	}

	public static void main(String[] args) {   //����
		Buyframe bf = new Buyframe();
		bf.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

//������ �����ϴ� Ŭ����
class Save {
	Frame f;
	String year;
	String month;
	String time;
	String day;
	String item;
	int cost;
	String memo;

	public Save(Frame f, String year, String month, String day, String time, String item, int cost, String memo) {
		this.f = f;
		this.year = year;
		this.month = month;
		this.time = time;
		this.day = day;
		this.item = item;
		this.cost = cost;
		this.memo = memo;
	}
	
	//�����ͺ��̽� ���� �� ����
	public void saveData() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // jdbc ����
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver loading error:");
		}

		try { 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "000000"); // ��
																													// DB����
			Statement st = con.createStatement();

			String strSql = "insert into cashdiary (year,month,time,day,item,cost,memo) values('" + year + "','"
					+ month + "','" + time + "','" + day + "','" + item + "'," + cost + ",'" + memo + "');"; // ���� ������

			JOptionPane.showMessageDialog(null, strSql);
			JOptionPane.showMessageDialog(null, "����Ǿ����ϴ�.");

			st.executeUpdate(strSql);

			st.close();
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "�����Ͽ����ϴ�.");
		}
	}
}

//��¥�� �����ִ� Ŭ����
class Todays {
	Calendar cal;
	SimpleDateFormat sdfy;
	SimpleDateFormat sdfm;
	SimpleDateFormat sdfd;
	SimpleDateFormat me;
	SimpleDateFormat te;
	SimpleDateFormat qw;

	public Todays() {

		cal = Calendar.getInstance();
		sdfy = new SimpleDateFormat(); // ��
		sdfm = new SimpleDateFormat(); // ��
		sdfd = new SimpleDateFormat(); // ��
		me = new SimpleDateFormat(); // �ð�

		sdfy.applyPattern("yyyy");
		sdfm.applyPattern("MM");
		sdfd.applyPattern("dd");
		me.applyPattern("HH : mm : ss");

	}

	//�ð��� �����ִ� �޼ҵ��
	public String gety() {
		String yyyy = sdfy.format(cal.getTime());
		return yyyy;
	}

	public String getm() {
		String mm = sdfm.format(cal.getTime());
		return mm;
	}

	public String getd() {
		String dd = sdfd.format(cal.getTime());
		return dd;
	}

	public String geta() {
		String hh = me.format(cal.getTime());
		return hh;
	}
}