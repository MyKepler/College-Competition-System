package com.yhcj.enity;

public class ProStuTeaAndRev {
	public ProRevObject prorevObj;
	public UserObject reviewerObj;
	public UserObject stuObj;
	public UserObject teaObj;
	public ProjectObject proObj;
	public ProjectObject getProObj() {
		return proObj;
	}
	public void setProObj(ProjectObject proObj) {
		this.proObj = proObj;
	}
	public UserObject getStuObj() {
		return stuObj;
	}
	public void setStuObj(UserObject stuObj) {
		this.stuObj = stuObj;
	}
	public UserObject getTeaObj() {
		return teaObj;
	}
	public void setTeaObj(UserObject teaObj) {
		this.teaObj = teaObj;
	}
	public ProRevObject getProrevObj() {
		return prorevObj;
	}
	public void setProrevObj(ProRevObject prorevObj) {
		this.prorevObj = prorevObj;
	}
	public UserObject getReviewerObj() {
		return reviewerObj;
	}
	public void setReviewerObj(UserObject reviewerObj) {
		this.reviewerObj = reviewerObj;
	}
}
