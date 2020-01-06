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
		search = new JButton("목록찾기");

		//버튼 이벤트
		search.addActionListener(this);

		p1.add(new JLabel("년도 : "));
		p1.add(y);
		p1.add(new JLabel("월 : "));
		p1.add(m);
		p1.add(search);

		add(p1, BorderLayout.CENTER);
		setLocation(400, 100);
		setSize(400, 100);
		setVisible(true);
		setTitle("월간 내역보기");
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
	String title[] = { "등록번호", "년", "월", "일", "지출내용", "지출금액", "코드" };
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
		//Panel 3개 생성
		dialogP1 = new Panel();     // 선택 결과배치
		dialogP2 = new Panel();     // 입력 부분 배치
		dialogP3 = new Panel();     // 닫기 버튼이 배치

		TextArea textArea = new TextArea(50, 50); // 입력 부분은 줄 : 50, 너비 : 50
		textArea.setEditable(false);   //수정 불가

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
			JOptionPane.showMessageDialog(null, "정보를 찾았습니다.");

			ResultSet result = st.executeQuery(strSql);
			ResultSetMetaData rmdata = result.getMetaData();
			int cols = rmdata.getColumnCount();

			//결과 도출
			while (result.next()) {
				textArea.append("\n날      짜 : " + result.getString(1) + "년" + result.getString(2) + "월"
						+ result.getString(4) + "일       " + result.getString(3));
				textArea.append("\n지출내용 : " + result.getString(5));
				textArea.append("\n지출금액 : " + result.getString(6));
				textArea.append("\n코      드 : " + result.getString(7));
				textArea.append("\n----------------------------------");
				textArea.append("\n                                                   ");
			}

			st.close();
			con.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "정보를 찾지 못해습니다.");
		}

		close.addActionListener(this);    // 다이얼로그닫는 이벤트

		dialog.setLocation(400, 200);
		dialog.setSize(400, 600);

		dialog.setTitle(year + "년" + month + "월 지출내역");     //다이얼로그제목
		dialog.setLayout(new BorderLayout());       //다이얼로그Layout

		dialogP1.add(new Label("선택 사항"));    //"선택사항"배치
		dialogP2.add(textArea);      //입력부분배치
		dialogP3.add(close);     //닫기버튼 배치

		dialog.add(dialogP1, BorderLayout.NORTH); //상단에 배치
		dialog.add(dialogP2, BorderLayout.CENTER); //가운데에 배치
		dialog.add(dialogP3, BorderLayout.SOUTH); //하단에 배치

		dialog.show();      //다이얼로그 보기

	}

	public void actionPerformed(ActionEvent e) {
		dialog.hide();      //다이얼로그 종료
	}
}