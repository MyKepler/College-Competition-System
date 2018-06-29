package com.yhcj.enity;

public class ChangeInfoObject {
	public UserObject userObj;
	public TeacherObject teaObj;
	public ReviewerObject revObj;
	public UserObject getUserObj() {
		return userObj;
	}
	public void setUserObj(UserObject userObj) {
		this.userObj = userObj;
	}
	public TeacherObject getTeaObj() {
		return teaObj;
	}
	public void setTeaObj(TeacherObject teaObj) {
		this.teaObj = teaObj;
	}
	public ReviewerObject getRevObj() {
		return revObj;
	}
	public void setRevObj(ReviewerObject revObj) {
		this.revObj = revObj;
	}

}
