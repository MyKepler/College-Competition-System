package com.yhcj.Dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.Student;
import com.yhcj.enity.SignUpEnableProjectObject;
import com.yhcj.enity.SignUpJoinProjectObject;
import com.yhcj.enity.SignUperObject;
import com.yhcj.enity.StudentObject;
import com.yhcj.enity.StudentSignUpFileObject;
import com.yhcj.utils.DBUtil;

public class StudentImpl extends DBUtil implements Student{
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
            String sqlForAllStu = "select count(*) from student left join user on user.user_id = student.user_id;";
            System.out.println(sqlForAllStu);
            resultSet = statement.executeQuery(sqlForAllStu);
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
	public List<StudentObject> findAllStu(String pageNum,String pageSize) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		int numer = Integer.parseInt(pageNum);
		int size	 = Integer.parseInt(pageSize);
		
		try {
			conn=getConn();
            statement = conn.createStatement();
            //��ѯѧ����Ϣ
            String sqlForAllStu = "select * from student left join user on user.user_id = student.user_id limit "+(numer-1)*size+","+size+";";
            System.out.println(sqlForAllStu);
            resultSet = statement.executeQuery(sqlForAllStu);
            if(resultSet != null) {
            		List<StudentObject> stuList = new ArrayList<StudentObject>();
            		while(resultSet.next()) {
            			StudentObject stuObj = new StudentObject();
            			stuObj.setUser_id(resultSet.getString("user_id"));
            			stuObj.setUser_name(resultSet.getString("user_name"));
            			stuObj.setUser_phone(resultSet.getString("user_phone"));
            			stuObj.setAccount_state(resultSet.getString("account_state"));
            			stuList.add(stuObj);
            		}
            		return stuList;
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
	public StudentObject findSpeStu(String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String sqlStu = "select user.user_id, user_identity,user.user_pwd,user.user_name, user.user_sex, user.user_mail, user.user_phone, user.account_state, student.student_academy, student.student_major, student.student_class from user, student where user.user_id = student.user_id and user.user_id = "+userId+";" ;
            System.out.println(sqlStu);
            resultSet = statement.executeQuery(sqlStu);
			if(resultSet.next()) {
				StudentObject stuObj = new StudentObject();
				stuObj.setUser_id(resultSet.getString("user_id"));
				stuObj.setUser_name(resultSet.getString("user_name"));
				stuObj.setUser_sex(resultSet.getString("user_sex"));
				stuObj.setUser_mail(resultSet.getString("user_mail"));
				stuObj.setUser_phone(resultSet.getString("user_phone"));
				stuObj.setUser_identity(resultSet.getString("user_identity"));
				stuObj.setAccount_state(resultSet.getString("account_state"));
				stuObj.setUser_pwd(resultSet.getString("user_pwd"));
				stuObj.setStudent_academy(resultSet.getString("student_academy"));
				stuObj.setStudent_major(resultSet.getString("student_major"));
				stuObj.setStudent_class(resultSet.getString("student_class"));
				return stuObj;
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
		}

	}
	@Override
	public boolean delStu(String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String delsql = "delete user,student from user left join student on user.user_id = student.user_id where user.user_id = '" +userId+"';";
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
	public int updateStu(StudentObject stuObj) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement stat = null;
		PreparedStatement stat1 = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            //UPDATE goods g INNER JOIN goods_price p on g.id=p.goods_id set p.price=p.price*0.5,g.deleted_at=unix_timestamp(now()) where g.is_deleted_at is null;
            String updsql1 = "update user set user_name = ?,user_sex = ?,user_mail = ?,user_phone = ?,account_state = ?,user_identity = ? where user_id = ?";
            stat=conn.prepareStatement(updsql1);
            System.out.println(stuObj.getUser_sex());
            stat.setString(1, stuObj.getUser_name());
            stat.setString(2, stuObj.getUser_sex());
            stat.setString(3, stuObj.getUser_mail());
            stat.setString(4, stuObj.getUser_phone());
            stat.setString(5, stuObj.getAccount_state());
            stat.setString(6, stuObj.getUser_identity());
            stat.setString(7, stuObj.getUser_id());
            int row1 = stat.executeUpdate();
            
            String updsql2 = "update student set user_name = ?,student_academy = ?,student_major = ?,student_class = ? where user_id = ?";
            stat1=conn.prepareStatement(updsql2);
            stat1.setString(1, stuObj.getUser_name());
            stat1.setString(2, stuObj.getStudent_academy());
            stat1.setString(3, stuObj.getStudent_major());
            stat1.setString(4, stuObj.getStudent_class());
            stat1.setString(5, stuObj.getUser_id());
            int row2 = stat1.executeUpdate();
            
            
            if(row1 > 0 && row2 >0) {
            		return 3;
            }
            else if(row1 > 0){
            		return 1;
            }else if(row2 > 0) {
            		return 2;
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
	public boolean reStates(String userId,String state) {
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
	public Integer StudentSignUpUploadFiles(String id,String path,String name){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
			statement = conn.createStatement();

			//�ж���Ҫִ����Щ����
			Integer flag = 0;

			Integer msg=0;


			//�ȶ�ԭ���ı���в�ѯ�����Ƿ����и��ļ�
			//������Ը��ļ����и���
			//������ִ�в����¼�¼
			String sqlForQuery = "SELECT * FROM recruitment_sign_up_files WHERE sign_up_id = '"+id+"' and file_path = '"+path+"';";
			resultSet = statement.executeQuery(sqlForQuery);
			if (resultSet!=null&&resultSet.next()){
				flag=1;
			}else
				flag=2;

			if(flag==1){
				String sqlForUpdate = "UPDATE recruitment_sign_up_files file_name = '"+name+"' WHERE sign_up_id ='"+id+"' and file_path = '"+path+"';";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForUpdate);
				System.out.println(msg+"�޸����");
			}

			if (flag==2){
				//�����µ�һ���ļ���ַ
				String sqlForAdd = "INSERT INTO recruitment_sign_up_files VALUES ('"
						+ id + "','"
						+ path + "','"
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
	@Override
	//��ѯ�����ϴ��ļ�
	public List<StudentSignUpFileObject> StudentSignUpQueryAllFiles(String id){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;

			List<StudentSignUpFileObject> list = new ArrayList<StudentSignUpFileObject>();

			try {
				conn=getConn();
				statement = conn.createStatement();
				//��ѯ���з����������ļ�
				String sqlForQueryFiles = "SELECT * FROM recruitment_sign_up_files WHERE  sign_up_id = '"+id+"';";
				System.out.println("conn succeed");
				resultSet = statement.executeQuery(sqlForQueryFiles);
				if(resultSet!=null){
					while(resultSet.next()) {
						StudentSignUpFileObject sfo = new StudentSignUpFileObject();
						sfo.setSignUpId(resultSet.getString("sign_up_id"));
						sfo.setFilePath(resultSet.getString("file_path"));
						sfo.setFileName(resultSet.getString("file_name"));
						list.add(sfo);
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
	@Override
	//��ѯ�����ļ�
	public StudentSignUpFileObject StudentSignUpQueryOneFile(String id,String path){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;

			StudentSignUpFileObject sfo = new StudentSignUpFileObject();

			sfo = null;

			try {
				conn=getConn();
				statement = conn.createStatement();
				//��ѯ���з����������ļ�
				String sqlForQueryFiles = "SELECT * FROM recruitment_sign_up_files WHERE  sign_up_id = '"+id+"';";
				System.out.println("conn succeed");
				resultSet = statement.executeQuery(sqlForQueryFiles);
				if(resultSet!=null){
					while(resultSet.next()) {
						sfo.setSignUpId(resultSet.getString("sign_up_id"));
						sfo.setFilePath(resultSet.getString("file_path"));
						sfo.setFileName(resultSet.getString("file_name"));
					}
				}else {
					//�������κι���
					return null;
				}
				return sfo;
			}
			catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			finally{
				closeConn(resultSet,statement, conn);
			}
		}
	@Override
	//ɾ�������ϴ��ļ�
	public Integer StudentSignUpDeleteAllFiles(String id){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				conn=getConn();
				statement = conn.createStatement();
				//ɾ�������ļ�
				String sqlForDeleteFile = "DELETE FROM recruitment_sign_up_files WHERE sign_up_id='"+id+"';";
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
	@Override
	//ɾ�������ļ�
	public Integer StudentSignUpDeleteOneFile(String id,String path){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				conn=getConn();
				statement = conn.createStatement();
				//ɾ�������ļ�
				String sqlForDeleteFile = "DELETE FROM recruitment_sign_up_files WHERE sign_up_id='"+id+"' and file_path = '"+path+"';";
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
	/*--------------------���ǿ�̫��ķָ���--------------------*/

	/*--------------------�����ǿ�̫��ӵĶ�ѧ����Ŀ������Ϣ�Ĳ�����Ӧ�ù�����--------------------*/
	@Override
	//����µı�����
	public Integer StudentSignUpAddNewPerson(String signUpId,String projectId,String userId,String userName,String email,
										  String phoneNumber,String time,String state){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				conn=getConn();
				statement = conn.createStatement();

				Date dateValue = Date.valueOf(time);

				Integer msg=0;

				//�����µ�һ���ļ���ַ
				String sqlForAdd = "INSERT INTO recruitment_sign_up VALUES ('"
						+ signUpId + "','"
						+ projectId + "','"
						+ userId + "','"
						+ userName + "','"
						+ email + "','"
						+ phoneNumber + "','"
						+ dateValue + "','"
						+ state + "');" ;
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForAdd);
				System.out.println(msg+"�����¼�¼���");

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
	@Override
	//��ѯ�µı����˵�id��name�Ƿ���ƥ�䵽��ȷ���û�
	public Integer StudentSignUpCheckUser(String userId,String userName){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				conn=getConn();
				statement = conn.createStatement();

				Integer msg=0;

				//��ѯ�Ƿ����޸��û�
				String sqlForAdd = "SELECT * FROM user WHERE user_id = '"+userId+"' and user_name = '"+userName+"' ;";
				System.out.println("conn succeed");
				msg = statement.executeUpdate(sqlForAdd);
				System.out.println(msg+"�����¼�¼���");

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
	@Override
	//��ѯ�����������δ��������ĿΪ����״̬��������Ŀ�ľ�����Ϣ
	public List<SignUpEnableProjectObject> StudentSignUpQueryAllEnableProjects(){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			List<SignUpEnableProjectObject> list = new ArrayList<SignUpEnableProjectObject>();
			try {
				conn=getConn();
				statement = conn.createStatement();

				Integer msg=0;

				String sqlForQuery = "SELECT * FROM recruitment WHERE state = '����';";
				resultSet = statement.executeQuery(sqlForQuery);

				if(resultSet!=null){
					while (resultSet.next()){
						SignUpEnableProjectObject spo = new SignUpEnableProjectObject();
						spo.setProjectId(resultSet.getString("id"));
						spo.setProjectName(resultSet.getString("title"));
						spo.setStartYear(resultSet.getString("publish_time"));
						list.add(spo);
					}
				}

				for (SignUpEnableProjectObject spo:list){
					sqlForQuery = "SELECT * FROM project WHERE project_id = '"+spo.getProjectId()+"';";
					resultSet = statement.executeQuery(sqlForQuery);
					if (resultSet!=null){
						while (resultSet.next()){
							spo.setProjectPrincipalId(resultSet.getString("project_principal_id"));
							spo.setProjectTeacherId(resultSet.getString("project_teacher_id"));
						}
					}
				}



				for (SignUpEnableProjectObject spo:list){
					String sqlForQueryTeacher = "SELECT * FROM user WHERE user_id = '"+spo.getProjectTeacherId()+"';";

					resultSet = statement.executeQuery(sqlForQueryTeacher);
					if (resultSet!=null){
						while (resultSet.next()){
							spo.setProjectTeacherName(resultSet.getString("user_name"));
							spo.setProjectTeacherPhoneNumber(resultSet.getString("user_phone"));
						}
					}
				}

				for (SignUpEnableProjectObject spo:list){
					String sqlForQueryStudent = "SELECT * FROM user WHERE user_id = '"+spo.getProjectPrincipalId()+"';";

					resultSet = statement.executeQuery(sqlForQueryStudent);
					if (resultSet!=null){
						while (resultSet.next()){
							spo.setProjectPrincipalName(resultSet.getString("user_name"));
							spo.setProjectPrincipalPhoneNumber(resultSet.getString("user_phone"));
						}
					}
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

	@Override
	//�ض�project_id��Ŀ�ľ�����Ϣ
	public SignUpEnableProjectObject StudentSignUpSelectOneEnableProjects(String id){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			SignUpEnableProjectObject spo = new SignUpEnableProjectObject();
			try {
				conn=getConn();
				statement = conn.createStatement();

				Integer msg=0;

				String sqlForQuery = "SELECT * FROM recruitment WHERE state = '����' and id = '"+id+"';";
				resultSet = statement.executeQuery(sqlForQuery);

				if(resultSet!=null){
					while (resultSet.next()){
						spo.setProjectId(resultSet.getString("id"));
						spo.setProjectName(resultSet.getString("title"));
						spo.setStartYear(resultSet.getString("publish_time"));
					}
				}

				sqlForQuery = "SELECT * FROM project WHERE project_id = '"+spo.getProjectId()+"';";
				resultSet = statement.executeQuery(sqlForQuery);
				if (resultSet!=null){
					while (resultSet.next()){
						spo.setProjectPrincipalId(resultSet.getString("project_principal_id"));
						spo.setProjectTeacherId(resultSet.getString("project_teacher_id"));
					}
				}

				String sqlForQueryTeacher = "SELECT * FROM user WHERE user_id = '"+spo.getProjectTeacherId()+"';";

				resultSet = statement.executeQuery(sqlForQueryTeacher);
				if (resultSet!=null){
					while (resultSet.next()){
						spo.setProjectTeacherName(resultSet.getString("user_name"));
						spo.setProjectTeacherPhoneNumber(resultSet.getString("user_phone"));
					}
				}

				String sqlForQueryStudent = "SELECT * FROM user WHERE user_id = '"+spo.getProjectPrincipalId()+"';";

				resultSet = statement.executeQuery(sqlForQueryStudent);
				if (resultSet!=null){
					while (resultSet.next()){
						spo.setProjectPrincipalName(resultSet.getString("user_name"));
						spo.setProjectPrincipalPhoneNumber(resultSet.getString("user_phone"));
					}
				}

				return spo;

			}
			catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			finally{
				closeConn(resultSet,statement, conn);
			}
		}
	@Override
	public List<SignUpJoinProjectObject> StudentSignUpMyJoin(String userId){
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<SignUpJoinProjectObject> list = new ArrayList<SignUpJoinProjectObject>();
		try {
			conn=getConn();
			statement = conn.createStatement();

			Integer msg=0;

			String sqlForQuery = "SELECT * FROM recruitment_sign_up WHERE user_id = '"+userId+"';";
			resultSet = statement.executeQuery(sqlForQuery);

			if(resultSet!=null){
				while (resultSet.next()){
					SignUpJoinProjectObject spo = new SignUpJoinProjectObject();
					spo.setProjectId(resultSet.getString("project_id"));
					spo.setState(resultSet.getString("state"));
					list.add(spo);
				}
			}

			for (SignUpJoinProjectObject spo:list){
				sqlForQuery = "SELECT * FROM project WHERE project_id = '"+spo.getProjectId()+"';";
				resultSet = statement.executeQuery(sqlForQuery);
				if (resultSet!=null){
					while (resultSet.next()){
						spo.setProjectName(resultSet.getString("project_name"));
						spo.setStartYear(resultSet.getString("start_year"));
						spo.setProjectPrincipalId(resultSet.getString("project_principal_id"));
						spo.setProjectTeacherId(resultSet.getString("project_teacher_id"));
					}
				}
			}

			for (SignUpJoinProjectObject spo:list){
				String sqlForQueryTeacher = "SELECT * FROM user WHERE user_id = '"+spo.getProjectTeacherId()+"';";

				resultSet = statement.executeQuery(sqlForQueryTeacher);
				if (resultSet!=null){
					while (resultSet.next()){
						spo.setProjectTeacherName(resultSet.getString("user_name"));
						spo.setProjectTeacherPhoneNumber(resultSet.getString("user_phone"));
					}
				}
			}

			for (SignUpJoinProjectObject spo:list){
				String sqlForQueryStudent = "SELECT * FROM user WHERE user_id = '"+spo.getProjectPrincipalId()+"';";

				resultSet = statement.executeQuery(sqlForQueryStudent);
				if (resultSet!=null){
					while (resultSet.next()){
						spo.setProjectPrincipalName(resultSet.getString("user_name"));
						spo.setProjectPrincipalPhoneNumber(resultSet.getString("user_phone"));
					}
				}
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
	@Override
	//�ض�project_id �� �ض��û�id ����Ϣ
	public SignUperObject StudentSignUpEachDetails(String projectId,String userId){
			Connection conn=null;
			Statement statement = null;
			ResultSet resultSet = null;
			SignUperObject suo = new SignUperObject();
			try {
				conn=getConn();
				statement = conn.createStatement();
				String sqlForQuery = "SELECT * FROM recruitment_sign_up WHERE user_id = '"+userId+"' and project_id = '"+projectId+"';";
				resultSet = statement.executeQuery(sqlForQuery);
				if(resultSet!=null){
					while (resultSet.next()){
						suo.setSignUpId(resultSet.getString("sign_up_id"));
						suo.setProjectId(resultSet.getString("project_id"));
						suo.setUserId(resultSet.getString("user_id"));
						suo.setUserName(resultSet.getString("user_name"));
						suo.setEmail(resultSet.getString("email"));
						suo.setPhoneNumber(resultSet.getString("phone_number"));
						suo.setSignUpTime(resultSet.getString("sign_up_time"));
						suo.setState(resultSet.getString("state"));
					}
				}
				return suo;

			}
			catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			finally{
				closeConn(resultSet,statement, conn);
			}
		}
	 /*--------------------���ǿ�̫��ķָ���--------------------*/

}
