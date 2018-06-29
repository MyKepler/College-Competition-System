package com.yhcj.Dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.Project;
import com.yhcj.enity.*;
import com.yhcj.utils.DBUtil;



public class ProReviewImpl extends DBUtil implements ProReview{

	@Override
	public int getCount() {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select count(*) from project left join project_review on project.project_id = project_review.project_id where project_status=review_type and project_state = '可用'";
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
	public List<ProStuTeaAndRev> findProReview(String pageNum, String pageSize) {
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
            String sqlForAllPro = "select * from project left join project_review on project.project_id = project_review.project_id where project_status=review_type and project_state = '可用' limit "+(numer-1)*size+","+size+";";
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
	public ProStuTeaAndRev findSpeProReview(String proId) {
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
	public ProRevObject findRevbytime(String proId, String proStatus) {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		Statement statementRev = null;
		ResultSet resultRev = null;
		
		try {
			conn=getConn();
			ProRevObject reviewObj = new ProRevObject();
            statement = conn.createStatement();
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
				resultRev = statementRev.executeQuery(sqlRev);
				if(resultRev.next()) {
					reviewObj.setReview_user_name(resultRev.getString("user_name"));
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
		}
	
	}
	
	@Override
	public int updateProReview(ProRevObject prorevObj) {
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

	@Override
	public boolean delProReview(String proId) {
		// TODO 自动生成的方法存根
		return false;
	}

	/*--------------------以下是孔太秀加的对项目评审管理文件的操作，应该够用了--------------------*/

	//上传文件，保存文件信息到服务器
	public Integer ProjectReviewUploadFiles(String id,String path,String type,String name){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();

			//判断需要执行那些操作
			Integer flag = 0;

			Integer msg=0;

			//先对原来的表进行查询，看是否已有该文件
			//若有则对该文件进行更新
			//若无则执行插入新记录
			String sqlForQuery = "SELECT * FROM review_files WHERE project_id = '"+id+"' and file_path = '"+path+"';";
			resultSet = statement.executeQuery(sqlForQuery);
			if (resultSet!=null&&resultSet.next()){
				flag=1;
			}else
				flag=2;

			if(flag==1){
				String sqlForUpdate = "UPDATE review_files SET file_type = '"+type+"',file_name = '"+name+"' WHERE project_id='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"修改完毕");
			}

			if (flag==2){
				//储存新的一条文件地址
				String sqlForAdd = "INSERT INTO review_files VALUES ('"
						+ id + "','"
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

	//特定id和type查询所有上传文件
	public List<ProjectReviewFileObject> ProjectReviewQueryByTypeFiles(String id, String type){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<ProjectReviewFileObject> list = new ArrayList<ProjectReviewFileObject>();

		try {
			conn=getConn();
			statement = conn.createStatement();
			String sqlForQueryFiles ="";
			//查询所有符合条件的文件
			if(type.equals("all")) {
				sqlForQueryFiles = "SELECT * FROM review_files WHERE project_id = '"+id+"';";
			}else {
				sqlForQueryFiles = "SELECT * FROM review_files WHERE  file_type = '"+type+"' and project_id = '"+id+"';";
			}
			
			System.out.println("conn succeed");
			resultSet = statement.executeQuery(sqlForQueryFiles);
			if(resultSet!=null){
				while(resultSet.next()) {
					ProjectReviewFileObject prf=new ProjectReviewFileObject();
					prf.setId(resultSet.getString("project_id"));
					prf.setFilePath(resultSet.getString("file_path"));
					prf.setFileType(resultSet.getString("file_type"));
					prf.setFileName(resultSet.getString("file_name"));
					list.add(prf);
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
	public List<ProjectReviewFileObject> ProjectReviewQueryAllFiles(String id){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<ProjectReviewFileObject> list = new ArrayList<ProjectReviewFileObject>();

		try {
			conn=getConn();
			statement = conn.createStatement();
			//查询所有符合条件的文件
			String sqlForQueryFiles = "SELECT * FROM review_files WHERE  project_id = '"+id+"';";
			System.out.println("conn succeed");
			resultSet = statement.executeQuery(sqlForQueryFiles);
			if(resultSet!=null){
				while(resultSet.next()) {
					ProjectReviewFileObject prf=new ProjectReviewFileObject();
					prf.setId(resultSet.getString("project_id"));
					prf.setFilePath(resultSet.getString("file_path"));
					prf.setFileType(resultSet.getString("file_type"));
					prf.setFileName(resultSet.getString("file_name"));
					list.add(prf);
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

	//查询单个上传文件
	public ProjectReviewFileObject ProjectReviewQueryOneFile(String id,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		ProjectReviewFileObject prf = new ProjectReviewFileObject();
		prf = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//查询所有公告
			String sqlForQueryOne = "SELECT * FROM review_files WHERE project_id='"+id+"' and file_path = '"+path+"';";
			System.out.println("conn succeed");
			resultSet = statement.executeQuery(sqlForQueryOne);
			if(resultSet!=null){
				while(resultSet.next()) {
					prf.setId(resultSet.getString("project_id"));
					prf.setFilePath(resultSet.getString("file_path"));
					prf.setFileType(resultSet.getString("file_type"));
					prf.setFileName(resultSet.getString("file_name"));
				}
			}else {
				//不存在任何公告
				return null;
			}
			return prf;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	//删除特定id所有上传文件
	public Integer ProjectReviewDeleteAllFiles(String id){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//删除所有文件
			String sqlForDeleteFile = "DELETE FROM review_files WHERE project_id='"+id+"';";
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
	public Integer ProjectReviewDeleteByTypeFiles(String id,String type){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//删除所有文件
			String sqlForDeleteFile = "DELETE FROM review_files WHERE project_id='"+id+"' and file_type = '"+type+"';";
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
	public Integer ProjectReviewDeleteOneFile(String id,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();
			//删除所有文件
			String sqlForDeleteFile = "DELETE FROM review_files WHERE project_id='"+id+"' and file_path = '"+path+"';";
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

	//更新特定id所有上传文件的type（path为*则为全部文件，path为可用地址则为单个文件）
	public Integer ProjectReviewUpdateAllFilesType(String id,String type,String path){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();

			Integer msg=0;

			if(path.equals("*")){
				String sqlForUpdate = "UPDATE review_files SET file_type ='"+type+"' WHERE project_id='"+id+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"修改完毕");
			}else {
				String sqlForUpdate = "UPDATE review_files SET file_type ='"+type+"' WHERE project_id='"+id+"' and file_path = '"+path+"';";
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
	
	//列出该教师指导的所有项目（可用）的评审结果
	@Override
	public List<ProStuTeaAndRev> findProReviewByTea(String pageNum, String pageSize, String userId) {
		// TODO Auto-generated method stub
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
            String sqlForAllPro = "select * from project left join project_review on project.project_id = project_review.project_id "
            		+ "where project_status=review_type and project_state = '可用' and project_teacher_id =  '" +  userId + "' "
            				+ "limit "+(numer-1)*size+","+size+";";
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
	public int getTeaCount(String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlForAllPro = "select count(*) from project left join project_review on project.project_id = project_review.project_id "
            		+ "where project_status=review_type and project_state = '可用' and project_teacher_id =  '" +  userId + "';";
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
            String sqlForAllPro = "select count(*) from project,project_review,project_student where project.project_id = project_review.project_id and project.project_id = project_student.project_id and project_status=review_type and is_in_service='可用' and user_id='"+userId+"';";
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
	public List<ProStuTeaAndRev> findAllStuProReview(String pageNum, String pageSize,String userId) {
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
            String sql = "select * from project,project_review,project_student where project.project_id = project_review.project_id and project.project_id = project_student.project_id and project_status=review_type and is_in_service='可用' and user_id='"+userId+"' limit "+(numer-1)*size+","+size+";";
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

	
}
