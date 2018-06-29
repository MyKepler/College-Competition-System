package com.yhcj.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.MidProject;
import com.yhcj.Dao.Project;
import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProRevObject;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.UserObject;
import com.yhcj.utils.DBUtil;

public class MidProjectImpl extends DBUtil implements MidProject{

	@Override
	public int getCount() {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select count(*) from project where project_status = '����' and project_state = '����'";
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
	public List<ProStuTeaAndRev> findMidPro(String pageNum, String pageSize) {
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
            statementFile = conn.createStatement();
            String sqlForAllMidPro = "select * from project where project_status = '����' and project_state = '����' limit "+(numer-1)*size+","+size+";";
            System.out.println(sqlForAllMidPro);
            resultSet = statement.executeQuery(sqlForAllMidPro);
            if(resultSet != null) {
            		List<ProStuTeaAndRev> proList = new ArrayList<ProStuTeaAndRev>();
            		while(resultSet.next()) {
            			ProjectObject proObj = new ProjectObject();
            			ProRevObject prorevObj = new ProRevObject();
        				UserObject stuObj = new UserObject();
        				UserObject teaObj = new UserObject();
        				proObj.setPro_id(resultSet.getString("project_id"));
        				String id = resultSet.getString("project_id");
            			System.out.println(id);
    					//��ѯ���з����������ļ�
    					String sqlForQueryFiles = "SELECT * FROM project_files WHERE  file_type = '����' and project_id = '"+id+"';";
    					resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
    					if(resultSetFile.next()) {
    						proObj.setIs_submit("���ύ");
    					}else {
    						proObj.setIs_submit("δ�ύ");
    					}
            			proObj.setPro_name(resultSet.getString("project_name"));
            			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
            			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
            			proObj.setPro_status(resultSet.getString("project_status"));
            			statementStu = conn.createStatement();
        				// ѧ��
        				String stuId = resultSet.getString("project_principal_id");
        				String sqlStu = "select user_name from user where user_id = '" +  stuId + "';";
        				resultSetStu = statementStu.executeQuery(sqlStu);
        				
        				statementTea = conn.createStatement();
        				// ��ʦ
        				String teaId = resultSet.getString("project_teacher_id");
        				System.out.println(teaId+"������");
        				String sqlTea = "select user_name from user where user_id = '" +  teaId + "';";
        				resultSetTea = statementTea.executeQuery(sqlTea);
        				
        				resultSetStu.next();
            			stuObj.setUser_name(resultSetStu.getString("user_name"));
            			resultSetTea.next();
            			teaObj.setUser_name(resultSetTea.getString("user_name"));
            			
        				// ����
            			statementRev = conn.createStatement();
        				String proId=proObj.getPro_id();
        				String proStatus = proObj.getPro_status();
        				String sqlProAndRev = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  proStatus + "';";
        				resultSetRev = statementRev.executeQuery(sqlProAndRev);
        				//������
        				if(resultSetRev.next()) {
        					prorevObj.setId(resultSetRev.getString("id"));
        					prorevObj.setReview_code(resultSetRev.getString("review_code"));
        					prorevObj.setReview_msg(resultSetRev.getString("review_msg"));
        					prorevObj.setReview_user_id(resultSetRev.getString("review_user_id"));
        					statementRev2 = conn.createStatement();
        					String reviewerId = prorevObj.getReview_user_id();
        					System.out.println(reviewerId+"������");
        					String sqlRev = "select user_name from user where user_id = '" +  reviewerId + "';";
        					resultSetRev2 = statementRev2.executeQuery(sqlRev);
        					if(resultSetRev.next()) {
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
	public ProStuTeaAndRev findSpeMidPro(String proId) {
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
		System.out.println(proId);
		try {
			conn=getConn();
            statement = conn.createStatement();
            statementFile = conn.createStatement();
            // ��Ŀ
            String sqlPro = "select * from project where project_id = '" +  proId + "';";
            System.out.println(sqlPro);
            resultSet = statement.executeQuery(sqlPro);
            if(resultSet.next()) {
	            	ProjectObject proObj = new ProjectObject();
	    			ProRevObject prorevObj = new ProRevObject();
				UserObject stuObj = new UserObject();
				UserObject teaObj = new UserObject();
				UserObject revObj = new UserObject();
				
				// ��Ŀ��Ϣ
				proObj.setPro_id(resultSet.getString("project_id"));
				String id = resultSet.getString("project_id");
    				System.out.println(id);
				//��ѯ���з����������ļ�
				String sqlForQueryFiles = "SELECT * FROM project_files WHERE  file_type = '����' and project_id = '"+id+"';";
				resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
				if(resultSetFile.next()) {
					proObj.setIs_submit("���ύ");
					proObj.setSubmit_time(resultSetFile.getDate("submit_time"));
				}else {
					proObj.setIs_submit("δ�ύ");
				}	
	    			proObj.setPro_name(resultSet.getString("project_name"));
	    			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
	    			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
	    			proObj.setPro_status(resultSet.getString("project_status"));
	    			proObj.setStart_year("2018-1-1");
	    			proObj.setFinish_year("2018-12-30");
	    			
        			statementStu = conn.createStatement();
    				// ѧ��
    				String stuId = resultSet.getString("project_principal_id");
    				String sqlStu = "select user_name from user where user_id = '" +  stuId + "';";
    				resultSetStu = statementStu.executeQuery(sqlStu);
    				
    				statementTea = conn.createStatement();
    				// ��ʦ
    				String teaId = resultSet.getString("project_teacher_id");
    				String sqlTea = "select user_name from user where user_id = '" +  teaId + "';";
    				resultSetTea = statementTea.executeQuery(sqlTea);
    				
    				//ѧ����Ϣ
    				resultSetStu.next();
        			stuObj.setUser_name(resultSetStu.getString("user_name"));
        			//��ʦ��Ϣ
        			resultSetTea.next();
        			teaObj.setUser_name(resultSetTea.getString("user_name"));
        			
    				// ����
    				statementRev = conn.createStatement();
    				String proStatus = proObj.getPro_status();
    				String sqlProAndRev = "select * from project_review where project_id = '" +  proId + "' and review_type = '����';";
    				resultSetRev = statementRev.executeQuery(sqlProAndRev);
    				//������
    				if(resultSetRev.next()) {
    					prorevObj.setId(resultSetRev.getString("id"));
    					prorevObj.setReview_code(resultSetRev.getString("review_code"));
    					prorevObj.setReview_msg(resultSetRev.getString("review_msg"));
    					prorevObj.setReview_user_id(resultSetRev.getString("review_user_id"));
    					statementRev2 = conn.createStatement();
    					String reviewerId = prorevObj.getReview_user_id();
    					String sqlRev = "select user_name from user where user_id = '" +  reviewerId + "';";
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
        			pstr.setReviewerObj(revObj);
        			return pstr;
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
			closeConn(resultSetStu,statementStu, conn);
			closeConn(resultSetTea,statementTea, conn);
			closeConn(resultSetRev,statementRev, conn);
		}
	}

	@Override
	public int updateMidPro(ProjectObject proObj) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		
		try {
			conn=getConn();
            statement = conn.createStatement();
            String updsql = "update project set project_name = ?,project_status = ?,project_principal_id = ?,project_teacher_id = ? where project_id = ?";
            stat=conn.prepareStatement(updsql);
            stat.setString(1, proObj.getPro_name());
            stat.setString(2, proObj.getPro_status());
            stat.setString(3, proObj.getPro_principal_id());
            stat.setString(4, proObj.getPro_teacher_id());
            stat.setString(5, proObj.getPro_id());
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
	public boolean delMidPro(String proId) {
		// TODO �Զ����ɵķ������
		return false;
	}

	/*--------------------------------------------------��ʦ��------------------------------------------------------*/
	
	//�г�ָ����ʦָ�������д������ڵ���Ŀ�����õģ�
	@Override
	public List<ProStuTeaAndRev> findMidProByTea(String pageNum, String pageSize, String userId) {
		// TODO Auto-generated method stub
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
            statementFile = conn.createStatement();
            String sqlForAllMidPro = "select * from project where project_status = '����' and project_state = '����' "
            		+ "and project_teacher_id =  '" +  userId + "' limit "+(numer-1)*size+","+size+";";
            System.out.println(sqlForAllMidPro);
            resultSet = statement.executeQuery(sqlForAllMidPro);
            if(resultSet != null) {
            		List<ProStuTeaAndRev> proList = new ArrayList<ProStuTeaAndRev>();
            		while(resultSet.next()) {
            			ProjectObject proObj = new ProjectObject();
            			ProRevObject prorevObj = new ProRevObject();
        				UserObject stuObj = new UserObject();
        				UserObject teaObj = new UserObject();
        				proObj.setPro_id(resultSet.getString("project_id"));
        				String id = resultSet.getString("project_id");
        				System.out.println(id);
        				//��ѯ���з����������ļ�
	    				String sqlForQueryFiles = "SELECT * FROM project_files WHERE  file_type = '����' and project_id = '"+id+"';";
	    				resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
	    				if(resultSetFile.next()) {
	    					proObj.setIs_submit("���ύ");
	    					proObj.setSubmit_time(resultSetFile.getDate("submit_time"));
	    				}else {
	    					proObj.setIs_submit("δ�ύ");
	    				}
            			proObj.setPro_name(resultSet.getString("project_name"));
            			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
            			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
            			proObj.setPro_status(resultSet.getString("project_status"));
            			statementStu = conn.createStatement();
        				// ѧ��
        				String stuId = resultSet.getString("project_principal_id");
        				String sqlStu = "select user_name from user where user_id = '" +  stuId + "';";
        				resultSetStu = statementStu.executeQuery(sqlStu);
        				
        				statementTea = conn.createStatement();
        				// ��ʦ
        				String teaId = resultSet.getString("project_teacher_id");
        				String sqlTea = "select user_name from user where user_id = '" +  teaId + "';";
        				resultSetTea = statementTea.executeQuery(sqlTea);
        				
        				resultSetStu.next();
            			stuObj.setUser_name(resultSetStu.getString("user_name"));
            			resultSetTea.next();
            			teaObj.setUser_name(resultSetTea.getString("user_name"));
            			
        				// ����
            			statementRev = conn.createStatement();
        				String proId=proObj.getPro_id();
        				String proStatus = proObj.getPro_status();
        				String sqlProAndRev = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  proStatus + "';";
        				resultSetRev = statementRev.executeQuery(sqlProAndRev);
        				//������
        				if(resultSetRev.next()) {
        					prorevObj.setId(resultSetRev.getString("id"));
        					prorevObj.setReview_code(resultSetRev.getString("review_code"));
        					prorevObj.setReview_msg(resultSetRev.getString("review_msg"));
        					prorevObj.setReview_user_id(resultSetRev.getString("review_user_id"));
        					statementRev2 = conn.createStatement();
        					String reviewerId = prorevObj.getReview_user_id();
        					String sqlRev = "select user_name from user where user_id = '" +  reviewerId + "';";
        					resultSetRev2 = statementRev2.executeQuery(sqlRev);
        					if(resultSetRev.next()) {
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

	
	//ͳ��ָ����ʦָ�������д������ڵ���Ŀ�����õģ�����
	@Override
	public int getTeaCount(String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //��ѯ������Ŀ��Ϣ
            String sqlForAllPro = "select count(*) from project where project_status = '����' and project_state = '����' "
            		+ "and project_teacher_id =  '" +  userId + "';";
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

	/*--------------------------------------------------ѧ����------------------------------------------------------*/
	@Override
	public int getCount(String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select count(*) from project left join project_student on project.project_id=project_student.project_id where project_status='����' and is_in_service='����' and user_id='"+userId+"';";
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
	public List<ProStuTeaAndRev> findAllStuMidPro(String pageNum, String pageSize,String userId) {
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
            statementFile = conn.createStatement();
            String sql = "select * from project left join project_student on project.project_id=project_student.project_id where project_status='����' and is_in_service='����' and user_id='"+userId+"' limit "+(numer-1)*size+","+size+";";
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
            if(resultSet != null) {
            		List<ProStuTeaAndRev> proList = new ArrayList<ProStuTeaAndRev>();
            		while(resultSet.next()) {
            			ProjectObject proObj = new ProjectObject();
            			ProRevObject prorevObj = new ProRevObject();
        				UserObject stuObj = new UserObject();
        				UserObject teaObj = new UserObject();
        				
        				//��Ŀ
        				proObj.setPro_id(resultSet.getString("project_id"));
        				String id = resultSet.getString("project_id");
	        			System.out.println(id);
	    				//��ѯ���з����������ļ�
	    				String sqlForQueryFiles = "SELECT * FROM project_files WHERE  file_type = '����' and project_id = '"+id+"';";
	    				resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
	    				if(resultSetFile.next()) {
	    					proObj.setIs_submit("���ύ");
	    					proObj.setSubmit_time(resultSetFile.getDate("submit_time"));
	    				}else {
	    					proObj.setIs_submit("δ�ύ");
	    				}
        				proObj.setPro_status(resultSet.getString("project_status"));
            			proObj.setPro_name(resultSet.getString("project_name"));
            			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
            			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
            		
            			Project findActNum = new ProjectImpl();
            			int num = findActNum.projectActNumber(proObj.getPro_id());
            			proObj.setAct_num(num+"");
            			System.out.println("ʵ������"+ proObj.getAct_num());
            			
            			proObj.setPlan_num(resultSet.getString("plan_number"));
            			proObj.setStart_year(resultSet.getString("start_year"));
            			proObj.setFinish_year(resultSet.getString("finish_year"));
            			proObj.setPro_state(resultSet.getString("project_state"));
            			
        				// ѧ��
            			statementStu = conn.createStatement();
        				String stuId = resultSet.getString("project_principal_id");
        				System.out.println(stuId+"������1");
        				String sqlStu = "select * from user where user_id = '" +  stuId + "';";
        				resultSetStu = statementStu.executeQuery(sqlStu);
        				resultSetStu.next();
            			stuObj.setUser_name(resultSetStu.getString("user_name"));
            			stuObj.setUser_mail(resultSetStu.getString("user_mail"));
            			stuObj.setUser_phone(resultSetStu.getString("user_phone"));
            			
        				// ��ʦ
        				statementTea = conn.createStatement();
        				String teaId = resultSet.getString("project_teacher_id");
        				System.out.println(teaId+"��ʦ");
        				String sqlTea = "select * from user where user_id = '" +  teaId + "';";
        				resultSetTea = statementTea.executeQuery(sqlTea);
            			resultSetTea.next();
            			teaObj.setUser_name(resultSetTea.getString("user_name"));
            			teaObj.setUser_mail(resultSetTea.getString("user_mail"));
            			teaObj.setUser_phone(resultSetTea.getString("user_phone"));
            			
        				// ����
            			statementRev = conn.createStatement();
        				String proId=proObj.getPro_id();
        				String proStatus = proObj.getPro_status();
        				String sqlProAndRev = "select * from project_review where project_id = '" +  proId + "' and review_type = '" +  proStatus + "';";
        				resultSetRev = statementRev.executeQuery(sqlProAndRev);
        				//������
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
}
