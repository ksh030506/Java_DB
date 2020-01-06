package Pack01;

//MenuJTabaleExam.java
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Menu_Window extends JFrame implements ActionListener {
	JMenu m = new JMenu("����");
	JMenuItem enter = new JMenuItem("�� ���̵�� ����� ���� �ϱ�");
	JMenuItem insert = new JMenuItem("����");
	JMenuItem update = new JMenuItem("����");
	JMenuItem delete = new JMenuItem("����");
	JMenuItem gaim = new JMenuItem("����");
	JMenuItem quit = new JMenuItem("����");
	JMenuBar mb = new JMenuBar();

	String[] name = { "id", "name", "age", "addr" };

	DefaultTableModel dt = new DefaultTableModel(name, 0);
	JTable jt = new JTable(dt);
	JScrollPane jsp = new JScrollPane(jt);

	/*
	 * South ������ �߰��� Componet��
	 */
	JPanel p = new JPanel();
	String[] comboName = { "  ALL  ", "  ID  ", " name ", " addr " };

	JComboBox combo = new JComboBox(comboName);
	JTextField jtf = new JTextField(20);
	JButton serach = new JButton("�˻�");

	function_login dao = new function_login();

	/**
	 * ȭ�鱸�� �� �̺�Ʈ���
	 */
	public Menu_Window() {

		super("GUI ȸ���������α׷�");

		// �޴��������� �޴��� �߰�
		m.add(enter);
		m.add(insert);
		m.add(update);
		m.add(delete);
		m.add(gaim);
		m.add(quit);
		// �޴��� �޴��ٿ� �߰�
		mb.add(m);

		// �����쿡 �޴��� ����
		setJMenuBar(mb);

		// South����
		p.setBackground(Color.yellow);
		p.add(combo);
		p.add(jtf);
		p.add(serach);

		add(jsp, "Center");
		add(p, "South");

		setSize(500, 400);
		setVisible(true);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// �̺�Ʈ���
		enter.addActionListener(this);
		insert.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		quit.addActionListener(this);
		gaim.addActionListener(this);
		serach.addActionListener(this);

		// ��緹�ڵ带 �˻��Ͽ� DefaultTableModle�� �ø���
		dao.userSelectAll(dt);

		// ù���� ����.
		if (dt.getRowCount() > 0)
			jt.setRowSelectionInterval(0, 0);

	}// �����ڳ�

	/**
	 * main�޼ҵ� �ۼ�
	 */
	public static void main(String[] args) {
		new Menu_Window();
	}

	/**
	 * ����/����/����/�˻������ ����ϴ� �޼ҵ�
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == insert) {// ���� �޴������� Ŭ��
			new Meun_GUI(this, "����");

		} else if (e.getSource() == enter) {
			int row = jt.getSelectedRow();
			System.out.println("���� ������ �� : " + row);
			Object obj = jt.getValueAt(row, 0);// �� ���� �ش��ϴ� value
			System.out.println("id : " + obj);
			Meun_GUI.messageBox(this, "���̵� : ' " + obj + " '" + "�� �����մϴ�.!");
			new Buyframe();
		} else if (e.getSource() == update) {// ���� �޴������� Ŭ��
			Meun_GUI.messageBox(this, "id���� �����Ͻ� �� �ֽ��ϴ�.");
			new Meun_GUI(this, "����");

		} else if (e.getSource() == delete) {// ���� �޴������� Ŭ��
			// ���� Jtable�� ���õ� ��� ���� ���� ���´�.
			int row = jt.getSelectedRow();
			System.out.println("���� ������ �� : " + row);

			Object obj = jt.getValueAt(row, 0);// �� ���� �ش��ϴ� value
			System.out.println("id : " + obj);

			if (dao.userDelete(obj.toString()) > 0) {
				Meun_GUI.messageBox(this, "���ڵ� �����Ǿ����ϴ�.");

				// ����Ʈ ����
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);

			} else {
				Meun_GUI.messageBox(this, "���ڵ尡 �������� �ʾҽ��ϴ�.");
			}

		} else if (e.getSource() == gaim) {
			Meun_GUI.messageBox(this, "������ �����մϴ�.");
			Gaim g = new Gaim();
			g.Run();
		} else if (e.getSource() == quit) {// ���� �޴������� Ŭ��
			Meun_GUI.messageBox(this, "�����մϴ�.");
			System.exit(0);

		}

		else if (e.getSource() == serach) {// �˻� ��ư Ŭ��
			// JComboBox�� ���õ� value ��������
			String fieldName = combo.getSelectedItem().toString();
			System.out.println("���� ������ �� : " + fieldName);

			if (fieldName.trim().equals("ALL")) {// ��ü�˻�
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else {
				if (jtf.getText().trim().equals("")) {
					Meun_GUI.messageBox(this, "�˻��ܾ �Է����ּ���!");
					jtf.requestFocus();
				} else {// �˻�� �Է��������
					dao.getUserSearch(dt, fieldName, jtf.getText());
					if (dt.getRowCount() > 0)
						jt.setRowSelectionInterval(0, 0);
				}
			}
		}

	}// actionPerformed()----------

}