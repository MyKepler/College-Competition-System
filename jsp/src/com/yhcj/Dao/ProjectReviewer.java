package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProAndReviewObject;
import com.yhcj.enity.ProjectInfoObject;
import com.yhcj.enity.ProjectObject;

public interface ProjectReviewer {
	
	/*
	 * �õ�project_review��������Ŀ������Ϣ������
	 */
	int getCount();
	
	/*
	 * �г���project_review��������Ŀ������Ϣ
	 */
	List<ProjectInfoObject> findAllProReview(String pageNum,String pageSize);
	
	
	/*
	 * ����proId��proStatus�ڱ�project_review��������޶�Ӧ����
	 */
	int isUpdate(String proId,String proStatus);

	/*
	 * ����ѯʱû�����ݣ�������Ŀ������Ϣ��
	 */
	 int insertProReview(ProAndReviewObject proAndrevObj);
	
	/*
	 * ����ѯʱ�����ݣ�������Ŀ������Ϣ��
	 */
	int UpdateProreview(ProAndReviewObject proAndrevObj);
	
	/*
	 * ���ݸ�����proId��review_typeɾ����Ӧ��������Ϣ
	 */
	boolean delProReviewMsg(String proId,String reviewType);
	
	/*
	 * �г�������Ŀ
	 */
	List<ProjectObject> findAllProject();
}
