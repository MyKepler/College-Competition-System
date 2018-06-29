package com.yhcj.enity;

public class ProjectMemberObject {
	public ProAndStuObject proAndStuObj;
	public ProjectObject proObj;
	public UserObject teaObj;
	public UserObject leaderObj;
	public StudentObject stuObj;
	public ProjectObject getProObj() {
		return proObj;
	}
	public void setProObj(ProjectObject proObj) {
		this.proObj = proObj;
	}
	public UserObject getTeaObj() {
		return teaObj;
	}
	public void setTeaObj(UserObject teaObj) {
		this.teaObj = teaObj;
	}
	public UserObject getLeaderObj() {
		return leaderObj;
	}
	public void setLeaderObj(UserObject leaderObj) {
		this.leaderObj = leaderObj;
	}
	public StudentObject getStuObj() {
		return stuObj;
	}
	public void setStuObj(StudentObject stuObj) {
		this.stuObj = stuObj;
	}
	public ProAndStuObject getProAndStuObj() {
		return proAndStuObj;
	}
	public void setProAndStuObj(ProAndStuObject proAndStuObj) {
		this.proAndStuObj = proAndStuObj;
	}

}
