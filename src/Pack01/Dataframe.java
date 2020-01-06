package Pack01;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class Dataframe extends JFrame implements ActionListener {
	Frame f;

	String years[] = { "2018", "2019", "2020", "2021", "2022"};
	String months[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	

	JComboBox m;
	JComboBox y;
	JButton search;

	public Dataframe(Frame f) {
		this.f = f;
	}

	public void Run() {
		JPanel p1 = new JPanel();
		y = new JComboBox(years);
		m = new JComboBox(months);
		search = new JButton("���ã��");

		//��ư �̺�Ʈ
		search.addActionListener(this);

		p1.add(new JLabel("�⵵ : "));
		p1.add(y);
		p1.add(new JLabel("�� : "));
		p1.add(m);
		p1.add(search);

		add(p1, BorderLayout.CENTER);
		setLocation(400, 100);
		setSize(400, 100);
		setVisible(true);
		setTitle("���� ��������");
	}
	public void actionPerformed(ActionEvent e) {
		Loaddata ld = new Loaddata(this, years[y.getSelectedIndex()], months[m.getSelectedIndex()]);
		ld.loadDatas();
	}
}

class Loaddata extends JFrame implements ActionListener {
	Frame f;
	String year;
	String month;
	String title[] = { "��Ϲ�ȣ", "��", "��", "��", "���⳻��", "����ݾ�", "�ڵ�" };
	JTable table;
	JScrollPane sp;
	Dialog dialog;
	Button close;
	Panel dialogP1;
	Panel dialogP2;
	Panel dialogP3;

	public Loaddata(Frame f, String year, String month) {
		this.f = f;
		this.year = year;
		this.month = month;
	}

	@SuppressWarnings("deprecation")
	public void loadDatas() {
		dialog = new Dialog(f);
		close = new Button("close");
		//Panel 3�� ����
		dialogP1 = new Panel();     // ���� �����ġ
		dialogP2 = new Panel();     // �Է� �κ� ��ġ
		dialogP3 = new Panel();     // �ݱ� ��ư�� ��ġ

		TextArea textArea = new TextArea(50, 50); // �Է� �κ��� �� : 50, �ʺ� : 50
		textArea.setEditable(false);   //���� �Ұ�

		try

		{
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver loading error:");
		}

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "000000");
			Statement st = con.createStatement();

			String strSql = "select * from cashdiary WHERE  year ='" + year + "' AND month = '" + month + "';";

			JOptionPane.showMessageDialog(null, strSql);
			JOptionPane.showMessageDialog(null, "������ ã�ҽ��ϴ�.");

			ResultSet result = st.executeQuery(strSql);
			ResultSetMetaData rmdata = result.getMetaData();
			int cols = rmdata.getColumnCount();

			//��� ����
			while (result.next()) {
				textArea.append("\n��      ¥ : " + result.getString(1) + "��" + result.getString(2) + "��"
						+ result.getString(4) + "��       " + result.getString(3));
				textArea.append("\n���⳻�� : " + result.getString(5));
				textArea.append("\n����ݾ� : " + result.getString(6));
				textArea.append("\n��      �� : " + result.getString(7));
				textArea.append("\n----------------------------------");
				textArea.append("\n                                                   ");
			}

			st.close();
			con.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "������ ã�� ���ؽ��ϴ�.");
		}

		close.addActionListener(this);    // ���̾�α״ݴ� �̺�Ʈ

		dialog.setLocation(400, 200);
		dialog.setSize(400, 600);

		dialog.setTitle(year + "��" + month + "�� ���⳻��");     //���̾�α�����
		dialog.setLayout(new BorderLayout());       //���̾�α�Layout

		dialogP1.add(new Label("���� ����"));    //"���û���"��ġ
		dialogP2.add(textArea);      //�Էºκй�ġ
		dialogP3.add(close);     //�ݱ��ư ��ġ

		dialog.add(dialogP1, BorderLayout.NORTH); //��ܿ� ��ġ
		dialog.add(dialogP2, BorderLayout.CENTER); //����� ��ġ
		dialog.add(dialogP3, BorderLayout.SOUTH); //�ϴܿ� ��ġ

		dialog.show();      //���̾�α� ����

	}

	public void actionPerformed(ActionEvent e) {
		dialog.hide();      //���̾�α� ����
	}
}