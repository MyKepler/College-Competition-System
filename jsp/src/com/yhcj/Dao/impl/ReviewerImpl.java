package com.yhcj.Dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.Reviewer;
import com.yhcj.enity.ReviewerObject;
import com.yhcj.utils.DBUtil;

public class ReviewerImpl extends DBUtil implements Reviewer{

	@Override
	public int getCount() {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			String sqlForAllReviewer = "select count(*) from reviewer left join user on user.user_id = reviewer.user_id;";
			System.out.println(sqlForAllReviewer);
			resultSet = statement.executeQuery(sqlForAllReviewer);
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
	public List<ReviewerObject> findAllReviewer(String pageNum, String pageSize) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		int number = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
          //查询专家信息
            String sqlForAllReviewer = "select * from reviewer left join user on user.user_id = reviewer.user_id limit "+(number-1)*size+","+size+";";
            System.out.println(sqlForAllReviewer);
            resultSet = statement.executeQuery(sqlForAllReviewer);
            if(resultSet != null) {
            	List<ReviewerObject> reviewerList = new ArrayList<ReviewerObject>();
            	while(resultSet.next()) {
            		ReviewerObject reviewerObj = new ReviewerObject();
            		reviewerObj.setUser_id(resultSet.getString("user_id"));
            		reviewerObj.setUser_name(resultSet.getString("user_name"));
            		reviewerObj.setCompany_name(resultSet.getString("company_name"));
            		reviewerObj.setUser_mail(resultSet.getString("user_mail"));
            		reviewerObj.setUser_phone(resultSet.getString("user_phone"));
            		reviewerObj.setAccount_state(resultSet.getString("account_state"));
            		reviewerList.add(reviewerObj);
            	}
            	return reviewerList;
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
	public ReviewerObject findReviewerById(String user_id) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
          //查询专家信息
            String sqlForReviewer = "select * from reviewer left join user on user.user_id = reviewer.user_id where user.user_id = '"+user_id+"';";
            System.out.println(sqlForReviewer);
            resultSet = statement.executeQuery(sqlForReviewer);
        	ReviewerObject reviewerObj = new ReviewerObject();
        	if(resultSet.next()) {
        		reviewerObj.setUser_id(resultSet.getString("user_id"));
        		System.out.println(resultSet.getString("user_id"));
        		reviewerObj.setUser_name(resultSet.getString("user_name"));
        		reviewerObj.setUser_sex(resultSet.getString("user_sex"));
        		reviewerObj.setUser_mail(resultSet.getString("user_mail"));
        		reviewerObj.setUser_phone(resultSet.getString("user_phone"));
        		reviewerObj.setAccount_state(resultSet.getString("account_state"));
        		reviewerObj.setUser_identity(resultSet.getString("user_identity"));
        		reviewerObj.setUser_pwd(resultSet.getString("user_pwd"));
        		
        		//专家特有信息
        		reviewerObj.setTeacher_bachelor(resultSet.getString("teacher_bachelor"));
        		reviewerObj.setTeacher_degree(resultSet.getString("teacher_degree"));
        		reviewerObj.setTeacher_major(resultSet.getString("teacher_major"));
        		System.out.println(resultSet.getString("teacher_major"));
        		
        		reviewerObj.setCompany_name(resultSet.getString("company_name"));
        		reviewerObj.setCompany_phone(resultSet.getString("company_phone"));
        		reviewerObj.setCompany_principal(resultSet.getString("company_principal"));
        		reviewerObj.setCompany_address(resultSet.getString("company_address"));
        		return reviewerObj;
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
	 
	//删除专家
	public boolean delReviewer(String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String delsql = "delete user,reviewer from user left join reviewer on user.user_id = reviewer.user_id where user.user_id = '" +userId+"';";
            int row = statement.executeUpdate(delsql);
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
	public int updateReviewer(ReviewerObject reviewerObj) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		PreparedStatement stat1 = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //UPDATE goods g INNER JOIN goods_price p on g.id=p.goods_id set p.price=p.price*0.5,g.deleted_at=unix_timestamp(now()) where g.is_deleted_at is null;
            String updsql1 = "update user set user_name = ?,user_sex = ?,user_mail = ?,"
            		+ "user_phone = ?,account_state = ?,user_identity = ? where user_id = ?";
            stat=conn.prepareStatement(updsql1);
            System.out.println(reviewerObj.getUser_sex());
            stat.setString(1, reviewerObj.getUser_name());
            stat.setString(2, reviewerObj.getUser_sex());
            stat.setString(3, reviewerObj.getUser_mail());
            stat.setString(4, reviewerObj.getUser_phone());
            stat.setString(5, reviewerObj.getAccount_state());
            stat.setString(6, reviewerObj.getUser_identity());
            stat.setString(7, reviewerObj.getUser_id());
            int row1 = stat.executeUpdate();
            
            String updsql2 = "update reviewer set user_name = ?,"
            		+ "teacher_bachelor = ?,teacher_degree = ?,teacher_major = ?,"
            		+ "company_name = ?,company_phone = ?,company_principal = ?,company_address = ? where user_id = ?";
            stat1=conn.prepareStatement(updsql2);
            stat1.setString(1, reviewerObj.getUser_name());
            stat1.setString(2, reviewerObj.getTeacher_bachelor());
            stat1.setString(3, reviewerObj.getTeacher_degree());
            stat1.setString(4, reviewerObj.getTeacher_major());
            stat1.setString(5, reviewerObj.getCompany_name());
            stat1.setString(6, reviewerObj.getCompany_phone());
            stat1.setString(7, reviewerObj.getCompany_principal());
            stat1.setString(8, reviewerObj.getCompany_address());
            stat1.setString(9, reviewerObj.getUser_id());
            int row2 = stat1.executeUpdate();
            //成功更新
            if(row1 > 0 && row2 >0) {
            		return 3;
            }
            //user表成功更新
            else if(row1 > 0){
            		return 1;
            }
            //reviewer表成功更新
            else if(row2 > 0) {
            		return 2;
            }
            //两张表都更新失败
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
	public boolean rePassword(String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String resql = "update user set user_pwd = '123456' where user_id = ?";
            stat=conn.prepareStatement(resql);
            stat.setString(1, userId);
            int row = stat.executeUpdate();
            if(row >0) {
            		return true;
            }else {
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
	public boolean reStates(String userId, String state) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String resql = "update user set account_state = ? where user_id = ?";
            
            stat=conn.prepareStatement(resql);
            stat.setString(1, state);
            stat.setString(2, userId);
            System.out.println(resql);
            int row = stat.executeUpdate();
            if(row >0) {
            		return true;
            }else {
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

}
