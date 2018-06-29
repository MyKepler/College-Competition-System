package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ChangeInfoObject;
import com.yhcj.enity.UserObject;

public interface User {
	/**
	 * ���ص��û�����list
	 * ������pageSize  ÿҳ����������
	 */
	int getCount();
	List<UserObject> findAllUser(String pageNum,String pageSize);
	
	List<UserObject> findUserView(String userid);
	
	boolean delUser(String userId);
	/**
	 * �����ض��û�
	 * �������û�����
	 */
	int updateUser(UserObject stuObj);
	/**
	 * ͨ��name��id
	 */
	UserObject findUserByName(String user_name);
	/**
	 * �����û�����
	 */
//	boolean rePassword(String userId);
	/**
	 * �����û�״̬
	 */
//	boolean reStates(String userId,String state);
	/*---------------------------------------��ʦ��----------------------------------*/
	/*
	 * ���Ҳ�ͬ�����û�����Ϣ
	 */
	ChangeInfoObject findUserMsgByType(String userId, String type);
	
	/*
	 * �����û���Ϣ
	 */
	
	int updateByTea(UserObject userObj);
}
