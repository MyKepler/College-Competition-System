package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProjectMemberObject;

public interface ProjectMember {
	
	/*
	 * 返回所有项目成员数量
	 */
	int getCount();
	
	/*
	 * 查询所有项目成员
	 */
	List<ProjectMemberObject> findAllProMembers(String pageNum,String pageSize);
	
	/*
	 * 查询成员信息
	 */
	ProjectMemberObject findProMemberById(String proId,String stuId);
	
	/*
	 * 重置成员状态（是否在项目中）
	 */
	boolean reStates(String proId,String userId,String state);

	/*-----------------------------教师端----------------------------------*/
	
	/*
	 * 查找该教师指导的所有项目的项目成员(包括可用，项目成员一定要有)
	 */
	List<ProjectMemberObject> findAllProInfoByTea(String pageNum,String pageSize,String userId);
	
	/*
	 * 统计出该教师指导的所有项目（包括可用、不可用）的总数
	 */
	int getTeaCount(String userId);
	
	/*-----------------------------学生端----------------------------------*/	 
		
		/*
		 * 查找该教师指导的所有项目的项目成员(包括可用，项目成员一定要有)
		 */
		List<ProjectMemberObject> findAllProInfoByStu(String pageNum,String pageSize,String userId);
		
		/*
		 * 统计出该教师指导的所有项目（包括可用、不可用）的总数
		 */
		int getStuCount(String userId);
}
