package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ReviewerObject;


public interface Reviewer {
	/*
	 * ���ص�ר������list
	 * ������pageSize  ÿҳ����������
	 */
	int getCount();
	List<ReviewerObject> findAllReviewer(String pageNum,String pageSize);
	/*
	 * ͨ��userId���Ҹ��û�����Ϣ
	 */
	ReviewerObject findReviewerById(String user_id);
	/*
	 * ɾ���ض�ר�ң���ͨ��user_idɾ��
	 */
	boolean delReviewer(String userId);
	/*
	 * ��ָ��ר���û�����Ϣ���и���
	 */
	int updateReviewer(ReviewerObject reviewerObj);
	/*
	 * �����û�����
	 */
	boolean rePassword(String userId);
	/*
	 * �����û�״̬
	 */
	boolean reStates(String userId,String state);

}
