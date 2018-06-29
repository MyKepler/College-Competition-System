package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProAndReviewObject;
import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectInfoObject;
import com.yhcj.enity.ProjectObject;

public interface ProjectInfo {
	
	/*
	 * 返回项目信息总的数量
	 */
	int getCount();
	/*
	 * 查找除未开始之外的所有项目
	 */
	List<ProjectInfoObject> findAllProInfo(String pageNum,String pageSize);
	
	/*
	 * 查找特定id的项目
	 */
	ProjectInfoObject findProInfoById(String proId);
	
	
	/*
	 * 更新特定id的项目
	 * 参数：项目对象
	 */
	int updateProInfo(ProjectObject proObj);
	
	/*
	 * 通过项目id和项目阶段获取评审人和评审分数
	 */
	ProAndReviewObject findRevMsgInProInfo(String proId,String proStatus);

	/*
	 * 改变项目状态（可用不可用）
	 */
	
	boolean reProInfoState(String proId,String state);
   /*-----------------------------教师端----------------------------------*/
	
	/*
	 * 查找该教师指导的所有项目(包括可用、不可用)
	 */
	List<ProjectInfoObject> findAllProInfoByTea(String pageNum,String pageSize,String userId);
	
	/*
	 * 统计出该教师指导的所有项目（包括可用、不可用）的总数
	 */
	int getTeaCount(String userId);

	/*
	 * 列出所有该教师指导的项目（可用）——文件管理
	 */
	List<ProjectInfoObject> findAllProFileByTea(String pageNum,String pageSize,String userId);
	
	
	int getProFileCount(String userId);
	/*-----------------------------学生端（学生查看）----------------------------------*/
	/**
	 * 返回项目总的数量
	 */
	int getCount(String userId);
	/**
	 * 返回的项目所有list
	 * 参数：pageSize  每页多少行数据
	 * 参数：pageNum 第几页
	 */
	List<ProStuTeaAndRev> findAllStuProInfo(String pageNum,String pageSize,String userId);
	/*-----------------------------学生端（学生管理/学生为负责人）----------------------------------*/
	
	/**
	 * 查找特定id的管理项目数量
	 */
	int getCountManage(String userId);
	/**
	 * 查找特定id的管理项目
	 */
	List<ProStuTeaAndRev> findAllStuProManage(String pageNum,String pageSize,String userId);
	/**
	 * 查找特定id的项目
	 */
	ProStuTeaAndRev findSpeStuProManage(String proId);
	/**
	 * 更新特定id的项目
	 * 参数：项目对象
	 */
	int updateStuProManage(ProjectObject proObj);
	/**
	 * 查找特定id的管理项目数量
	 */
	int getCountFile(String userId);
	/**
	 * 查找特定id的管理项目
	 */
	List<ProStuTeaAndRev> findAllStuFileManage(String pageNum,String pageSize,String userId);
	
}
