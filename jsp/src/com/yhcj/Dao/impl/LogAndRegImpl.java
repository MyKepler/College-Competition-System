package com.yhcj.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.yhcj.Dao.LogAndReg;
import com.yhcj.enity.RegisterObject;
import com.yhcj.enity.UserObject;
import com.yhcj.utils.DBUtil;



public class LogAndRegImpl extends DBUtil implements LogAndReg{

	@Override
	public UserObject login(String id, String password) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();
            //用户存在
            String sqlForCheck = "select * from user where user_id = '" + id + "' and user_pwd = '" + password + "';" ;
			System.out.println("conn succeed");
            resultSet = statement.executeQuery(sqlForCheck);
			if(resultSet.next()) {
				UserObject user = new UserObject();
				user.setUser_id(resultSet.getString("user_id"));
				user.setUser_name(resultSet.getString("user_name"));
				user.setUser_identity(resultSet.getString("user_identity"));
				return user;
			}else {
				//用户不存在
				return null;
			}
        }
        catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	@Override
	public UserObject register(RegisterObject register) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		String user_id = register.getStudentId();
		String user_name = register.getName();
		String user_pwd = register.getPassword();
		String user_sex = register.getGender();
		String user_mail = register.getMail();
		String user_phone = register.getTele();
		String account_state = register.getState();
		String user_identity = register.getIdentity();
		int rowCount=0;
		//根据用户id，查询表格，是否已经存在该用户
		//没有存在则添加这个用户信息
		try {
            conn=getConn();
            statement = conn.createStatement();
            //用户存在
            String sqlForCheck = "select * from user where user_id = '" + user_id + "';" ;
			System.out.println("conn succeed");
            resultSet = statement.executeQuery(sqlForCheck);
			if(resultSet.next()) {
				return null;
			}else {
				//用户不存在
				String sql="insert into user(user_id, user_name,user_pwd,user_sex,user_mail,user_phone,account_state,user_identity) value(?,?,?,?,?,?,?,?)";
				stat=conn.prepareStatement(sql);
	            //设置值
	            stat.setString(1, user_id);
	            stat.setString(2, user_name);
	            stat.setString(3, user_pwd);
	            stat.setString(4, user_sex);
	            stat.setString(5, user_mail);
	            stat.setString(6, user_phone);
	            stat.setString(7, account_state);
	            stat.setString(8, user_identity);
	            //执行
	            rowCount=stat.executeUpdate();
	            if(rowCount >0) {
	            		UserObject user = new UserObject();
	            		user.setUser_id(resultSet.getString("user_id"));
	    				user.setUser_name(resultSet.getString("user_name"));
	    				user.setUser_identity(resultSet.getString("user_identity"));
					return user;
	            }else {
	            		return null;
	            }
			}			
        }
        catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
		
	}
	
}
