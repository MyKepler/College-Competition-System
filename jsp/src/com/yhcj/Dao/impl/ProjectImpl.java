package com.yhcj.Dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.Project;
import com.yhcj.enity.*;
import com.yhcj.utils.DBUtil;

public class ProjectImpl extends DBUtil implements Project{

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //��ѯѧ����Ϣ
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
	//��ѯ�ض�id����Ŀ�µ��ѱ�����˳ɹ�������
		@Override
		public int projectActNumber(String id){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				conn=getConn();
				statement = conn.createStatement();
				String sqlForQueryAll = "select count(*) from project_student where project_id = '"+id+"' and is_in_service = '����';";
				System.out.println("conn succeed");
				resultSet = statement.executeQuery(sqlForQueryAll);
				if(resultSet.next()) {
					System.out.println(resultSet.getInt(1)+"");
					return resultSet.getInt(1);
				}else {
					return 0;
				}

			}
			catch(Exception e) {
				e.printStackTrace();
				return 0;
			}
			finally{
				closeConn(resultSet,statement, conn);
			}
		}
	@Override
	public List<ProjectApplyObject> findAllPro(String pageNum, String pageSize) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		Statement statementFile = null;
		ResultSet resultSetFile = null;
		Statement statementRec = null;
		ResultSet resultSetRec = null;
		
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		
		try {
			conn=getConn();
            statement = conn.createStatement();
            statementFile = conn.createStatement();
            //��ѯѧ����Ϣ
            String sqlForAllPro = "select * from project where project_status = '����' and project_state = '����' limit "+(numer-1)*size+","+size+";";
            System.out.println(sqlForAllPro);
            resultSet = statement.executeQuery(sqlForAllPro);
            if(resultSet != null) {
            		List<ProjectApplyObject> proList = new ArrayList<ProjectApplyObject>();
            		while(resultSet.next()) {
            			ProjectObject proObj = new ProjectObject();
            			RecruitmentObject recObj = new RecruitmentObject();
            			proObj.setPro_id(resultSet.getString("project_id"));
            			proObj.setPro_name(resultSet.getString("project_name"));
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
            			proObj.setPro_principal_id(resultSet.getString("project_principal_id"));
            			proObj.setPro_teacher_id(resultSet.getString("project_teacher_id"));
            			proObj.setPro_status(resultSet.getString("project_status"));
            			proObj.setStart_year(resultSet.getString("start_year"));
            			
            			statementRec = conn.createStatement();
            			String proId = proObj.getPro_id();
            			String recsql = "select * from recruitment where id = '" +  proId + "';";
            			System.out.println(recsql);
            			resultSetRec = statementRec.executeQuery(recsql);
            			if(resultSetRec.next()) {
            				recObj.setId(resultSetRec.getString("id"));
            				recObj.setState(resultSetRec.getString("state"));
            				recObj.setTitle(resultSetRec.getString("title"));
            			}
            			ProjectApplyObject proApplyObj = new ProjectApplyObject();
            			proApplyObj.setProObj(proObj);
            			proApplyObj.setRecObj(recObj);
            			proList.add(proApplyObj);
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
	public ProStuTeaAndRev findSpePro(String proId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		Statement statementStu = null;
		Statement statementTea = null;
		Statement statementProRev = null;
		Statement statementRev = null;
		
		ResultSet resultSet = null;
		ResultSet resultSetStu = null;
		ResultSet resultSetTea = null;
		ResultSet resultSetProRev = null;
		ResultSet resultSetRev = null;
		
		Statement statementFile = null;
		ResultSet resultSetFile = null;
		
		ProjectObject proObj = new ProjectObject();
		UserObject stuObj = new UserObject();
		UserObject teaObj = new UserObject();
		ProRevObject proRevObj = new ProRevObject();
		UserObject revObj = new UserObject();
		
		System.out.println(proId);
		try {
			conn=getConn();
            statement = conn.createStatement();
            statementFile = conn.createStatement();
           
            String sqlPro = "select * from project where project_id = '" +  proId + "';";
            System.out.println(sqlPro);
            // ��Ŀ
            resultSet = statement.executeQuery(sqlPro);
            
			if(resultSet.next()) {
				statementStu = conn.createStatement();
				// ѧ��
				String stuId = resultSet.getString("project_principal_id");
				String sqlStu = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  stuId + "';";
				resultSetStu = statementStu.executeQuery(sqlStu);
				
				statementTea = conn.createStatement();
				// ��ʦ
				String teaId = resultSet.getString("project_teacher_id");
				String sqlTea = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  teaId + "';";
				resultSetTea = statementTea.executeQuery(sqlTea);
				
				statementProRev = conn.createStatement();
				// ��Ŀ������
				System.out.println(proId);
				String sqlReview = "select * from project_review where review_type = '����' and project_id = '" +  proId + "';";
				resultSetProRev = statementProRev.executeQuery(sqlReview);

				
				if(resultSetProRev.next()) {
					String reviewId = resultSetProRev.getString("review_user_id");
					System.out.println(reviewId);
					statementRev = conn.createStatement();
					// ��������Ϣ
					String sqlReInfo = "select user_name from reviewer where user_id = '" +  reviewId + "';";
					resultSetRev = statementRev.executeQuery(sqlReInfo);

					
					// ��Ŀ������Ϣ
					proRevObj.setId(resultSetProRev.getString("review_user_id"));
					proRevObj.setReview_code(resultSetProRev.getString("review_code"));
					
					resultSetRev.next();
					// ��������Ϣ
					revObj.setUser_name(resultSetRev.getString("user_name"));
					
				}else {
					proRevObj = null;
					revObj = null;
				}

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
				
				resultSetStu.next();
				// ѧ����Ϣ
				stuObj.setUser_id(resultSetStu.getString("user_id"));
				stuObj.setUser_name(resultSetStu.getString("user_name"));
				stuObj.setUser_mail(resultSetStu.getString("user_mail"));
				stuObj.setUser_phone(resultSetStu.getString("user_phone"));
				
				resultSetTea.next();
				// ��ʦ��Ϣ
				teaObj.setUser_id(resultSetTea.getString("user_id"));
				teaObj.setUser_name(resultSetTea.getString("user_name"));
				teaObj.setUser_mail(resultSetTea.getString("user_mail"));
				teaObj.setUser_phone(resultSetTea.getString("user_phone"));
				
				
				
				
				//��Ŀѧ����ʦ����
				ProStuTeaAndRev pst = new ProStuTeaAndRev();
				pst.setProObj(proObj);
				pst.setStuObj(stuObj);
				pst.setTeaObj(teaObj);
				pst.setProrevObj(proRevObj);
				pst.setReviewerObj(revObj);
				
				return pst;
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
			closeConn(resultSetStu,statement, conn);
			closeConn(resultSetTea,statement, conn);
		}
	}

	@Override
	public int updatePro(ProjectObject proObj) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //UPDATE goods g INNER JOIN goods_price p on g.id=p.goods_id set p.price=p.price*0.5,g.deleted_at=unix_timestamp(now()) where g.is_deleted_at is null;
            String updsql = "update project set project_name = ?,plan_number = ?,start_year = ?,finish_year = ?,project_principal_id = ?,project_teacher_id = ? where project_id = ?";
            stat=conn.prepareStatement(updsql);
            stat.setString(1, proObj.getPro_name());
            stat.setString(2, proObj.getPlan_num());
            stat.setString(3, proObj.getStart_year());
            stat.setString(4, proObj.getFinish_year());
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
	public boolean reProState(String proId, String state) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //UPDATE goods g INNER JOIN goods_price p on g.id=p.goods_id set p.price=p.price*0.5,g.deleted_at=unix_timestamp(now()) where g.is_deleted_at is null;
            String updsql = "update project set project_status = ? where project_id = ?";
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

	// �߼������
	@Override
	public boolean delPro(String proId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String delsql = "delete from project where project_id= '" +proId+"';";
            int row = statement.executeUpdate(delsql);
            if(row > 0) {
            		return true;
            }
            else {
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
	public boolean insertPro(ProjectObject proObj) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //UPDATE goods g INNER JOIN goods_price p on g.id=p.goods_id set p.price=p.price*0.5,g.deleted_at=unix_timestamp(now()) where g.is_deleted_at is null;
            String updsql = "insert into project (project_id,project_status,project_name,project_teacher_id,project_principal_id,plan_number,start_year,finish_year,project_state) values('"
            		+ proObj.getPro_id() +"','"
            		+proObj.getPro_status()+"','"
            		+proObj.getPro_name()+"','"
            		+proObj.getPro_teacher_id()+"','"
            		+proObj.getPro_principal_id()+"','"
            		+proObj.getPlan_num()+"','"
            		+proObj.getStart_year()+"','"
            		+proObj.getFinish_year()+"','"
            		+proObj.getPro_state()+"');";
            int row = statement.executeUpdate(updsql);
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
	
	@Override
	public boolean reRecState(RecruitmentObject recObj) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		PreparedStatement statRec = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            if(recObj.getState().equals("����")) {
            	String updsql = "update recruitment set publish_user = ?, publish_time = ?, state = ? where id = ?";
	            stat=conn.prepareStatement(updsql);
	            stat.setString(1, recObj.getPublish_user());
	            stat.setString(2, recObj.getPublish_time());
	            stat.setString(3,recObj.getState());
	            stat.setString(4,recObj.getId());
	            int row = stat.executeUpdate();
	            if(row > 0) {
	            		return true;
	            }else {
	            		return false;
	            }
	        }
	        else {
            	String updsql = "update recruitment set end_time = ?, state = ? where id = ?";
            	statRec=conn.prepareStatement(updsql);
            	statRec.setString(1, recObj.getEnd_time());
            	statRec.setString(2,recObj.getState());
            	statRec.setString(3,recObj.getId());
	            int row = statRec.executeUpdate();
	            if(row > 0) {
	            		return true;
	            }else {
	            		return false;
	            }
	        }
            
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}
	
	/*--------------------�����ǿ�̫��ӵĶ���Ŀ�����ļ��Ĳ�����Ӧ�ù�����--------------------*/

	//�ϴ��ļ��������ļ���Ϣ��������
	@Override
	public Integer ProjectSignUpUploadFiles(String id, String time, String state, String path, String type, String name){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();

			//�ж���Ҫִ����Щ����
			Integer flag = 0;

			Integer msg=0;

			//����ȡ��date�ַ���ת����Date����
			Date dateValue=Date.valueOf(time);

			//�ȶ�ԭ���ı���в�ѯ�����Ƿ����и��ļ�
			//������Ը��ļ����и���
			//������ִ�в����¼�¼
			String sqlForQuery = "SELECT * FROM project_files WHERE project_id = '"+id+"' and file_path = '"+path+"';";
			resultSet = statement.executeQuery(sqlForQuery);
			if (resultSet!=null&&resultSet.next()){
				flag=1;
			}else
				flag=2;

			if(flag==1){
				String sqlForUpdate = "UPDATE project_files SET submit_time = '"+dateValue+"',state ='"+state+"',file_type = '"+type+"',file_name = '"+name+"' WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}

			if (flag==2){
				//�����µ�һ���ļ���ַ
				String sqlForAdd = "INSERT INTO project_files VALUES ('"
						+ id + "','"
						+ dateValue + "','"
						+ state + "','"
						+ path + "','"
						+ type + "','"
						+ name + "');" ;
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForAdd);
				System.out.println(msg+"�����¼�¼���");

			}

			return msg;

		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//�ض�id��state��type��ѯ�����ϴ��ļ�
	public List<ProjectFileObject> ProjectSignUpQueryByStateAndTypeFiles(String id, String state, String type){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<ProjectFileObject> list = new ArrayList<ProjectFileObject>();

		try {
			conn=getConn();
			statement = conn.createStatement();
			//��ѯ���з����������ļ�
			String sqlForQueryFiles = "SELECT * FROM project_files WHERE  state = '"+state+"' " +
					"and file_type = '"+type+"' and project_id = '"+id+"';";
			System.out.println("conn succeed");
			resultSet = statement.executeQuery(sqlForQueryFiles);
			if(resultSet!=null){
				while(resultSet.next()) {
					ProjectFileObject psf=new ProjectFileObject();
					psf.setId(resultSet.getString("project_id"));
					psf.setSubmitTime(resultSet.getDate("submit_time"));
					psf.setState(resultSet.getString("state"));
					psf.setFilePath(resultSet.getString("file_path"));
					psf.setFileType(resultSet.getString("file_type"));
					psf.setFileName(resultSet.getString("file_name"));
					list.add(psf);
				}
			}else {
				//�������κι���
				return null;
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//�ض�id��state��ѯ�����ϴ��ļ�
	public List<ProjectFileObject> ProjectSignUpQueryByStateFiles(String id, String state){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<ProjectFileObject> list = new ArrayList<ProjectFileObject>();

		try {
			conn=getConn();
			statement = conn.createStatement();
			//��ѯ���з����������ļ�
			String sqlForQueryFiles = "SELECT * FROM project_files WHERE  state = '"+state+"' and project_id = '"+id+"';";
			System.out.println("conn succeed");
			resultSet = statement.executeQuery(sqlForQueryFiles);
			if(resultSet!=null){
				while(resultSet.next()) {
					ProjectFileObject psf=new ProjectFileObject();
					psf.setId(resultSet.getString("project_id"));
					psf.setSubmitTime(resultSet.getDate("submit_time"));
					psf.setState(resultSet.getString("state"));
					psf.setFilePath(resultSet.getString("file_path"));
					psf.setFileType(resultSet.getString("file_type"));
					psf.setFileName(resultSet.getString("file_name"));
					list.add(psf);
				}
			}else {
				//�������κι���
				return null;
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//�ض�id��type��ѯ�����ϴ��ļ�
	public List<ProjectFileObject> ProjectSignUpQueryByTypeFiles(String id, String type){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<ProjectFileObject> list = new ArrayList<ProjectFileObject>();

		try {
			conn=getConn();
			statement = conn.createStatement();
			String sqlForQueryFiles = "";
			if(type.equals("all")) {
				//��ѯ���з����������ļ�
				sqlForQueryFiles = "SELECT * FROM project_files WHERE project_id = '"+id+"';";
			}else {
				//��ѯ���з����������ļ�
				sqlForQueryFiles = "SELECT * FROM project_files WHERE  file_type = '"+type+"' and project_id = '"+id+"';";	
			}
			System.out.println("conn succeed");
			resultSet = statement.executeQuery(sqlForQueryFiles);
			if(resultSet!=null){
				while(resultSet.next()) {
					ProjectFileObject psf=new ProjectFileObject();
					psf.setId(resultSet.getString("project_id"));
					psf.setSubmitTime(resultSet.getDate("submit_time"));
					psf.setState(resultSet.getString("state"));
					psf.setFilePath(resultSet.getString("file_path"));
					psf.setFileType(resultSet.getString("file_type"));
					psf.setFileName(resultSet.getString("file_name"));
					list.add(psf);
				}
			}else {
				//�������κι���
				return null;
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//�ض�id��ѯ�����ϴ��ļ�
	public List<ProjectFileObject> ProjectSignUpQueryAllFiles(String id){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<ProjectFileObject> list = new ArrayList<ProjectFileObject>();

		try {
			conn=getConn();
			statement = conn.createStatement();
			//��ѯ���з����������ļ�
			String sqlForQueryFiles = "SELECT * FROM project_files WHERE  project_id = '"+id+"';";
			System.out.println("conn succeed");
			resultSet = statement.executeQuery(sqlForQueryFiles);
			if(resultSet!=null){
				while(resultSet.next()) {
					ProjectFileObject psf=new ProjectFileObject();
					psf.setId(resultSet.getString("project_id"));
					psf.setSubmitTime(resultSet.getDate("submit_time"));
					psf.setState(resultSet.getString("state"));
					psf.setFilePath(resultSet.getString("file_path"));
					psf.setFileType(resultSet.getString("file_type"));
					psf.setFileName(resultSet.getString("file_name"));
					list.add(psf);
				}
			}else {
				//�������κι���
				return null;
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//�ض�id��path��ѯ�����ϴ��ļ�
	public ProjectFileObject ProjectSignUpQueryOneFile(String id,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		ProjectFileObject pfo = new ProjectFileObject();
		pfo=null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//��ѯ���й���
			String sqlForQueryOne = "SELECT * FROM project_files WHERE project_id='"+id+"' and file_path = '"+path+"';";
			System.out.println("conn succeed");
			resultSet = statement.executeQuery(sqlForQueryOne);
			if(resultSet!=null){
				while(resultSet.next()) {
					pfo.setId(resultSet.getString("project_id"));
					pfo.setSubmitTime(resultSet.getDate("submit_time"));
					pfo.setState(resultSet.getString("state"));
					pfo.setFilePath(resultSet.getString("file_path"));
					pfo.setFileType(resultSet.getString("file_type"));
					pfo.setFileName(resultSet.getString("file_name"));
				}
			}else {
				//�������κι���
				return null;
			}
			return pfo;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//ɾ�������ص�id�ϴ��ļ�
	public Integer ProjectSignUpDeleteAllFiles(String id){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//ɾ�������ļ�
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"ɾ�����");
			return msgFile;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//ɾ���ض�id��state�����ļ�
	public Integer ProjectSignUpDeleteByStateFiles(String id,String state){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//ɾ�������ļ�
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"' and state ='"+state+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"ɾ�����");
			return msgFile;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//ɾ���ض�id��file_type�����ļ�
	public Integer ProjectSignUpDeleteByTypeFiles(String id,String type){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//ɾ�������ļ�
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"' and file_type = '"+type+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"ɾ�����");
			return msgFile;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//ɾ���ض�id��state��file_type�����ļ�
	public Integer ProjectSignUpDeleteByStateAndTypeFiles(String id,String state,String type){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//ɾ�������ļ�
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"' and state = '"+state+"' and file_type = '"+type+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"ɾ�����");
			return msgFile;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//ɾ�������ϴ��ļ�
	public Integer ProjectSignUpDeleteOneFile(String id,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//ɾ�������ļ�
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"' and file_path = '"+path+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"ɾ�����");
			return msgFile;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//�����ض�id�����ϴ��ļ���state��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
	public Integer ProjectSignUpUpdateAllFilesState(String id,String state,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();

			Integer msg=0;

			if(path.equals("*")){
				String sqlForUpdate = "UPDATE project_files SET state ='"+state+"' WHERE project_id='"+id+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}else {
				String sqlForUpdate = "UPDATE project_files SET state ='"+state+"' WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}


			return msg;

		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//�����ض�id�����ϴ��ļ���type��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
	public Integer ProjectSignUpUpdateAllFilesType(String id,String type,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();

			Integer msg=0;

			if(path.equals("*")){
				String sqlForUpdate = "UPDATE project_files SET file_type ='"+type+"' WHERE project_id='"+id+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}else {
				String sqlForUpdate = "UPDATE project_files SET file_type ='"+type+"' WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}


			return msg;

		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//�����ض�id�����ϴ��ļ���state��type��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
	public Integer ProjectSignUpUpdateAllFilesStateAndType(String id,String state,String type,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();

			Integer msg=0;

			if (path.equals("*")){
				String sqlForUpdate = "UPDATE project_files SET state ='"+state+"',file_type = '"+type+"'  WHERE project_id='"+id+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}else {
				String sqlForUpdate = "UPDATE project_files SET state ='"+state+"',file_type = '"+type+"'  WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}


			return msg;

		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//�����ض�id�����ϴ��ļ���submit_time��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
	public Integer ProjectSignUpUpdateAllFilesSubmitTime(String id,String time,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();

			Integer msg=0;

			Date dateValue=Date.valueOf(time);

			if (path.equals("*")){
				String sqlForUpdate = "UPDATE project_files SET  submit_time = '"+dateValue+"' WHERE project_id='"+id+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}else {
				String sqlForUpdate = "UPDATE project_files SET  submit_time = '"+dateValue+"' WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}


			return msg;

		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}


	/*--------------------���ǿ�̫��Ľ�����--------------------*/
/*--------------------------------------------------��ʦ��------------------------------------------------------*/
	
	@Override
	public List<ProjectObject> findAllProInTeacher(String pageNum, String pageSize, String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		Statement statementFile = null;
		ResultSet resultSetFile = null;
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		
		try {
			conn=getConn();
            statement = conn.createStatement();
            statementFile = conn.createStatement();
            //��ѯѧ����Ϣ
            String sqlForAllPro = "select * from project where project_status = '����' and project_state = '����' "
            		+ "and project_teacher_id =  '" +  userId + "' limit "+(numer-1)*size+","+size+";";
            System.out.println(sqlForAllPro);
            resultSet = statement.executeQuery(sqlForAllPro);
            if(resultSet != null) {
            		List<ProjectObject> proList = new ArrayList<ProjectObject>();
            		while(resultSet.next()) {
            			ProjectObject proObj = new ProjectObject();
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
            			proObj.setStart_year(resultSet.getString("start_year"));
            			proObj.setFinish_year(resultSet.getString("finish_year"));
            			proList.add(proObj);
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
	
	public int getTeaCount(String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //��ѯѧ����Ϣ
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
	public List<ProStuTeaAndRev> findAllStuProApply(String pageNum, String pageSize,String userId) {
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
            String sql = "select * from project left join project_student on project.project_id=project_student.project_id where project_status = '����' and is_in_service='����' and user_id='"+userId+"' limit "+(numer-1)*size+","+size+";";
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
            			
        				//ѧ��
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
