package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProAndReviewObject;
import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectInfoObject;
import com.yhcj.enity.ProjectObject;

public interface ProjectInfo {
	
	/*
	 * ������Ŀ��Ϣ�ܵ�����
	 */
	int getCount();
	/*
	 * ���ҳ�δ��ʼ֮���������Ŀ
	 */
	List<ProjectInfoObject> findAllProInfo(String pageNum,String pageSize);
	
	/*
	 * �����ض�id����Ŀ
	 */
	ProjectInfoObject findProInfoById(String proId);
	
	
	/*
	 * �����ض�id����Ŀ
	 * ��������Ŀ����
	 */
	int updateProInfo(ProjectObject proObj);
	
	/*
	 * ͨ����Ŀid����Ŀ�׶λ�ȡ�����˺��������
	 */
	ProAndReviewObject findRevMsgInProInfo(String proId,String proStatus);

	/*
	 * �ı���Ŀ״̬�����ò����ã�
	 */
	
	boolean reProInfoState(String proId,String state);
   /*-----------------------------��ʦ��----------------------------------*/
	
	/*
	 * ���Ҹý�ʦָ����������Ŀ(�������á�������)
	 */
	List<ProjectInfoObject> findAllProInfoByTea(String pageNum,String pageSize,String userId);
	
	/*
	 * ͳ�Ƴ��ý�ʦָ����������Ŀ���������á������ã�������
	 */
	int getTeaCount(String userId);

	/*
	 * �г����иý�ʦָ������Ŀ�����ã������ļ�����
	 */
	List<ProjectInfoObject> findAllProFileByTea(String pageNum,String pageSize,String userId);
	
	
	int getProFileCount(String userId);
	/*-----------------------------ѧ���ˣ�ѧ���鿴��----------------------------------*/
	/**
	 * ������Ŀ�ܵ�����
	 */
	int getCount(String userId);
	/**
	 * ���ص���Ŀ����list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<ProStuTeaAndRev> findAllStuProInfo(String pageNum,String pageSize,String userId);
	/*-----------------------------ѧ���ˣ�ѧ������/ѧ��Ϊ�����ˣ�----------------------------------*/
	
	/**
	 * �����ض�id�Ĺ�����Ŀ����
	 */
	int getCountManage(String userId);
	/**
	 * �����ض�id�Ĺ�����Ŀ
	 */
	List<ProStuTeaAndRev> findAllStuProManage(String pageNum,String pageSize,String userId);
	/**
	 * �����ض�id����Ŀ
	 */
	ProStuTeaAndRev findSpeStuProManage(String proId);
	/**
	 * �����ض�id����Ŀ
	 * ��������Ŀ����
	 */
	int updateStuProManage(ProjectObject proObj);
	/**
	 * �����ض�id�Ĺ�����Ŀ����
	 */
	int getCountFile(String userId);
	/**
	 * �����ض�id�Ĺ�����Ŀ
	 */
	List<ProStuTeaAndRev> findAllStuFileManage(String pageNum,String pageSize,String userId);
	
}
