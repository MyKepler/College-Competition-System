package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectApplyObject;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.RecruitmentObject;
import com.yhcj.enity.ProjectFileObject;


//��Ŀ����ӿ�
public interface Project {
	/**
	 * ������Ŀ�ܵ�����
	 */
	int getCount();
	/**
	 * ���ص���Ŀ����list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<ProjectApplyObject> findAllPro(String pageNum,String pageSize);
	/**
	 * �����ض�id����Ŀ
	 */
	ProStuTeaAndRev findSpePro(String proId);
	/**
	 * �����ض�id����Ŀ
	 * ��������Ŀ����
	 */
	int updatePro(ProjectObject proObj);
	/**
	 * �ı���Ŀ״̬��
	 * ��Ŀ�����У�״̬�������� �ı�״̬�ͱ������
	 * ��Ŀ���ڣ�״̬�������� �ı�״̬�ͱ�ɽ���
	 * ��Ŀ���⣺״̬���ǽ���
	 */
	boolean reProState(String proId,String state);
	
	/**
	 * ɾ����Ŀ
	 */
	boolean delPro(String proId);

	/**
	 * �������Ŀ
	 * ��������Ŀ����
	 */
	boolean insertPro(ProjectObject proObj);
	/*
	 * �ı���Ŀ����֪ͨ��״̬���������¼ܱ���֪ͨ
	 */
	boolean reRecState(RecruitmentObject recObj);
	
	int projectActNumber(String id);
	/*--------------------����Ŀ�����ļ��Ĳ���--------------------*/

	//�ϴ��ļ��������ļ���Ϣ��������
	Integer ProjectSignUpUploadFiles(String id,String date,String state,String path,String type,String name);

	//�ض�id��state��type��ѯ�����ϴ��ļ�
	List<ProjectFileObject> ProjectSignUpQueryByStateAndTypeFiles(String id, String state, String type);

	//�ض�id��state��ѯ�����ϴ��ļ�
	List<ProjectFileObject> ProjectSignUpQueryByStateFiles(String id, String state);

	//�ض�id��type��ѯ�����ϴ��ļ�
	List<ProjectFileObject> ProjectSignUpQueryByTypeFiles(String id, String type);

	//�ض�id��ѯ�����ϴ��ļ�
	List<ProjectFileObject> ProjectSignUpQueryAllFiles(String id);

	//��ѯ�����ϴ��ļ�
	ProjectFileObject ProjectSignUpQueryOneFile(String id,String path);

	//ɾ���ض�id�����ϴ��ļ�
	Integer ProjectSignUpDeleteAllFiles(String id);

	//ɾ���ض�id��state�����ļ�
	Integer ProjectSignUpDeleteByStateFiles(String id,String state);

	//ɾ���ض�id��file_type�����ļ�
	Integer ProjectSignUpDeleteByTypeFiles(String id,String type);

	//ɾ���ض�id��state��file_type�����ļ�
	Integer ProjectSignUpDeleteByStateAndTypeFiles(String id,String state,String type);

	//ɾ�������ϴ��ļ�
	Integer ProjectSignUpDeleteOneFile(String id,String path);

	//�����ض�id�����ϴ��ļ���state��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
	Integer ProjectSignUpUpdateAllFilesState(String id,String state,String path);

	//�����ض�id�����ϴ��ļ���type��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
	Integer ProjectSignUpUpdateAllFilesType(String id,String type,String path);

	//�����ض�id�����ϴ��ļ���state��type��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
	Integer ProjectSignUpUpdateAllFilesStateAndType(String id,String state,String type,String path);

	//�����ض�id�����ϴ��ļ���submit_time��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
	Integer ProjectSignUpUpdateAllFilesSubmitTime(String id,String time,String path);

	
	/*-------------------------��ʦ��-------------------------------------*/
	

	 // �г��ý�ʦָ������Ŀ����������׶Σ������ǿ��õģ� 
	List<ProjectObject> findAllProInTeacher(String pageNum,String pageSize,String userId);
	 // ���ظý�ʦ����ָ����Ŀ������Ŀ��������׶Σ��ܵ�����
	int getTeaCount(String userId);
	/*-------------------------ѧ����-------------------------------------*/
	/**
	 * ������Ŀ�ܵ�����
	 */
	int getCount(String userId);
	/**
	 * ���ص���Ŀ����list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<ProStuTeaAndRev> findAllStuProApply(String pageNum,String pageSize,String userId);
	
}
