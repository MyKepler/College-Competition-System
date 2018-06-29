package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectObject;

public interface EndProject {
	/**
	 * 返回项目总的数量
	 */
	int getCount();
	/**
	 * 返回的项目所有list
	 * 参数：pageSize  每页多少行数据
	 * 参数：pageNum 第几页
	 */
	List<ProStuTeaAndRev> findEndPro(String pageNum,String pageSize);
	/**
	 * 查找特定id的中期项目
	 */
	ProStuTeaAndRev findSpeEndPro(String proId);
	/**
	 * 更新特定id的项目
	 * 参数：项目对象
	 */
	int updateEndPro(ProjectObject proObj);
	/**
	 * 改变项目状态的
	 * 项目申请中：状态就是未开始/开始
	 * 项目中期：状态就是开始/中期
	 * 项目结题：状态就是中期/结题
	 */
	/**
	 * 删除项目
	 */
	boolean delEndPro(String proId);
	/*-----------------------------教师端----------------------------------*/
	
	/*
	 * 查找该教师指导的所有项目（阶段为中期，且项目为可用状态）
	 */
	List<ProStuTeaAndRev> findEndProByTea(String pageNum,String pageSize,String userId);
	
	/*
	 * 统计出该教师指导的所有项目（阶段为中期，且项目为可用状态）的总数
	 */
	int getTeaCount(String userId);
	/*-----------------------------教师端----------------------------------*/
	/**
	 * 返回项目总的数量
	 */
	int getCount(String userId);
	/**
	 * 返回的项目所有list
	 * 参数：pageSize  每页多少行数据
	 * 参数：pageNum 第几页
	 */
	List<ProStuTeaAndRev> findAllStuEndPro(String pageNum,String pageSize,String userId);
}
