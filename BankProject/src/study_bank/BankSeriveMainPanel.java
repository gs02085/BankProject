package study_bank;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;
import java.awt.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class BankSeriveMainPanel extends Frame implements ActionListener, Runnable {
	Bankservice sv;
	Label Userinfo, gap1, gap2, la_count;
	JButton input, output, balance, withdraw, exit, showInfo, logout, extension;
	Panel pa, pa2;
	LogInEX logInEX;
	MemberJoinEX ex;
	LogoutDialog lo_Da;
	LogInEX log;
	MakeAccountDialog make;
	BankPerson person;
	DespoitDialog Despoist_da;
	OutputDialog Output_da;
	Bankinfo info;

	int countNumber = 10000;

	BankSeriveMainPanel(LogInEX login) {
		log = login;
		sv = new Bankservice();
		la_count = new Label(String.valueOf(countNumber) + "초");
//		extension = new JButton("연장");
		lo_Da = new LogoutDialog(log, this);
		
		
		pa = new Panel();
		pa2 = new Panel();
		gap1 = new Label("");
		gap2 = new Label("");
		input = new JButton("입금");
		output = new JButton("송금(출금)");
//		balance = new JButton("잔고");
//		withdraw = new JButton("탈퇴");
		logout = new JButton("로그아웃");
		showInfo = new JButton("정보보기(현재 잔고 포함)");
		exit = new JButton("프로그램 종료");
		Userinfo = new Label();

		add(pa, "North");
		add(pa2, "Center");
		add(new Panel(), "South");

		pa.add(Userinfo);
		pa.add(la_count);
//		pa.add(gap1);
//		pa.add(gap2);
//		pa.add(extension);

		pa2.add(input);
		pa2.add(output);
//		pa2.add(balance);
		pa2.add(exit);
		pa2.add(showInfo);
		pa2.add(logout);

		input.addActionListener(this);
		output.addActionListener(this);
//		balance.addActionListener(this);
		exit.addActionListener(this);
		showInfo.addActionListener(this);
		logout.addActionListener(this);
//		extension.addActionListener(this);

//		pa.setLayout(new GridLayout(2, 2));
		pa2.setLayout(new GridLayout(4, 2));
		setSize(400, 400);
		setVisible(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});

	}

	public void bankservieceCall(BankPerson person) {
		this.person = person;
		Userinfo.setText(this.person.getName() + "님 환영합니다.");
		make = new MakeAccountDialog(this, person);
		Despoist_da = new DespoitDialog(this, person);
		Output_da = new OutputDialog(this, person);
		info = new Bankinfo(this, person);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj instanceof JButton) {
			JButton button = (JButton) obj;
			if ("입금" == button.getLabel()) {
				if (this.person.getAccountNumber().equals("1")) {
					JOptionPane.showMessageDialog(null, "계좌가 개설되어있지않아서 개설이 필요합니다.");
					make.setVisible(true);
				} else {
					Despoist_da.setVisible(true);
				}

			} else if ("송금(출금)" == button.getLabel()) {
				if (this.person.getAccountNumber().equals("1")) {
					JOptionPane.showMessageDialog(null, "계좌가 개설되어있지않아서 개설이 필요합니다.");
					make.setVisible(true);
				} else {
					Output_da.setVisible(true);
				}
			} else if ("정보보기(현재 잔고 포함)" == button.getLabel()) {
				info.setVisible(true);

			} else if ("로그아웃" == button.getLabel()) {
				lo_Da.setVisible(true);
				setVisible(false);
			} else if ("프로그램 종료" == button.getLabel()) {

			} else if ("연장" == button.getLabel()) {

			}

		}

	}

	public static void main(String[] args) {

	}

	@Override
	public void run() {

		try {
			for (int i = 0; i < 10000; i++) {
				Thread.sleep(1000);
				la_count.setText("\t" + String.valueOf(--countNumber) + "초");
			}
			JOptionPane.showMessageDialog(null, "100초 동안 로그인을 하지않아서 로그아웃처리 되었습니다.");
			setVisible(false);
			log.setVisible(true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

//==============================================================

class Bankinfo extends Dialog implements ActionListener {
	Connection C_InfoPerson = BankConnetion.getInstance().getConnection();
	Bankservice sv;
	LogInEX log; // 로그인 frame
	BankSeriveMainPanel sp; // 뱅크 메인서비스 frame
	JButton back, leave;
	Label la_info, space;
	TextField tf_mokey;
	Panel p1, p2, p3;
	BankPerson loginInfoPerson;
	Choice AccountChoice;

	Bankinfo(BankSeriveMainPanel sp, BankPerson person) {
		super(sp);
		this.sp = sp;
		this.loginInfoPerson = person;

		sv = new Bankservice();
		log=new LogInEX();
		log.setVisible(false);

		p1 = new Panel(new GridLayout(2, 1));
		p2 = new Panel(new GridLayout(6, 6));
		p3 = new Panel();

		la_info = new Label("개인 정보 보기");

		tf_mokey = new TextField();

		back = new JButton("Back");
		leave = new JButton("leave");

		space = new Label("");

		p1.add(la_info);
		p1.add(space);

		p2.add(new Label("ID"));
		p2.add(new Label(loginInfoPerson.getId()));
		p2.add(new Label("이름"));
		p2.add(new Label(loginInfoPerson.getName()));
		p2.add(new Label("핸드폰 번호"));
		p2.add(new Label(loginInfoPerson.getPhoneNumber()));
		p2.add(new Label("계좌번호"));
		if(!loginInfoPerson.getAccountNumber().equals("1")) {
		p2.add(new Label(loginInfoPerson.getAccountNumber()));
		}else {
			p2.add(new Label("현재 개설된 계좌가 없습니다."));
		}
		p2.add(new Label("잔고 금액"));
		if(!loginInfoPerson.getAccountNumber().equals("1")&&
			loginInfoPerson.getMoney()!=0) {
		p2.add(new Label(String.valueOf(loginInfoPerson.getMoney())));
		}else {
			p2.add(new Label("-"));
		}
		p2.add(new Label(""));
		p2.add(new Label(""));

		p3.add(back);
		p3.add(leave);

		back.addActionListener(this);
		leave.addActionListener(this);

		add(p1, "North");
		add(p2, "Center");
		add(p3, "South");

		setVisible(false);
		setSize(350, 300);
		setLocation(200, 200);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}

		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj instanceof JButton) {
			JButton button1 = (JButton) obj;
			if (button1.getLabel() == "leave") {
				PreparedStatement leavePerson = null;

				int leaveanswer = JOptionPane.showConfirmDialog(null, "정말 탈퇴하시겠습니까?", "탈퇴여부 확인",
						JOptionPane.YES_NO_OPTION);

				if (leaveanswer == 0) {
					try {
						String sql = "delete from bankmember where id='" + loginInfoPerson.getId() + "'";

						leavePerson = C_InfoPerson.prepareStatement(sql);
						int Pre_result = leavePerson.executeUpdate();
						String pre_massage = Pre_result > -1 ? "탈퇴 연동성공" : "탈퇴 연동실패";
						System.out.println("account DB:\t" + pre_massage); // 계좌번호 디비에 계좌번호를 연결하는 작업
						JOptionPane.showMessageDialog(null, "성공적으로 탈퇴되었습니다.");

						
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					} finally {
						setVisible(false);
						sp.setVisible(false);
						log.setVisible(true);
						try {
							if (leavePerson != null)
								leavePerson.close();
							if (C_InfoPerson != null)
								C_InfoPerson.close();
						} catch (Exception e3) {
							// TODO: handle exception
							e3.printStackTrace();
						}
					}
				}
			}
			if (button1.getLabel() == "Back") {
				setVisible(false);
				sp.setVisible(true);
			}
		}

	}

}

//==============================================================================

class LogoutDialog extends Frame implements ActionListener {
	LogInEX log;// 로그인 frame
	BankSeriveMainPanel sp; // 뱅크 메인서비스 frame;
	JButton button1;
	JButton yes, no;
	Label loOutMessage;
	Panel p1, p2;

	LogoutDialog(LogInEX frame, BankSeriveMainPanel sp) {
		log = frame;
		this.sp = sp;
		p1 = new Panel();
		p2 = new Panel();
		p1.setLayout(new BorderLayout());
		setLayout(new GridLayout(2, 1));
		no = new JButton("No");
		yes = new JButton("Yes");
		loOutMessage = new Label();
		setSize(200, 200);
		setLocation(200, 200);
		add(p1);
		add(p2);
		p1.add(new Label(" "), BorderLayout.EAST);
		p1.add(new Label("로그아웃하시겠습니까?"), "Center");
		p1.add(new Label(" "), BorderLayout.WEST);
		p2.add(yes);
		p2.add(no);
		yes.addActionListener(this);
		no.addActionListener(this);
		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj instanceof JButton) {
			JButton button1 = (JButton) obj;
			if (button1.getLabel() == "Yes") {
				log.setVisible(true);
				setVisible(false);
				sp.setVisible(false);
			} else if (button1.getLabel() == "No") {
				setVisible(false);
				sp.setVisible(true);
			}
		}

	}

}

//===============================================================================

class MakeAccountDialog extends Dialog implements ActionListener {
	Connection C_makeAccount = BankConnetion.getInstance().getConnection();
	Bankservice sv;
	LogInEX log; // 로그인 frame
	BankSeriveMainPanel sp; // 뱅크 메인서비스 frame
	Dialog makeAccountDialog = new Dialog(log);
	JButton yes, no;
	Label la_password, la_oneMore, la_info;
	TextField tf_password, tf_oneMore;
	Panel p1, p2, p3;
	BankPerson makeAcoountPerson;
	DespoitDialog Despoist_da;

	MakeAccountDialog(BankSeriveMainPanel sp, BankPerson person) {
		super(sp);
		this.sp = sp;
		sv = new Bankservice();
		this.makeAcoountPerson = person;
		Despoist_da = new DespoitDialog(sp, person);
		p1 = new Panel(new GridLayout(1, 2));
		p2 = new Panel(new GridLayout(4, 2));
		p3 = new Panel();

		la_info = new Label("계좌 비밀번호 설정");
		la_oneMore = new Label("비밀번호 확인");
		la_password = new Label("비밀번호");

		tf_oneMore = new TextField();
		tf_password = new TextField();

		no = new JButton("No");
		yes = new JButton("Yes");

		p1.add(la_info);

		p2.add(la_password);
		p2.add(tf_password);
		p2.add(new Label());
		p2.add(new Label());
		p2.add(la_oneMore);
		p2.add(tf_oneMore);
		p2.add(new Label());
		p2.add(new Label());

		p3.add(yes);
		p3.add(no);

		yes.addActionListener(this);
		no.addActionListener(this);

		add(p1, "North");
		add(p2, "Center");
		add(p3, "South");

		setVisible(false);
		setSize(250, 250);
		setLocation(200, 200);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}

		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj instanceof JButton) {
			JButton button1 = (JButton) obj;
			if (button1.getLabel() == "Yes") {
				String AccountPasswordCord = "^[0-9]{4}$";
				boolean Account_mathch = Pattern.matches(AccountPasswordCord, tf_password.getText());
				if (Account_mathch) {
					if (tf_password.getText().equals(tf_oneMore.getText())) {
						PreparedStatement Pre_makeAcconut = null; // 계좌번호를 디비에 연결하기 위하여 설정 한것
						makeAcoountPerson.setBankPassword(Integer.valueOf(tf_password.getText()));
						try {
							String sql = "update bankmember set AccountPassword='"
									+ Integer.valueOf(tf_password.getText()) + "' where id='"
									+ makeAcoountPerson.getId() + "'";
							Pre_makeAcconut = C_makeAccount.prepareStatement(sql);
							int Pre_result = Pre_makeAcconut.executeUpdate();
							String pre_massage = Pre_result > -1 ? "비밀번호 연동성공" : "비빌번호 연동실패";
							System.out.println("account DB:\t" + pre_massage); // 계좌번호 디비에 계좌번호를 연결하는 작업
						} catch (Exception e4) {
							// TODO: handle exception
							e4.printStackTrace();
						} finally {
							try {
								if (Pre_makeAcconut != null)
									Pre_makeAcconut.close();
								// if(C_makeAccount!=null) C_makeAccount.close();
							} catch (Exception e3) {
								// TODO: handle exception
								e3.printStackTrace();
							}
						}

						StringBuffer st = new StringBuffer();
						boolean result = true;
						int a = 0;
						while (result) {

							switch (a) {
							case 0:
								int number1 = (int) (Math.random() * (999 - 100 + 1) + 100);
								int number2 = (int) (Math.random() * (999 - 100 + 1) + 100);
								int number3 = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
								// 원하는 값-시작할 값+1 +시작할 값
								st.append(number1);
								st.append("-");
								st.append(number2);
								st.append("-");
								st.append(number3);
								System.out.println(st.toString());
								a = 1;
								break;

							case 1:

								for (int i = 0; i < sv.member.size(); i++) {
									if (st.toString().equals(sv.member.get(i))) {
										st.delete(0, 14);
										System.out.println("계좌번호 중복입니다.");
										a = 0;
									} else {
										a = 3;
									}
								}
								break;
							default:
								JOptionPane.showMessageDialog(null, "계좌번호가 개설되었습니다. 귀하의계좌번호는" + st.toString() + "입니다");
								makeAcoountPerson.setAccountNumber(st.toString()); // 계좌번호를 해당 사람의 계좌 넘버에 연결한 작업

								try {
									String sql = "update bankmember set Accountnumber='" + st.toString()
											+ "' where id='" + makeAcoountPerson.getId() + "'";
									Pre_makeAcconut = C_makeAccount.prepareStatement(sql);
									int Pre_result = Pre_makeAcconut.executeUpdate();
									String pre_massage = Pre_result > -1 ? " 연동성공" : " 연동실패";
									System.out.println("bakperson DB:\t" + pre_massage); // 사람관련 디비에 계좌번호를 연결하는 작업

									Despoist_da.setVisible(true);
								} catch (Exception e2) {
									// TODO: handle exception
									e2.printStackTrace();
								} finally {
									try {
										if (Pre_makeAcconut != null)
											Pre_makeAcconut.close();
										if (C_makeAccount != null)
											C_makeAccount.close();
									} catch (Exception e3) {
										// TODO: handle exception
										e3.printStackTrace();
									}
								}
								result = false; // 계좌번호가 설정되면 루프에서 빠져나가기 위하여 설정
								break;
							}
						}
						setVisible(false);
						sp.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다. 다시 입력해주세요");
						setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "비빌번호는 4자리로 설정해주세요");
					yes.setVisible(true);
				}
			} else if (button1.getLabel() == "No") {
				setVisible(false);
				sp.setVisible(true);
			}
		}

	}

}
//===================================================================

class DespoitDialog extends Dialog implements ActionListener {
	Connection C_DespoitAccount = BankConnetion.getInstance().getConnection();
	Bankservice sv;
	LogInEX log; // 로그인 frame
	BankSeriveMainPanel sp; // 뱅크 메인서비스 frame
	JButton yes, no;
	Label la_info;
	TextField tf_mokey;
	Panel p1, p2, p3;
	BankPerson DespoitPerson;
	Choice AccountChoice;

	DespoitDialog(BankSeriveMainPanel sp, BankPerson person) {
		super(sp);
		this.sp = sp;
		this.DespoitPerson = person;
		p1 = new Panel(new GridLayout(2, 1));
		p2 = new Panel(new GridLayout(4, 1));
		p3 = new Panel();

		la_info = new Label("얼마를 입금하시겠습니까?");

		tf_mokey = new TextField();

		no = new JButton("Back");
		yes = new JButton("Despoit");

		AccountChoice = new Choice();
//		AccountChoice.add("계좌번호 선택");
//		AccountChoice.add(DespoitPerson.getAccountNumber());
//		AccountChoice.add("");
//		AccountChoice.add("");
//		AccountChoice.add("");

//		p1.add(AccountChoice);
		p1.add(la_info);

		p2.add(new Label());
		p2.add(tf_mokey);
		p2.add(new Label());
		p2.add(new Label());

		p3.add(yes);
		p3.add(no);

		yes.addActionListener(this);
		no.addActionListener(this);

		add(p1, "North");
		add(p2, "Center");
		add(p3, "South");

		setVisible(false);
		setSize(250, 250);
		setLocation(200, 200);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}

		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj instanceof JButton) {
			JButton button1 = (JButton) obj;
			if (button1.getLabel() == "Despoit") {
				PreparedStatement Pre_DespoitAcconut = null;
				PreparedStatement Pre_person = null;

				int balance = DespoitPerson.getMoney();
				DespoitPerson.setMoney(Integer.valueOf(tf_mokey.getText()) + balance);
				System.out.println(DespoitPerson.getName() + "님" + tf_mokey.getText() + "을 입금하였습니다");
				System.out.println(DespoitPerson.getName() + "님의 남은 잔액은" + DespoitPerson.getMoney() + "입니다");

				try {
					String sql = "insert into Account(Id,name,AccountNumber,purpose,input,time) values(" + "'"
							+ DespoitPerson.getId() + "','" + DespoitPerson.getName() + "'," + "'"
							+ DespoitPerson.getAccountNumber() + "','본인계좌입금','" + Integer.valueOf(tf_mokey.getText())
							+ "'" + ",now())";
					Pre_DespoitAcconut = C_DespoitAccount.prepareStatement(sql);
					int Pre_result = Pre_DespoitAcconut.executeUpdate();
					String pre_massage = Pre_result > -1 ? " 연동성공" : " 연동실패";
					System.out.println("bakperson DB:\t" + pre_massage); // 사람관련 디비에 계좌번호를 연결하는 작업
					int sumblance = Integer.valueOf(tf_mokey.getText()) + balance;

					sql = "update bankmember set AccountBalace='" + sumblance + "'" + " where id='"
							+ DespoitPerson.getId() + "'";
					Pre_person = C_DespoitAccount.prepareStatement(sql);
					Pre_result = Pre_person.executeUpdate();
					pre_massage = Pre_result > -1 ? "계좌 연동성공" : "계좌 연동실패";
					System.out.println("account DB:\t" + pre_massage); // 계좌번호 디비에 계좌번호를 연결하는 작업

					setVisible(false);
					sp.setVisible(true);
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				} finally {
					try {
						if (Pre_person != null)
							Pre_person.close();
						if (C_DespoitAccount != null)
							C_DespoitAccount.close();
					} catch (Exception e3) {
						// TODO: handle exception
						e3.printStackTrace();
					}
				}

			} else if (button1.getLabel() == "Back") {
				setVisible(false);
				sp.setVisible(true);
			}
		}

	}

}
//=========================================================

class OutputDialog extends Dialog implements ActionListener, ItemListener, KeyListener {
	Connection C_DespoitAccount = BankConnetion.getInstance().getConnection();
	Bankservice sv = new Bankservice();
	LogInEX log; // 로그인 frame
	BankSeriveMainPanel sp; // 뱅크 메인서비스 frame
	JButton yes, no;
	Label la_info;
	TextField tf_mokey, tf_person;
	Panel p1, p2, p3;
	BankPerson OutputPerson, inputPerson;
	Choice AccountChoice;

	public OutputDialog(BankSeriveMainPanel sp, BankPerson person) {
		super(sp);
		this.sp = sp;
		this.OutputPerson = person;
		p1 = new Panel(new GridLayout(2, 1));
		p2 = new Panel(new GridLayout(5, 1));
		p3 = new Panel();

		la_info = new Label("출금");

		tf_mokey = new TextField();
		tf_person = new TextField();

		no = new JButton("Back");
		yes = new JButton("output");

		AccountChoice = new Choice();
		AccountChoice.add("계좌번호 선택(본인계좌)");
		AccountChoice.add(OutputPerson.getAccountNumber());

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
		AccountChoice.addItemListener(this);
		tf_person.addKeyListener(this);

		add(p1, "North");
		add(p2, "Center");
		add(p3, "South");

		setVisible(false);
		setSize(250, 250);
		setLocation(200, 200);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}

		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj instanceof JButton) {
			JButton button1 = (JButton) obj;
			if (button1.getLabel() == "output") {
				System.out.println("dk");
				if (OutputPerson.getMoney() >= Integer.valueOf(tf_mokey.getText())) {
					for (int i = 0; i < sv.member.size(); i++) {
						if (tf_person.getText().equals(sv.member.get(i).getAccountNumber())) {
							inputPerson = sv.member.get(i);
							sv.output(OutputPerson, inputPerson, tf_mokey.getText());
						}

					}
					sp.setVisible(true);
					setVisible(false);

				} else {
					JOptionPane.showMessageDialog(null, "잔액이 부족하여 출금이 어렵습니다.");
					yes.setVisible(true);
				}

			} else if (button1.getLabel() == "Back") {
				setVisible(false);
				sp.setVisible(true);
			}
		}

	}

	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == AccountChoice) {
			tf_person.setText(AccountChoice.getSelectedItem());
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == tf_person) {
			if (!tf_person.getText().equals(OutputPerson.getAccountNumber())) {
				AccountChoice.select("계좌번호 선택(본인계좌)");
			} else {
				AccountChoice.select(OutputPerson.getAccountNumber());
			}

		}
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