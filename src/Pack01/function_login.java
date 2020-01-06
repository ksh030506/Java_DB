package Pack01;

//UserDefaultJTableDAO.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class function_login {
 
  /**
   * �ʿ��� ��������
   * */
  Connection con;
  Statement st;
  PreparedStatement ps;
  ResultSet rs;

  /**
   * �ε� ������ ���� ������
   * */
  public function_login() {
      try {
          // �ε�
          Class.forName("com.mysql.jdbc.Driver");
          // ����
          con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "000000");

      } catch (ClassNotFoundException e) {
          System.out.println(e + "=> �ε� fail");
      } catch (SQLException e) {
          System.out.println(e + "=> ���� fail");
      }
  }//������

  /**
   * DB�ݱ� ��� �޼ҵ�
   * */
  public void dbClose() {
      try {
          if (rs != null) rs.close();
          if (st != null) st.close();
          if (ps != null) ps.close();
      } catch (Exception e) {
          System.out.println(e + "=> dbClose fail");
      }
  }//dbClose() ---

  /**
   * �μ��� ���� ID�� �ش��ϴ� ���ڵ� �˻��Ͽ� �ߺ����� üũ�ϱ� ���ϰ��� true =��밡�� , false = �ߺ���
   * */
  public boolean getIdByCheck(String id) {
      boolean result = true;

      try {
          ps = con.prepareStatement("SELECT * FROM TB_USERLIST WHERE id=?");
          ps.setString(1, id.trim());
          rs = ps.executeQuery(); //����
          if (rs.next())
              result = false; //���ڵ尡 �����ϸ� false

      } catch (SQLException e) {
          System.out.println(e + "=>  getIdByCheck fail");
      } finally {
          dbClose();
      }

      return result;

  }//getIdByCheck()

  /**
   * userlist ȸ�������ϴ� ��� �޼ҵ�
   * */
  public int userListInsert(Meun_GUI user) {
      int result = 0;
      try {
          ps = con.prepareStatement("insert into TB_USERLIST values(?,?,?,?)");
          ps.setString(1, user.id.getText());
          ps.setString(2, user.name.getText());
          ps.setInt(3, Integer.parseInt(user.age.getText()));
          ps.setString(4, user.addr.getText());

          result = ps.executeUpdate(); //���� -> ����

      } catch (SQLException e) {
          System.out.println(e + "=> userListInsert fail");
      } finally {
          dbClose();
      }

      return result;

  }//userListInsert()

  /**
   * userlist�� ��� ���ڵ� ��ȸ
   * */
  public void userSelectAll(DefaultTableModel t_model) {
      try {
          st = con.createStatement();
          rs = st.executeQuery("select * from TB_USERLIST order by id");

          // DefaultTableModel�� �ִ� ���� ������ �����
          for (int i = 0; i < t_model.getRowCount();) {
              t_model.removeRow(0);
          }

          while (rs.next()) {
              Object data[] = { rs.getString(1), rs.getString(2),
                      rs.getInt(3), rs.getString(4) };

              t_model.addRow(data); //DefaultTableModel�� ���ڵ� �߰�
          }

      } catch (SQLException e) {
          System.out.println(e + "=> userSelectAll fail");
      } finally {
          dbClose();
      }
  }//userSelectAll()

  /**
   * ID�� �ش��ϴ� ���ڵ� �����ϱ�
   * */
  public int userDelete(String id) {
      int result = 0;
      try {
          ps = con.prepareStatement("delete from TB_USERLIST where id=?");
          ps.setString(1, id.trim());
          result = ps.executeUpdate();

      } catch (SQLException e) {
          System.out.println(e + "=> userDelete fail");
      }finally {
          dbClose();
      }

      return result;
  }//userDelete()

  /**
   * ID�� �ش��ϴ� ���ڵ� �����ϱ�
   * */
  public int userUpdate(Meun_GUI user) {
      int result = 0;
      String sql = "UPDATE TB_USERLIST SET NAME=?, age=? , addr=? WHERE id=?";

      try {
          ps = con.prepareStatement(sql);
          // ?�� ������� �� �ֱ�
          ps.setString(1, user.name.getText());
          ps.setString(2, user.age.getText());
          ps.setString(3, user.addr.getText());
          ps.setString(4, user.id.getText().trim());

          // �����ϱ�
          result = ps.executeUpdate();

      } catch (SQLException e) {
          System.out.println(e + "=> userUpdate fail");
      } finally {
          dbClose();
      }

      return result;
  }//userUpdate()

  /**
   * �˻��ܾ �ش��ϴ� ���ڵ� �˻��ϱ� (like�����ڸ� ����Ͽ� _, %�� ����Ҷ��� PreparedStatemnet�ȵȴ�. �ݵ��
   * Statement��ü�� �̿���)
   * */
  public void getUserSearch(DefaultTableModel dt, String fieldName,
          String word) {
      String sql = "SELECT * FROM TB_USERLIST WHERE " + fieldName.trim()
              + " LIKE '%" + word.trim() + "%'";

      try {
          st = con.createStatement();
          rs = st.executeQuery(sql);

          // DefaultTableModel�� �ִ� ���� ������ �����
          for (int i = 0; i < dt.getRowCount();) {
              dt.removeRow(0);
          }

          while (rs.next()) {
              Object data[] = { rs.getString(1), rs.getString(2),
                      rs.getInt(3), rs.getString(4) };

              dt.addRow(data);
          }

      } catch (SQLException e) {
          System.out.println(e + "=> getUserSearch fail");
      } finally {
          dbClose();
      }

  }//getUserSearch()

}// Ŭ������