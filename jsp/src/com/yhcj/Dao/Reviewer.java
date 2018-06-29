package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ReviewerObject;


public interface Reviewer {
	/*
	 * 返回的专家所有list
	 * 参数：pageSize  每页多少行数据
	 */
	int getCount();
	List<ReviewerObject> findAllReviewer(String pageNum,String pageSize);
	/*
	 * 通过userId查找该用户的信息
	 */
	ReviewerObject findReviewerById(String user_id);
	/*
	 * 删除特定专家（）通过user_id删除
	 */
	boolean delReviewer(String userId);
	/*
	 * 对指定专家用户的信息进行更新
	 */
	int updateReviewer(ReviewerObject reviewerObj);
	/*
	 * 重置用户密码
	 */
	boolean rePassword(String userId);
	/*
	 * 重置用户状态
	 */
	boolean reStates(String userId,String state);

}
