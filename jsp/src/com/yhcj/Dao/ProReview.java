package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProRevObject;
import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.ProjectReviewFileObject;

public interface ProReview {
	/**
	 * ������Ŀ�ܵ�����
	 */
	int getCount();
	/**
	 * ���ص���Ŀ����list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<ProStuTeaAndRev> findProReview(String pageNum,String pageSize);
	/**
	 * �����ض�id��������Ŀ
	 */
	ProStuTeaAndRev findSpeProReview(String proId);
	/**
	 * 
	 */
	ProRevObject findRevbytime(String proId,String proStatus) ;
	/**
	 * �����ض�id����Ŀ
	 * ��������Ŀ����
	 */
	int updateProReview(ProRevObject prorevObj);
	/**
	 * �ı���Ŀ״̬��
	 * ��Ŀ�����У�״̬����δ��ʼ/��ʼ
	 * ��Ŀ���ڣ�״̬���ǿ�ʼ/����
	 * ��Ŀ���⣺״̬��������/����
	 */
	/**
	 * ɾ����Ŀ
	 */
	boolean delProReview(String proId);

	/*--------------------����Ŀ��������ļ��Ĳ���--------------------*/

	//�ϴ��ļ��������ļ���Ϣ��������
	Integer ProjectReviewUploadFiles(String id,String path,String type,String name);

	//�ض�id��type��ѯ�����ϴ��ļ�
	List<ProjectReviewFileObject> ProjectReviewQueryByTypeFiles(String id, String type);

	//�ض�id��ѯ�����ϴ��ļ�
	List<ProjectReviewFileObject> ProjectReviewQueryAllFiles(String id);

	//��ѯ�����ϴ��ļ�
	ProjectReviewFileObject ProjectReviewQueryOneFile(String id,String path);

	//ɾ���ض�id�����ϴ��ļ�
	Integer ProjectReviewDeleteAllFiles(String id);

	//ɾ���ض�id��file_type�����ļ�
	Integer ProjectReviewDeleteByTypeFiles(String id,String type);

	//ɾ�������ϴ��ļ�
	Integer ProjectReviewDeleteOneFile(String id,String path);

	//�����ض�id�����ϴ��ļ���type��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
	Integer ProjectReviewUpdateAllFilesType(String id,String type,String path);

    /*-----------------------------��ʦ��----------------------------------*/
	
	/*
	 * ���Ҹý�ʦָ����������Ŀ����������Ϣ(ֻ��ʾ���õ�)
	 */
	List<ProStuTeaAndRev> findProReviewByTea(String pageNum,String pageSize,String userId);
	
	/*
	 * ͳ�Ƴ��ý�ʦָ����������Ŀ����������Ϣ(ֻ��ʾ���õ�)������
	 */
	int getTeaCount(String userId);
	/*-----------------------------ѧ����----------------------------------*/
	/**
	 * ������Ŀ�ܵ�����
	 */
	int getCount(String userId);
	/**
	 * ���ص���Ŀ����list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<ProStuTeaAndRev> findAllStuProReview(String pageNum,String pageSize,String userId);
}
