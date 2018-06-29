package com.yhcj.Dao.impl;

import com.yhcj.Dao.Teacher;
import com.yhcj.enity.ProjectFileObject;
import com.yhcj.enity.ProjectReviewFileObject;
import com.yhcj.enity.TeacherObject;
import com.yhcj.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherImpl extends DBUtil implements Teacher {
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ��ʦ��Ϣ
            String sqlForAllStu = "select count(*) from teacher left join user on user.user_id = teacher.user_id;";
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
    public List<TeacherObject> findAllTea(String pageNum, String pageSize) {
        // TODO Auto-generated method stub
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        int numer = Integer.parseInt(pageNum);
        int size  = Integer.parseInt(pageSize);

        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ��ʦ��Ϣ
            String sqlForAllTea = "select * from teacher left join user on user.user_id = teacher.user_id limit "+(numer-1)*size+","+size+";";
            System.out.println(sqlForAllTea);
            resultSet = statement.executeQuery(sqlForAllTea);
            if(resultSet != null) {
                List<TeacherObject> teaList = new ArrayList<TeacherObject>();
                while(resultSet.next()) {
                    TeacherObject teaObj = new TeacherObject();
                    teaObj.setUser_id(resultSet.getString("user_id"));
                    teaObj.setUser_name(resultSet.getString("user_name"));
                    teaObj.setUser_phone(resultSet.getString("user_phone"));
                    teaObj.setAccount_state(resultSet.getString("account_state"));
                    teaList.add(teaObj);
                }
                return teaList;
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
    public TeacherObject findSpeTea(String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            
            String sqlTea = "select user.user_id, user_identity,user.user_pwd,user.user_name, user.user_sex, user.user_mail, user.user_phone, user.account_state, teacher.teacher_degree, teacher.teacher_bachelor, teacher.teacher_major from user, teacher where user.user_id = teacher.user_id and user.user_id = "+userId+";" ;
            System.out.println(sqlTea);
            resultSet = statement.executeQuery(sqlTea);
			if(resultSet.next()) {
				TeacherObject teaObj = new TeacherObject();
				teaObj.setUser_id(resultSet.getString("user_id"));
				teaObj.setUser_name(resultSet.getString("user_name"));
				teaObj.setUser_sex(resultSet.getString("user_sex"));
				teaObj.setUser_mail(resultSet.getString("user_mail"));
				teaObj.setUser_phone(resultSet.getString("user_phone"));
				teaObj.setUser_identity(resultSet.getString("user_identity"));
				teaObj.setAccount_state(resultSet.getString("account_state"));
				teaObj.setUser_pwd(resultSet.getString("user_pwd"));
				teaObj.setTeacher_degree(resultSet.getString("teacher_degree"));
				teaObj.setTeacher_bachelor(resultSet.getString("teacher_bachelor"));
				teaObj.setTeacher_major(resultSet.getString("teacher_major"));
				return teaObj;
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
	public boolean delTea(String userId) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
            String delsql = "delete user,teacher from user left join teacher on user.user_id = teacher.user_id where user.user_id = '" +userId+"';";
            int row = statement.executeUpdate(delsql);
            if(row > 0) {
            		return true;
            }
            else {
            		//	���Ӧ�����ع�����
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
	public int updateTea(TeacherObject teaObj) {
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
            System.out.println(teaObj.getUser_sex());
            stat.setString(1, teaObj.getUser_name());
            stat.setString(2, teaObj.getUser_sex());
            stat.setString(3, teaObj.getUser_mail());
            stat.setString(4, teaObj.getUser_phone());
            stat.setString(5, teaObj.getAccount_state());
            stat.setString(6, teaObj.getUser_identity());
            stat.setString(7, teaObj.getUser_id());
            int row1 = stat.executeUpdate();
            
            String updsql2 = "update teacher set user_name = ?,teacher_degree = ?,teacher_bachelor = ?,teacher_major = ? where user_id = ?";
            stat1=conn.prepareStatement(updsql2);
            stat1.setString(1, teaObj.getUser_name());
            stat1.setString(2, teaObj.getTeacher_degree());
            stat1.setString(3, teaObj.getTeacher_bachelor());
            stat1.setString(4, teaObj.getTeacher_major());
            stat1.setString(5, teaObj.getUser_id());
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

    /*--------------------�����ǿ�̫��ӵĶԽ�ʦ��Ŀ�����ļ��Ĳ�����Ӧ�ù�����--------------------*/

    //�Խ�ʦ��Ŀ�����ļ�,�ϴ��ļ��������ļ���Ϣ��������
    public Integer TeacherUploadFiles(String id,String time,String state,String path,String type,String name){
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

    //��ѯ�����ϴ��ļ�
    public ProjectFileObject TeacherQueryOneFile(String id,String path){
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

    //�Խ�ʦ��Ŀ�����ļ�,�ض�id��state��type��ѯ�����ϴ��ļ�
    public List<ProjectFileObject> TeacherQueryByStateAndTypeFiles(String id, String state, String type){
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
    public List<ProjectFileObject> TeacherQueryByStateFiles(String id, String state){
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

    //�Խ�ʦ��Ŀ�����ļ�,ɾ���ض�id��file_type�����ļ�
    public Integer TeacherDeleteByTypeFiles(String id,String state,String type){
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

    //�Խ�ʦ��Ŀ�����ļ�,ɾ�������ϴ��ļ�
    public Integer TeacherDeleteOneFile(String id,String path){
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

    //�Խ�ʦ��Ŀ�����ļ�,ɾ���ض�id�����ϴ��ļ�
    public Integer TeacherDeleteAllFiles(String id){
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

    /*--------------------���ǿ�̫��ķָ���--------------------*/

    /*--------------------�����ǿ�̫��ӵĶԽ�ʦ��Ŀ�����ļ��Ĳ�����Ӧ�ù�����--------------------*/

    //�Խ�ʦ��Ŀ�����ļ�,�ض�id��type��ѯ�����ϴ��ļ�
    public List<ProjectReviewFileObject> TeacherReviewQueryByTypeFiles(String id, String type){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<ProjectReviewFileObject> list = new ArrayList<ProjectReviewFileObject>();

        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ���з����������ļ�
            String sqlForQueryFiles = "SELECT * FROM review_files WHERE  file_type = '"+type+"' and project_id = '"+id+"';";
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

    /*--------------------���ǿ�̫��ķָ���--------------------*/
	
}
