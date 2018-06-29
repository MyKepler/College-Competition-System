package com.yhcj.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.Project;
import com.yhcj.Dao.ProjectMember;
import com.yhcj.enity.ProAndReviewObject;
import com.yhcj.enity.ProAndStuObject;
import com.yhcj.enity.ProjectInfoObject;
import com.yhcj.enity.ProjectMemberObject;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.StudentObject;
import com.yhcj.enity.UserObject;
import com.yhcj.utils.DBUtil;

public class ProjectMemberImpl extends DBUtil implements ProjectMember {

	@Override
	public int getCount() {
		// TODO Auto-gsenerated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		Statement statementPro = null;
		ResultSet resultSetPro = null;
		int count = 0;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //��ѯѧ����Ϣ
            String sqlForAllProMem = "select * from project_student;";
            System.out.println(sqlForAllProMem);
            resultSet = statement.executeQuery(sqlForAllProMem);
            if(resultSet != null) {
//            	System.out.println(sqlForAllPro);
            	while(resultSet.next()) {
            		System.out.println(sqlForAllProMem);
            		
            		statementPro = conn.createStatement();
            		String proId = resultSet.getString("project_id");
            		String sqlPro = "select * from project where project_id = '" +  proId + "';";
                    System.out.println(sqlPro);
                    resultSetPro = statementPro.executeQuery(sqlPro);
                    if(resultSetPro.next()) {
                    	if(resultSetPro.getString("project_state").equals("����")) {
	                    	count++;
                    	}
                    }
					 
            	}
            	System.out.println(count);
            	return count;
				
            }else {
				//�û�������
				return count;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return count;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}
	
	@Override
	public List<ProjectMemberObject> findAllProMembers(String pageNum,String pageSize) {
		Connection conn=null;
		Statement statement = null;
		Statement statementPro = null;
		Statement statementMember = null;
		
		ResultSet resultSet = null;
		ResultSet resultSetPro = null;
		ResultSet resultSetMember = null;
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
           
            String sqlForAllProMem = "select * from project_student;";
            System.out.println(sqlForAllProMem);
            resultSet = statement.executeQuery(sqlForAllProMem);
            if(resultSet != null) {
//            	System.out.println(sqlForAllPro);
            	List<ProjectMemberObject> proMemberList = new ArrayList<ProjectMemberObject>();
            	while(resultSet.next()) {
            		System.out.println(sqlForAllProMem);
            		ProAndStuObject proAndStuObj = new ProAndStuObject();
            		ProjectObject proObj = new ProjectObject();
            		StudentObject memberObj = new StudentObject();
            		
            		//��Ŀ��Ա��Ϣ
					proAndStuObj.setProject_id(resultSet.getString("project_id"));
					proAndStuObj.setUser_id(resultSet.getString("user_id"));
					proAndStuObj.setIs_in_service(resultSet.getString("is_in_service"));
					
					
					// ��Ŀ��Ϣ
					statementPro = conn.createStatement();
            		String proId = proAndStuObj.getProject_id();
            		String sqlPro = "select * from project where project_id = '" +  proId + "';";
                    System.out.println(sqlPro);
                    resultSetPro = statementPro.executeQuery(sqlPro);
                    if(resultSetPro.next()) {
                    	if(resultSetPro.getString("project_state").equals("����")) {
	                    	proObj.setPro_id(resultSetPro.getString("project_id"));
	            			proObj.setPro_name(resultSetPro.getString("project_name"));
	            			proObj.setPro_status(resultSetPro.getString("project_status"));
	    					
	            			Project findActNum = new ProjectImpl();
	            			int num = findActNum.projectActNumber(proObj.getPro_id());
	            			proObj.setAct_num(num+"");
	            			System.out.println("ʵ������"+ proObj.getAct_num());
	            			
	            			proObj.setStart_year(resultSetPro.getString("start_year"));
	            			proObj.setFinish_year(resultSetPro.getString("finish_year"));
	    					proObj.setPlan_num(resultSetPro.getString("plan_number"));
	            			proObj.setPro_principal_id(resultSetPro.getString("project_principal_id"));
	            			proObj.setPro_teacher_id(resultSetPro.getString("project_teacher_id"));
	            			proObj.setPro_state(resultSetPro.getString("project_state"));
	            			// ��Ա��Ϣ
	                        statementMember = conn.createStatement();
	                        String userId = proAndStuObj.getUser_id();
	                        System.out.println(userId);
	                        String sqlForMem = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  userId + "';";
	                        System.out.println(sqlForMem);
	                        resultSetMember = statementMember.executeQuery(sqlForMem);
	                        if(resultSetMember.next()) {
	                        	memberObj.setUser_id(resultSetMember.getString("user_id"));
	                        	memberObj.setUser_name(resultSetMember.getString("user_name"));
	                        	memberObj.setUser_mail(resultSetMember.getString("user_mail"));
	                        	memberObj.setUser_phone(resultSetMember.getString("user_phone"));
	                        }
	                        System.out.println("��Ա��Ϣ�ɹ�");
	                  
	    					//��Ŀ��Ա����
	                    ProjectMemberObject proMember = new ProjectMemberObject();
	    					proMember.setProAndStuObj(proAndStuObj);
	    					proMember.setProObj(proObj);
	    					proMember.setStuObj(memberObj);
	    					proMemberList.add(proMember);
                    	}
                    }
					 
            	}
//            	System.out.println(proMemberList.size());
            	List<ProjectMemberObject> proMemberListsub = new ArrayList<ProjectMemberObject>();
            	size = size>=proMemberList.size()?proMemberList.size():size;
            	for(int i = (numer-1)*size;i<size;i++ ) {
            		proMemberListsub.add(proMemberList.get(i));
            	}
            	return proMemberListsub;
				
            }else {
				//�û�������
				return null;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
			closeConn(resultSetPro,statement, conn);
			closeConn(resultSetMember,statement, conn);
		}
	}
	
	@Override
	public ProjectMemberObject findProMemberById(String proId, String stuId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		Statement statementLeader = null;
		Statement statementTea = null;
		Statement statementMember = null;
		
		ResultSet resultSet = null;
		ResultSet resultSetLeader = null;
		ResultSet resultSetTea = null;
		ResultSet resultSetMember = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
    		ProjectObject proObj = new ProjectObject();
    		StudentObject memberObj = new StudentObject();
			UserObject teaObj = new UserObject();
			UserObject leaderObj = new UserObject();
           
            // ��Ŀ
            String sqlPro = "select * from project where project_id = '" +  proId + "';";
            System.out.println(sqlPro);
            resultSet = statement.executeQuery(sqlPro);
			if(resultSet.next()) {
				proObj.setPro_id(resultSet.getString("project_id"));
				proObj.setPro_name(resultSet.getString("project_name"));
				proObj.setPro_status(resultSet.getString("project_status"));
			
				Project findActNum = new ProjectImpl();
				int num = findActNum.projectActNumber(proObj.getPro_id());
				proObj.setAct_num(num+"");
				System.out.println("ʵ������"+ proObj.getAct_num());
				
				proObj.setPlan_num(resultSet.getString("plan_number"));
				proObj.setStart_year(resultSet.getString("start_year"));
				proObj.setFinish_year(resultSet.getString("finish_year"));
				proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
				proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
			
				// ������
				statementLeader = conn.createStatement();
				String leaderId = proObj.getPro_principal_id();
				String sqlLeader = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  leaderId + "';";
				resultSetLeader = statementLeader.executeQuery(sqlLeader);
				if(resultSetLeader.next()) {
					leaderObj.setUser_id(resultSetLeader.getString("user_id"));
					leaderObj.setUser_name(resultSetLeader.getString("user_name"));
					leaderObj.setUser_mail(resultSetLeader.getString("user_mail"));
					leaderObj.setUser_phone(resultSetLeader.getString("user_phone"));
				}
	
				// ��ʦ
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
						
				//��Ŀ��Ա
				statementMember = conn.createStatement();
				String sqlForMember = "select * from student left join user on user.user_id = student.user_id where user.user_id = '"+stuId+"';";
				resultSetMember = statementMember.executeQuery(sqlForMember);
				if(resultSetMember.next()) {
					memberObj.setUser_id(resultSetMember.getString("user_id"));
					memberObj.setUser_name(resultSetMember.getString("user_name"));
					memberObj.setUser_sex(resultSetMember.getString("user_sex"));
					memberObj.setUser_mail(resultSetMember.getString("user_mail"));
					memberObj.setUser_phone(resultSetMember.getString("user_phone"));
					memberObj.setUser_identity(resultSetMember.getString("user_identity"));
					memberObj.setAccount_state(resultSetMember.getString("account_state"));
					memberObj.setUser_pwd(resultSetMember.getString("user_pwd"));
					memberObj.setStudent_academy(resultSetMember.getString("student_academy"));
					memberObj.setStudent_major(resultSetMember.getString("student_major"));
					memberObj.setStudent_class(resultSetMember.getString("student_class"));
				}
				//��Ŀ��Ա����
				ProjectMemberObject proMember = new ProjectMemberObject();
				proMember.setLeaderObj(leaderObj);
				proMember.setTeaObj(teaObj);
				proMember.setProObj(proObj);
				proMember.setStuObj(memberObj);
				return proMember;
			}else {
				//�û�������
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
			closeConn(resultSetMember,statementMember,conn);
		}
	}
	
	@Override
	public boolean reStates(String proId, String userId, String state) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String resql = "update project_student set is_in_service = ? where user_id = ? and project_id = ?";
            
            stat=conn.prepareStatement(resql);
            stat.setString(1, state);
            stat.setString(2, userId);
            stat.setString(3, proId);
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


	/*--------------------------------------------------��ʦ��------------------------------------------------------*/
	
	//�г��ý�ʦָ�������п�����Ŀ�ĳ�Ա����Աһ��Ҫ���ڣ�
	@Override
	public List<ProjectMemberObject> findAllProInfoByTea(String pageNum, String pageSize, String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		Statement statementPro = null;
		Statement statementMember = null;
		
		ResultSet resultSet = null;
		ResultSet resultSetPro = null;
		ResultSet resultSetMember = null;
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
           
            String sqlForAllProMem = "select * from project_student limit;";
            System.out.println(sqlForAllProMem);
            resultSet = statement.executeQuery(sqlForAllProMem);
            if(resultSet != null) {
//            	System.out.println(sqlForAllPro);
            	List<ProjectMemberObject> proMemberList = new ArrayList<ProjectMemberObject>();
            	List<ProjectMemberObject> proMemberListsub = new ArrayList<ProjectMemberObject>();
            	while(resultSet.next()) {
            		System.out.println(sqlForAllProMem);
            		ProAndStuObject proAndStuObj = new ProAndStuObject();
            		ProjectObject proObj = new ProjectObject();
            		StudentObject memberObj = new StudentObject();
            		
            		//��Ŀ��Ա��Ϣ
					proAndStuObj.setProject_id(resultSet.getString("project_id"));
					proAndStuObj.setUser_id(resultSet.getString("user_id"));
					proAndStuObj.setIs_in_service(resultSet.getString("is_in_service"));
					
					
					// ��Ŀ��Ϣ
					statementPro = conn.createStatement();
            		String proId = proAndStuObj.getProject_id();
            		String sqlPro = "select * from project where project_id = '" +  proId + "';";
                    System.out.println(sqlPro);
                    resultSetPro = statementPro.executeQuery(sqlPro);
                    if(resultSetPro.next()) {
                    	String proState = resultSetPro.getString("project_state");
                    	String proTeacher = resultSetPro.getString("project_teacher_id");
                    	if(proState.equals("����") && proTeacher.equals(userId)) {
	                    	proObj.setPro_id(resultSetPro.getString("project_id"));
	            			proObj.setPro_name(resultSetPro.getString("project_name"));
	            			proObj.setPro_status(resultSetPro.getString("project_status"));
	    					
	            			Project findActNum = new ProjectImpl();
	            			int num = findActNum.projectActNumber(proObj.getPro_id());
	            			proObj.setAct_num(num+"");
	            			System.out.println("ʵ������"+ proObj.getAct_num());
	            			
	            			proObj.setStart_year(resultSetPro.getString("start_year"));
	            			proObj.setFinish_year(resultSetPro.getString("finish_year"));
	    					proObj.setPlan_num(resultSetPro.getString("plan_number"));
	            			proObj.setPro_principal_id(resultSetPro.getString("project_principal_id"));
	            			proObj.setPro_teacher_id(resultSetPro.getString("project_teacher_id"));
	            			proObj.setPro_state(resultSetPro.getString("project_state"));
	            			// ��Ա��Ϣ
	                        statementMember = conn.createStatement();
	                        String memId = proAndStuObj.getUser_id();
	                        System.out.println(userId);
	                        String sqlForMem = "select * from user where user_id = '" +  memId + "';";
	                        System.out.println(sqlForMem);
	                        resultSetMember = statementMember.executeQuery(sqlForMem);
	                        if(resultSetMember.next()) {
	                        	memberObj.setUser_id(resultSetMember.getString("user_id"));
	                        	memberObj.setUser_name(resultSetMember.getString("user_name"));
	                        	memberObj.setUser_mail(resultSetMember.getString("user_mail"));
	                        	memberObj.setUser_phone(resultSetMember.getString("user_phone"));
	                        }
	                        System.out.println("��Ա��Ϣ�ɹ�");
	                  
	    					//��Ŀ��Ա����
	                    ProjectMemberObject proMember = new ProjectMemberObject();
	    					proMember.setProAndStuObj(proAndStuObj);
	    					proMember.setProObj(proObj);
	    					proMember.setStuObj(memberObj);
	    					proMemberList.add(proMember);
                    	}
                    }
					 
            	}
            	size = size>=proMemberList.size()?proMemberList.size():size;
            	for(int i = (numer-1)*size;i<size;i++ ) {
            		proMemberListsub.add(proMemberList.get(i));
            	}
            	return proMemberListsub;
				
            }else {
				//�û�������
				return null;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
			closeConn(resultSetPro,statement, conn);
			closeConn(resultSetMember,statement, conn);
		}
	}

	
	//ͳ�Ƹý�ʦָ�������п�����Ŀ�ĳ�Ա����Աһ��Ҫ���ڣ�������
	@Override
	public int getTeaCount(String userId) {
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
            //��ѯ��Ŀ��Ա��Ϣ
            String sqlForAllProMem = "select * from project_student;";
            System.out.println(sqlForAllProMem);
            resultSet = statement.executeQuery(sqlForAllProMem);
            if(resultSet != null) {
//            	System.out.println(sqlForAllPro);
            	while(resultSet.next()) {
            		System.out.println(sqlForAllProMem);
            		
            		statementPro = conn.createStatement();
            		String proId = resultSet.getString("project_id");
            		String sqlPro = "select * from project where project_id = '" +  proId + "';";
                    System.out.println(sqlPro);
                    resultSetPro = statementPro.executeQuery(sqlPro);
                    if(resultSetPro.next()) {
                    	String proState = resultSetPro.getString("project_state");
                    	String proTeacher = resultSetPro.getString("project_teacher_id");
                    	if(proState.equals("����") && proTeacher.equals(userId)) {
	                    	count++;
                    	}
                    }
					 
            	}
            	System.out.println(count);
            	return count;
				
            }else {
				//�û�������
				return count;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return count;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}
	@Override
	public List<ProjectMemberObject> findAllProInfoByStu(String pageNum, String pageSize, String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		Statement statementPro = null;
		Statement statementMember = null;
		
		ResultSet resultSet = null;
		ResultSet resultSetPro = null;
		ResultSet resultSetMember = null;
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		try {
			conn=getConn();
            statement = conn.createStatement();
           
            String sqlForAllProMem = "select * from project_student;";
            System.out.println(sqlForAllProMem);
            resultSet = statement.executeQuery(sqlForAllProMem);
            if(resultSet != null) {
//            	System.out.println(sqlForAllPro);
            	List<ProjectMemberObject> proMemberList = new ArrayList<ProjectMemberObject>();
            	while(resultSet.next()) {
            		System.out.println(sqlForAllProMem);
            		ProAndStuObject proAndStuObj = new ProAndStuObject();
            		ProjectObject proObj = new ProjectObject();
            		StudentObject memberObj = new StudentObject();
            		
            		//��Ŀ��Ա��Ϣ
					proAndStuObj.setProject_id(resultSet.getString("project_id"));
					proAndStuObj.setUser_id(resultSet.getString("user_id"));
					proAndStuObj.setIs_in_service(resultSet.getString("is_in_service"));
					
					
					// ��Ŀ��Ϣ
					statementPro = conn.createStatement();
            		String proId = proAndStuObj.getProject_id();
            		String sqlPro = "select * from project where project_id = '" +  proId + "';";
                    System.out.println(sqlPro);
                    resultSetPro = statementPro.executeQuery(sqlPro);
                    if(resultSetPro.next()) {
                    	String proState = resultSetPro.getString("project_state");
                    	String proLeader = resultSetPro.getString("project_principal_id");
                    	if(proState.equals("����") && proLeader.equals(userId)) {
	                    	proObj.setPro_id(resultSetPro.getString("project_id"));
	            			proObj.setPro_name(resultSetPro.getString("project_name"));
	            			proObj.setPro_status(resultSetPro.getString("project_status"));
	    					
	            			Project findActNum = new ProjectImpl();
	            			int num = findActNum.projectActNumber(proObj.getPro_id());
	            			proObj.setAct_num(num+"");
	            			System.out.println("ʵ������"+ proObj.getAct_num());
	            			
	            			proObj.setStart_year(resultSetPro.getString("start_year"));
	            			proObj.setFinish_year(resultSetPro.getString("finish_year"));
	    					proObj.setPlan_num(resultSetPro.getString("plan_number"));
	            			proObj.setPro_principal_id(resultSetPro.getString("project_principal_id"));
	            			proObj.setPro_teacher_id(resultSetPro.getString("project_teacher_id"));
	            			proObj.setPro_state(resultSetPro.getString("project_state"));
	            			// ��Ա��Ϣ
	                        statementMember = conn.createStatement();
	                        String memId = proAndStuObj.getUser_id();
	                        System.out.println(userId);
	                        String sqlForMem = "select * from user where user_id = '" +  memId + "';";
	                        System.out.println(sqlForMem);
	                        resultSetMember = statementMember.executeQuery(sqlForMem);
	                        if(resultSetMember.next()) {
	                        	memberObj.setUser_id(resultSetMember.getString("user_id"));
	                        	memberObj.setUser_name(resultSetMember.getString("user_name"));
	                        	memberObj.setUser_mail(resultSetMember.getString("user_mail"));
	                        	memberObj.setUser_phone(resultSetMember.getString("user_phone"));
	                        }
	                        System.out.println("��Ա��Ϣ�ɹ�");
	                  
	    					//��Ŀ��Ա����
	                    ProjectMemberObject proMember = new ProjectMemberObject();
	    					proMember.setProAndStuObj(proAndStuObj);
	    					proMember.setProObj(proObj);
	    					proMember.setStuObj(memberObj);
	    					proMemberList.add(proMember);
                    	}
                    }
					 
            	}
//            	System.out.println(proMemberList.size());
            	List<ProjectMemberObject> proMemberListsub = new ArrayList<ProjectMemberObject>();
            	size = size>=proMemberList.size()?proMemberList.size():size;
            	for(int i = (numer-1)*size;i<size;i++ ) {
            		
            		proMemberListsub.add(proMemberList.get(i));
            	}
            	return proMemberListsub;
				
            }else {
				//�û�������
				return null;
			}
				
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
			closeConn(resultSetPro,statement, conn);
			closeConn(resultSetMember,statement, conn);
		}
	}

	
	@Override
	public int getStuCount(String userId) {
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
            //��ѯ��Ŀ��Ա��Ϣ
            String sqlForAllProMem = "select * from project_student;";
            System.out.println(sqlForAllProMem);
            resultSet = statement.executeQuery(sqlForAllProMem);
            if(resultSet != null) {
//            	System.out.println(sqlForAllPro);
            	while(resultSet.next()) {
            		System.out.println(sqlForAllProMem);
            		
            		statementPro = conn.createStatement();
            		String proId = resultSet.getString("project_id");
            		String sqlPro = "select * from project where project_id = '" +  proId + "';";
                    System.out.println(sqlPro);
                    resultSetPro = statementPro.executeQuery(sqlPro);
                    if(resultSetPro.next()) {
                    	String proState = resultSetPro.getString("project_state");
                    	String proLeader = resultSetPro.getString("project_principal_id");
                    	if(proState.equals("����") && proLeader.equals(userId)) {
	                    	count++;
                    	}
                    }
					 
            	}
            	System.out.println(count);
            	return count;
				
            }else {
				//�û�������
				return count;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return count;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

}
