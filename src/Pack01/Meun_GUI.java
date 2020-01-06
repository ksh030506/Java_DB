package Pack01;

//UserJDailogGUI.java
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Meun_GUI extends JDialog implements ActionListener{
 
  JPanel pw=new JPanel(new GridLayout(4,1));
  JPanel pc=new JPanel(new GridLayout(4,1));
  JPanel ps=new JPanel();

  JLabel lable_Id = new JLabel("ID");
  JLabel lable_Name=new JLabel("�̸�");
  JLabel lable_Age=new JLabel("����");
  JLabel lable_Addr=new JLabel("�ּ�");


  JTextField id=new JTextField();
  JTextField name=new JTextField();
  JTextField age=new JTextField();
  JTextField addr=new JTextField();
 

  JButton confirm;
  JButton reset=new JButton("���");

 Menu_Window me;

 JPanel idCkP =new JPanel(new BorderLayout());
 JButton idCkBtn = new JButton("IDCheck");
 
 function_login dao =new function_login();
 

  public Meun_GUI(Menu_Window me, String index){
      super(me,"���̾�α�");
      this.me=me;
      if(index.equals("����")){
          confirm=new JButton(index);
      }else{
          confirm=new JButton("����"); 
         
          //text�ڽ��� ���õ� ���ڵ��� ���� �ֱ�
          int row = me.jt.getSelectedRow();//���õ� ��
          id.setText( me.jt.getValueAt(row, 0).toString() );
          name.setText( me.jt.getValueAt(row, 1).toString() );
          age.setText( me.jt.getValueAt(row, 2).toString() );
          addr.setText( me.jt.getValueAt(row, 3).toString() );
         
          //id text�ڽ� ��Ȱ��
          id.setEditable(false);
 
          //IDCheck��ư ��Ȱ��ȭ
          idCkBtn.setEnabled(false);
      }
     
     
      //Label�߰��κ�
      pw.add(lable_Id);//ID
      pw.add(lable_Name);//�̸�
      pw.add(lable_Age);//����
      pw.add(lable_Addr);//�ּ�
 
     
      idCkP.add(id,"Center");
      idCkP.add(idCkBtn,"East");
     
      //TextField �߰�
      pc.add(idCkP);
      pc.add(name);
      pc.add(age);
      pc.add(addr);
     
     
     
      ps.add(confirm);
      ps.add(reset);
 
      add(pw,"West");
      add(pc,"Center");
      add(ps,"South");
     
      setSize(300,250);
      setVisible(true);

      setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
     
      //�̺�Ʈ���
      confirm.addActionListener(this); //����/���� �̺�Ʈ���
      reset.addActionListener(this); //��� �̺�Ʈ���
      idCkBtn.addActionListener(this);// ID�ߺ�üũ �̺�Ʈ ���
     
  }//�����ڳ�
 
  /**
   * ����/����/���� ��ɿ� ���� �κ�
   * */
  @Override
  public void actionPerformed(ActionEvent e) {
     String btnLabel =e.getActionCommand();//�̺�Ʈ��ü ���� Label ��������
     
     if(btnLabel.equals("����")){
         if(dao.userListInsert(this) > 0 ){//���Լ���
             messageBox(this , name.getText()+"�� ������帳�ϴ�.");
             dispose();//JDialog â�ݱ�
             
             dao.userSelectAll(me.dt);//��緹�ڵ尡���ͼ� DefaultTableModel�� �ø���
             
             if(me.dt.getRowCount() > 0)
                 me.jt.setRowSelectionInterval(0, 0);//ù��° �� ����
             
         }else{//���Խ���
             messageBox(this,"���Ե��� �ʾҽ��ϴ�.");
         }
         
     }else if(btnLabel.equals("����")){
         
          if( dao.userUpdate(this) > 0){
              messageBox(this, "�����Ϸ�Ǿ����ϴ�.");
              dispose();
              dao.userSelectAll(me.dt);
              if(me.dt.getRowCount() > 0 ) me.jt.setRowSelectionInterval(0, 0);
             
          }else{
              messageBox(this, "�������� �ʾҽ��ϴ�.");
          }
         
         
         
     }else if(btnLabel.equals("���")){
         dispose();
         
     }else if(btnLabel.equals("IDCheck")){
         //id�ؽ�Ʈ�ڽ��� �� ������ �޼��� ��� ������ DB�����Ѵ�.
         if(id.getText().trim().equals("")){
             messageBox(this,"ID�� �Է����ּ���.");
             id.requestFocus();//��Ŀ���̵�
         }else{
             
            if(  dao.getIdByCheck(id.getText()) ){ //�ߺ��ƴϴ�.(��밡��)
                messageBox(this, id.getText()+"�� ��밡���մϴ�.");  
            }else{ //�ߺ��̴�.
                messageBox(this,id.getText()+"�� �ߺ��Դϴ�.");
               
                id.setText("");//text�ڽ������
                id.requestFocus();//Ŀ������
            }
             
         }
         
     }
     
     
  }//actionPerformed��
 
  /**
   * �޽����ڽ� ����ִ� �޼ҵ� �ۼ�
   * */
  public static void messageBox(Object obj , String message){
      JOptionPane.showMessageDialog( (Component)obj , message);
  }

}//Ŭ������