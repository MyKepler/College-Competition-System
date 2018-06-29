package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProjectAuditObject;

public interface ProjectAudit {

	/*
	 * 查询数据总数
	 */
	int getCount();
	
	/*
	 * 列出所有待审核的报名者
	 */
	List<ProjectAuditObject> findAllProAudit(String pageNum, String pageSize);
	
	/*
	 * 列出指定项目与报名者的信息
	 */
	ProjectAuditObject findProAuditById(String recId);
	
	/*
	 * 审核，改变recruitment_sign_up状态,审核成功添加数据到project_student表中
	 */
	
	boolean ReState(String recId, String state,String proId,String userId);
	
	
}
