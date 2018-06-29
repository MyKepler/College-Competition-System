package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.SignUpEnableProjectObject;
import com.yhcj.enity.SignUpJoinProjectObject;
import com.yhcj.enity.SignUperObject;
import com.yhcj.enity.StudentObject;
import com.yhcj.enity.StudentSignUpFileObject;

public interface Student {
	/**
	 * ����ѧ�����ܹ�������
	 */
	int getCount();
	/**
	 * ���ص�ѧ������list
	 * ������pageSize  ÿҳ����������
	 * ������pageNum �ڼ�ҳ
	 */
	List<StudentObject> findAllStu(String pageNum,String pageSize);
	/**
	 * �����ض�id��ѧ��
	 */
	StudentObject findSpeStu(String userId);
	/**
	 * ɾ���ض�id��ѧ��
	 * û�з����κ����ݣ�ǰ������ֻ����ת�����ҳ��
	 */
	boolean delStu(String userId);
	/**
	 * �����ض��û���ѧ��
	 * ������ѧ������
	 */
	int updateStu(StudentObject stuObj);
	/**
	 * �����û�����
	 */
	boolean rePassword(String userId);
	/**
	 * �����û�״̬
	 */
	boolean reStates(String userId,String state);
	
	/*--------------------��ѧ����Ŀ�����ļ��Ĳ���--------------------*/

	//�ϴ��ļ��������ļ���Ϣ��������
	Integer StudentSignUpUploadFiles(String id,String path,String name);

	//��ѯ�����ϴ��ļ�
	List<StudentSignUpFileObject> StudentSignUpQueryAllFiles(String id);

	//��ѯ�����ļ�
	StudentSignUpFileObject StudentSignUpQueryOneFile(String id,String path);

	//ɾ�������ϴ��ļ�
	Integer StudentSignUpDeleteAllFiles(String id);

	//ɾ�������ļ�
	Integer StudentSignUpDeleteOneFile(String id,String path);
	//����µı�����
	Integer StudentSignUpAddNewPerson(String signUpId,String projectId,String userId,String userName,String email,
									  String phoneNumber,String time,String state);


	//��ѯ�µı����˵�id��name�Ƿ���ƥ�䵽��ȷ���û�
	Integer StudentSignUpCheckUser(String userId,String userName);

	//��ѯ�����������δ��������ĿΪ����״̬��������Ŀ
	List<SignUpEnableProjectObject> StudentSignUpQueryAllEnableProjects();

	//��ѯ�ض���Ŀid��Ŀ�ľ�����Ϣ
	SignUpEnableProjectObject StudentSignUpSelectOneEnableProjects(String id);

	//��ѯ�����˲μӵ���Ŀ
	List<SignUpJoinProjectObject> StudentSignUpMyJoin(String userId);

	//�ض�project_id �� �ض��û�id ����д����Ϣ
	SignUperObject StudentSignUpEachDetails(String projectId,String userId);
}
