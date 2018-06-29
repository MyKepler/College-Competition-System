package com.yhcj.enity;

public class RegisterObject {
	//user±Ì∏Ò÷–
	public String studentId;
	public String name;
	public String password;
	public String gender;
	public String mail;
	public String tele;
	public String state;
	public String identity;
	
	
	
	public RegisterObject(String studentId,String name,String password,String gender,
			String mail,String tele,String state,String identity){
		this.studentId = studentId;
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.mail = mail;
		this.tele = tele;
		this.state = state;
		this.identity = identity;
		
	}
	
	
	
	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getName() {
		return name;
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
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
}
