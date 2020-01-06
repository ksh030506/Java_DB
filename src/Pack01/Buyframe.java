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
	String select[] = { "식  비", "교  통", "통  신", "쇼  핑", "문  화", "의  료", "세  금", " 수 도 세", "가 스 비", "학 비", "자 격 증",
			"게 임", "가 전 제 품", "장 난 감", "기 타"};
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
		super("자바 가계부 프로그램");
		setSize(500, 500);
		setLayout(new GridLayout(6, 1));
		setLocationRelativeTo(SENTER);

		// 패널설정
		JPanel p0 = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();

		t = new Todays(); // 날짜 클래스
		JMenuBar menuBar = new JMenuBar();
		JMenu datas = new JMenu("내역보기");
		JMenu deld = new JMenu("레코드삭제");
		JMenu da = new JMenu("기타");
		//메뉴 아이템
		menuItem1 = new JMenuItem("월간내역");
		menuItem2 = new JMenuItem("코드검색삭제");
		menuItem3 = new JMenuItem("공학용 계산기");
		menuItem4 = new JMenuItem("미니게임");
		alldel = new JMenuItem("모든레코드삭제");
		datas.add(menuItem1);
		deld.add(menuItem2);
		da.add(menuItem3);
		da.add(menuItem4);
		deld.add(alldel);
		menuBar.add(datas);
		menuBar.add(deld);
		menuBar.add(da);
		add(menuBar);

		//날짜 보여주는 것
		todayy = new JLabel("8");
		todayy.setText("날 짜 : " + t.gety());
		todaym = new JLabel("8");
		todaym.setText(" " + t.getm());
		todayd = new JLabel("8");
		todayd.setText(" " + t.getd());

		buyLabel1 = new JLabel("지출내용 :");
		buySelect = new JComboBox(select);
		buyLabel2 = new JLabel("    금  액 :");
		buyTextField = new JTextField(8);
		buyLabel3 = new JLabel("코드     등록 :");
		buyMemo = new JTextField(19);
		save = new JButton("입  력");
		cancel = new JButton("지우기");

		//이벤트 추가
		menuItem4.addActionListener(this);
		menuItem3.addActionListener(this);
		menuItem2.addActionListener(this);
		menuItem1.addActionListener(this);
		alldel.addActionListener(this);
		save.addActionListener(this);
		cancel.addActionListener(this);

		// 버튼
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

	//메뉴바 실행하기
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItem1) {
			JOptionPane.showMessageDialog(null, "월간내역보기");
			Dataframe df = new Dataframe(this);
			df.Run();
		} else if (e.getSource() == menuItem2) {
			JOptionPane.showMessageDialog(null, "레코드삭제");
			Delectframe delf = new Delectframe(this);
			delf.Run();
		} else if (e.getSource() == menuItem3) {
			JOptionPane.showMessageDialog(null, "공학용 계산기");
			Main del = new Main();
			del.Run();
		} 
		else if (e.getSource() == menuItem4) {
			JOptionPane.showMessageDialog(null, "미니게임");
			Gaim g = new Gaim();
			g.Run();
		}else if (e.getSource() == cancel)     //cancel이면 실행
		{
			buyTextField.setText("");
			buyMemo.setText("");
		} else if (e.getSource() == alldel) {
			try {
			String strSql = "Delete from cashdiary;";
			Delectdata dd = new Delectdata(this, strSql);
			dd.delDatas();
			JOptionPane.showMessageDialog(null, "모두 삭제하였습니다. ");
			} catch(Exception e2) {
				JOptionPane.showMessageDialog(null, "모두 삭제하지 못하였습니다.");
			}

		} else if (e.getSource() == save)      //save이면 실행
		{
			boolean number = false;     //숫자  boolean
			boolean character = false;  //문자 boolean
			String src = buyTextField.getText();

			for (int i = 0; i < src.length(); i++) {    //입력받은 값을 한글자씩 검색
				char result = src.substring(i, i + 1).charAt(0);
				if ((int) result < 48 || (int) result > 57) {     //만약 숫자가 아니라면
					character = true;
				} else {        //만약 숫자라면
					number = true;
				}
			}
			if (character == true && number == true) {    //둘다 참일시
				JOptionPane.showMessageDialog(null, "문자와 숫자가 혼용되어있습니다 숫자만입력하세요");
			} else if (character == true && number == false) {    //문자는 참, 숫자는 거짓일때
				JOptionPane.showMessageDialog(null, "문자열입니다 숫자만 입력하세요");
			} else if (character == false && number == true) {    //문자는 거짓, 문자가 거짓일때 저장한다.

				int c = Integer.parseInt(buyTextField.getText());

				Save s = new Save(this, t.gety(), t.getm(), t.getd(), t.geta(), select[buySelect.getSelectedIndex()], c,
						buyMemo.getText());
				s.saveData();

			}
		}

	}

	public static void main(String[] args) {   //메인
		Buyframe bf = new Buyframe();
		bf.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

//정보를 저장하는 클래스
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
	
	//데이터베이스 연동 및 저장
	public void saveData() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // jdbc 저장
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver loading error:");
		}

		try { 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "000000"); // 내
																													// DB정보
			Statement st = con.createStatement();

			String strSql = "insert into cashdiary (year,month,time,day,item,cost,memo) values('" + year + "','"
					+ month + "','" + time + "','" + day + "','" + item + "'," + cost + ",'" + memo + "');"; // 저장 데이터

			JOptionPane.showMessageDialog(null, strSql);
			JOptionPane.showMessageDialog(null, "저장되었습니다.");

			st.executeUpdate(strSql);

			st.close();
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "실패하였습니다.");
		}
	}
}

//날짜를 구해주는 클래스
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
		sdfy = new SimpleDateFormat(); // 년
		sdfm = new SimpleDateFormat(); // 월
		sdfd = new SimpleDateFormat(); // 일
		me = new SimpleDateFormat(); // 시간

		sdfy.applyPattern("yyyy");
		sdfm.applyPattern("MM");
		sdfd.applyPattern("dd");
		me.applyPattern("HH : mm : ss");

	}

	//시간을 구해주는 메소드들
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