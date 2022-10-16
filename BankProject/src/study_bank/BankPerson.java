package study_bank;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BankPerson {
	private String name; //회원의 이름
	private String password; //패스워드(뱅크 접속을 위한)
	private String accountNumber; // 계좌번호
	private Date idCardNumber; //주민번호
	private String PhoneNumber; // 개인의 핸드폰번호
	private String id;
	private int money;
	private int bankPassword;
	private Timestamp Timejoin;
	private int inputmoney,outputmoney;
	private String purpose;
	private Time AccountTime;
	
	
	 List<AccontClass> accountPerson=new ArrayList<AccontClass>();
	

	public String getName() {
		return name;
	}
	
	public BankPerson() {
		
	}
	public BankPerson(String name, String password, String id) {
		this.name = name;
		this.password = password;
		this.id = id;
	}

	
	public BankPerson(String name, String password, String id,String PhoneNumber,String accountNumber) {
		this.name = name;
		this.password = password;
		this.id = id;  
		this.accountNumber=accountNumber;
		this.PhoneNumber=PhoneNumber;
	}
	
	
	public BankPerson(String name, String password, String accountNumber, Date idCardNumber, String phoneNumber,
			 String id, int money, int bankPassword, Timestamp timejoin) {
		super();
		this.name = name;
		this.password = password;
		this.accountNumber = accountNumber;
		this.idCardNumber = idCardNumber;
		PhoneNumber = phoneNumber;
		this.id = id;
		this.money = money;
		this.bankPassword = bankPassword;
		Timejoin = timejoin;
	    
	}

	public List<AccontClass> getAccount() {
		return accountPerson;
	}

	public void setAccount(List<AccontClass> account) {
		this.accountPerson = account;
	}
	
	public void addAccount(AccontClass account) {
		accountPerson.add(account);
		 
	}

	public Timestamp getTimejoin() {
		return Timejoin;
	}

	public int getBankPassword() {
		return bankPassword;
	}

	public void setBankPassword(int bankPassword) {
		this.bankPassword = bankPassword;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money =money;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	public Date getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(Date idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public int getInputmoney() {
		return inputmoney;
	}

	public void setInputmoney(int inputmoney) {
		this.inputmoney = inputmoney;
	}

	public int getOutputmoney() {
		return outputmoney;
	}

	public void setOutputmoney(int outputmoney) {
		this.outputmoney = outputmoney;
	}

	public void setTimejoin(Timestamp timejoin) {
		Timejoin = timejoin;
	}

	public Time getAccountTime() {
		return AccountTime;
	}

	public void setAccountTime(Time accountTime) {
		AccountTime = accountTime;
	}

	
	
	
		
}
