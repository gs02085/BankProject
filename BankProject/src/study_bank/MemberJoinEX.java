package study_bank;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class MemberJoinEX extends Frame implements ActionListener, TextListener {
	Bankservice sv =new Bankservice();
	LogInEX lo;
	public Label[] la;
	public TextField Tf_name, Tf_id, Tf_pw, Tf_phone;
	public Button b_joinOk;
	public Panel p1, p2 ;

	
//	public void memberJoin() {
	public MemberJoinEX() {
		
		la = new Label[20];
		for (int i = 0; i < la.length; i++) {
			la[i] = new Label("");
		}

		la[0].setText("회원가입");
		la[4].setText("이름을 입력하세요");
		la[1].setText("id를 입력하세요");
		la[2].setText("pw를 입력하세요");
		la[3].setText("휴대번호를 입력해주세요");

		p1 = new Panel();
		p2 = new Panel();

		Tf_name = new TextField("", 200);
		Tf_id = new TextField("", 200);
		Tf_pw = new TextField("", 200);
		Tf_phone = new TextField("");

		b_joinOk = new Button("join");
		b_joinOk.setBackground(Color.WHITE);
		b_joinOk.setForeground(Color.pink);

		add(p1);
		add(p2);

		p1.add(la[0]);
		p1.add(la[5]);
		p1.add(la[4]);
		p1.add(la[6]);
		p1.add(la[1]);
		p1.add(la[7]);
		p1.add(la[2]);
		p1.add(la[15]);
		p1.add(la[3]);

		p2.add(la[9]);
		p2.add(la[10]);
		p2.add(Tf_name);
		p2.add(la[12]);
		p2.add(Tf_id);
		p2.add(la[13]);
		p2.add(Tf_pw);
		p2.add(la[16]);
		p2.add(Tf_phone);
		p2.add(la[17]);
		p2.add(b_joinOk);

		b_joinOk.addActionListener(this);
		Tf_name.addTextListener(this);
		Tf_id.addTextListener(this);
		Tf_pw.addTextListener(this);
		Tf_phone.addTextListener(this);

		setLayout(new GridLayout(1, 2));
		p1.setLayout(new GridLayout(11, 1));
		p2.setLayout(new GridLayout(11, 1));
		setSize(500, 500);
		setVisible(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		lo = new LogInEX();
		Object obj = e.getSource();
		if (obj instanceof Button) {
			String name = Tf_name.getText();
			String pattern_name = "^[가-힣]*"; // 정규식 검사 패턴
			
			String id = Tf_id.getText();
			String pattern_id = "^[a-z0-9]{4,12}$";
			
			String pw = Tf_pw.getText();
			String pattern_pw = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"; // 정규식 검사 패턴
			
			String phone = Tf_phone.getText();
			String pattern_phone = "^(0[2-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$"; // 정규식 검사 패턴
			
			boolean name_mathch = Pattern.matches(pattern_name, Tf_name.getText());
			if (!name_mathch) {
				JOptionPane.showMessageDialog(null, "이름이 잘못되었습니다. 다시확인해주세요");
			}
			
			boolean id_mathch = Pattern.matches(pattern_id, Tf_id.getText());
			if (!id_mathch) {
				JOptionPane.showMessageDialog(null, "아이디가 잘못되었습니다. 다시확인해주세요");
			}
			
			boolean pw_mathch = Pattern.matches(pattern_pw, Tf_pw.getText());
			if (!pw_mathch) {
				JOptionPane.showMessageDialog(null, "비밀번호가 잘못되었습니다. 다시확인해주세요");
			}
			
			boolean phone_mathch = Pattern.matches(pattern_phone, Tf_phone.getText());
			if (!phone_mathch) {
				JOptionPane.showMessageDialog(null, "핸드폰번호가 잘못되었습니다. 다시확인해주세요");
			}
			
			if(name_mathch&&id_mathch&&phone_mathch&&pw_mathch) {
				JOptionPane.showMessageDialog(null, "회원가입이 성공되었습니다.환영합니다.");
				try {
					sv.join(id, pw, name, phone);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Tf_id.setText("");
				Tf_name.setText("");
				Tf_phone.setText("");
				Tf_pw.setText("");
				setVisible(false);
			}
			
			
			
		}

	}

	public static void main(String[] args) {
//	 new MemberJoinEX();
	}

	@Override
	public void textValueChanged(TextEvent e) {
		boolean id_check, pw_check, phone_check, name_check;

		Object obj = e.getSource();
		if (obj instanceof TextField) {
			id_check = true;
			name_check = true;
			pw_check = true;
			phone_check = true;
			
			if (obj == Tf_name) {
				String pattern_name = "^[가-힣]*"; // 정규식 검사 패턴
				boolean name_mathch = Pattern.matches(pattern_name, Tf_name.getText());
				if (name_check) {
					if (name_mathch) { // 정규식 검사에 일치할 경우
						la[12].setText("");
						la[12].setForeground(new Color(0, 0, 0));
					} else { // 정규직 검사가 일치하지않을 경우 문구 적용
						la[12].setText("영어 ,숫자, 공백이 불가합니다.");
						la[12].setForeground(new Color(255, 0, 0));
						name_mathch = Pattern.matches(Tf_name.getText(), pattern_name);
						name_check = false;
					}
				}
				if (!name_check) { // 정규식 검사가 일치하지않을 경우 다시 검사를 위해 올리기 위해 적용
					name_check = true;
				}
			}

			if (obj == Tf_id) {
				String pattern_id = "^[a-z0-9]{4,12}$";
				boolean id_mathch = Pattern.matches(pattern_id, Tf_id.getText());
				boolean overlap = false; // 중복검사

				if (id_check) {
					if (id_mathch) {
						la[13].setText("");
						la[13].setForeground(new Color(0, 0, 0));
					} else {
						Font la13Font = new Font("Airal", Font.PLAIN, 9);
						la[13].setText("한글사용이 불가합니다.4~12글자로 아이디를 적어주세요");
						la[13].setFont(la13Font);
						la[13].setForeground(new Color(255, 0, 0));
						id_mathch = Pattern.matches(pattern_id, Tf_id.getText());
						id_check = false;
					}

					for (int i = 0; i < sv.member.size(); i++) {
						if (Tf_id.getText().equals(sv.member.get(i).getId())) { // 아이디 중복을 검사
							la[13].setText("중복된 아이디입니다. 다시 입력해주세요");
							System.out.println("중복!");
							la[13].setForeground(new Color(255, 0, 0));
							overlap = false;
							id_check = false;
						}
					}
					if (overlap) {
						la[13].setText("");
						la[13].setForeground(new Color(0, 0, 0));
					}
				}
				if (!id_check) {
					id_check = true;
				}
			}
			
			if (obj == Tf_pw) {
				String pattern_pw = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"; // 정규식 검사 패턴
				Font la16Font = new Font("Airal", Font.PLAIN, 7);
				boolean pw_mathch = Pattern.matches(pattern_pw, Tf_pw.getText());
				if (pw_check) {
					if (pw_mathch) { // 정규식 검사에 일치할 경우
						la[16].setText("");
						la[16].setForeground(new Color(0, 0, 0));
					} else { // 정규직 검사가 일치하지않을 경우 문구 적용
						la[16].setText("비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.");
						la[16].setForeground(new Color(255, 0, 0));
						la[16].setFont(la16Font);
						pw_mathch = Pattern.matches(pattern_pw, Tf_pw.getText());
						pw_check = false;
					}
					if (Tf_pw.getText().equals(Tf_id.getText())) { //패스워드와 아이디랑 일치할 경우
						la16Font = new Font("Airal", Font.PLAIN, 11);
						la[16].setText("아이디랑 동일한것을 사용할수 없습니다.");
						la[16].setFont(la16Font);
						la[16].setForeground(new Color(255, 0, 0));
					} else {
						la[13].setText("");
						la[13].setForeground(new Color(0, 0, 0));
					}

				}
				if (!pw_check) { // 정규식 검사가 일치하지않을 경우 다시 검사를 위해 올리기 위해 적용
					pw_check = true;
				}

			}
			
			if (obj == Tf_phone) {
				
				Font la17Font = new Font("Airal", Font.PLAIN, 10);
				String pattern_phone = "^(0[2-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$"; // 정규식 검사 패턴
				boolean phone_mathch = Pattern.matches(pattern_phone, Tf_phone.getText());
				if (phone_check) {
					if (phone_mathch) { // 정규식 검사에 일치할 경우
						la[17].setText("");
						la[17].setForeground(new Color(0, 0, 0));
					} else { // 정규직 검사가 일치하지않을 경우 문구 적용
						la[17].setText("'00&000'-'000&0000'-'0000' 형식으로 적어주세요");
						la[17].setFont(la17Font);
						la[17].setForeground(new Color(255, 0, 0));
						phone_mathch = Pattern.matches(pattern_phone, Tf_phone.getText());
						phone_check = false;
					}
	
				}
				if (!phone_check) { // 정규식 검사가 일치하지않을 경우 다시 검사를 위해 올리기 위해 적용
					phone_check = true;
				}

			}

		}
	}

	
	
}
