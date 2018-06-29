package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ChangeInfoObject;
import com.yhcj.enity.UserObject;

public interface User {
	/**
	 * 返回的用户所有list
	 * 参数：pageSize  每页多少行数据
	 */
	int getCount();
	List<UserObject> findAllUser(String pageNum,String pageSize);
	
	List<UserObject> findUserView(String userid);
	
	boolean delUser(String userId);
	/**
	 * 更新特定用户
	 * 参数：用户对象
	 */
	int updateUser(UserObject stuObj);
	/**
	 * 通过name找id
	 */
	UserObject findUserByName(String user_name);
	/**
	 * 重置用户密码
	 */
//	boolean rePassword(String userId);
	/**
	 * 重置用户状态
	 */
//	boolean reStates(String userId,String state);
	/*---------------------------------------教师端----------------------------------*/
	/*
	 * 查找不同类型用户的信息
	 */
	ChangeInfoObject findUserMsgByType(String userId, String type);
	
	/*
	 * 更新用户信息
	 */
	
	int updateByTea(UserObject userObj);
}
