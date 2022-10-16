package study_bank;

import java.awt.Choice;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

import javax.swing.JButton;

public class EventEX extends Frame implements ActionListener {
	Bankservice sv;
	LogInEX log; // 로그인 frame
	BankSeriveMainPanel sp; // 뱅크 메인서비스 frame
	JButton yes, no;
	Label la_info;
	TextField tf_mokey,tf_person;
	Panel p1, p2, p3;
	BankPerson OutputPerson;
	Choice AccountChoice;
	

	
	EventEX(){
	
		p1 = new Panel(new GridLayout(2, 1));
		p2 = new Panel(new GridLayout(5, 1));
		p3 = new Panel();

		la_info = new Label("출금");

		tf_mokey = new TextField();
		tf_person = new TextField();

		no = new JButton("Back");
		yes = new JButton("Despoit");

		AccountChoice = new Choice();
		AccountChoice.add("계좌번호 선택(본인계좌)");
		AccountChoice.add("");
		AccountChoice.add("");
		AccountChoice.add("");
		AccountChoice.add("");

		p1.add(la_info);
		p1.add(AccountChoice);

		p2.add(new Label("계좌번호를 입력해주세요"));
		p2.add(tf_person);
		p2.add(new Label("금액을 입력해주세요"));
		p2.add(tf_mokey);
		p2.add(new Label(""));

		p3.add(yes);
		p3.add(no);

		yes.addActionListener(this);
		no.addActionListener(this);

		add(p1, "North");
		add(p2, "Center");
		add(p3, "South");

		setVisible(true);
		setSize(250, 250);
		setLocation(200, 200);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}

		});
}
	
	public static void main(String[] args) {
		new EventEX();
	}
	

@Override
public void actionPerformed(ActionEvent e) {
	Object obj = e.getSource();
	if (obj instanceof JButton) {
		JButton button1 = (JButton) obj;
		if (button1.getLabel() == "Yes") {
			
						} 
		else if (button1.getLabel() == "No") {
			
		}
	}

}
}