package study_bank;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;


public class LogInEX extends Frame implements ActionListener,MouseListener,KeyListener {
	Bankservice sv;
	public Label[] la;
	public TextField tf_id, tf_pw;
	public Button b_login, b_join, b_idFind, b_PwFind;
	public Panel[] p;
	MemberJoinEX ex;
	IdFindDialog id;
	PwFindDialog pw;
	BankSeriveMainPanel sp;
	LogoutDialog lo_da;
	LineBorder line;
	
	
	public LogInEX() {
		sv = new Bankservice();
		sv.callMember();
		ex= new MemberJoinEX();
		sp=new BankSeriveMainPanel(this);
		pw = new PwFindDialog(this);
		id=new IdFindDialog(this);
		

		p = new Panel[5];
		for (int i = 0; i < p.length; i++) {
			p[i] = new Panel();
		}

		la = new Label[20];
		
		for (int i = 0; i < la.length; i++) {
			la[i] = new Label();
		}

		la[0] = new Label();
		la[0].setText("로그인");
		la[0].setLocation(50, 50);

		setLayout(new BorderLayout());
		p[0].setLayout(new GridLayout(8, 1));
		p[1].setLayout(new BorderLayout());
		p[3].setLayout(new GridLayout(1, 2));

		tf_id = new TextField("id", 300);
		tf_id.setLocation(200, 300);
		tf_pw = new TextField("pw");
		tf_pw.setLocation(200, 300);
		

		b_login = new Button("로그인");
		b_join = new Button("회원가입");
		b_idFind = new Button("ID 찾기");
		b_PwFind = new Button("PW 찾기");

		add(p[3], "Center");
		add(p[2], "South");
		p[3].add(p[0]);
		p[3].add(p[1]);

		p[0].add(la[0]);
		p[0].add(la[1]);
		p[0].add(tf_id);
		p[0].add(tf_pw);
		p[0].add(la[6]);
		p[0].add(la[7]);
		p[0].add(b_join);

		p[1].add(b_login, "Center");
		p[1].add(la[2], "West");
		p[1].add(la[3], "East");
		p[1].add(la[5], "North");
		p[1].add(la[4], "South");

		p[2].add(b_idFind);
		p[2].add(new Label(" "));
		p[2].add(b_PwFind);

		b_login.addActionListener(this);
		b_join.addActionListener(this);
		b_idFind.addActionListener(this);
		b_PwFind.addActionListener(this);
		
		tf_id.addMouseListener(this);
		tf_pw.addMouseListener(this);
		tf_pw.addKeyListener(this);
		

		setSize(350, 350);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj instanceof Button) {
			Button event_obj = (Button) obj;
			if ("회원가입" == event_obj.getLabel()) {
				ex.setVisible(true);


			} else if ("로그인" == event_obj.getLabel()) {
				String id = tf_id.getText();
				String pw = tf_pw.getText();

				boolean login = false;
				for (int i = 0; i < sv.member.size(); i++) {
					if (tf_id.getText().equals(sv.member.get(i).getId())
							&& tf_pw.getText().equals(sv.member.get(i).getPassword())) {
						login = true;
					}
				}
				if (login) {
					sp.bankservieceCall(sv.logIn1(id, pw));       
					JOptionPane.showMessageDialog(null, "로그인 성공하였습니다.");
					tf_id.setText("id");
					tf_pw.setText("pw");
					tf_pw.setEchoChar('\0');
					setVisible(false);
					new Thread(sp).start();
					sp.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 틀렸습니다. 다시 시도해주세요");
				}

			} else if ("ID 찾기" == event_obj.getLabel()) {
				id.setVisible(true);

			} else if ("PW 찾기" == event_obj.getLabel()) {
				pw.setVisible(true);
			}

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LogInEX ex = new LogInEX();

	}

	int a=0;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==tf_id) {
			tf_id.setText("");
		}else if(e.getSource()==tf_pw) {
			if(a==0) {
			tf_pw.setText("");
			a++;}else {
				tf_pw.setEchoChar('\0'); // char값에 \0을 주면 원래 모양이 나옴
			}
			
		}
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	  tf_pw.setEchoChar('*');
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}


//====================================================================================
class IdFindDialog extends Dialog implements ActionListener {
	Bankservice sv ;
	LogInEX log ; // 로그인 frame
	BankSeriveMainPanel sp; // 뱅크 메인서비스 frame
	JButton button1;
	JButton yes, no;
	Label loOutMessage;
	Panel p1, p2, p3;
	TextField phoneNumber;

	

	
	public IdFindDialog(LogInEX frame) {
		super(frame);
		log=frame;
		sv=new Bankservice();
		p1 = new Panel(); // 라벨 붙일 파넬
		p2 = new Panel(); // 취소 확인 붙일 파넬
		p3 = new Panel(); // 가운데 붙일 파넬 (텍스트필드)
		phoneNumber = new TextField(" ");
		setLayout(new GridLayout(3, 1));
		p3.setLayout(new GridLayout(2, 1));
		no = new JButton("취소");
		yes = new JButton("확인");
		setVisible(false);
		setSize(250, 250);
		setLocation(200, 200);
		add(p1);
		add(p3);
		add(p2);
		p1.add(new Label(" "));
		p1.add(new Label(" "));
		p1.add(new Label(" "));
		p1.add(new Label(" "));
		p1.add(new Label("휴대폰 번호를 입력해주세요"));
		p2.add(yes);
		p2.add(no);

		p3.add(phoneNumber);

		yes.addActionListener(this);
		no.addActionListener(this);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}

		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj instanceof JButton) {
			JButton button1 = (JButton) obj;
			if (button1.getLabel() == "확인") {
				boolean result = false;
				for (int i = 0; i < sv.member.size(); i++) {
					if (phoneNumber.getText().equals(sv.member.get(i).getPhoneNumber())) {
						result = true;
						JOptionPane.showMessageDialog(button1, "인증번호가 발송되었습니다. 입력해주세요");
						int number = (int) (Math.random() * 1000000) + 1;
						String number1 = String.valueOf(number);
						JOptionPane.showMessageDialog(button1, "인증번호는" + number + "입니다");
						String confirm = JOptionPane.showInputDialog("인증번호를 입력해주세요");
						if (confirm.equals(number1)) {
							JOptionPane.showMessageDialog(null,
									sv.member.get(i).getName() + "님의 id는" + sv.member.get(i).getId() + "입니다");
							setVisible(false);
							log.setVisible(true);
						} else {
//							log = new LogInEX();
							JOptionPane.showMessageDialog(null, "인증번호가 일치하지않습니다. 다시 실행해주세요.");
							setVisible(false);
							log.setVisible(true);
						}
					}
				}
				if (!result) { // 일치하는 휴대폰이 없으면 result가 변경이 되지않아 출력됨
					JOptionPane.showMessageDialog(null, "일치하는 휴대폰이 없습니다. 다시 시도해주세요");
				}

			} else if (button1.getLabel() == "취소") {
				setVisible(false);
			}

		}

	}

}

//	===============================================================================
class PwFindDialog extends Dialog implements ActionListener {
	Bankservice sv ;
	LogInEX log; // 로그인 frame
	BankSeriveMainPanel sp; // 뱅크 메인서비스 frame
	JButton button1;
	JButton yes, no;
	Label loOutMessage;
	Panel p1, p2, p3;
	TextField phoneNumber;

	public PwFindDialog(LogInEX frame) {
		super(frame);
		log=frame;
		sv=new Bankservice();
		p1 = new Panel(); // 라벨 붙일 파넬
		p2 = new Panel(); // 취소 확인 붙일 파넬
		p3 = new Panel(); // 가운데 붙일 파넬 (텍스트필드)
		phoneNumber = new TextField(" ");
		setLayout(new GridLayout(3, 1));
		p3.setLayout(new GridLayout(2, 1));
		no = new JButton("취소");
		yes = new JButton("확인");
		loOutMessage = new Label();
		setVisible(false);
		setSize(250, 250);
		setLocation(200, 200);
		add(p1);
		add(p3);
		add(p2);
		p1.add(new Label(" "));
		p1.add(new Label(" "));
		p1.add(new Label(" "));
		p1.add(new Label(" "));
		p1.add(new Label("휴대폰 번호를 입력해주세요"));
		p2.add(yes);
		p2.add(no);
		p3.add(phoneNumber);

		yes.addActionListener(this);
		no();

	}

	public void no() {
		no.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj instanceof JButton) {
			JButton button1 = (JButton) obj;
			if (button1.getLabel() == "확인") {
				boolean result = false;
				for (int i = 0; i < sv.member.size(); i++) {
					if (phoneNumber.getText().equals(sv.member.get(i).getPhoneNumber())) {
						result = true;
						JOptionPane.showMessageDialog(button1, "인증번호가 발송되었습니다. 입력해주세요");
						String number1 = String.valueOf((int) (Math.random() * 1000000) + 1);
						JOptionPane.showMessageDialog(button1, "인증번호는" + number1 + "입니다");
						String confirm = JOptionPane.showInputDialog("인증번호를 입력해주세요");
						if (confirm.equals(number1)) {
							String reset = String.valueOf((int) (Math.random() * 1000000) + 1);
							String resetMassge = "초기화 한 비밀번호는 " + reset + "입니다 \n";
							sv.member.get(i).setPassword(reset);
							try {
								
								File file = new File("C:\\Users\\gs020\\OneDrive\\바탕 화면\\자바 비밀번호\\password.txt");
								FileWriter pwfile = new FileWriter(file, true);
								BufferedWriter bfpwfile = new BufferedWriter(pwfile);

								bfpwfile.write(resetMassge);
								bfpwfile.close();
								JOptionPane.showMessageDialog(null, "비밀번호가 성공적으로 초기화 되었습니다.");
								log.setVisible(true);
								setVisible(false);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "인증번호가 일치하지않습니다. 다시 실행해주세요.");
							log.setVisible(true);
							setVisible(false);
						}
						}

				}

				if (!result) { // 일치하는 휴대폰이 없으면 result가 변경이 되지않아 출력됨
					JOptionPane.showMessageDialog(null, "일치하는 휴대폰이 없습니다. 다시 시도해주세요");

				}
			}
			if (obj == no) {
				setVisible(false);
				dispose();

			}

		}
	}
}
