package Pack01;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

class Delectframe extends JFrame implements ActionListener {
	Frame f;
	JTextField codeField;
	String strSql;
	JButton del;

	public Delectframe(Frame f) {
		this.f = f;
	}

	public void Run() {
		JPanel p1 = new JPanel();
		del = new JButton("레코드삭제");
		codeField = new JTextField(8);

		// 버튼이벤트
		del.addActionListener(this);

		p1.add(new JLabel("삭제할등록코드입력 : "));
		p1.add(codeField);
		p1.add(del);

		add(p1, BorderLayout.CENTER);
		setLocation(400, 100);
		setSize(400, 100);
		setVisible(true);
		setTitle("레코드 삭제");
	}

	//삭제 메서드
	public void actionPerformed(ActionEvent e) {
		try {
			strSql = "Delete from cashdiary WHERE memo =" + codeField.getText() + ";";
			Delectdata dd = new Delectdata(this, strSql);
			dd.delDatas();
			JOptionPane.showMessageDialog(null, codeField.getText());
			JOptionPane.showMessageDialog(null, "성공~~~~!!!");
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "실패!!~~~");
		}
	}
}
//DB연결하기
class Delectdata extends JFrame {
	Frame f;
	String strSql;

	public Delectdata(Frame f, String strSql) {
		this.f = f;
		this.strSql = strSql;
	}

	public void delDatas() {
		try

		{
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver loading error:");
		}

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "000000");

			Statement st = con.createStatement();

			JOptionPane.showMessageDialog(null, strSql);

			st.executeUpdate(strSql);

			st.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}

	}
}