package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProRevObject;
import com.yhcj.enity.ProjectObject;

public interface MidProject {
	/**
	 * ������Ŀ�ܵ�����
	 */
	int getCount();
	/**
	 * ���ص���Ŀ����list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<ProStuTeaAndRev> findMidPro(String pageNum,String pageSize);
	/**
	 * �����ض�id��������Ŀ
	 */
	ProStuTeaAndRev findSpeMidPro(String proId);
	/**
	 * �����ض�id����Ŀ
	 * ��������Ŀ����
	 */
	int updateMidPro(ProjectObject proObj);
	/**
	 * �ı���Ŀ״̬��
	 * ��Ŀ�����У�״̬����δ��ʼ/��ʼ
	 * ��Ŀ���ڣ�״̬���ǿ�ʼ/����
	 * ��Ŀ���⣺״̬��������/����
	 */
	/**
	 * ɾ����Ŀ
	 */
	boolean delMidPro(String proId);
	/*-----------------------------��ʦ��----------------------------------*/
	
	/*
	 * ���Ҹý�ʦָ����������Ŀ���׶�Ϊ���ڣ�����ĿΪ����״̬��
	 */
	List<ProStuTeaAndRev> findMidProByTea(String pageNum,String pageSize,String userId);
	
	/*
	 * ͳ�Ƴ��ý�ʦָ����������Ŀ���׶�Ϊ���ڣ�����ĿΪ����״̬��������
	 */
	/*-----------------------------ѧ����----------------------------------*/
	int getTeaCount(String userId);
	/**
	 * ������Ŀ�ܵ�����
	 */
	int getCount(String userId);
	/**
	 * ���ص���Ŀ����list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<ProStuTeaAndRev> findAllStuMidPro(String pageNum,String pageSize,String userId);
}
