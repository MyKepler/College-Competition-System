package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectObject;

public interface EndProject {
	/**
	 * ������Ŀ�ܵ�����
	 */
	int getCount();
	/**
	 * ���ص���Ŀ����list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<ProStuTeaAndRev> findEndPro(String pageNum,String pageSize);
	/**
	 * �����ض�id��������Ŀ
	 */
	ProStuTeaAndRev findSpeEndPro(String proId);
	/**
	 * �����ض�id����Ŀ
	 * ��������Ŀ����
	 */
	int updateEndPro(ProjectObject proObj);
	/**
	 * �ı���Ŀ״̬��
	 * ��Ŀ�����У�״̬����δ��ʼ/��ʼ
	 * ��Ŀ���ڣ�״̬���ǿ�ʼ/����
	 * ��Ŀ���⣺״̬��������/����
	 */
	/**
	 * ɾ����Ŀ
	 */
	boolean delEndPro(String proId);
	/*-----------------------------��ʦ��----------------------------------*/
	
	/*
	 * ���Ҹý�ʦָ����������Ŀ���׶�Ϊ���ڣ�����ĿΪ����״̬��
	 */
	List<ProStuTeaAndRev> findEndProByTea(String pageNum,String pageSize,String userId);
	
	/*
	 * ͳ�Ƴ��ý�ʦָ����������Ŀ���׶�Ϊ���ڣ�����ĿΪ����״̬��������
	 */
	int getTeaCount(String userId);
	/*-----------------------------��ʦ��----------------------------------*/
	/**
	 * ������Ŀ�ܵ�����
	 */
	int getCount(String userId);
	/**
	 * ���ص���Ŀ����list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<ProStuTeaAndRev> findAllStuEndPro(String pageNum,String pageSize,String userId);
}
