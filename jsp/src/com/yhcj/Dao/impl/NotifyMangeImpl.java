package com.yhcj.Dao.impl;

import com.yhcj.Dao.NotifyMange;
import com.yhcj.enity.NotifyFilesObject;
import com.yhcj.enity.NotifyObject;
import com.yhcj.enity.PublishProjectInfoObject;
import com.yhcj.utils.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NotifyMangeImpl extends DBUtil implements NotifyMange {
	public int getCount() {
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			conn=getConn();
            statement = conn.createStatement();
           
            String sqlForAllStu = "select count(*) from notification";
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
    //��ѯһ������ľ�����Ϣ
    @Override
    public NotifyObject NotifyDetails(String id){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;

        NotifyObject nfo = new NotifyObject();

        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ���й���
            String sqlForQueryDetails = "SELECT * FROM notification WHERE notification_id = '"+id+"';" ;
            System.out.println("conn succeed");
            resultSet = statement.executeQuery(sqlForQueryDetails);
            if(resultSet!=null){
                while(resultSet.next()) {
                    nfo.setId(resultSet.getString("notification_id"));
                    nfo.setTitle(resultSet.getString("notification_title"));
                    nfo.setState(resultSet.getString("state"));
                    nfo.setTime(resultSet.getDate("publish_time"));
                    nfo.setUserName(resultSet.getString("publish_user"));
                    nfo.setIntroduction(resultSet.getString("notification_introduction"));
                }
            }else {
                //�������κι���
                nfo = null;
            }
            return nfo;
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
    public Integer addNewNotify(String id, String title, String state,
                                     Date time, String userName, String introduction){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();

            //�����µ�һ������
            String sqlForAdd = "INSERT INTO notification VALUES ('"
                    +id+"','"
                    +title+"','"
                    +state+"','"
                    +time+"','"
                    +userName+"','"
                    +introduction
                    +"');" ;
            System.out.println("conn succeed");
            Integer msg = statement.executeUpdate(sqlForAdd);
            System.out.println(msg+"�������");
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
    public Integer editNotify(String id,String title,Date time,String userName,String introduction){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();

            //�޸�һ������
            String sqlForEdit = "UPDATE notification SET notification_title = '"+
                    title+"',publish_time = '"+
                    time+"',publish_user = '"+
                    userName+"',notification_introduction = '"+
                    introduction+"' WHERE notification_id='"+
                    id+"';";
            System.out.println(sqlForEdit);
            Integer msg = statement.executeUpdate(sqlForEdit);
            System.out.println(msg+"�޸����");
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
    //����һ����������¼�һ������
    public Integer publishNotify(String id,String state){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();

            //����һ����������¼�һ������
            String sqlForEdit = "UPDATE notification SET state = '"+state+"' WHERE notification_id='"+id+"';";
            System.out.println("conn succeed");
            Integer msg = statement.executeUpdate(sqlForEdit);
            System.out.println(msg+"�޸����");
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
    //ɾ��һ������
    public Integer deleteNotify(String id){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();

            //ɾ��һ�������Լ������ļ���ַ
            String sqlForDelete = "DELETE FROM notification WHERE notification_id='"+id+"';";
            String sqlForDeleteFile = "DELETE FROM notification_files WHERE notification_id='"+id+"';";
            System.out.println("conn succeed");
            Integer msg = statement.executeUpdate(sqlForDelete);
            Integer msgFile = statement.executeUpdate(sqlForDeleteFile);
            System.out.println(msg+"ɾ�����");
            System.out.println(msgFile+"ɾ�����");
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
    //�ϴ��ļ��������ļ���Ϣ��������
    public Integer NotifyUploadFiles(String id,String path,String name){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();

            //�ж���Ҫִ����Щ����
            Integer flag = 0;

            Integer msg=0;

            String sqlForQuery = "SELECT * FROM notification_files WHERE notification_id = '"+id+"' and file_path = '"+path+"';";
            resultSet = statement.executeQuery(sqlForQuery);
            if (resultSet!=null&&resultSet.next()){
                flag=1;
            }else
                flag=2;

            if(flag==1){
                String sqlForUpdate = "UPDATE FROM notification_files SET file_path = '"+path+"',file_name = '"+name+"' WHERE notification_id='"+id+"';";
                System.out.println("conn succeed");
                msg = statement.executeUpdate(sqlForUpdate);
                System.out.println(msg+"�޸����");
            }

            if (flag==2){
                //�����µ�һ���ļ���ַ
                String sqlForAdd = "INSERT INTO notification_files VALUES ('"
                        + id + "','"
                        + path + "','"
                        + name + "');" ;
                System.out.println("conn succeed");
                msg = statement.executeUpdate(sqlForAdd);
                System.out.println(msg+"��ַ���");

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
    //��ʼ��ʱ�������й���
    public List<NotifyObject> queryAllNotifyInfo(String pageNum,String pageSize){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        Integer number = Integer.parseInt(pageNum);
        Integer size = Integer.parseInt(pageSize);

        List<NotifyObject> list = new ArrayList<NotifyObject>();

        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ���й���
            String sqlForQueryAll = "SELECT * FROM notification where state != '����' limit "+(number-1)*size+","+size+";";
            System.out.println("conn succeed");
            resultSet = statement.executeQuery(sqlForQueryAll);
             if(resultSet!=null){
                 while(resultSet.next()) {
                     NotifyObject nfo=new NotifyObject();
                     nfo.setId(resultSet.getString("notification_id"));
                     nfo.setTitle(resultSet.getString("notification_title"));
                     nfo.setState(resultSet.getString("state"));
                     nfo.setTime(resultSet.getDate("publish_time"));
                     nfo.setUserName(resultSet.getString("publish_user"));
                     nfo.setIntroduction(resultSet.getString("notification_introduction"));
                     list.add(nfo);
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
    public List<NotifyFilesObject> queryAllNotifyFiles(String id){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<NotifyFilesObject> list = new ArrayList<NotifyFilesObject>();

        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ���иù���������ļ�
            String sqlForQueryFiles = "SELECT * FROM notification_files WHERE  notification_id = '"+id+"';";
            System.out.println("conn succeed");
            resultSet = statement.executeQuery(sqlForQueryFiles);
            if(resultSet!=null){
                while(resultSet.next()) {
                    NotifyFilesObject nfo=new NotifyFilesObject();
                    nfo.setId(resultSet.getString("notification_id"));
                    nfo.setFilePath(resultSet.getString("file_path"));
                    nfo.setFileName(resultSet.getString("file_name"));
                    list.add(nfo);
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
    public Integer deleteAllNotifyFiles(String id){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();
            //ɾ�������ļ�
            String sqlForDeleteFile = "DELETE FROM notification_files WHERE notification_id='"+id+"';";
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
    public Integer deleteOneNotifyFiles(String id,String path){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();
            //ɾ�������ļ�
            String sqlForDeleteFile = "DELETE FROM notification_files WHERE notification_id='"+id+"' and file_path = '"+path+"';";
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
    public NotifyFilesObject queryOneNotifyFiles(String id,String path){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        NotifyFilesObject nfo = new NotifyFilesObject();
        nfo=null;
        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ���й���
            String sqlForQueryOne = "SELECT * FROM notification_files WHERE notification_id = '"+id+"' and file_path = '"+path+"';";
            System.out.println("conn succeed");
            resultSet = statement.executeQuery(sqlForQueryOne);
            if(resultSet!=null){
                while(resultSet.next()) {
                    nfo.setId(resultSet.getString("notification_id"));
                    nfo.setFileName(resultSet.getString("file_name"));
                    nfo.setFilePath(resultSet.getString("file_path"));
                }
            }else {
                //�������κι���
                return null;
            }
            return nfo;
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
	public NotifyObject queryAllNotify(String id) {
		// TODO Auto-generated method stub
	    Connection conn=null;
        Statement statement = null;
        Statement statementFile = null;
        ResultSet resultSet = null;
        ResultSet resultSetFile = null;
        NotifyObject nfo=new NotifyObject();
        List<NotifyFilesObject> list = new ArrayList<NotifyFilesObject>();
        
        try {
            conn=getConn();
            statementFile = conn.createStatement();
            String sqlForQueryFiles = "SELECT * FROM notification_files WHERE  notification_id = '"+id+"';";
            System.out.println("conn succeed");
            resultSetFile = statementFile.executeQuery(sqlForQueryFiles);
            if(resultSetFile!=null){
                while(resultSetFile.next()) {
                    NotifyFilesObject obj=new NotifyFilesObject();
                    obj.setId(resultSetFile.getString("notification_id"));
                    obj.setFilePath(resultSetFile.getString("file_path"));
                    obj.setFileName(resultSetFile.getString("file_name"));
                    list.add(obj);
                }
            }else {
            		list = null;
            }
      
            statement = conn.createStatement();
            //��ѯ���иù���������ļ�
            String sqlForQuery = "SELECT * FROM notification WHERE  notification_id = '"+id+"';";
            System.out.println("conn succeed");
            resultSet = statement.executeQuery(sqlForQuery);
            if(resultSet!=null){
                while(resultSet.next()) {
                	     nfo.setId(resultSet.getString("notification_id"));
                     nfo.setTitle(resultSet.getString("notification_title"));
                     nfo.setState(resultSet.getString("state"));
                     nfo.setTime(resultSet.getDate("publish_time"));
                     nfo.setUserName(resultSet.getString("publish_user"));
                     nfo.setIntroduction(resultSet.getString("notification_introduction"));
                }
            }else {
                //�������κι���
                return null;
            }
            nfo.setNotifyFilesObj(list);
            return nfo;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        finally{
            closeConn(resultSet,statement, conn);
            closeConn(resultSetFile,statementFile, conn);
        }
	}
	//���ظ�ǰ�����з�������Ŀ��Ϣ
    @Override
    public List<PublishProjectInfoObject> queryAllPublishProjectInfo(String pageNum,String pageSize){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;

        Integer number = Integer.parseInt(pageNum);
        Integer size = Integer.parseInt(pageSize);

        List<PublishProjectInfoObject> list = new ArrayList<PublishProjectInfoObject>();

        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ���й���
            String sqlForQueryAll = "SELECT * FROM recruitment WHERE state = '����' limit "+(number-1)*size+","+size+";";
            System.out.println("conn succeed");
            resultSet = statement.executeQuery(sqlForQueryAll);
            if(resultSet!=null){
                while(resultSet.next()) {
                    PublishProjectInfoObject pio = new PublishProjectInfoObject();
                    pio.setProjectId(resultSet.getString("id"));
                    pio.setPublishUser(resultSet.getString("publish_user"));
                    pio.setPublishTime(resultSet.getDate("publish_time"));
                    pio.setEndTime(resultSet.getDate("end_time"));
                    pio.setTitle(resultSet.getString("title")+"   ������������");
                    pio.setState(resultSet.getString("state"));
                    list.add(pio);
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

    //���ط�������Ŀcount
    @Override
    public Integer getPublishProjectCount(){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ���й���
            String sqlForQueryAll = "SELECT COUNT(*) FROM recruitment WHERE state = '����';";
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
            return null;
        }
        finally{
            closeConn(resultSet,statement, conn);
        }
    }

    //���ظ�ǰ�����з����Ĺ���
    @Override
    public List<NotifyObject> queryAllPublishNotifyInfo(String pageNum,String pageSize){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        Integer number = Integer.parseInt(pageNum);
        Integer size = Integer.parseInt(pageSize);

        List<NotifyObject> list = new ArrayList<NotifyObject>();

        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ���й���
            String sqlForQueryAll = "SELECT * FROM notification where state = '����' limit "+(number-1)*size+","+size+";";
            System.out.println("conn succeed");
            resultSet = statement.executeQuery(sqlForQueryAll);
            if(resultSet!=null){
                while(resultSet.next()) {
                    NotifyObject nfo=new NotifyObject();
                    nfo.setId(resultSet.getString("notification_id"));
                    nfo.setTitle(resultSet.getString("notification_title"));
                    nfo.setState(resultSet.getString("state"));
                    nfo.setTime(resultSet.getDate("publish_time"));
                    nfo.setUserName(resultSet.getString("publish_user"));
                    nfo.setIntroduction(resultSet.getString("notification_introduction"));
                    list.add(nfo);
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

    //���ط�������Ŀcount
    @Override
    public Integer getPublishNotifyCount(){
        Connection conn=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn=getConn();
            statement = conn.createStatement();
            //��ѯ���й���
            String sqlForQueryAll = "SELECT COUNT(*) FROM notification WHERE state = '����';";
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
            return null;
        }
        finally{
            closeConn(resultSet,statement, conn);
        }
    }
}
