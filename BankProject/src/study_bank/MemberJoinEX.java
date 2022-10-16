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

		la[0].setText("È¸¿ø°¡ÀÔ");
		la[4].setText("ÀÌ¸§À» ÀÔ·ÂÇÏ¼¼¿ä");
		la[1].setText("id¸¦ ÀÔ·ÂÇÏ¼¼¿ä");
		la[2].setText("pw¸¦ ÀÔ·ÂÇÏ¼¼¿ä");
		la[3].setText("ÈÞ´ë¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");

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
			String pattern_name = "^[°¡-ÆR]*"; // Á¤±Ô½Ä °Ë»ç ÆÐÅÏ
			
			String id = Tf_id.getText();
			String pattern_id = "^[a-z0-9]{4,12}$";
			
			String pw = Tf_pw.getText();
			String pattern_pw = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"; // Á¤±Ô½Ä °Ë»ç ÆÐÅÏ
			
			String phone = Tf_phone.getText();
			String pattern_phone = "^(0[2-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$"; // Á¤±Ô½Ä °Ë»ç ÆÐÅÏ
			
			boolean name_mathch = Pattern.matches(pattern_name, Tf_name.getText());
			if (!name_mathch) {
				JOptionPane.showMessageDialog(null, "ÀÌ¸§ÀÌ Àß¸øµÇ¾ú½À´Ï´Ù. ´Ù½ÃÈ®ÀÎÇØÁÖ¼¼¿ä");
			}
			
			boolean id_mathch = Pattern.matches(pattern_id, Tf_id.getText());
			if (!id_mathch) {
				JOptionPane.showMessageDialog(null, "¾ÆÀÌµð°¡ Àß¸øµÇ¾ú½À´Ï´Ù. ´Ù½ÃÈ®ÀÎÇØÁÖ¼¼¿ä");
			}
			
			boolean pw_mathch = Pattern.matches(pattern_pw, Tf_pw.getText());
			if (!pw_mathch) {
				JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£°¡ Àß¸øµÇ¾ú½À´Ï´Ù. ´Ù½ÃÈ®ÀÎÇØÁÖ¼¼¿ä");
			}
			
			boolean phone_mathch = Pattern.matches(pattern_phone, Tf_phone.getText());
			if (!phone_mathch) {
				JOptionPane.showMessageDialog(null, "ÇÚµåÆù¹øÈ£°¡ Àß¸øµÇ¾ú½À´Ï´Ù. ´Ù½ÃÈ®ÀÎÇØÁÖ¼¼¿ä");
			}
			
			if(name_mathch&&id_mathch&&phone_mathch&&pw_mathch) {
				JOptionPane.showMessageDialog(null, "È¸¿ø°¡ÀÔÀÌ ¼º°øµÇ¾ú½À´Ï´Ù.È¯¿µÇÕ´Ï´Ù.");
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
				String pattern_name = "^[°¡-ÆR]*"; // Á¤±Ô½Ä °Ë»ç ÆÐÅÏ
				boolean name_mathch = Pattern.matches(pattern_name, Tf_name.getText());
				if (name_check) {
					if (name_mathch) { // Á¤±Ô½Ä °Ë»ç¿¡ ÀÏÄ¡ÇÒ °æ¿ì
						la[12].setText("");
						la[12].setForeground(new Color(0, 0, 0));
					} else { // Á¤±ÔÁ÷ °Ë»ç°¡ ÀÏÄ¡ÇÏÁö¾ÊÀ» °æ¿ì ¹®±¸ Àû¿ë
						la[12].setText("¿µ¾î ,¼ýÀÚ, °ø¹éÀÌ ºÒ°¡ÇÕ´Ï´Ù.");
						la[12].setForeground(new Color(255, 0, 0));
						name_mathch = Pattern.matches(Tf_name.getText(), pattern_name);
						name_check = false;
					}
				}
				if (!name_check) { // Á¤±Ô½Ä °Ë»ç°¡ ÀÏÄ¡ÇÏÁö¾ÊÀ» °æ¿ì ´Ù½Ã °Ë»ç¸¦ À§ÇØ ¿Ã¸®±â À§ÇØ Àû¿ë
					name_check = true;
				}
			}

			if (obj == Tf_id) {
				String pattern_id = "^[a-z0-9]{4,12}$";
				boolean id_mathch = Pattern.matches(pattern_id, Tf_id.getText());
				boolean overlap = false; // Áßº¹°Ë»ç

				if (id_check) {
					if (id_mathch) {
						la[13].setText("");
						la[13].setForeground(new Color(0, 0, 0));
					} else {
						Font la13Font = new Font("Airal", Font.PLAIN, 9);
						la[13].setText("ÇÑ±Û»ç¿ëÀÌ ºÒ°¡ÇÕ´Ï´Ù.4~12±ÛÀÚ·Î ¾ÆÀÌµð¸¦ Àû¾îÁÖ¼¼¿ä");
						la[13].setFont(la13Font);
						la[13].setForeground(new Color(255, 0, 0));
						id_mathch = Pattern.matches(pattern_id, Tf_id.getText());
						id_check = false;
					}

					for (int i = 0; i < sv.member.size(); i++) {
						if (Tf_id.getText().equals(sv.member.get(i).getId())) { // ¾ÆÀÌµð Áßº¹À» °Ë»ç
							la[13].setText("Áßº¹µÈ ¾ÆÀÌµðÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
							System.out.println("Áßº¹!");
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
				String pattern_pw = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"; // Á¤±Ô½Ä °Ë»ç ÆÐÅÏ
				Font la16Font = new Font("Airal", Font.PLAIN, 7);
				boolean pw_mathch = Pattern.matches(pattern_pw, Tf_pw.getText());
				if (pw_check) {
					if (pw_mathch) { // Á¤±Ô½Ä °Ë»ç¿¡ ÀÏÄ¡ÇÒ °æ¿ì
						la[16].setText("");
						la[16].setForeground(new Color(0, 0, 0));
					} else { // Á¤±ÔÁ÷ °Ë»ç°¡ ÀÏÄ¡ÇÏÁö¾ÊÀ» °æ¿ì ¹®±¸ Àû¿ë
						la[16].setText("ºñ¹Ð¹øÈ£´Â ¿µ¹®°ú Æ¯¼ö¹®ÀÚ ¼ýÀÚ¸¦ Æ÷ÇÔÇÏ¸ç 8ÀÚ ÀÌ»óÀÌ¾î¾ß ÇÕ´Ï´Ù.");
						la[16].setForeground(new Color(255, 0, 0));
						la[16].setFont(la16Font);
						pw_mathch = Pattern.matches(pattern_pw, Tf_pw.getText());
						pw_check = false;
					}
					if (Tf_pw.getText().equals(Tf_id.getText())) { //ÆÐ½º¿öµå¿Í ¾ÆÀÌµð¶û ÀÏÄ¡ÇÒ °æ¿ì
						la16Font = new Font("Airal", Font.PLAIN, 11);
						la[16].setText("¾ÆÀÌµð¶û µ¿ÀÏÇÑ°ÍÀ» »ç¿ëÇÒ¼ö ¾ø½À´Ï´Ù.");
						la[16].setFont(la16Font);
						la[16].setForeground(new Color(255, 0, 0));
					} else {
						la[13].setText("");
						la[13].setForeground(new Color(0, 0, 0));
					}

				}
				if (!pw_check) { // Á¤±Ô½Ä °Ë»ç°¡ ÀÏÄ¡ÇÏÁö¾ÊÀ» °æ¿ì ´Ù½Ã °Ë»ç¸¦ À§ÇØ ¿Ã¸®±â À§ÇØ Àû¿ë
					pw_check = true;
				}

			}
			
			if (obj == Tf_phone) {
				
				Font la17Font = new Font("Airal", Font.PLAIN, 10);
				String pattern_phone = "^(0[2-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$"; // Á¤±Ô½Ä °Ë»ç ÆÐÅÏ
				boolean phone_mathch = Pattern.matches(pattern_phone, Tf_phone.getText());
				if (phone_check) {
					if (phone_mathch) { // Á¤±Ô½Ä °Ë»ç¿¡ ÀÏÄ¡ÇÒ °æ¿ì
						la[17].setText("");
						la[17].setForeground(new Color(0, 0, 0));
					} else { // Á¤±ÔÁ÷ °Ë»ç°¡ ÀÏÄ¡ÇÏÁö¾ÊÀ» °æ¿ì ¹®±¸ Àû¿ë
						la[17].setText("'00&000'-'000&0000'-'0000' Çü½ÄÀ¸·Î Àû¾îÁÖ¼¼¿ä");
						la[17].setFont(la17Font);
						la[17].setForeground(new Color(255, 0, 0));
						phone_mathch = Pattern.matches(pattern_phone, Tf_phone.getText());
						phone_check = false;
					}
	
				}
				if (!phone_check) { // Á¤±Ô½Ä °Ë»ç°¡ ÀÏÄ¡ÇÏÁö¾ÊÀ» °æ¿ì ´Ù½Ã °Ë»ç¸¦ À§ÇØ ¿Ã¸®±â À§ÇØ Àû¿ë
					phone_check = true;
				}

			}

		}
	}

	
	
}
