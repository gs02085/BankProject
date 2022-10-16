package study_bank;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

import java.sql.*;

public class BalanceDialog extends Frame {
	Connection C_DespoitAccount = BankConnetion.getInstance().getConnection();
	Bankservice sv = new Bankservice();
	LogInEX log; // 로그인 frame
	BankSeriveMainPanel sp; // 뱅크 메인서비스 frame
	JButton yes, no;
	Label la_info,la_balance,la_Account;
	Panel p1, p2, p3,p4;
	BankPerson balancePerson;
	Choice periodChoice,formChoice;
	TextArea ta_mokey;
	
	//BankSeriveMainPanel sp,BankPerson person
	
	BalanceDialog(){
		this.sp = sp;
//		this.balancePerson = person;
		p1 = new Panel(new GridLayout(2, 1));
		p2 = new Panel(new BorderLayout());
		p3 = new Panel(new GridLayout(1,1));

		la_info = new Label("<잔고>");
		la_balance=new Label("계좌: 110-413-129923   금액:1000원");

		ta_mokey=new TextArea();
		ta_mokey.setEnabled(false);
		
		
		periodChoice=new Choice();
		periodChoice.add("입금");
		periodChoice.add("출금");
		periodChoice.add("전체");
		
		formChoice=new Choice();
		formChoice.add("1개월");
		formChoice.add("3개월");
		formChoice.add("6개월");
		formChoice.add("1년");

		
		p1.add(la_info);
//		p1.add(new Label());
		p1.add(la_balance);

		p2.add(new Label("거래내역"),"North");
		p2.add(formChoice,"Center");
		p2.add(periodChoice,"West");
		

		p3.add(ta_mokey);

		add(p1, "North");
		add(p2, "Center");
		add(p3, "South");

		setVisible(true);
		setSize(300, 300);
		setLocation(200, 200);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});

	}

	public static void main(String[] args) {
		new BalanceDialog();
	}
	
  public void balanceinput() {
	  Statement stmt=null;
	  ResultSet re=null;
	  
	 try {
		 String url="select purpose,input,time form bankAccount where id='sk12'";
		 stmt=C_DespoitAccount.createStatement();
		 re=stmt.executeQuery(url);
		 ResultSetMetaData rsmd=re.getMetaData();
		 int number=rsmd.getColumnCount(); 
		
		 while(re.next()) {
			 
			
		}
		 
		 
		 
	 }catch (Exception e) {
		// TODO: handle exception
	}
	 
	  
  }
	
	
}
