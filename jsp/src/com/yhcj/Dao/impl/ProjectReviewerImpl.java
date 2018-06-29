package com.yhcj.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.Project;
import com.yhcj.Dao.ProjectReviewer;
import com.yhcj.enity.EducationObject;
import com.yhcj.enity.ProAndReviewObject;
import com.yhcj.enity.ProjectInfoObject;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.UserObject;
import com.yhcj.utils.DBUtil;

public class ProjectReviewerImpl extends DBUtil implements ProjectReviewer {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		Statement statementPro = null;
		ResultSet resultSetPro = null;
		int count = 0;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String sqlPro = "select distinct project_id from project_review ;";
            System.out.println(sqlPro);
            resultSet = statement.executeQuery(sqlPro);
            if(resultSet != null) {
				while(resultSet.next()) {
	
					// 项目信息
					statementPro = conn.createStatement();
					String proId = resultSet.getString("project_id");
					System.out.println(proId);
					String sql = "select * from project where project_id = '" +  proId + "';";
					resultSetPro = statementPro.executeQuery(sql);
					if(resultSetPro.next()) {
						if(resultSetPro.getString("project_state").equals("可用")) {
							count++;
						}
					}
					
				}
				System.out.println(count);
				return count;
			}else {
				//用户不存在
				return count;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return count;
		}
		finally{
			closeConn(resultSet,statement, conn);
			closeConn(resultSetPro,statementPro, conn);
		}
	}
	
	@Override
	public List<ProjectInfoObject> findAllProReview(String pageNum, String pageSize) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		Statement statementPro = null;
		Statement statementLeader = null;
//		Statement statementTea = null;
		Statement statementProAndRev = null;
		Statement statementRev = null;
		
		ResultSet resultSet = null;
		ResultSet resultSetPro = null;
		ResultSet resultSetLeader = null;
//		ResultSet resultSetTea = null;
		ResultSet resultSetProAndRev = null;
		ResultSet resultSetRev = null;
		
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		
		try {
			conn=getConn();
            statement = conn.createStatement();
            
            // 项目
            String sqlPro = "select distinct project_id from project_review limit "+(numer-1)*size+","+size+";";
            System.out.println(sqlPro);
            resultSet = statement.executeQuery(sqlPro);
            if(resultSet != null) {
            	List<ProjectInfoObject> proReviewList = new ArrayList<ProjectInfoObject>();
				while(resultSet.next()) {
					ProjectObject proObj = new ProjectObject();
			        UserObject leaderObj = new UserObject();
			        ProAndReviewObject reviewObj = new ProAndReviewObject();
	
					// 项目信息
					statementPro = conn.createStatement();
					String proId = resultSet.getString("project_id");
					System.out.println(proId);
					String sql = "select * from project where project_id = '" +  proId + "';";
					resultSetPro = statementPro.executeQuery(sql);
					if(resultSetPro.next()) {
						if(resultSetPro.getString("project_state").equals("可用")) {
							proObj.setPro_id(resultSetPro.getString("project_id"));
							proObj.setPro_name(resultSetPro.getString("project_name"));
							proObj.setPro_status(resultSetPro.getString("project_status"));
							
							Project findActNum = new ProjectImpl();
							int num = findActNum.projectActNumber(proObj.getPro_id());
							proObj.setAct_num(num+"");
							System.out.println("实际人数"+ proObj.getAct_num());
							
							proObj.setPlan_num(resultSetPro.getString("plan_number"));
							proObj.setStart_year(resultSetPro.getString("start_year"));
							proObj.setFinish_year(resultSetPro.getString("finish_year"));
							proObj.setPro_principal_id(resultSetPro.getString("project_principal_id"));
							proObj.setPro_teacher_id(resultSetPro.getString("project_teacher_id"));
							proObj.setPro_state(resultSetPro.getString("project_state"));
							
							// 负责人
							statementLeader = conn.createStatement();
							String leaderId = proObj.getPro_principal_id();
							String sqlStu = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  leaderId + "';";
							resultSetLeader = statementLeader.executeQuery(sqlStu);
							if(resultSetLeader.next()) {
								leaderObj.setUser_id(resultSetLeader.getString("user_id"));
								leaderObj.setUser_name(resultSetLeader.getString("user_name"));
								leaderObj.setUser_mail(resultSetLeader.getString("user_mail"));
								leaderObj.setUser_phone(resultSetLeader.getString("user_phone"));
							}
								
							//项目评审
							statementProAndRev = conn.createStatement();
							String proStatus = proObj.getPro_status();
							String sqlProAndRev = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  proStatus + "';";
							resultSetProAndRev = statementProAndRev.executeQuery(sqlProAndRev);
							
							//评审人
							if(resultSetProAndRev.next()) {
								reviewObj.setId(resultSetProAndRev.getString("id"));
								reviewObj.setReview_code(resultSetProAndRev.getString("review_code"));
								reviewObj.setReview_msg(resultSetProAndRev.getString("review_msg"));
								reviewObj.setReview_user_id(resultSetProAndRev.getString("review_user_id"));
								
								statementRev = conn.createStatement();
								String reviewerId = reviewObj.getReview_user_id();
								String sqlRev = "select user_name from user where user_id = '" +  reviewerId + "';";
								resultSetRev = statementRev.executeQuery(sqlRev);
								if(resultSetRev.next()) {
									reviewObj.setReview_user_name(resultSetRev.getString("user_name"));
								}
							}
							//项目学生老师评审对象
							ProjectInfoObject proInfo = new ProjectInfoObject();
							proInfo.setProObj(proObj);
							proInfo.setLeaderObj(leaderObj);
//							proInfo.setTeaObj(teaObj);
							proInfo.setReviewObj(reviewObj);
							System.out.println(reviewObj.getReview_user_name());
							proReviewList.add(proInfo);
						}
					}
					
				}
				return proReviewList;
			}else {
				//用户不存在
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
			closeConn(resultSetLeader,statementLeader, conn);
			closeConn(resultSetPro,statementPro, conn);
			closeConn(resultSetProAndRev,statementProAndRev,conn);
			closeConn(resultSetRev,statementRev,conn);
		}
	}

	@Override
	public int isUpdate(String proId, String reviewType) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String sqlForRevMsg = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  reviewType + "';";
            System.out.println(sqlForRevMsg);
            resultSet = statement.executeQuery(sqlForRevMsg);
            if(resultSet.next()) {
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
	public int insertProReview(ProAndReviewObject proAndRevObj) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String sql = "insert into project_review(project_id,review_type,review_user_id) values(?,?,?)";
            System.out.println(sql);
            stat=conn.prepareStatement(sql);
            stat.setString(1, proAndRevObj.getProject_id());
            stat.setString(2, proAndRevObj.getReview_type());
            stat.setString(3, proAndRevObj.getReview_user_id());
            int row = stat.executeUpdate();
            if(row > 0) {
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
	public int UpdateProreview(ProAndReviewObject proAndRevObj) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String updsql = "update project_review set review_msg = ?,review_code = ?,review_user_id = ? where project_id = ? and review_type = ?";
            System.out.println(updsql);
            stat=conn.prepareStatement(updsql);
            stat.setString(1, proAndRevObj.getReview_msg());
            stat.setString(2, proAndRevObj.getReview_code());
            stat.setString(3, proAndRevObj.getReview_user_id());
            stat.setString(4, proAndRevObj.getProject_id());
            stat.setString(5, proAndRevObj.getReview_type());
            int row = stat.executeUpdate();
            if(row > 0) {
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
	public boolean delProReviewMsg(String proId, String reviewType) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String delsql = "delete from project_review where project_id = '" +  proId + "' and review_type = '" +  reviewType + "';";
            System.out.println(delsql);
            int row = statement.executeUpdate(delsql);
            System.out.println(row);
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
	public List<ProjectObject> findAllProject() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<ProjectObject> proList = new ArrayList<ProjectObject>();
		try {
			conn = getConn();
			statement = conn.createStatement();
			//sql语句查询所有可以用的学院
			String sqlForPro = "select * from project where project_state = '可用' ;";
			System.out.println(sqlForPro);
			resultSet = statement.executeQuery(sqlForPro);
			if(resultSet != null) {
				while(resultSet.next()) {
					ProjectObject proObj = new ProjectObject();
					proObj.setPro_id(resultSet.getString("project_id"));
					proObj.setPro_name(resultSet.getString("project_name"));
					proObj.setPro_status(resultSet.getString("project_status"));
					
					Project findActNum = new ProjectImpl();
					int num = findActNum.projectActNumber(proObj.getPro_id());
					proObj.setAct_num(num+"");
					System.out.println("实际人数"+ proObj.getAct_num());
					
					proObj.setPlan_num(resultSet.getString("plan_number"));
					proObj.setStart_year(resultSet.getString("start_year"));
					proObj.setFinish_year(resultSet.getString("finish_year"));
					proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
					proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
					proObj.setPro_state(resultSet.getString("project_state"));
					proList.add(proObj);
				}
				return proList;
			}else {
				// 学历信息为空
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			// 报错
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	

}
