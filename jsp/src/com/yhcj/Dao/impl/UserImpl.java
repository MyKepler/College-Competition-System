package com.yhcj.Dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.User;
import com.yhcj.enity.ChangeInfoObject;
import com.yhcj.enity.ReviewerObject;
import com.yhcj.enity.StudentObject;
import com.yhcj.enity.TeacherObject;
import com.yhcj.enity.UserObject;
import com.yhcj.utils.DBUtil;

public class UserImpl extends DBUtil implements User{
	@Override
	public List<UserObject> findAllUser(String pageNum,String pageSize) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		int numer = Integer.parseInt(pageNum);
		int size = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
           // String sqlForAllUser = "select * from user;";
            String sqlForAllUser = "select * from user limit "+(numer-1)*size+","+size+";";
            System.out.println(sqlForAllUser);
            resultSet = statement.executeQuery(sqlForAllUser);
            if(resultSet != null) {
            		List<UserObject> userList = new ArrayList<UserObject>();
            		while(resultSet.next()) {
            			UserObject userObj = new UserObject();
            			userObj.setUser_id(resultSet.getString("user_id"));
            			userObj.setUser_name(resultSet.getString("user_name"));
            			userObj.setUser_pwd(resultSet.getString("user_pwd"));
            			userObj.setUser_sex(resultSet.getString("user_sex"));
            			userObj.setUser_mail(resultSet.getString("user_mail"));
            			userObj.setUser_phone(resultSet.getString("user_phone"));
            			userObj.setAccount_state(resultSet.getString("account_state"));
            			userObj.setUser_identity(resultSet.getString("user_identity"));
            			userList.add(userObj);
            		}
            		return userList;
            }else {
            		return null;
            }
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String sqlForAllUser = "select count(*) from user;";
            System.out.println(sqlForAllUser);
            resultSet = statement.executeQuery(sqlForAllUser);
            if(resultSet.next()) {
            		System.out.println(resultSet.getInt(1)+"");
            		return resultSet.getInt(1);	
            }else {
            		return 0;
            }
			
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}
	
	@Override
	public List<UserObject> findUserView(String userid) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
//		int myuserid = Integer.parseInt(userid);
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String sqlForUserView = "select * from user where user_id='"+userid+"';";
            System.out.println(sqlForUserView);
            resultSet = statement.executeQuery(sqlForUserView);
            if(resultSet != null) {
            		List<UserObject> userList = new ArrayList<UserObject>();
            		while(resultSet.next()) {
            			UserObject userObj = new UserObject();
            			userObj.setUser_id(resultSet.getString("user_id"));
            			userObj.setUser_name(resultSet.getString("user_name"));
            			userObj.setUser_pwd(resultSet.getString("user_pwd"));
            			userObj.setUser_sex(resultSet.getString("user_sex"));
            			userObj.setUser_mail(resultSet.getString("user_mail"));
            			userObj.setUser_phone(resultSet.getString("user_phone"));
            			userObj.setAccount_state(resultSet.getString("account_state"));
            			userObj.setUser_identity(resultSet.getString("user_identity"));
            			userList.add(userObj);
            		}
            		return userList;
            }else {
            		return null;
            }
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	
	@Override
	public boolean delUser(String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
//		int myuserid = Integer.parseInt(userId);
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String sqlForUserDel = "delete user from user where user_id='"+userId+"';";
            System.out.println(sqlForUserDel);
            int row = statement.executeUpdate(sqlForUserDel);
            if(row > 0) {
            		return true;
            }
            else {
            		//	这边应该做回滚处理
            		return false;
            }
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	@Override
	public int updateUser(UserObject userObj) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String updsql = "update user set user_name = ?,user_sex = ?,user_mail = ?,user_phone = ?,account_state = ?,user_identity = ? where user_id = ?";
            stat=conn.prepareStatement(updsql);
            stat.setString(1, userObj.getUser_name());
            stat.setString(2, userObj.getUser_sex());
            stat.setString(3, userObj.getUser_mail());
            stat.setString(4, userObj.getUser_phone());
            stat.setString(5, userObj.getAccount_state());
            stat.setString(6, userObj.getUser_identity());
            stat.setString(7, userObj.getUser_id());
            int row = stat.executeUpdate();
            if(row > 0 ) {
            		return 1;
            }
            else {
            		return 0;
            }
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	@Override
	public UserObject findUserByName(String user_name) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String sqlForUserView = "select * from user where user_name='"+user_name+"';";
            System.out.println(sqlForUserView);
            resultSet = statement.executeQuery(sqlForUserView);
            UserObject userObj = new UserObject();
    		if(resultSet.next()) {
    			userObj.setUser_id(resultSet.getString("user_id"));
    			userObj.setUser_name(resultSet.getString("user_name"));
    			userObj.setUser_pwd(resultSet.getString("user_pwd"));
    			userObj.setUser_sex(resultSet.getString("user_sex"));
    			userObj.setUser_mail(resultSet.getString("user_mail"));
    			userObj.setUser_phone(resultSet.getString("user_phone"));
    			userObj.setAccount_state(resultSet.getString("account_state"));
    			userObj.setUser_identity(resultSet.getString("user_identity"));
    			return userObj;
    		}
            else {
            	return null;
            }
        }catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	/*-------------------------------------------------教师端------------------------------------------------*/
	@Override
	public ChangeInfoObject findUserMsgByType(String userId, String type) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statementUser = null;
		ResultSet resultSetUser = null;
		Statement statementTea = null;
		ResultSet resultSetTea = null;
		Statement statementRev = null;
		ResultSet resultSetRev = null;
		ChangeInfoObject infoObj = new ChangeInfoObject();
		
		try {
			conn=getConn();
			UserObject userObj = new UserObject();
				statementUser = conn.createStatement();
				String sqlForStu = "select * from user where user_id = '"+userId+"';";
				System.out.println(sqlForStu);
				resultSetUser = statementUser.executeQuery(sqlForStu);
	        	if(resultSetUser.next()) {
	        		userObj.setUser_id(resultSetUser.getString("user_id"));
	        		userObj.setUser_name(resultSetUser.getString("user_name"));
	        		userObj.setUser_mail(resultSetUser.getString("user_mail"));
	        		userObj.setUser_phone(resultSetUser.getString("user_phone"));
	        		userObj.setUser_sex(resultSetUser.getString("user_sex"));
	        		userObj.setAccount_state(resultSetUser.getString("account_state"));
	        		userObj.setUser_pwd(resultSetUser.getString("user_pwd"));
	        		userObj.setUser_identity(resultSetUser.getString("user_identity"));
	        		infoObj.setUserObj(userObj);
//            		stuObj.setStudent_academy(resultSetStu.getString("student_academy"));
//            		stuObj.setStudent_major(resultSetStu.getString("student_major"));
//            		stuObj.setStudent_class(resultSetStu.getString("student_class"));
	        	}
			 if(type.equals("教师")) {
				statementTea = conn.createStatement();
				String sqlForTea = "select * from teacher left join user on user.user_id = teacher.user_id where user.user_id = '"+userId+"';";
				System.out.println(sqlForTea);
	            resultSetTea = statementTea.executeQuery(sqlForTea);
            	if(resultSetTea.next()) {
            		TeacherObject teaObj = new TeacherObject();
//            		teaObj.setUser_id(resultSetTea.getString("user_id"));
//            		teaObj.setUser_name(resultSetTea.getString("user_name"));
//            		teaObj.setUser_mail(resultSetTea.getString("user_mail"));
//            		teaObj.setUser_phone(resultSetTea.getString("user_phone"));
//            		teaObj.setUser_sex(resultSetTea.getString("user_sex"));
//            		teaObj.setUser_pwd(resultSetTea.getString("user_pwd"));
//            		teaObj.setAccount_state(resultSetTea.getString("account_state"));
//            		teaObj.setUser_identity(resultSetTea.getString("user_identity"));
            		
            		teaObj.setTeacher_bachelor(resultSetTea.getString("teacher_bachelor"));
            		teaObj.setTeacher_degree(resultSetTea.getString("teacher_degree"));
            		teaObj.setTeacher_major(resultSetTea.getString("teacher_major"));
            		infoObj.setTeaObj(teaObj);
            	}
			}
			else {
				statementRev = conn.createStatement();
				String sqlForReviewer = "select * from reviewer left join user on user.user_id = reviewer.user_id where user.user_id = '"+userId+"';";
	            System.out.println(sqlForReviewer);
	            resultSetRev = statementRev.executeQuery(sqlForReviewer);
	        	if(resultSetRev.next()) {
	        		ReviewerObject revObj = new ReviewerObject();
//	        		revObj.setUser_id(resultSetRev.getString("user_id"));
//	        		revObj.setUser_name(resultSetRev.getString("user_name"));
//	        		revObj.setUser_sex(resultSetRev.getString("user_sex"));
//	        		revObj.setUser_mail(resultSetRev.getString("user_mail"));
//	        		revObj.setUser_phone(resultSetRev.getString("user_phone"));
//	        		revObj.setAccount_state(resultSetRev.getString("account_state"));
//	        		revObj.setUser_identity(resultSetRev.getString("user_identity"));
//	        		revObj.setUser_pwd(resultSetRev.getString("user_pwd"));
	        		
	        		//专家特有信息
	        		revObj.setTeacher_bachelor(resultSetRev.getString("teacher_bachelor"));
	        		revObj.setTeacher_degree(resultSetRev.getString("teacher_degree"));
	        		revObj.setTeacher_major(resultSetRev.getString("teacher_major"));
	        		System.out.println(resultSetRev.getString("teacher_major"));
	        		
	        		revObj.setCompany_name(resultSetRev.getString("company_name"));
	        		revObj.setCompany_phone(resultSetRev.getString("company_phone"));
	        		revObj.setCompany_principal(resultSetRev.getString("company_principal"));
	        		revObj.setCompany_address(resultSetRev.getString("company_address"));
	        		infoObj.setRevObj(revObj);
	        	}
			}
			return infoObj;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSetUser,statementUser, conn);
			closeConn(resultSetTea,statementTea, conn);
			closeConn(resultSetRev,statementRev, conn);
		}
	}

	
	@Override
	public int updateByTea(UserObject userObj) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String updsql = "update user set user_mail = ?,user_phone = ?,user_pwd = ? where user_id = ?";
            stat=conn.prepareStatement(updsql);
            stat.setString(1, userObj.getUser_mail());
            stat.setString(2, userObj.getUser_phone());
            stat.setString(3, userObj.getUser_pwd());
            stat.setString(4, userObj.getUser_id());
            int row = stat.executeUpdate();
            if(row > 0 ) {
            		return 1;
            }
            else {
            		return 0;
            }
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}


	
}
