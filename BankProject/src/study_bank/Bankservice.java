package study_bank;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Base64.Decoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.print.attribute.Size2DSyntax;
import javax.swing.JOptionPane;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

public class Bankservice {

	static List<BankPerson> member = new ArrayList<BankPerson>();// 은행이용회원을 만들기 위한 배열

	Connection bankConnection = BankConnetion.getInstance().getConnection();

	public Bankservice() {

	}

	public List<BankPerson> callMember() { // db에서 정보를 가져와서 객체에 넣는 과정
		Statement bankStatment = null;
		ResultSet bankResultSet = null; // 회원정보에 대한 디비를 연결하는 객체
		try {
			String select = "select * from bankmember";
			bankStatment = bankConnection.createStatement();
			bankResultSet = bankStatment.executeQuery(select);

//			String select2="select AccountPassword form Account ";
//			AccounResultset=bankStatment.executeQuery(select2);
			while (bankResultSet.next()) {
				member.add(new BankPerson(bankResultSet.getString("name"), bankResultSet.getNString("pw"),
						bankResultSet.getString("AccountNumber"), bankResultSet.getDate("idCardNumber"),
						bankResultSet.getString("phonenumber"), bankResultSet.getString("id"),
						bankResultSet.getInt("AccountBalace"), bankResultSet.getInt("AccountPassword"),
						bankResultSet.getTimestamp("Timejoin")));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("로딩실패");
		}

		try {
			if (bankResultSet != null) {
				bankResultSet.close();
			}
			if (bankStatment != null) {
				bankStatment.close();
			}
			// if (bankConnection != null) {bankConnection.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

	public void join(String id, String pw, String name, String phoneNumber) throws Exception {
		// 로그인 하는 메소드 , 스캐너로 각자 정보를 받아서 추가(메소드를 생성시 객체에 넣기 위해 i사용=순차적으로 넣는 역활)

		member.add(new BankPerson(name, pw, id, phoneNumber, ""));

		Statement st = null;

		try {

//			System.out.println("db연결성공");
			st = bankConnection.createStatement();
//			 String sql="create table BankMember (Name varchar(20),idCardNumber int,"
//			 		+ "idvarchar(12),pw varchar(20),phoneNumber varchar(20),AccountNumber"
//			        +"varchar(20),AccountBalace int,AccountPassword int,Timejoin DATETIME)";
			String sql = "insert into BANKMEMBER (Name,id,pw,phoneNumber,timejoin,AccountNumber,AccountBalace) values ('"
					+ name + "','" + id + "','" + pw + "','" + phoneNumber + "',now(),'1',0)";
			int result = st.executeUpdate(sql);
			String msg = result > -1 ? "성공" : "실패";
			System.out.println("Bankeprson DB:" + msg);

			System.out.println(" 아이디가 생성되었습니다.");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
				// if (bankConnection != null) bankConnection.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}
//	----------------------------------------------------------

	public BankPerson onePerson(String id) {
		BankPerson Chooseperson = null;
		Statement choose = null;
		ResultSet chooseRS = null;
		try {
			choose = bankConnection.createStatement();
			String sql = "select * from bankmember where id=" + id;
			chooseRS = choose.executeQuery(sql);
			if (chooseRS.next()) {
				Chooseperson = new BankPerson(chooseRS.getString("name"), chooseRS.getNString("pw"),
						chooseRS.getString("AccountNumber"), chooseRS.getDate("idCardNumber"),
						chooseRS.getString("phonenumber"), chooseRS.getString("id"), chooseRS.getInt("AccountBalace"),
						chooseRS.getInt("AccountPassword"), chooseRS.getTimestamp("Timejoin"));
			}
			return Chooseperson;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				chooseRS.close();
				choose.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public BankPerson logIn1(String id, String password) {
		BankPerson mm = null;
		// 로그인하는 메소드 (bankperson으로 받아서 id,pw와 맞는 멤버를 찾음

		for (int i = 0; i < member.size(); i++) {
			if (id.equals(member.get(i).getId()) && password.equals(member.get(i).getPassword())) {
				System.out.println(member.get(i).getId());
				System.out.println(member.get(i).getName() + "님이 로그인 되었습니다");
				mm = member.get(i); // null인 mm에 로그인이 된 사람을 넣어줌
				System.out.println(mm.getId());
				continue;
			}
		}
		if (mm == null) {
			System.out.println("id와 pw가 일치하지않습니다.");
			System.out.println("다시시도하여 주세요");
		}
		return mm;
	}

	public void output(BankPerson person, BankPerson inputperson, String balance) {
		PreparedStatement Pre_DespoitAcconut = null;
		PreparedStatement Pre_person = null;

		int money = person.getMoney();
		int inputMoney = inputperson.getMoney();
		int outblance = money - Integer.valueOf(balance);
		int sumblance = Integer.valueOf(balance) + inputMoney;
		boolean result = true;

		try {
			if (inputperson.getId().equals(person.getId())) {
				for (int j = 1; j <= 3; j++) { // 비밀번호 3회오류를 넣기위해 넣은 for문
					String password = JOptionPane.showInputDialog(null, "비빌번호를 입력해주세요.");
					int pw = Integer.valueOf(password);
					if (pw == person.getBankPassword()) {
						result = false;
						String sql = "insert into Account(Id,name,AccountNumber,purpose,output,time) values(" + "'"
								+ person.getId() + "','" + person.getName() + "'," + "'" + person.getAccountNumber()
								+ "','본인계좌출금','" + Integer.valueOf(balance) + "',now())";
						Pre_DespoitAcconut = bankConnection.prepareStatement(sql);
						int Pre_result = Pre_DespoitAcconut.executeUpdate();
						String pre_massage = Pre_result > -1 ? " 계좌 연동성공" : "계좌 연동실패";
						System.out.println("bakperson DB:\t" + pre_massage); // 사람관련 디비에 계좌번호를 연결하는 작업

						sql = "update bankmember set AccountBalace='" + outblance + "'" + " where id='" + person.getId()
								+ "'";
						Pre_person = bankConnection.prepareStatement(sql);
						Pre_result = Pre_person.executeUpdate();
						pre_massage = Pre_result > -1 ? "사람 연동성공" : "사람 연동실패";
						System.out.println("account DB:\t" + pre_massage); // 계좌번호 디비에 계좌번호를 연결하는 작업
						person.setMoney(outblance);
						break;
					}
				}
				if (result) {
					JOptionPane.showMessageDialog(null, "비밀번호가 3회 오류되어서 10분후에 사용이 가능합니다.");
					System.out.println("비밀번호가 일치하지않습니다.");
				}
				System.out.println(person.getName() + "님" + balance + "을 출금하였습니다");
				System.out.println(person.getName() + "님의 남은 잔액은" + person.getMoney() + "입니다");
			}

			else {
				for (int j = 1; j <= 3; j++) { // 비밀번호 3회오류를 넣기위해 넣은 for문
					String password = JOptionPane.showInputDialog(null, "비빌번호를 입력해주세요.");
					int pw = Integer.valueOf(password);
					if (pw == person.getBankPassword()) {
						String sql = "insert into Account(Id,name,AccountNumber,purpose,output,time) values(" + "'"
								+ person.getId() + "','" + person.getName() + "'," + "'" + person.getAccountNumber()
								+ "','" + inputperson.getName() + "에게 송금','" + Integer.valueOf(balance) + "'"
								+ ",now())";
						Pre_DespoitAcconut = bankConnection.prepareStatement(sql);
						int Pre_result = Pre_DespoitAcconut.executeUpdate();
						String pre_massage = Pre_result > -1 ? "송금 계좌 연동성공" : "송금 계좌 연동실패";
						System.out.println("bakperson DB:\t" + pre_massage); // 사람관련 디비에 계좌번호를 연결하는 작업

						sql = "update bankmember set AccountBalace='" + outblance + "'" + " where id='" + person.getId()
								+ "'";
						Pre_person = bankConnection.prepareStatement(sql);
						Pre_result = Pre_person.executeUpdate();
						pre_massage = Pre_result > -1 ? "송금 사람 연동성공" : "송금 사람 연동실패";
						System.out.println("account DB:\t" + pre_massage); // 계좌번호 디비에 계좌번호를 연결하는 작업

						person.setMoney(outblance);

						// 입금된 사람(돈들어온 사람)
						sql = "insert into Account(Id,name,AccountNumber,purpose,input,time) values(" + "'"
								+ inputperson.getId() + "','" + inputperson.getName() + "'," + "'"
								+ inputperson.getAccountNumber() + "','" + person.getName() + "님에게 입금됨','"
								+ Integer.valueOf(balance) + "'" + ",now())";
						Pre_DespoitAcconut = bankConnection.prepareStatement(sql);
						Pre_result = Pre_DespoitAcconut.executeUpdate();
						pre_massage = Pre_result > -1 ? " 입금 계좌 연동성공" : "입금 계좌 연동실패";
						System.out.println("bakperson DB:\t" + pre_massage); // 사람관련 디비에 계좌번호를 연결하는 작업

						sql = "update bankmember set AccountBalace='" + sumblance + "'" + " where id='"
								+ inputperson.getId() + "'";
						Pre_person = bankConnection.prepareStatement(sql);
						Pre_result = Pre_person.executeUpdate();
						pre_massage = Pre_result > -1 ? "입금 사람 연동성공" : "입금 사람 연동실패";
						System.out.println("account DB:\t" + pre_massage); // 계좌번호 디비에 계좌번호를 연결하는 작업

						inputperson.setMoney(sumblance);

						break;
					} else {
						password = JOptionPane.showInputDialog(null, "비밀번호가 일치하지않습니다.");
						System.out.println("비밀번호가 일치하지않습니다.");
						if (j == 3) { // 3회 오류가 되면 뜨는 문구를 위한 if문
							password = JOptionPane.showInputDialog(null, "비밀번호가 3회 오류되어서 10분후에 사용이 가능합니다.");
							System.out.println("비밀번호가 3회 오류되어서 10분후에 사용이 가능합니다.");
						}
					}
				}

				System.out.println(person.getName() + "님" + balance + "을 출금하였습니다");
				System.out.println(person.getName() + "님의 남은 잔액은" + person.getMoney() + "입니다");

				System.out.println(person.getName() + "님께서" + inputperson.getName() + "님에게" + balance + "을 입금하였습니다");
				System.out.println(inputperson.getName() + "님의 남은 잔액은" + inputperson.getMoney() + "입니다");

			}

		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		} finally {
			try {
				if (Pre_person != null)
					Pre_person.close();
				// if (bankConnection != null)
				// bankConnection.close();
			} catch (Exception e3) {
				// TODO: handle exception
				e3.printStackTrace();
			}
		}

	}

	public boolean withdraw(BankPerson person, String question) {
		// 회원 탈퇴하는 메소드
		boolean result = true; // 비밀번호가 맞는 회원이 탈퇴하여 스위치 케이스에서 나가게 하기 위한 result
		boolean result1 = false; // 비밀번호가 아닐 경우에 틀렸다는 여부를 보내기 위한 변수
		System.out.println("정말로 탈퇴하시겠습니까? (예,아니오 중 선택)");
//		question = scanner.next(); // 탈퇴 여부를 묻는 스캐너
		if (question.equals("예")) {
			System.out.println("탈퇴를 위한 비밀번호를 입력해주세요");
//			question = scanner.next(); // 비밀번호를 입력 시키기 위한 스캐너
			for (int i = 0; i < member.size(); i++) {
				if (question.equals(member.get(i).getPassword())) { // 맴버와 패스워드가 맞는 회원찾기
					if (member.get(i) == person) { // 위에서 찾은 패스워드가 맞는 회원이 person과 동일한지 확인
						System.out.println("성공적으로 탈퇴되었습니다.");
						member.remove(i); // 그 회원의 배열값을 초기화
						result = false; // 탈퇴되어서 switch케이스를 빠져나감(return값에 넘겨줌)
					} else {
						result1 = true; // 비빌번호가 틀렸다고 표시하기 위하여 준 값
					}
				}

			}
			if (result1) { // 비밀번호가 틀리면 result1이 true가 되어서 실행됨
				System.out.println("비밀번호가 틀립니다. 다시 입력해주세요.");
			}
		}
		return result;
	}

}