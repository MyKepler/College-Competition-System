package com.yhcj.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.Project;
import com.yhcj.Dao.ProjectInfo;
import com.yhcj.enity.ProAndReviewObject;
import com.yhcj.enity.ProRevObject;
import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectInfoObject;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.UserObject;
import com.yhcj.utils.DBUtil;

public class ProjectInfoImpl extends DBUtil implements ProjectInfo {

	@Override
	public int getCount() {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String sqlForAllProInfo = "select count(*) from project";
            System.out.println(sqlForAllProInfo);
            resultSet = statement.executeQuery(sqlForAllProInfo);
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
	public List<ProjectInfoObject> findAllProInfo(String pageNum, String pageSize) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		Statement statementLeader = null;
		Statement statementTea = null;
		ResultSet resultSet = null;
		ResultSet resultSetLeader = null;
		ResultSet resultSetTea = null;
//		System.out.println(proId);
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
           
            String sqlForAllPro = "select * from project limit "+(numer-1)*size+","+size+";";
//            System.out.println(sqlForAllPro);
            resultSet = statement.executeQuery(sqlForAllPro);
            if(resultSet != null) {
//            	System.out.println(sqlForAllPro);
            	List<ProjectInfoObject> proInfoList = new ArrayList<ProjectInfoObject>();
            	while(resultSet.next()) {
            		System.out.println(sqlForAllPro);
            		ProjectObject proObj = new ProjectObject();
					UserObject teaObj = new UserObject();
					UserObject leaderObj = new UserObject();
            		
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
        			proObj.setPro_state(resultSet.getString("project_state"));
        			
        			// 负责人信息
        			statementLeader = conn.createStatement();
					String leaderId = proObj.getPro_principal_id();
					String sqlLeader = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  leaderId + "';";
					resultSetLeader = statementLeader.executeQuery(sqlLeader);
					if(resultSetLeader.next()) {
						// 负责人信息
						leaderObj.setUser_id(resultSetLeader.getString("user_id"));
						leaderObj.setUser_name(resultSetLeader.getString("user_name"));
						leaderObj.setUser_mail(resultSetLeader.getString("user_mail"));
						leaderObj.setUser_phone(resultSetLeader.getString("user_phone"));
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
					//项目学生老师对象
					ProjectInfoObject proInfo = new ProjectInfoObject();
					proInfo.setProObj(proObj);
					proInfo.setLeaderObj(leaderObj);
					proInfo.setTeaObj(teaObj);
					proInfoList.add(proInfo);
            	}
            	return proInfoList;
				
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
			closeConn(resultSetTea,statementTea, conn);
		}
	}
	
	@Override
	public ProjectInfoObject findProInfoById(String proId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		Statement statementLeader = null;
		Statement statementTea = null;
		Statement statementProAndRev = null;
		Statement statementRev = null;
		
		ResultSet resultSet = null;
		ResultSet resultSetLeader = null;
		ResultSet resultSetTea = null;
		ResultSet resultSetProAndRev = null;
		ResultSet resultSetRev = null;
		
		
		System.out.println(proId);
		try {
			conn=getConn();
            statement = conn.createStatement();
          
            ProjectObject proObj = new ProjectObject();
            UserObject leaderObj = new UserObject();
			UserObject teaObj = new UserObject();
            ProAndReviewObject reviewObj = new ProAndReviewObject();
            
            // 项目
            String sqlPro = "select * from project where project_id = '" +  proId + "';";
            System.out.println(sqlPro);
            resultSet = statement.executeQuery(sqlPro);
			if(resultSet.next()) {
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

				// 教师
				statementTea = conn.createStatement();
				String teaId = proObj.getPro_teacher_id();
				String sqlTea = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  teaId + "';";
				resultSetTea = statementTea.executeQuery(sqlTea);
				if(resultSetTea.next()) {
					teaObj.setUser_id(resultSetTea.getString("user_id"));
					teaObj.setUser_name(resultSetTea.getString("user_name"));
					teaObj.setUser_mail(resultSetTea.getString("user_mail"));
					teaObj.setUser_phone(resultSetTea.getString("user_phone"));
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
				proInfo.setTeaObj(teaObj);
				proInfo.setReviewObj(reviewObj);
				return proInfo;
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
			closeConn(resultSetTea,statementTea, conn);
			closeConn(resultSetProAndRev,statementProAndRev,conn);
			closeConn(resultSetRev,statementRev,conn);
		}
	}

	@Override
	public ProAndReviewObject findRevMsgInProInfo(String proId,String proStatus) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		Statement statementRev = null;
		ResultSet resultSetRev = null;
		
		try {
			conn=getConn();
			ProAndReviewObject reviewObj = new ProAndReviewObject();
            statement = conn.createStatement();
            //查询学生信息
            String sqlForRevMsg = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  proStatus + "';";
            System.out.println(sqlForRevMsg);
            resultSet = statement.executeQuery(sqlForRevMsg);
            if(resultSet.next()) {
            	reviewObj.setId(resultSet.getString("id"));
				reviewObj.setReview_code(resultSet.getString("review_code"));
				reviewObj.setReview_msg(resultSet.getString("review_msg"));
				reviewObj.setReview_user_id(resultSet.getString("review_user_id"));
				statementRev = conn.createStatement();
				String reviewerId = reviewObj.getReview_user_id();
				String sqlRev = "select user_name from user where user_id = '" +  reviewerId + "';";
				resultSetRev = statementRev.executeQuery(sqlRev);
				if(resultSetRev.next()) {
					reviewObj.setReview_user_name(resultSetRev.getString("user_name"));
				}
				return reviewObj;
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
			closeConn(resultSetRev,statementRev,conn);
		}
		
	}
	
	@Override
	public int updateProInfo(ProjectObject proObj) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //UPDATE goods g INNER JOIN goods_price p on g.id=p.goods_id set p.price=p.price*0.5,g.deleted_at=unix_timestamp(now()) where g.is_deleted_at is null;
            String updsql = "update project set project_name = ?,finish_year = ?,plan_number = ?,"
            		+ "start_year = ?,project_principal_id = ?,project_teacher_id = ? where project_id = ?";
            stat=conn.prepareStatement(updsql);
            stat.setString(1, proObj.getPro_name());
            stat.setString(2, proObj.getFinish_year());
            stat.setString(3, proObj.getPlan_num());
            stat.setString(4, proObj.getStart_year());
            stat.setString(5, proObj.getPro_principal_id());
            stat.setString(6, proObj.getPro_teacher_id());
            stat.setString(7, proObj.getPro_id());
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

	@Override
	public boolean reProInfoState(String proId, String state) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //UPDATE goods g INNER JOIN goods_price p on g.id=p.goods_id set p.price=p.price*0.5,g.deleted_at=unix_timestamp(now()) where g.is_deleted_at is null;
            String updsql = "update project set project_state = ? where project_id = ?";
            stat=conn.prepareStatement(updsql);
            stat.setString(1, state);
            stat.setString(2, proId);
            int row = stat.executeUpdate();
            if(row > 0) {
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

	

	/*--------------------------------------------------教师端------------------------------------------------------*/
	
	//列出该教师指导的所有项目
	@Override
	public List<ProjectInfoObject> findAllProInfoByTea(String pageNum, String pageSize, String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		Statement statementLeader = null;
		Statement statementTea = null;
		ResultSet resultSet = null;
		ResultSet resultSetLeader = null;
		ResultSet resultSetTea = null;
//		System.out.println(proId);
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
           
            String sqlForAllPro = "select * from project where project_teacher_id =  '" +  userId + "' "
            		+ "limit "+(numer-1)*size+","+size+";";
//            System.out.println(sqlForAllPro);
            resultSet = statement.executeQuery(sqlForAllPro);
            if(resultSet != null) {
//            	System.out.println(sqlForAllPro);
            	List<ProjectInfoObject> proInfoList = new ArrayList<ProjectInfoObject>();
            	while(resultSet.next()) {
            		System.out.println(sqlForAllPro);
            		ProjectObject proObj = new ProjectObject();
					UserObject teaObj = new UserObject();
					UserObject leaderObj = new UserObject();
            		
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
        			proObj.setPro_state(resultSet.getString("project_state"));
        			
        			// 负责人信息
        			statementLeader = conn.createStatement();
					String leaderId = proObj.getPro_principal_id();
					String sqlLeader = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  leaderId + "';";
					resultSetLeader = statementLeader.executeQuery(sqlLeader);
					if(resultSetLeader.next()) {
						// 负责人信息
						leaderObj.setUser_id(resultSetLeader.getString("user_id"));
						leaderObj.setUser_name(resultSetLeader.getString("user_name"));
						leaderObj.setUser_mail(resultSetLeader.getString("user_mail"));
						leaderObj.setUser_phone(resultSetLeader.getString("user_phone"));
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
					//项目学生老师对象
					ProjectInfoObject proInfo = new ProjectInfoObject();
					proInfo.setProObj(proObj);
					proInfo.setLeaderObj(leaderObj);
					proInfo.setTeaObj(teaObj);
					proInfoList.add(proInfo);
            	}
            	return proInfoList;
				
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
			closeConn(resultSetTea,statementTea, conn);
		}
	}

	//统计该教师指导的所有项目的总数
	@Override
	public int getTeaCount(String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //查询学生信息
            String sqlForAllProInfo = "select count(*) from project where project_teacher_id =  '" +  userId + "';";
            System.out.println(sqlForAllProInfo);
            resultSet = statement.executeQuery(sqlForAllProInfo);
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
	// 列出所有该教师指导的项目（可用）——文件管理
		public List<ProjectInfoObject> findAllProFileByTea(String pageNum,String pageSize,String userId){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			Statement statementLeader = null;
			ResultSet resultSetLeader = null;
			Statement statementFile = null;
			ResultSet resultSetFile = null;
			int numer = Integer.parseInt(pageNum);
			int size	 = Integer.parseInt(pageSize);
			
			try {
				conn=getConn();
	            statement = conn.createStatement();
	            //查询学生信息
	            String sqlForAllPro = "select * from project where project_state = '可用' "
	            		+ "and project_teacher_id =  '" +  userId + "' limit "+(numer-1)*size+","+size+";";
	            System.out.println(sqlForAllPro);
	            resultSet = statement.executeQuery(sqlForAllPro);
	            if(resultSet != null) {
	            		List<ProjectInfoObject> proList = new ArrayList<ProjectInfoObject>();
	            		while(resultSet.next()) {
	            			ProjectObject proObj = new ProjectObject();
	    					UserObject leaderObj = new UserObject();
	    					
//		            			// 项目信息
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
	            			proObj.setPro_state(resultSet.getString("project_state"));
	            			//项目文件问题
	            			//查询所有符合条件的文件
	            			statementFile = conn.createStatement();
	            			String proId = proObj.getPro_id();
	    					String sqlForQueryFiles = "select * from project_files where project_id = '"+proId+"';";
	    					resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
	    					if(resultSetFile.next()) {
	    						proObj.setIs_submit("已提交");
	    					}else {
	    						proObj.setIs_submit("未提交");
	    					}
	            			// 负责人信息
	            			statementLeader = conn.createStatement();
	    					String leaderId = proObj.getPro_principal_id();
	    					String sqlLeader = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  leaderId + "';";
	    					resultSetLeader = statementLeader.executeQuery(sqlLeader);
	    					if(resultSetLeader.next()) {
	    						// 负责人信息
	    						leaderObj.setUser_id(resultSetLeader.getString("user_id"));
	    						leaderObj.setUser_name(resultSetLeader.getString("user_name"));
	    						leaderObj.setUser_mail(resultSetLeader.getString("user_mail"));
	    						leaderObj.setUser_phone(resultSetLeader.getString("user_phone"));
	    					}
	            			ProjectInfoObject proInfoObj = new ProjectInfoObject();
	            			proInfoObj.setLeaderObj(leaderObj);
	            			proInfoObj.setProObj(proObj);
	            			proList.add(proInfoObj);
	            		}
	            		return proList;
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

		public int getProFileCount(String userId) {
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				conn=getConn();
	            statement = conn.createStatement();
	            //查询学生信息
	            String sqlForAllProInfo = "select count(*) from project where project_state = '可用' and project_teacher_id =  '" +  userId + "';";
	            System.out.println(sqlForAllProInfo);
	            resultSet = statement.executeQuery(sqlForAllProInfo);
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
	/*--------------------------------------------------学生端（查看）------------------------------------------------------*/
	

	@Override
	public int getCount(String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select count(*) from project left join project_student on project.project_id=project_student.project_id where  is_in_service='可用' and user_id='"+userId+"';";
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
	public List<ProStuTeaAndRev> findAllStuProInfo(String pageNum, String pageSize,String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		Statement statementStu=null;
		ResultSet resultSetStu = null;
		Statement statementTea=null;
		ResultSet resultSetTea = null;
		Statement statementRev=null;
		ResultSet resultSetRev = null;
		Statement statementRev2=null;
		ResultSet resultSetRev2= null;
		int numer = Integer.parseInt(pageNum);
		int size = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sql = "select * from project left join project_student on project.project_id=project_student.project_id where is_in_service='可用' and user_id='"+userId+"' limit "+(numer-1)*size+","+size+";";
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
            if(resultSet != null) {
            		List<ProStuTeaAndRev> proList = new ArrayList<ProStuTeaAndRev>();
            		while(resultSet.next()) {
            			ProjectObject proObj = new ProjectObject();
            			ProRevObject prorevObj = new ProRevObject();
        				UserObject stuObj = new UserObject();
        				UserObject teaObj = new UserObject();
        				
        				//项目
        				proObj.setPro_id(resultSet.getString("project_id"));
        				proObj.setPro_status(resultSet.getString("project_status"));
            			proObj.setPro_name(resultSet.getString("project_name"));
            			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
            			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
            			
            			
            			Project findActNum = new ProjectImpl();
            			int num = findActNum.projectActNumber(proObj.getPro_id());
            			proObj.setAct_num(num+"");
            			System.out.println("实际人数"+ proObj.getAct_num());
            			
            			
            			proObj.setPlan_num(resultSet.getString("plan_number"));
            			proObj.setStart_year(resultSet.getString("start_year"));
            			proObj.setFinish_year(resultSet.getString("finish_year"));
            			proObj.setPro_state(resultSet.getString("project_state"));
            			
        				// 学生
            			statementStu = conn.createStatement();
        				String stuId = resultSet.getString("project_principal_id");
        				System.out.println(stuId+"徐欣奕1");
        				String sqlStu = "select * from user where user_id = '" +  stuId + "';";
        				resultSetStu = statementStu.executeQuery(sqlStu);
        				resultSetStu.next();
            			stuObj.setUser_name(resultSetStu.getString("user_name"));
            			stuObj.setUser_mail(resultSetStu.getString("user_mail"));
            			stuObj.setUser_phone(resultSetStu.getString("user_phone"));
            			
        				// 教师
        				statementTea = conn.createStatement();
        				String teaId = resultSet.getString("project_teacher_id");
        				System.out.println(teaId+"教师");
        				String sqlTea = "select * from user where user_id = '" +  teaId + "';";
        				resultSetTea = statementTea.executeQuery(sqlTea);
            			resultSetTea.next();
            			teaObj.setUser_name(resultSetTea.getString("user_name"));
            			teaObj.setUser_mail(resultSetTea.getString("user_mail"));
            			teaObj.setUser_phone(resultSetTea.getString("user_phone"));
            			
        				// 评审
            			statementRev = conn.createStatement();
        				String proId=proObj.getPro_id();
        				String proStatus = proObj.getPro_status();
        				String sqlProAndRev = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  proStatus + "';";
        				resultSetRev = statementRev.executeQuery(sqlProAndRev);
        				//评审人
        				if(resultSetRev.next()) {
        					prorevObj.setId(resultSetRev.getString("id"));
        					prorevObj.setReview_code(resultSetRev.getString("review_code"));
        					prorevObj.setReview_msg(resultSetRev.getString("review_msg"));
        					prorevObj.setReview_user_id(resultSetRev.getString("review_user_id"));
        					statementRev2 = conn.createStatement();
        					String reviewerId = prorevObj.getReview_user_id();
        					String sqlRev = "select * from user where user_id = '" +  reviewerId + "';";
        					resultSetRev2 = statementRev2.executeQuery(sqlRev);
        					if(resultSetRev2.next()) {
        						prorevObj.setReview_user_name(resultSetRev2.getString("user_name"));
        					}
        				}
            			
            			ProStuTeaAndRev pstr=new ProStuTeaAndRev();
            			pstr.setProrevObj(prorevObj);
            			pstr.setProObj(proObj);
            			pstr.setStuObj(stuObj);
            			pstr.setTeaObj(teaObj);
            			proList.add(pstr);
            		}
            		return proList;
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

	/*--------------------------------------------------学生端（管理）------------------------------------------------------*/


	@Override
	public int getCountManage(String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select count(*) from project where project_principal_id='"+userId+"';";
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
	public List<ProStuTeaAndRev> findAllStuProManage(String pageNum, String pageSize, String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		Statement statementStu=null;
		ResultSet resultSetStu = null;
		Statement statementTea=null;
		ResultSet resultSetTea = null;
		Statement statementRev=null;
		ResultSet resultSetRev = null;
		Statement statementRev2=null;
		ResultSet resultSetRev2= null;
		int numer = Integer.parseInt(pageNum);
		int size = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sql = "select * from project where project_principal_id='"+userId+"' limit "+(numer-1)*size+","+size+";";
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
            if(resultSet != null) {
            		List<ProStuTeaAndRev> proList = new ArrayList<ProStuTeaAndRev>();
            		while(resultSet.next()) {
            			ProjectObject proObj = new ProjectObject();
            			ProRevObject prorevObj = new ProRevObject();
        				UserObject stuObj = new UserObject();
        				UserObject teaObj = new UserObject();
        				
        				//项目
        				proObj.setPro_id(resultSet.getString("project_id"));
        				proObj.setPro_status(resultSet.getString("project_status"));
            			proObj.setPro_name(resultSet.getString("project_name"));
            			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
            			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
            			
            			Project findActNum = new ProjectImpl();
            			int num = findActNum.projectActNumber(proObj.getPro_id());
            			proObj.setAct_num(num+"");
            			System.out.println("实际人数"+ proObj.getAct_num());
            			
            			proObj.setPlan_num(resultSet.getString("plan_number"));
            			proObj.setStart_year(resultSet.getString("start_year"));
            			proObj.setFinish_year(resultSet.getString("finish_year"));
            			proObj.setPro_state(resultSet.getString("project_state"));
            			
        				// 学生
            			statementStu = conn.createStatement();
        				String stuId = resultSet.getString("project_principal_id");
        				System.out.println(stuId+"徐欣奕1");
        				String sqlStu = "select * from user where user_id = '" +  stuId + "';";
        				resultSetStu = statementStu.executeQuery(sqlStu);
        				resultSetStu.next();
            			stuObj.setUser_name(resultSetStu.getString("user_name"));
            			stuObj.setUser_mail(resultSetStu.getString("user_mail"));
            			stuObj.setUser_phone(resultSetStu.getString("user_phone"));
            			
        				// 教师
        				statementTea = conn.createStatement();
        				String teaId = resultSet.getString("project_teacher_id");
        				System.out.println(teaId+"教师");
        				String sqlTea = "select * from user where user_id = '" +  teaId + "';";
        				resultSetTea = statementTea.executeQuery(sqlTea);
            			resultSetTea.next();
            			teaObj.setUser_name(resultSetTea.getString("user_name"));
            			teaObj.setUser_mail(resultSetTea.getString("user_mail"));
            			teaObj.setUser_phone(resultSetTea.getString("user_phone"));
            			
        				// 评审
            			statementRev = conn.createStatement();
        				String proId=proObj.getPro_id();
        				String proStatus = proObj.getPro_status();
        				String sqlProAndRev = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  proStatus + "';";
        				resultSetRev = statementRev.executeQuery(sqlProAndRev);
        				//评审人
        				if(resultSetRev.next()) {
        					prorevObj.setId(resultSetRev.getString("id"));
        					prorevObj.setReview_code(resultSetRev.getString("review_code"));
        					prorevObj.setReview_msg(resultSetRev.getString("review_msg"));
        					prorevObj.setReview_user_id(resultSetRev.getString("review_user_id"));
        					statementRev2 = conn.createStatement();
        					String reviewerId = prorevObj.getReview_user_id();
        					String sqlRev = "select * from user where user_id = '" +  reviewerId + "';";
        					resultSetRev2 = statementRev2.executeQuery(sqlRev);
        					if(resultSetRev2.next()) {
        						prorevObj.setReview_user_name(resultSetRev2.getString("user_name"));
        					}
        				}
            			
            			ProStuTeaAndRev pstr=new ProStuTeaAndRev();
            			pstr.setProrevObj(prorevObj);
            			pstr.setProObj(proObj);
            			pstr.setStuObj(stuObj);
            			pstr.setTeaObj(teaObj);
            			proList.add(pstr);
            		}
            		return proList;
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
	public ProStuTeaAndRev findSpeStuProManage(String proId) {
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
            String sqlForAllPro = "select * from project left join project_review on project.project_id=project_review.project_id where project.project_id = '" + proId + "';";
            resultSet = statement.executeQuery(sqlForAllPro);
            System.out.println(sqlForAllPro);
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
      			proObj.setFinish_year(resultSet.getString("finish_year"));
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
				String sqlProAndRev = "select * from project_review where project_id = '" + proId + "' and review_type = '" +  proStatus + "';";
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
	public int updateStuProManage(ProjectObject proObj) {
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
            String updsql = "update project set project_status = ?,project_principal_id = ?,finish_year = ?,plan_number=? where project_id = ?";
            stat=conn.prepareStatement(updsql);
            stat.setString(1, proObj.getPro_status());
            stat.setString(2, proObj.getPro_principal_id());
            stat.setString(3, proObj.getFinish_year());
            stat.setString(4, proObj.getPlan_num());
            stat.setString(5, proObj.getPro_id());
            int row = stat.executeUpdate();
            System.out.println(proObj.getPro_status());
            System.out.println(proObj.getPro_principal_id());
            System.out.println(proObj.getFinish_year());
            System.out.println(proObj.getPlan_num());
            System.out.println(proObj.getPro_id());
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
	@Override
	public int getCountFile(String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select count(*) from project where project_state = '可用' and project_principal_id='"+userId+"';";
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
	public List<ProStuTeaAndRev> findAllStuFileManage(String pageNum, String pageSize, String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		Statement statementStu=null;
		ResultSet resultSetStu = null;
		Statement statementTea=null;
		ResultSet resultSetTea = null;
		Statement statementRev=null;
		ResultSet resultSetRev = null;
		Statement statementRev2=null;
		ResultSet resultSetRev2= null;
		Statement statementFile = null;
		ResultSet resultSetFile = null;
		int numer = Integer.parseInt(pageNum);
		int size = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sql = "select * from project where project_state = '可用' and project_principal_id='"+userId+"' limit "+(numer-1)*size+","+size+";";
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
            if(resultSet != null) {
            		List<ProStuTeaAndRev> proList = new ArrayList<ProStuTeaAndRev>();
            		while(resultSet.next()) {
            			ProjectObject proObj = new ProjectObject();
            			ProRevObject prorevObj = new ProRevObject();
        				UserObject stuObj = new UserObject();
        				UserObject teaObj = new UserObject();
        				
        				//项目
        				proObj.setPro_id(resultSet.getString("project_id"));
        				proObj.setPro_status(resultSet.getString("project_status"));
            			proObj.setPro_name(resultSet.getString("project_name"));
            			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
            			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
            			
            			Project findActNum = new ProjectImpl();
            			int num = findActNum.projectActNumber(proObj.getPro_id());
            			proObj.setAct_num(num+"");
            			System.out.println("实际人数"+ proObj.getAct_num());
            			
            			proObj.setPlan_num(resultSet.getString("plan_number"));
            			proObj.setStart_year(resultSet.getString("start_year"));
            			proObj.setFinish_year(resultSet.getString("finish_year"));
            			proObj.setPro_state(resultSet.getString("project_state"));
            			//proObj.setIs_submit("提交");
            			statementFile = conn.createStatement();
            			String proId = proObj.getPro_id();
    					String sqlForQueryFiles = "select * from project_files where project_id = '"+proId+"';";
    					resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
    					if(resultSetFile.next()) {
    						proObj.setIs_submit("已提交");
    					}else {
    						proObj.setIs_submit("未提交");
    					}
            			
        				// 学生
            			statementStu = conn.createStatement();
        				String stuId = resultSet.getString("project_principal_id");
        				System.out.println(stuId+"徐欣奕1");
        				String sqlStu = "select * from user where user_id = '" +  stuId + "';";
        				resultSetStu = statementStu.executeQuery(sqlStu);
        				resultSetStu.next();
            			stuObj.setUser_name(resultSetStu.getString("user_name"));
            			stuObj.setUser_mail(resultSetStu.getString("user_mail"));
            			stuObj.setUser_phone(resultSetStu.getString("user_phone"));
            			
        				// 教师
        				statementTea = conn.createStatement();
        				String teaId = resultSet.getString("project_teacher_id");
        				System.out.println(teaId+"教师");
        				String sqlTea = "select * from user where user_id = '" +  teaId + "';";
        				resultSetTea = statementTea.executeQuery(sqlTea);
            			resultSetTea.next();
            			teaObj.setUser_name(resultSetTea.getString("user_name"));
            			teaObj.setUser_mail(resultSetTea.getString("user_mail"));
            			teaObj.setUser_phone(resultSetTea.getString("user_phone"));
            			
            			
            			ProStuTeaAndRev pstr=new ProStuTeaAndRev();
            			pstr.setProObj(proObj);
            			pstr.setStuObj(stuObj);
            			pstr.setTeaObj(teaObj);
            			proList.add(pstr);
            		}
            		return proList;
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
	

}
