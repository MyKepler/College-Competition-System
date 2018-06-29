package com.yhcj.enity;

public class ProjectInfoObject {
	
	public ProjectObject proObj;
	public UserObject leaderObj;
	public UserObject teaObj;
	public ProAndReviewObject reviewObj;
	public ProjectObject getProObj() {
		return proObj;
	}
	public ProAndReviewObject getReviewObj() {
		return reviewObj;
	}
	public void setReviewObj(ProAndReviewObject reviewObj) {
		this.reviewObj = reviewObj;
	}
	public void setProObj(ProjectObject proObj) {
		this.proObj = proObj;
	}
	
	public UserObject getLeaderObj() {
		return leaderObj;
	}
	public void setLeaderObj(UserObject leaderObj) {
		this.leaderObj = leaderObj;
	}
	public UserObject getTeaObj() {
		return teaObj;
	}
	public void setTeaObj(UserObject teaObj) {
		this.teaObj = teaObj;
	}

}
