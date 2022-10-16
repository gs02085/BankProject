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

		la[0].setText("ȸ������");
		la[4].setText("�̸��� �Է��ϼ���");
		la[1].setText("id�� �Է��ϼ���");
		la[2].setText("pw�� �Է��ϼ���");
		la[3].setText("�޴��ȣ�� �Է����ּ���");

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
			String pattern_name = "^[��-�R]*"; // ���Խ� �˻� ����
			
			String id = Tf_id.getText();
			String pattern_id = "^[a-z0-9]{4,12}$";
			
			String pw = Tf_pw.getText();
			String pattern_pw = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"; // ���Խ� �˻� ����
			
			String phone = Tf_phone.getText();
			String pattern_phone = "^(0[2-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$"; // ���Խ� �˻� ����
			
			boolean name_mathch = Pattern.matches(pattern_name, Tf_name.getText());
			if (!name_mathch) {
				JOptionPane.showMessageDialog(null, "�̸��� �߸��Ǿ����ϴ�. �ٽ�Ȯ�����ּ���");
			}
			
			boolean id_mathch = Pattern.matches(pattern_id, Tf_id.getText());
			if (!id_mathch) {
				JOptionPane.showMessageDialog(null, "���̵� �߸��Ǿ����ϴ�. �ٽ�Ȯ�����ּ���");
			}
			
			boolean pw_mathch = Pattern.matches(pattern_pw, Tf_pw.getText());
			if (!pw_mathch) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �߸��Ǿ����ϴ�. �ٽ�Ȯ�����ּ���");
			}
			
			boolean phone_mathch = Pattern.matches(pattern_phone, Tf_phone.getText());
			if (!phone_mathch) {
				JOptionPane.showMessageDialog(null, "�ڵ�����ȣ�� �߸��Ǿ����ϴ�. �ٽ�Ȯ�����ּ���");
			}
			
			if(name_mathch&&id_mathch&&phone_mathch&&pw_mathch) {
				JOptionPane.showMessageDialog(null, "ȸ�������� �����Ǿ����ϴ�.ȯ���մϴ�.");
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
				String pattern_name = "^[��-�R]*"; // ���Խ� �˻� ����
				boolean name_mathch = Pattern.matches(pattern_name, Tf_name.getText());
				if (name_check) {
					if (name_mathch) { // ���Խ� �˻翡 ��ġ�� ���
						la[12].setText("");
						la[12].setForeground(new Color(0, 0, 0));
					} else { // ������ �˻簡 ��ġ�������� ��� ���� ����
						la[12].setText("���� ,����, ������ �Ұ��մϴ�.");
						la[12].setForeground(new Color(255, 0, 0));
						name_mathch = Pattern.matches(Tf_name.getText(), pattern_name);
						name_check = false;
					}
				}
				if (!name_check) { // ���Խ� �˻簡 ��ġ�������� ��� �ٽ� �˻縦 ���� �ø��� ���� ����
					name_check = true;
				}
			}

			if (obj == Tf_id) {
				String pattern_id = "^[a-z0-9]{4,12}$";
				boolean id_mathch = Pattern.matches(pattern_id, Tf_id.getText());
				boolean overlap = false; // �ߺ��˻�

				if (id_check) {
					if (id_mathch) {
						la[13].setText("");
						la[13].setForeground(new Color(0, 0, 0));
					} else {
						Font la13Font = new Font("Airal", Font.PLAIN, 9);
						la[13].setText("�ѱۻ���� �Ұ��մϴ�.4~12���ڷ� ���̵� �����ּ���");
						la[13].setFont(la13Font);
						la[13].setForeground(new Color(255, 0, 0));
						id_mathch = Pattern.matches(pattern_id, Tf_id.getText());
						id_check = false;
					}

					for (int i = 0; i < sv.member.size(); i++) {
						if (Tf_id.getText().equals(sv.member.get(i).getId())) { // ���̵� �ߺ��� �˻�
							la[13].setText("�ߺ��� ���̵��Դϴ�. �ٽ� �Է����ּ���");
							System.out.println("�ߺ�!");
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
				String pattern_pw = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"; // ���Խ� �˻� ����
				Font la16Font = new Font("Airal", Font.PLAIN, 7);
				boolean pw_mathch = Pattern.matches(pattern_pw, Tf_pw.getText());
				if (pw_check) {
					if (pw_mathch) { // ���Խ� �˻翡 ��ġ�� ���
						la[16].setText("");
						la[16].setForeground(new Color(0, 0, 0));
					} else { // ������ �˻簡 ��ġ�������� ��� ���� ����
						la[16].setText("��й�ȣ�� ������ Ư������ ���ڸ� �����ϸ� 8�� �̻��̾�� �մϴ�.");
						la[16].setForeground(new Color(255, 0, 0));
						la[16].setFont(la16Font);
						pw_mathch = Pattern.matches(pattern_pw, Tf_pw.getText());
						pw_check = false;
					}
					if (Tf_pw.getText().equals(Tf_id.getText())) { //�н������ ���̵�� ��ġ�� ���
						la16Font = new Font("Airal", Font.PLAIN, 11);
						la[16].setText("���̵�� �����Ѱ��� ����Ҽ� �����ϴ�.");
						la[16].setFont(la16Font);
						la[16].setForeground(new Color(255, 0, 0));
					} else {
						la[13].setText("");
						la[13].setForeground(new Color(0, 0, 0));
					}

				}
				if (!pw_check) { // ���Խ� �˻簡 ��ġ�������� ��� �ٽ� �˻縦 ���� �ø��� ���� ����
					pw_check = true;
				}

			}
			
			if (obj == Tf_phone) {
				
				Font la17Font = new Font("Airal", Font.PLAIN, 10);
				String pattern_phone = "^(0[2-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$"; // ���Խ� �˻� ����
				boolean phone_mathch = Pattern.matches(pattern_phone, Tf_phone.getText());
				if (phone_check) {
					if (phone_mathch) { // ���Խ� �˻翡 ��ġ�� ���
						la[17].setText("");
						la[17].setForeground(new Color(0, 0, 0));
					} else { // ������ �˻簡 ��ġ�������� ��� ���� ����
						la[17].setText("'00&000'-'000&0000'-'0000' �������� �����ּ���");
						la[17].setFont(la17Font);
						la[17].setForeground(new Color(255, 0, 0));
						phone_mathch = Pattern.matches(pattern_phone, Tf_phone.getText());
						phone_check = false;
					}
	
				}
				if (!phone_check) { // ���Խ� �˻簡 ��ġ�������� ��� �ٽ� �˻縦 ���� �ø��� ���� ����
					phone_check = true;
				}

			}

		}
	}

	
	
}
