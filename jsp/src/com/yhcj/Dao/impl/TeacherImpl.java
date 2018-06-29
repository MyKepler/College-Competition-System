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
            //查询教师信息
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
            //查询教师信息
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
				//用户不存在
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

    /*--------------------以下是孔太秀加的对教师项目管理文件的操作，应该够用了--------------------*/

    //对教师项目管理文件,上传文件，保存文件信息到服务器
    public Integer TeacherUploadFiles(String id,String time,String state,String path,String type,String name){
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

    //查询单个上传文件
    public ProjectFileObject TeacherQueryOneFile(String id,String path){
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

    //对教师项目管理文件,特定id、state和type查询所有上传文件
    public List<ProjectFileObject> TeacherQueryByStateAndTypeFiles(String id, String state, String type){
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
    public List<ProjectFileObject> TeacherQueryByStateFiles(String id, String state){
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

    //对教师项目管理文件,删除特定id和file_type所有文件
    public Integer TeacherDeleteByTypeFiles(String id,String state,String type){
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

    //对教师项目管理文件,删除单个上传文件
    public Integer TeacherDeleteOneFile(String id,String path){
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

    //对教师项目管理文件,删除特定id所有上传文件
    public Integer TeacherDeleteAllFiles(String id){
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

    /*--------------------我是孔太秀的分割线--------------------*/

    /*--------------------以下是孔太秀加的对教师项目评审文件的操作，应该够用了--------------------*/

    //对教师项目评审文件,特定id和type查询所有上传文件
    public List<ProjectReviewFileObject> TeacherReviewQueryByTypeFiles(String id, String type){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<ProjectReviewFileObject> list = new ArrayList<ProjectReviewFileObject>();

        try {
            conn=getConn();
            statement = conn.createStatement();
            //查询所有符合条件的文件
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

    /*--------------------我是孔太秀的分割线--------------------*/
	
}
