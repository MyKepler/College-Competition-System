package com.yhcj.Dao;

import com.yhcj.enity.RegisterObject;
import com.yhcj.enity.UserObject;

public interface LogAndReg {
	/**
	 * login ����boolean����
	 * param id ѧ��
	 * password ����
	 * isRememberPassword �Ƿ��ס����
	 */
	UserObject login(String id,String password);
	/**
	 * register����UserObject
	 * param register����
	 */
	UserObject register(RegisterObject register);
}
