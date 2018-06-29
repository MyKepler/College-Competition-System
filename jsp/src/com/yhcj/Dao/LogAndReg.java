package com.yhcj.Dao;

import com.yhcj.enity.RegisterObject;
import com.yhcj.enity.UserObject;

public interface LogAndReg {
	/**
	 * login 返回boolean类型
	 * param id 学号
	 * password 密码
	 * isRememberPassword 是否记住密码
	 */
	UserObject login(String id,String password);
	/**
	 * register返回UserObject
	 * param register对象
	 */
	UserObject register(RegisterObject register);
}
