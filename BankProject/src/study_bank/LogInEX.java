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
		la[0].setText("�α���");
		la[0].setLocation(50, 50);

		setLayout(new BorderLayout());
		p[0].setLayout(new GridLayout(8, 1));
		p[1].setLayout(new BorderLayout());
		p[3].setLayout(new GridLayout(1, 2));

		tf_id = new TextField("id", 300);
		tf_id.setLocation(200, 300);
		tf_pw = new TextField("pw");
		tf_pw.setLocation(200, 300);
		

		b_login = new Button("�α���");
		b_join = new Button("ȸ������");
		b_idFind = new Button("ID ã��");
		b_PwFind = new Button("PW ã��");

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
			if ("ȸ������" == event_obj.getLabel()) {
				ex.setVisible(true);


			} else if ("�α���" == event_obj.getLabel()) {
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
					JOptionPane.showMessageDialog(null, "�α��� �����Ͽ����ϴ�.");
					tf_id.setText("id");
					tf_pw.setText("pw");
					tf_pw.setEchoChar('\0');
					setVisible(false);
					new Thread(sp).start();
					sp.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "���̵� ��й�ȣ�� Ʋ�Ƚ��ϴ�. �ٽ� �õ����ּ���");
				}

			} else if ("ID ã��" == event_obj.getLabel()) {
				id.setVisible(true);

			} else if ("PW ã��" == event_obj.getLabel()) {
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
				tf_pw.setEchoChar('\0'); // char���� \0�� �ָ� ���� ����� ����
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
	LogInEX log ; // �α��� frame
	BankSeriveMainPanel sp; // ��ũ ���μ��� frame
	JButton button1;
	JButton yes, no;
	Label loOutMessage;
	Panel p1, p2, p3;
	TextField phoneNumber;

	

	
	public IdFindDialog(LogInEX frame) {
		super(frame);
		log=frame;
		sv=new Bankservice();
		p1 = new Panel(); // �� ���� �ĳ�
		p2 = new Panel(); // ��� Ȯ�� ���� �ĳ�
		p3 = new Panel(); // ��� ���� �ĳ� (�ؽ�Ʈ�ʵ�)
		phoneNumber = new TextField(" ");
		setLayout(new GridLayout(3, 1));
		p3.setLayout(new GridLayout(2, 1));
		no = new JButton("���");
		yes = new JButton("Ȯ��");
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
		p1.add(new Label("�޴��� ��ȣ�� �Է����ּ���"));
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
			if (button1.getLabel() == "Ȯ��") {
				boolean result = false;
				for (int i = 0; i < sv.member.size(); i++) {
					if (phoneNumber.getText().equals(sv.member.get(i).getPhoneNumber())) {
						result = true;
						JOptionPane.showMessageDialog(button1, "������ȣ�� �߼۵Ǿ����ϴ�. �Է����ּ���");
						int number = (int) (Math.random() * 1000000) + 1;
						String number1 = String.valueOf(number);
						JOptionPane.showMessageDialog(button1, "������ȣ��" + number + "�Դϴ�");
						String confirm = JOptionPane.showInputDialog("������ȣ�� �Է����ּ���");
						if (confirm.equals(number1)) {
							JOptionPane.showMessageDialog(null,
									sv.member.get(i).getName() + "���� id��" + sv.member.get(i).getId() + "�Դϴ�");
							setVisible(false);
							log.setVisible(true);
						} else {
//							log = new LogInEX();
							JOptionPane.showMessageDialog(null, "������ȣ�� ��ġ�����ʽ��ϴ�. �ٽ� �������ּ���.");
							setVisible(false);
							log.setVisible(true);
						}
					}
				}
				if (!result) { // ��ġ�ϴ� �޴����� ������ result�� ������ �����ʾ� ��µ�
					JOptionPane.showMessageDialog(null, "��ġ�ϴ� �޴����� �����ϴ�. �ٽ� �õ����ּ���");
				}

			} else if (button1.getLabel() == "���") {
				setVisible(false);
			}

		}

	}

}

//	===============================================================================
class PwFindDialog extends Dialog implements ActionListener {
	Bankservice sv ;
	LogInEX log; // �α��� frame
	BankSeriveMainPanel sp; // ��ũ ���μ��� frame
	JButton button1;
	JButton yes, no;
	Label loOutMessage;
	Panel p1, p2, p3;
	TextField phoneNumber;

	public PwFindDialog(LogInEX frame) {
		super(frame);
		log=frame;
		sv=new Bankservice();
		p1 = new Panel(); // �� ���� �ĳ�
		p2 = new Panel(); // ��� Ȯ�� ���� �ĳ�
		p3 = new Panel(); // ��� ���� �ĳ� (�ؽ�Ʈ�ʵ�)
		phoneNumber = new TextField(" ");
		setLayout(new GridLayout(3, 1));
		p3.setLayout(new GridLayout(2, 1));
		no = new JButton("���");
		yes = new JButton("Ȯ��");
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
		p1.add(new Label("�޴��� ��ȣ�� �Է����ּ���"));
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
			if (button1.getLabel() == "Ȯ��") {
				boolean result = false;
				for (int i = 0; i < sv.member.size(); i++) {
					if (phoneNumber.getText().equals(sv.member.get(i).getPhoneNumber())) {
						result = true;
						JOptionPane.showMessageDialog(button1, "������ȣ�� �߼۵Ǿ����ϴ�. �Է����ּ���");
						String number1 = String.valueOf((int) (Math.random() * 1000000) + 1);
						JOptionPane.showMessageDialog(button1, "������ȣ��" + number1 + "�Դϴ�");
						String confirm = JOptionPane.showInputDialog("������ȣ�� �Է����ּ���");
						if (confirm.equals(number1)) {
							String reset = String.valueOf((int) (Math.random() * 1000000) + 1);
							String resetMassge = "�ʱ�ȭ �� ��й�ȣ�� " + reset + "�Դϴ� \n";
							sv.member.get(i).setPassword(reset);
							try {
								
								File file = new File("C:\\Users\\gs020\\OneDrive\\���� ȭ��\\�ڹ� ��й�ȣ\\password.txt");
								FileWriter pwfile = new FileWriter(file, true);
								BufferedWriter bfpwfile = new BufferedWriter(pwfile);

								bfpwfile.write(resetMassge);
								bfpwfile.close();
								JOptionPane.showMessageDialog(null, "��й�ȣ�� ���������� �ʱ�ȭ �Ǿ����ϴ�.");
								log.setVisible(true);
								setVisible(false);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "������ȣ�� ��ġ�����ʽ��ϴ�. �ٽ� �������ּ���.");
							log.setVisible(true);
							setVisible(false);
						}
						}

				}

				if (!result) { // ��ġ�ϴ� �޴����� ������ result�� ������ �����ʾ� ��µ�
					JOptionPane.showMessageDialog(null, "��ġ�ϴ� �޴����� �����ϴ�. �ٽ� �õ����ּ���");

				}
			}
			if (obj == no) {
				setVisible(false);
				dispose();

			}

		}
	}
}
