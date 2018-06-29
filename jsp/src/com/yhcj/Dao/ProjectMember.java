package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProjectMemberObject;

public interface ProjectMember {
	
	/*
	 * ����������Ŀ��Ա����
	 */
	int getCount();
	
	/*
	 * ��ѯ������Ŀ��Ա
	 */
	List<ProjectMemberObject> findAllProMembers(String pageNum,String pageSize);
	
	/*
	 * ��ѯ��Ա��Ϣ
	 */
	ProjectMemberObject findProMemberById(String proId,String stuId);
	
	/*
	 * ���ó�Ա״̬���Ƿ�����Ŀ�У�
	 */
	boolean reStates(String proId,String userId,String state);

	/*-----------------------------��ʦ��----------------------------------*/
	
	/*
	 * ���Ҹý�ʦָ����������Ŀ����Ŀ��Ա(�������ã���Ŀ��Աһ��Ҫ��)
	 */
	List<ProjectMemberObject> findAllProInfoByTea(String pageNum,String pageSize,String userId);
	
	/*
	 * ͳ�Ƴ��ý�ʦָ����������Ŀ���������á������ã�������
	 */
	int getTeaCount(String userId);
	
	/*-----------------------------ѧ����----------------------------------*/	 
		
		/*
		 * ���Ҹý�ʦָ����������Ŀ����Ŀ��Ա(�������ã���Ŀ��Աһ��Ҫ��)
		 */
		List<ProjectMemberObject> findAllProInfoByStu(String pageNum,String pageSize,String userId);
		
		/*
		 * ͳ�Ƴ��ý�ʦָ����������Ŀ���������á������ã�������
		 */
		int getStuCount(String userId);
}
