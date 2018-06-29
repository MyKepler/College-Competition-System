package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProAndReviewObject;
import com.yhcj.enity.ProjectInfoObject;
import com.yhcj.enity.ProjectObject;

public interface ProjectReviewer {
	
	/*
	 * 得到project_review的所有项目评审信息的总数
	 */
	int getCount();
	
	/*
	 * 列出表project_review的所有项目评审信息
	 */
	List<ProjectInfoObject> findAllProReview(String pageNum,String pageSize);
	
	
	/*
	 * 根据proId和proStatus在表project_review表查找有无对应数据
	 */
	int isUpdate(String proId,String proStatus);

	/*
	 * 若查询时没有数据（插入项目评审信息）
	 */
	 int insertProReview(ProAndReviewObject proAndrevObj);
	
	/*
	 * 若查询时有数据（更新项目评审信息）
	 */
	int UpdateProreview(ProAndReviewObject proAndrevObj);
	
	/*
	 * 根据给定的proId和review_type删除对应的评审信息
	 */
	boolean delProReviewMsg(String proId,String reviewType);
	
	/*
	 * 列出所有项目
	 */
	List<ProjectObject> findAllProject();
}
