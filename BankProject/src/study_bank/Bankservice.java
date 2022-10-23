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

	static List<BankPerson> member = new ArrayList<BankPerson>();// �����̿�ȸ���� ����� ���� �迭

	Connection bankConnection = BankConnetion.getInstance().getConnection();

	public Bankservice() {

	}

	public List<BankPerson> callMember() { // db���� ������ �����ͼ� ��ü�� �ִ� ����
		Statement bankStatment = null;
		ResultSet bankResultSet = null; // ȸ�������� ���� ��� �����ϴ� ��ü
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
			System.out.println("�ε�����");
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
		// �α��� �ϴ� �޼ҵ� , ��ĳ�ʷ� ���� ������ �޾Ƽ� �߰�(�޼ҵ带 ������ ��ü�� �ֱ� ���� i���=���������� �ִ� ��Ȱ)

		member.add(new BankPerson(name, pw, id, phoneNumber, ""));

		Statement st = null;

		try {

//			System.out.println("db���Ἲ��");
			st = bankConnection.createStatement();
//			 String sql="create table BankMember (Name varchar(20),idCardNumber int,"
//			 		+ "idvarchar(12),pw varchar(20),phoneNumber varchar(20),AccountNumber"
//			        +"varchar(20),AccountBalace int,AccountPassword int,Timejoin DATETIME)";
			String sql = "insert into BANKMEMBER (Name,id,pw,phoneNumber,timejoin,AccountNumber,AccountBalace) values ('"
					+ name + "','" + id + "','" + pw + "','" + phoneNumber + "',now(),'1',0)";
			int result = st.executeUpdate(sql);
			String msg = result > -1 ? "����" : "����";
			System.out.println("Bankeprson DB:" + msg);

			System.out.println(" ���̵� �����Ǿ����ϴ�.");

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
		// �α����ϴ� �޼ҵ� (bankperson���� �޾Ƽ� id,pw�� �´� ����� ã��

		for (int i = 0; i < member.size(); i++) {
			if (id.equals(member.get(i).getId()) && password.equals(member.get(i).getPassword())) {
				System.out.println(member.get(i).getId());
				System.out.println(member.get(i).getName() + "���� �α��� �Ǿ����ϴ�");
				mm = member.get(i); // null�� mm�� �α����� �� ����� �־���
				System.out.println(mm.getId());
				continue;
			}
		}
		if (mm == null) {
			System.out.println("id�� pw�� ��ġ�����ʽ��ϴ�.");
			System.out.println("�ٽýõ��Ͽ� �ּ���");
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
				for (int j = 1; j <= 3; j++) { // ��й�ȣ 3ȸ������ �ֱ����� ���� for��
					String password = JOptionPane.showInputDialog(null, "�����ȣ�� �Է����ּ���.");
					int pw = Integer.valueOf(password);
					if (pw == person.getBankPassword()) {
						result = false;
						String sql = "insert into Account(Id,name,AccountNumber,purpose,output,time) values(" + "'"
								+ person.getId() + "','" + person.getName() + "'," + "'" + person.getAccountNumber()
								+ "','���ΰ������','" + Integer.valueOf(balance) + "',now())";
						Pre_DespoitAcconut = bankConnection.prepareStatement(sql);
						int Pre_result = Pre_DespoitAcconut.executeUpdate();
						String pre_massage = Pre_result > -1 ? " ���� ��������" : "���� ��������";
						System.out.println("bakperson DB:\t" + pre_massage); // ������� ��� ���¹�ȣ�� �����ϴ� �۾�

						sql = "update bankmember set AccountBalace='" + outblance + "'" + " where id='" + person.getId()
								+ "'";
						Pre_person = bankConnection.prepareStatement(sql);
						Pre_result = Pre_person.executeUpdate();
						pre_massage = Pre_result > -1 ? "��� ��������" : "��� ��������";
						System.out.println("account DB:\t" + pre_massage); // ���¹�ȣ ��� ���¹�ȣ�� �����ϴ� �۾�
						person.setMoney(outblance);
						break;
					}
				}
				if (result) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� 3ȸ �����Ǿ 10���Ŀ� ����� �����մϴ�.");
					System.out.println("��й�ȣ�� ��ġ�����ʽ��ϴ�.");
				}
				System.out.println(person.getName() + "��" + balance + "�� ����Ͽ����ϴ�");
				System.out.println(person.getName() + "���� ���� �ܾ���" + person.getMoney() + "�Դϴ�");
			}

			else {
				for (int j = 1; j <= 3; j++) { // ��й�ȣ 3ȸ������ �ֱ����� ���� for��
					String password = JOptionPane.showInputDialog(null, "�����ȣ�� �Է����ּ���.");
					int pw = Integer.valueOf(password);
					if (pw == person.getBankPassword()) {
						String sql = "insert into Account(Id,name,AccountNumber,purpose,output,time) values(" + "'"
								+ person.getId() + "','" + person.getName() + "'," + "'" + person.getAccountNumber()
								+ "','" + inputperson.getName() + "���� �۱�','" + Integer.valueOf(balance) + "'"
								+ ",now())";
						Pre_DespoitAcconut = bankConnection.prepareStatement(sql);
						int Pre_result = Pre_DespoitAcconut.executeUpdate();
						String pre_massage = Pre_result > -1 ? "�۱� ���� ��������" : "�۱� ���� ��������";
						System.out.println("bakperson DB:\t" + pre_massage); // ������� ��� ���¹�ȣ�� �����ϴ� �۾�

						sql = "update bankmember set AccountBalace='" + outblance + "'" + " where id='" + person.getId()
								+ "'";
						Pre_person = bankConnection.prepareStatement(sql);
						Pre_result = Pre_person.executeUpdate();
						pre_massage = Pre_result > -1 ? "�۱� ��� ��������" : "�۱� ��� ��������";
						System.out.println("account DB:\t" + pre_massage); // ���¹�ȣ ��� ���¹�ȣ�� �����ϴ� �۾�

						person.setMoney(outblance);

						// �Աݵ� ���(������ ���)
						sql = "insert into Account(Id,name,AccountNumber,purpose,input,time) values(" + "'"
								+ inputperson.getId() + "','" + inputperson.getName() + "'," + "'"
								+ inputperson.getAccountNumber() + "','" + person.getName() + "�Կ��� �Աݵ�','"
								+ Integer.valueOf(balance) + "'" + ",now())";
						Pre_DespoitAcconut = bankConnection.prepareStatement(sql);
						Pre_result = Pre_DespoitAcconut.executeUpdate();
						pre_massage = Pre_result > -1 ? " �Ա� ���� ��������" : "�Ա� ���� ��������";
						System.out.println("bakperson DB:\t" + pre_massage); // ������� ��� ���¹�ȣ�� �����ϴ� �۾�

						sql = "update bankmember set AccountBalace='" + sumblance + "'" + " where id='"
								+ inputperson.getId() + "'";
						Pre_person = bankConnection.prepareStatement(sql);
						Pre_result = Pre_person.executeUpdate();
						pre_massage = Pre_result > -1 ? "�Ա� ��� ��������" : "�Ա� ��� ��������";
						System.out.println("account DB:\t" + pre_massage); // ���¹�ȣ ��� ���¹�ȣ�� �����ϴ� �۾�

						inputperson.setMoney(sumblance);

						break;
					} else {
						password = JOptionPane.showInputDialog(null, "��й�ȣ�� ��ġ�����ʽ��ϴ�.");
						System.out.println("��й�ȣ�� ��ġ�����ʽ��ϴ�.");
						if (j == 3) { // 3ȸ ������ �Ǹ� �ߴ� ������ ���� if��
							password = JOptionPane.showInputDialog(null, "��й�ȣ�� 3ȸ �����Ǿ 10���Ŀ� ����� �����մϴ�.");
							System.out.println("��й�ȣ�� 3ȸ �����Ǿ 10���Ŀ� ����� �����մϴ�.");
						}
					}
				}

				System.out.println(person.getName() + "��" + balance + "�� ����Ͽ����ϴ�");
				System.out.println(person.getName() + "���� ���� �ܾ���" + person.getMoney() + "�Դϴ�");

				System.out.println(person.getName() + "�Բ���" + inputperson.getName() + "�Կ���" + balance + "�� �Ա��Ͽ����ϴ�");
				System.out.println(inputperson.getName() + "���� ���� �ܾ���" + inputperson.getMoney() + "�Դϴ�");

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
		// ȸ�� Ż���ϴ� �޼ҵ�
		boolean result = true; // ��й�ȣ�� �´� ȸ���� Ż���Ͽ� ����ġ ���̽����� ������ �ϱ� ���� result
		boolean result1 = false; // ��й�ȣ�� �ƴ� ��쿡 Ʋ�ȴٴ� ���θ� ������ ���� ����
		System.out.println("������ Ż���Ͻðڽ��ϱ�? (��,�ƴϿ� �� ����)");
//		question = scanner.next(); // Ż�� ���θ� ���� ��ĳ��
		if (question.equals("��")) {
			System.out.println("Ż�� ���� ��й�ȣ�� �Է����ּ���");
//			question = scanner.next(); // ��й�ȣ�� �Է� ��Ű�� ���� ��ĳ��
			for (int i = 0; i < member.size(); i++) {
				if (question.equals(member.get(i).getPassword())) { // �ɹ��� �н����尡 �´� ȸ��ã��
					if (member.get(i) == person) { // ������ ã�� �н����尡 �´� ȸ���� person�� �������� Ȯ��
						System.out.println("���������� Ż��Ǿ����ϴ�.");
						member.remove(i); // �� ȸ���� �迭���� �ʱ�ȭ
						result = false; // Ż��Ǿ switch���̽��� ��������(return���� �Ѱ���)
					} else {
						result1 = true; // �����ȣ�� Ʋ�ȴٰ� ǥ���ϱ� ���Ͽ� �� ��
					}
				}

			}
			if (result1) { // ��й�ȣ�� Ʋ���� result1�� true�� �Ǿ �����
				System.out.println("��й�ȣ�� Ʋ���ϴ�. �ٽ� �Է����ּ���.");
			}
		}
		return result;
	}

}