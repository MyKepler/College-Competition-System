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
            //查询学生信息
            String sqlForAllPro = "select count(*) from project where project_status = '申请' and project_state = '可用'";
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
	//查询特定id的项目下的已报名审核成功的人数
		@Override
		public int projectActNumber(String id){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				conn=getConn();
				statement = conn.createStatement();
				String sqlForQueryAll = "select count(*) from project_student where project_id = '"+id+"' and is_in_service = '可用';";
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
            //查询学生信息
            String sqlForAllPro = "select * from project where project_status = '申请' and project_state = '可用' limit "+(numer-1)*size+","+size+";";
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
    					//查询所有符合条件的文件
    					String sqlForQueryFiles = "SELECT * FROM project_files WHERE  file_type = '申请' and project_id = '"+id+"';";
    					resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
    					if(resultSetFile.next()) {
    						proObj.setIs_submit("已提交");
    					}else {
    						proObj.setIs_submit("未提交");
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
            // 项目
            resultSet = statement.executeQuery(sqlPro);
            
			if(resultSet.next()) {
				statementStu = conn.createStatement();
				// 学生
				String stuId = resultSet.getString("project_principal_id");
				String sqlStu = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  stuId + "';";
				resultSetStu = statementStu.executeQuery(sqlStu);
				
				statementTea = conn.createStatement();
				// 教师
				String teaId = resultSet.getString("project_teacher_id");
				String sqlTea = "select user_id,user_name,user_mail,user_phone from user where user_id = '" +  teaId + "';";
				resultSetTea = statementTea.executeQuery(sqlTea);
				
				statementProRev = conn.createStatement();
				// 项目评审人
				System.out.println(proId);
				String sqlReview = "select * from project_review where review_type = '申请' and project_id = '" +  proId + "';";
				resultSetProRev = statementProRev.executeQuery(sqlReview);

				
				if(resultSetProRev.next()) {
					String reviewId = resultSetProRev.getString("review_user_id");
					System.out.println(reviewId);
					statementRev = conn.createStatement();
					// 评审人信息
					String sqlReInfo = "select user_name from reviewer where user_id = '" +  reviewId + "';";
					resultSetRev = statementRev.executeQuery(sqlReInfo);

					
					// 项目评审信息
					proRevObj.setId(resultSetProRev.getString("review_user_id"));
					proRevObj.setReview_code(resultSetProRev.getString("review_code"));
					
					resultSetRev.next();
					// 评审人信息
					revObj.setUser_name(resultSetRev.getString("user_name"));
					
				}else {
					proRevObj = null;
					revObj = null;
				}

				// 项目信息
				proObj.setPro_id(resultSet.getString("project_id"));
				
				String id = resultSet.getString("project_id");
    				System.out.println(id);
				//查询所有符合条件的文件
				String sqlForQueryFiles = "SELECT * FROM project_files WHERE  file_type = '申请' and project_id = '"+id+"';";
				resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
				if(resultSetFile.next()) {
					proObj.setIs_submit("已提交");
					proObj.setSubmit_time(resultSetFile.getDate("submit_time"));
				}else {
					proObj.setIs_submit("未提交");
				}				
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
				
				resultSetStu.next();
				// 学生信息
				stuObj.setUser_id(resultSetStu.getString("user_id"));
				stuObj.setUser_name(resultSetStu.getString("user_name"));
				stuObj.setUser_mail(resultSetStu.getString("user_mail"));
				stuObj.setUser_phone(resultSetStu.getString("user_phone"));
				
				resultSetTea.next();
				// 教师信息
				teaObj.setUser_id(resultSetTea.getString("user_id"));
				teaObj.setUser_name(resultSetTea.getString("user_name"));
				teaObj.setUser_mail(resultSetTea.getString("user_mail"));
				teaObj.setUser_phone(resultSetTea.getString("user_phone"));
				
				
				
				
				//项目学生老师对象
				ProStuTeaAndRev pst = new ProStuTeaAndRev();
				pst.setProObj(proObj);
				pst.setStuObj(stuObj);
				pst.setTeaObj(teaObj);
				pst.setProrevObj(proRevObj);
				pst.setReviewerObj(revObj);
				
				return pst;
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

	// 逻辑错误的
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
            if(recObj.getState().equals("发布")) {
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
	
	/*--------------------以下是孔太秀加的对项目管理文件的操作，应该够用了--------------------*/

	//上传文件，保存文件信息到服务器
	@Override
	public Integer ProjectSignUpUploadFiles(String id, String time, String state, String path, String type, String name){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();

			//判断需要执行那些操作
			Integer flag = 0;

			Integer msg=0;

			//将获取的date字符串转化成Date数据
			Date dateValue=Date.valueOf(time);

			//先对原来的表进行查询，看是否已有该文件
			//若有则对该文件进行更新
			//若无则执行插入新记录
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
				System.out.println(msg+"修改完毕");
			}

			if (flag==2){
				//储存新的一条文件地址
				String sqlForAdd = "INSERT INTO project_files VALUES ('"
						+ id + "','"
						+ dateValue + "','"
						+ state + "','"
						+ path + "','"
						+ type + "','"
						+ name + "');" ;
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForAdd);
				System.out.println(msg+"插入新记录完毕");

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

	//特定id、state和type查询所有上传文件
	public List<ProjectFileObject> ProjectSignUpQueryByStateAndTypeFiles(String id, String state, String type){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<ProjectFileObject> list = new ArrayList<ProjectFileObject>();

		try {
			conn=getConn();
			statement = conn.createStatement();
			//查询所有符合条件的文件
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
				//不存在任何公告
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

	//特定id和state查询所有上传文件
	public List<ProjectFileObject> ProjectSignUpQueryByStateFiles(String id, String state){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<ProjectFileObject> list = new ArrayList<ProjectFileObject>();

		try {
			conn=getConn();
			statement = conn.createStatement();
			//查询所有符合条件的文件
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
				//不存在任何公告
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

	//特定id和type查询所有上传文件
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
				//查询所有符合条件的文件
				sqlForQueryFiles = "SELECT * FROM project_files WHERE project_id = '"+id+"';";
			}else {
				//查询所有符合条件的文件
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
				//不存在任何公告
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

	//特定id查询所有上传文件
	public List<ProjectFileObject> ProjectSignUpQueryAllFiles(String id){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<ProjectFileObject> list = new ArrayList<ProjectFileObject>();

		try {
			conn=getConn();
			statement = conn.createStatement();
			//查询所有符合条件的文件
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
				//不存在任何公告
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

	//特定id和path查询单个上传文件
	public ProjectFileObject ProjectSignUpQueryOneFile(String id,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		ProjectFileObject pfo = new ProjectFileObject();
		pfo=null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//查询所有公告
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
				//不存在任何公告
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

	//删除所有特点id上传文件
	public Integer ProjectSignUpDeleteAllFiles(String id){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//删除所有文件
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"删除完毕");
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

	//删除特定id和state所有文件
	public Integer ProjectSignUpDeleteByStateFiles(String id,String state){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//删除所有文件
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"' and state ='"+state+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"删除完毕");
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

	//删除特定id和file_type所有文件
	public Integer ProjectSignUpDeleteByTypeFiles(String id,String type){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//删除所有文件
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"' and file_type = '"+type+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"删除完毕");
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

	//删除特定id和state和file_type所有文件
	public Integer ProjectSignUpDeleteByStateAndTypeFiles(String id,String state,String type){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//删除所有文件
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"' and state = '"+state+"' and file_type = '"+type+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"删除完毕");
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

	//删除单个上传文件
	public Integer ProjectSignUpDeleteOneFile(String id,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//删除所有文件
			String sqlForDeleteFile = "DELETE FROM project_files WHERE project_id='"+id+"' and file_path = '"+path+"';";
			System.out.println("conn succeed");
			Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
			System.out.println(msgFile+"删除完毕");
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

	//更新特定id所有上传文件的state（path为*则为全部文件，path为可用地址则为单个文件）
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
				System.out.println(msg+"修改完毕");
			}else {
				String sqlForUpdate = "UPDATE project_files SET state ='"+state+"' WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"修改完毕");
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

	//更新特定id所有上传文件的type（path为*则为全部文件，path为可用地址则为单个文件）
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
				System.out.println(msg+"修改完毕");
			}else {
				String sqlForUpdate = "UPDATE project_files SET file_type ='"+type+"' WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"修改完毕");
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

	//更新特定id所有上传文件的state和type（path为*则为全部文件，path为可用地址则为单个文件）
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
				System.out.println(msg+"修改完毕");
			}else {
				String sqlForUpdate = "UPDATE project_files SET state ='"+state+"',file_type = '"+type+"'  WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"修改完毕");
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

	//更新特定id所有上传文件的submit_time（path为*则为全部文件，path为可用地址则为单个文件）
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
				System.out.println(msg+"修改完毕");
			}else {
				String sqlForUpdate = "UPDATE project_files SET  submit_time = '"+dateValue+"' WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"修改完毕");
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


	/*--------------------我是孔太秀的结束线--------------------*/
/*--------------------------------------------------教师端------------------------------------------------------*/
	
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
            //查询学生信息
            String sqlForAllPro = "select * from project where project_status = '申请' and project_state = '可用' "
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
	    				//查询所有符合条件的文件
	    				String sqlForQueryFiles = "SELECT * FROM project_files WHERE  file_type = '中期' and project_id = '"+id+"';";
	    				resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
	    				if(resultSetFile.next()) {
	    					proObj.setIs_submit("已提交");
	    					proObj.setSubmit_time(resultSetFile.getDate("submit_time"));
	    				}else {
	    					proObj.setIs_submit("未提交");
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
            //查询学生信息
            String sqlForAllPro = "select count(*) from project where project_status = '申请' and project_state = '可用' "
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
/*--------------------------------------------------学生端------------------------------------------------------*/
	
	@Override
	public int getCount(String userId) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select count(*) from project left join project_student on project.project_id=project_student.project_id where project_status='申请' and is_in_service='可用' and user_id='"+userId+"';";
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
            String sql = "select * from project left join project_student on project.project_id=project_student.project_id where project_status = '申请' and is_in_service='可用' and user_id='"+userId+"' limit "+(numer-1)*size+","+size+";";
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
            			
        				//学生
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
	
}
