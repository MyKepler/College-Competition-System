package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProRevObject;
import com.yhcj.enity.ProStuTeaAndRev;

public interface ReviewTask {
	/**
	 * 返回项目总的数量
	 */
	int getCount(String userId);
	
	List<ProStuTeaAndRev> findReviewTask(String pageNum,String pageSize,String userId);
	
	ProStuTeaAndRev findSpeReviewTask(String proId);
	
	int updateReviewTask(ProRevObject prorevObj);
}
