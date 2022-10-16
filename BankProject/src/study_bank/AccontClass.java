package study_bank;

public class AccontClass {
	
private String Accontnumber;
private int Accountpassword;
private String name;
private String id;




public AccontClass(String accontnumber, int accountpassword, String name, String id) {
	super();
	Accontnumber = accontnumber;
	Accountpassword = accountpassword;
	this.name = name;
	this.id = id;
}
public String getAccontnumber() {
	return Accontnumber;
}
public void setAccontnumber(String accontnumber) {
	Accontnumber = accontnumber;
}
public int getAccountpassword() {
	return Accountpassword;
}
public void setAccountpassword(int accountpassword) {
	Accountpassword = accountpassword;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}




}
