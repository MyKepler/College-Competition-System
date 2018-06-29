package com.yhcj.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.ReviewTask;
import com.yhcj.enity.ProRevObject;
import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.UserObject;
import com.yhcj.utils.DBUtil;

public class ReviewTaskImpl extends DBUtil implements ReviewTask{

	@Override
	public int getCount(String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select count(*) from project left join project_review on project.project_id = project_review.project_id where review_user_id='"+userId+"';";
            System.out.println(sqlForAllPro);
            resultSet = statement.executeQuery(sqlForAllPro);
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
	public List<ProStuTeaAndRev> findReviewTask(String pageNum, String pageSize, String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		Statement statementStu=null;
		ResultSet resultSetStu = null;
		Statement statementTea=null;
		ResultSet resultSetTea = null;
		Statement statementObjRev=null;
		ResultSet resultSetObjRev = null;
		Statement statementRev=null;
		ResultSet resultSetRev = null;
		int numer = Integer.parseInt(pageNum);
		int size = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select * from project left join project_review on project.project_id = project_review.project_id where project_status=review_type and review_user_id='"+userId+"' limit "+(numer-1)*size+","+size+";";
            resultSet = statement.executeQuery(sqlForAllPro);
            if(resultSet != null) {
          	List<ProStuTeaAndRev> proInfoList = new ArrayList<ProStuTeaAndRev>();
          	while(resultSet.next()) {
          		ProjectObject proObj = new ProjectObject();
				UserObject teaObj = new UserObject();
				UserObject leaderObj = new UserObject();
				ProRevObject prorevObj=new ProRevObject();
          		
          		// 项目信息
      			proObj.setPro_id(resultSet.getString("project_id"));
      			proObj.setPro_name(resultSet.getString("project_name"));
      			proObj.setPro_status(resultSet.getString("project_status"));
			
      			Project findActNum = new ProjectImpl();
      			int num = findActNum.projectActNumber(proObj.getPro_id());
      			proObj.setAct_num(num+"");
      			System.out.println("实际人数"+ proObj.getAct_num());
      			
      			proObj.setStart_year(resultSet.getString("start_year"));
				proObj.setPlan_num(resultSet.getString("plan_number"));
      			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
      			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
      			
      			// 负责人信息
      			statementStu = conn.createStatement();
				String leaderId = proObj.getPro_principal_id();
				String sqlLeader = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  leaderId + "';";
				resultSetStu = statementStu.executeQuery(sqlLeader);
				if(resultSetStu.next()) {
					// 负责人信息
					leaderObj.setUser_id(resultSetStu.getString("user_id"));
					leaderObj.setUser_name(resultSetStu.getString("user_name"));
					leaderObj.setUser_mail(resultSetStu.getString("user_mail"));
					leaderObj.setUser_phone(resultSetStu.getString("user_phone"));
				}
				
				statementTea = conn.createStatement();
				// 教师信息
				String teaId = proObj.getPro_teacher_id();
				String sqlTea = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  teaId + "';";
				resultSetTea = statementTea.executeQuery(sqlTea);
				if(resultSetTea.next()) {
					// 负责人信息
					teaObj.setUser_id(resultSetTea.getString("user_id"));
					teaObj.setUser_name(resultSetTea.getString("user_name"));
					teaObj.setUser_mail(resultSetTea.getString("user_mail"));
					teaObj.setUser_phone(resultSetTea.getString("user_phone"));
				}
				
				//项目评审
				statementObjRev = conn.createStatement();
				String proId=proObj.getPro_id();
				String proStatus = proObj.getPro_status();
				String sqlProAndRev = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  proStatus + "';";
				resultSetObjRev = statementObjRev.executeQuery(sqlProAndRev);
				
				//评审人
				if(resultSetObjRev.next()) {
					prorevObj.setId(resultSetObjRev.getString("id"));
					prorevObj.setReview_code(resultSetObjRev.getString("review_code"));
					prorevObj.setReview_msg(resultSetObjRev.getString("review_msg"));
					prorevObj.setReview_user_id(resultSetObjRev.getString("review_user_id"));
					statementRev = conn.createStatement();
					String reviewerId = prorevObj.getReview_user_id();
					String sqlRev = "select user_name from user where user_id = '" +  reviewerId + "';";
					resultSetRev = statementRev.executeQuery(sqlRev);
					if(resultSetRev.next()) {
						prorevObj.setReview_user_name(resultSetRev.getString("user_name"));
					}
				}
				//项目学生老师对象
				ProStuTeaAndRev pstr = new ProStuTeaAndRev();
				pstr.setProObj(proObj);
				pstr.setStuObj(leaderObj);
				pstr.setTeaObj(teaObj);
				pstr.setProrevObj(prorevObj);
				proInfoList.add(pstr);
          	}
          	return proInfoList;
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
	public ProStuTeaAndRev findSpeReviewTask(String proId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		Statement statementStu=null;
		ResultSet resultSetStu = null;
		Statement statementTea=null;
		ResultSet resultSetTea = null;
		Statement statementObjRev=null;
		ResultSet resultSetObjRev = null;
		Statement statementRev=null;
		ResultSet resultSetRev = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select * from project where project_id = '" +  proId + "';";
            resultSet = statement.executeQuery(sqlForAllPro);
            if(resultSet.next()) {
          		ProjectObject proObj = new ProjectObject();
				UserObject teaObj = new UserObject();
				UserObject leaderObj = new UserObject();
				ProRevObject prorevObj=new ProRevObject();
          		
          		// 项目信息
      			proObj.setPro_id(resultSet.getString("project_id"));
      			proObj.setPro_name(resultSet.getString("project_name"));
      			proObj.setPro_status(resultSet.getString("project_status"));
				
      			Project findActNum = new ProjectImpl();
      			int num = findActNum.projectActNumber(proObj.getPro_id());
      			proObj.setAct_num(num+"");
      			System.out.println("实际人数"+ proObj.getAct_num());
      			
      			proObj.setStart_year(resultSet.getString("start_year"));
				proObj.setPlan_num(resultSet.getString("plan_number"));
      			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
      			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
      			
      			// 负责人信息
      			statementStu = conn.createStatement();
				String leaderId = proObj.getPro_principal_id();
				String sqlLeader = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  leaderId + "';";
				resultSetStu = statementStu.executeQuery(sqlLeader);
				if(resultSetStu.next()) {
					// 负责人信息
					leaderObj.setUser_id(resultSetStu.getString("user_id"));
					leaderObj.setUser_name(resultSetStu.getString("user_name"));
					leaderObj.setUser_mail(resultSetStu.getString("user_mail"));
					leaderObj.setUser_phone(resultSetStu.getString("user_phone"));
				}
				
				statementTea = conn.createStatement();
				// 教师信息
				String teaId = proObj.getPro_teacher_id();
				String sqlTea = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  teaId + "';";
				resultSetTea = statementTea.executeQuery(sqlTea);
				if(resultSetTea.next()) {
					// 负责人信息
					teaObj.setUser_id(resultSetTea.getString("user_id"));
					teaObj.setUser_name(resultSetTea.getString("user_name"));
					teaObj.setUser_mail(resultSetTea.getString("user_mail"));
					teaObj.setUser_phone(resultSetTea.getString("user_phone"));
				}
				
				//项目评审
				statementObjRev = conn.createStatement();
				String proStatus = proObj.getPro_status();
				String sqlProAndRev = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  proStatus + "';";
				resultSetObjRev = statementObjRev.executeQuery(sqlProAndRev);
				
				//评审人
				if(resultSetObjRev.next()) {
					prorevObj.setId(resultSetObjRev.getString("id"));
					prorevObj.setReview_code(resultSetObjRev.getString("review_code"));
					prorevObj.setReview_msg(resultSetObjRev.getString("review_msg"));
					prorevObj.setReview_user_id(resultSetObjRev.getString("review_user_id"));
					statementRev = conn.createStatement();
					String reviewerId = prorevObj.getReview_user_id();
					String sqlRev = "select user_name from user where user_id = '" +  reviewerId + "';";
					resultSetRev = statementRev.executeQuery(sqlRev);
					if(resultSetRev.next()) {
						prorevObj.setReview_user_name(resultSetRev.getString("user_name"));
					}
				}
				//项目学生老师评审对象
				ProStuTeaAndRev pstr = new ProStuTeaAndRev();
				pstr.setProObj(proObj);
				pstr.setStuObj(leaderObj);
				pstr.setTeaObj(teaObj);
				pstr.setProrevObj(prorevObj);
				
          	return pstr;
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
	public int updateReviewTask(ProRevObject prorevObj) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		Statement userstatement = null;
		ResultSet userresultSet = null;
		PreparedStatement userstat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String updsql = "update project_review set review_msg = ?,review_code = ? where project_id = ? and review_type=?";
            stat=conn.prepareStatement(updsql);
            stat.setString(1, prorevObj.getReview_msg());
            stat.setString(2, prorevObj.getReview_code());
            stat.setString(3, prorevObj.getProject_id());
            stat.setString(4, prorevObj.getReview_type());
            System.out.println(prorevObj.getReview_code());
            int row = stat.executeUpdate();
            if(row > 0) {
            		return 1;
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

}
