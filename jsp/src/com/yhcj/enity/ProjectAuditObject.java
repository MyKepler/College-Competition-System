package com.yhcj.enity;

public class ProjectAuditObject {
	
	public RecruitmentSignUpObject signUpObj;
	public ProjectObject proObj;
	public UserObject teaObj;
	public UserObject leaderObj;
	public RecruitmentSignUpObject getSignUpObj() {
		return signUpObj;
	}
	public void setSignUpObj(RecruitmentSignUpObject signUpObj) {
		this.signUpObj = signUpObj;
	}
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

}
