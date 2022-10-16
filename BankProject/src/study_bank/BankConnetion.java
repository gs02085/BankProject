package study_bank;

import java.io.FileReader;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.Properties;

public class BankConnetion {
	public static BankConnetion instance=new BankConnetion();
	Connection bnakConnection= null;
	
	
	BankConnetion(){
		Properties bankpro=new Properties();
		try {
		String path=BankConnetion.class.getResource("Bank2.properties").getPath();
		path=URLDecoder.decode(path, "utf-8");
		bankpro.load(new FileReader(path));
		
		String driver=bankpro.getProperty("Driver");
		String url=bankpro.getProperty("URL");
		
		Class.forName(driver);
		System.out.println("드라이버 적재성공");
		 bnakConnection = DriverManager.getConnection(url, "root","java");
		System.out.println("DB연결성공했습니다 드이어!");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("왜왜 연결실패냐구 ㅠㅠ!");
		}
	}
	
	
	
	public static BankConnetion  getInstance() {
		if(instance==null) {
			new BankConnetion();
		}
		return instance;
	}
	
	
	public Connection getConnection(){
		return this.bnakConnection;
	}
	
	
	
	
	

}
