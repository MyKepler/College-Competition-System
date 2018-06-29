package com.yhcj.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.Project;
import com.yhcj.Dao.ProjectAudit;

import com.yhcj.enity.ProjectAuditObject;

import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.RecruitmentSignUpObject;

import com.yhcj.enity.UserObject;
import com.yhcj.utils.DBUtil;

public class ProjectAuditImpl extends DBUtil implements ProjectAudit {

	@Override
	public int getCount() {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			String sqlForAllProAudit = "select count(*) from recruitment_sign_up left join project "
					+ "on recruitment_sign_up.project_id = project.project_id "
					+ "where project.project_state = '可用';";
			System.out.println(sqlForAllProAudit);
			resultSet = statement.executeQuery(sqlForAllProAudit);
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
	public List<ProjectAuditObject> findAllProAudit(String pageNum, String pageSize) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		Statement statementPro = null;
		ResultSet resultSetPro = null;
		
		Statement statementStu = null;
		ResultSet resultSetStu = null;
		
		Statement statementTea = null;
		ResultSet resultSetTea = null;
		
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
           
            String sqlForAllProAudit = "select * from recruitment_sign_up limit "+(numer-1)*size+","+size+";";
            System.out.println(sqlForAllProAudit);
            resultSet = statement.executeQuery(sqlForAllProAudit);
            if(resultSet != null) {
//            	System.out.println(sqlForAllPro);
            	List<ProjectAuditObject> proAuditList = new ArrayList<ProjectAuditObject>();
            	while(resultSet.next()) {
            		RecruitmentSignUpObject signUpObj = new RecruitmentSignUpObject();
            		ProjectObject proObj = new ProjectObject();
            		UserObject teaObj  = new UserObject();
            		
            		//项目报名信息
            		signUpObj.setSign_up_id(resultSet.getString("sign_up_id"));
            		signUpObj.setProject_id(resultSet.getString("project_id"));
            		signUpObj.setUser_id(resultSet.getString("user_id"));
            		signUpObj.setUser_name(resultSet.getString("user_name"));
            		signUpObj.setEmail(resultSet.getString("email"));
            		signUpObj.setPhone_number(resultSet.getString("phone_number"));
            		signUpObj.setSign_up_time(resultSet.getString("sign_up_time"));
            		signUpObj.setState(resultSet.getString("state"));
					
					
					// 项目信息
					statementPro = conn.createStatement();
				String proId = signUpObj.getProject_id();
            		String sqlPro = "select * from project where project_id = '" +  proId + "';";
                    System.out.println(sqlPro);
                    resultSetPro = statementPro.executeQuery(sqlPro);
                    if(resultSetPro.next()) {
                    	if(resultSetPro.getString("project_state").equals("可用")) {
	                    	proObj.setPro_id(resultSetPro.getString("project_id"));
	            			proObj.setPro_name(resultSetPro.getString("project_name"));
	            			proObj.setPro_status(resultSetPro.getString("project_status"));

	            			Project findActNum = new ProjectImpl();
	            			int num = findActNum.projectActNumber(proObj.getPro_id());
	            			proObj.setAct_num(num+"");
	            			System.out.println("实际人数"+ proObj.getAct_num());
	            			
	            			proObj.setStart_year(resultSetPro.getString("start_year"));
	            			proObj.setFinish_year(resultSetPro.getString("finish_year"));
	    					proObj.setPlan_num(resultSetPro.getString("plan_number"));
	            			proObj.setPro_principal_id(resultSetPro.getString("project_principal_id"));
	            			proObj.setPro_teacher_id(resultSetPro.getString("project_teacher_id"));
	            			proObj.setPro_state(resultSetPro.getString("project_state"));
	            			
	         
	                        
	                     // 教师信息
	                        statementTea = conn.createStatement();
	                        String teaId = proObj.getPro_teacher_id();
	                        String sqlForTea = "select * from user where user_id = '" +  teaId + "';";
	                        System.out.println(sqlForTea);
	                        resultSetTea = statementTea.executeQuery(sqlForTea);
	                        if(resultSetTea.next()) {
	                        	teaObj.setUser_id(resultSetTea.getString("user_id"));
	                        	teaObj.setUser_name(resultSetTea.getString("user_name"));
	                        	teaObj.setUser_mail(resultSetTea.getString("user_mail"));
	                        	teaObj.setUser_phone(resultSetTea.getString("user_phone"));
	                        }
	                        System.out.println("报名者信息成功");
	                  
	                        ProjectAuditObject proAudit = new ProjectAuditObject();
	                        proAudit.setProObj(proObj);
	                        proAudit.setSignUpObj(signUpObj);
	                     
	                        proAudit.setTeaObj(teaObj);
	                        proAuditList.add(proAudit);
	                       
                    	}
                    }
					 
            	}
//            	System.out.println(proMemberList.size());
            	return proAuditList;
				
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
			closeConn(resultSetPro,statementPro, conn);
			closeConn(resultSetStu,statementStu, conn);
			closeConn(resultSetTea,statementTea, conn);
		}
	}

	
	@Override
	public ProjectAuditObject findProAuditById(String recId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		Statement statementPro = null;
		Statement statementLeader = null;
		Statement statementTea = null;
		Statement statementStu = null;
		
		ResultSet resultSet = null;
		ResultSet resultSetPro = null;
		ResultSet resultSetLeader = null;
		ResultSet resultSetTea = null;
		ResultSet resultSetStu = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            
            String sqlForAllProAudit = "select * from recruitment_sign_up where sign_up_id = '" +  recId + "';";
            System.out.println(sqlForAllProAudit);
            resultSet = statement.executeQuery(sqlForAllProAudit);
            ProjectAuditObject proAudit = new ProjectAuditObject();
            if(resultSet != null) {
            	if(resultSet.next()) {
            		RecruitmentSignUpObject signUpObj = new RecruitmentSignUpObject();
            	    ProjectObject proObj = new ProjectObject();
  
            		UserObject teaObj = new UserObject();
            		UserObject leaderObj = new UserObject();
            		//项目报名信息
            		signUpObj.setSign_up_id(resultSet.getString("sign_up_id"));
            		signUpObj.setProject_id(resultSet.getString("project_id"));
            		signUpObj.setUser_id(resultSet.getString("user_id"));
            		signUpObj.setUser_name(resultSet.getString("user_name"));
            		signUpObj.setEmail(resultSet.getString("email"));
            		signUpObj.setPhone_number(resultSet.getString("phone_number"));
            		signUpObj.setSign_up_time(resultSet.getString("sign_up_time"));
            		signUpObj.setState(resultSet.getString("state"));
					
					
				// 项目信息
				statementPro = conn.createStatement();
				String proId = signUpObj.getProject_id();
            		String sqlPro = "select * from project where project_id = '" +  proId + "';";
                    System.out.println(sqlPro);
                    resultSetPro = statementPro.executeQuery(sqlPro);
                    if(resultSetPro.next()) {
                    	if(resultSetPro.getString("project_state").equals("可用")) {
	                    	proObj.setPro_id(resultSetPro.getString("project_id"));
	            			proObj.setPro_name(resultSetPro.getString("project_name"));
	            			proObj.setPro_status(resultSetPro.getString("project_status"));

	            			Project findActNum = new ProjectImpl();
	            			int num = findActNum.projectActNumber(proObj.getPro_id());
	            			proObj.setAct_num(num+"");
	            			System.out.println("实际人数"+ proObj.getAct_num());
	            			
	            			proObj.setStart_year(resultSetPro.getString("start_year"));
	            			proObj.setFinish_year(resultSetPro.getString("finish_year"));
	    					proObj.setPlan_num(resultSetPro.getString("plan_number"));
	            			proObj.setPro_principal_id(resultSetPro.getString("project_principal_id"));
	            			proObj.setPro_teacher_id(resultSetPro.getString("project_teacher_id"));
	            			proObj.setPro_state(resultSetPro.getString("project_state"));
	            			
	            			
	                        
	                        
	                     // 教师信息
	                        statementTea = conn.createStatement();
	                        String teaId = proObj.getPro_teacher_id();
	     
	                        String sqlForTea = "select * from user where user_id = '" +  teaId + "';";
	                        System.out.println(sqlForTea);
	                        resultSetTea = statementTea.executeQuery(sqlForTea);
	                        if(resultSetTea.next()) {
	                        	teaObj.setUser_id(resultSetTea.getString("user_id"));
	                        	teaObj.setUser_name(resultSetTea.getString("user_name"));
	                        	teaObj.setUser_mail(resultSetTea.getString("user_mail"));
	                        	teaObj.setUser_phone(resultSetTea.getString("user_phone"));
	                        	System.out.println("教师信息成功");
	      	                  
	                        }
	                        
	                     // 负责人信息
	                        statementLeader = conn.createStatement();
	                        String leaderId = proObj.getPro_principal_id();
	                        System.out.println(leaderId);
	                        String sqlForLeader = "select * from user where user_id = '" +  leaderId + "';";
	                        System.out.println(sqlForLeader);
	                        resultSetLeader = statementLeader.executeQuery(sqlForLeader);
	                        if(resultSetLeader.next()) {
	                        	leaderObj.setUser_id(resultSetLeader.getString("user_id"));
	                        	leaderObj.setUser_name(resultSetLeader.getString("user_name"));
	                        	leaderObj.setUser_mail(resultSetLeader.getString("user_mail"));
	                        	leaderObj.setUser_phone(resultSetLeader.getString("user_phone"));
	                        	 System.out.println("负责人信息成功");
	                        }
	                        
	                        proAudit.setProObj(proObj);
	                        proAudit.setSignUpObj(signUpObj);
	               
	                        proAudit.setTeaObj(teaObj);
	                        proAudit.setLeaderObj(leaderObj);
                    	}
                    }
					 
            	}
//            	System.out.println(proMemberList.size());
            	return proAudit;
				
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
			closeConn(resultSetPro,statementPro, conn);
			closeConn(resultSetStu,statementStu, conn);
			closeConn(resultSetTea,statementTea, conn);
			closeConn(resultSetLeader,statementLeader, conn);
		}
	}

	@Override
	public boolean ReState(String recId, String state,String proId,String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		
		Statement statementInsert = null;
		ResultSet resultSetInsert = null;
		PreparedStatement statInsert = null;
		
		Statement statementDel = null;
		ResultSet resultSetDel = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String resql = "update recruitment_sign_up set state = ? where sign_up_id = ? ;"; 
            stat=conn.prepareStatement(resql);
            stat.setString(1, state);
            stat.setString(2, recId);
            System.out.println(resql);
            int row = stat.executeUpdate();
            if(row > 0) {
            	if(state.equals("审核成功")) {
                	statementInsert = conn.createStatement();
                    //查询学生信息
                    String insertSql = "insert into project_student(project_id,user_id) values (?,?)";
                    System.out.println(insertSql);
                    statInsert = conn.prepareStatement(insertSql);
                    statInsert.setString(1, proId);
                    statInsert.setString(2, userId);
                    int row2 = statInsert.executeUpdate();
                    if(row2 > 0) {
                    	return true;
                    }
                    else {
                    	return false;
                    }
                }
            	else {
            		statementDel = conn.createStatement();
                    //查询学生信息
                    String delSql = "delete from project_student  where project_id = '" +  proId + "' and user_id = '" +  userId + "';";
                    System.out.println(delSql);
                    int row3 = statementDel.executeUpdate(delSql);
                    System.out.println(row3);
                    if(row3 > 0) {
                    	return true;
                    }
                    else {
                    	return false;
                    }
            	}
            }else {
            		return false;
            }
            
        }catch(Exception e) {
    			e.printStackTrace();
    			return false;
		}
		finally{
			closeConn(resultSet,statement, conn);
			closeConn(resultSetInsert,statementInsert, conn);
			closeConn(resultSetDel,statementDel, conn);
		
		}
	}

}
