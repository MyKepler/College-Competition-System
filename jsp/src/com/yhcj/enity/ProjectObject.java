package com.yhcj.enity;

import java.sql.Date;

public class ProjectObject {
	public String pro_id;
	public String pro_status;
	public String pro_name;
	public String pro_teacher_id;
	public String pro_principal_id;
	public String plan_num;
	public String start_year;
	public String finish_year;
	public String pro_state;
	// 实际报名人数  通过查询参加改项目的id 的学生数量得到  这边我还没有实现，直接返回前端统一为0
	public String act_num;
	// 是否提交文件
	public String is_submit;
	// 提交文件的时间
	public Date submit_time;
	public String getPro_state() {
		return pro_state;
	}
	public void setPro_state(String pro_state) {
		this.pro_state = pro_state;
	}
	
	
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public String getPro_status() {
		return pro_status;
	}
	public void setPro_status(String pro_status) {
		this.pro_status = pro_status;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPro_teacher_id() {
		return pro_teacher_id;
	}
	public void setPro_teacher_id(String pro_teacher_id) {
		this.pro_teacher_id = pro_teacher_id;
	}
	public String getPro_principal_id() {
		return pro_principal_id;
	}
	public void setPro_principal_id(String pro_principal_id) {
		this.pro_principal_id = pro_principal_id;
	}
	public String getPlan_num() {
		return plan_num;
	}
	public void setPlan_num(String plan_num) {
		this.plan_num = plan_num;
	}
	public String getStart_year() {
		return start_year;
	}
	public void setStart_year(String start_year) {
		this.start_year = start_year;
	}
	public String getFinish_year() {
		return finish_year;
	}
	public void setFinish_year(String finish_year) {
		this.finish_year = finish_year;
	}
	public String getAct_num() {
		return act_num;
	}
	public void setAct_num(String act_num) {
		this.act_num = act_num;
	}
	public String getIs_submit() {
		return is_submit;
	}
	public void setIs_submit(String is_submit) {
		this.is_submit = is_submit;
	}
	public Date getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(Date submit_time) {
		this.submit_time = submit_time;
	}
	
	
}
