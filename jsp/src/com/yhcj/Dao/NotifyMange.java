package com.yhcj.Dao;
import com.yhcj.enity.NotifyObject;
import com.yhcj.enity.PublishProjectInfoObject;
import com.yhcj.enity.NotifyFilesObject;

import java.sql.Date;
import java.util.List;

public interface NotifyMange{
	// ��ȡ��������
	int getCount();
    //�鿴һ���¹���
    NotifyObject NotifyDetails(String id);

    //���һ���µĹ���
    Integer addNewNotify(String id, String title, String state,
                              Date time,String userName,String introduction);
    //�޸�һ������
    Integer editNotify(String id,String title,Date time,String userName,String introduction);

    //����һ����������¼�һ������
    Integer publishNotify(String id,String state);

    //ɾ��һ������
    Integer deleteNotify(String id);

    //�ϴ��ļ��������ļ���Ϣ��������
    Integer NotifyUploadFiles(String id,String path,String name);

    //��ʼ��ʱ�������й���
    List<NotifyObject> queryAllNotifyInfo(String pageNum,String pageSize);
    // ��ѯ�ù������Ϣ
    NotifyObject  queryAllNotify(String id);

    //��ѯ�ù���������ϴ��ļ�
    List<NotifyFilesObject> queryAllNotifyFiles(String id);

    //��ѯ�ù����ϵ�һ���ļ�
    NotifyFilesObject queryOneNotifyFiles(String id,String path);

    //ɾ���ù���������ļ�
    Integer deleteAllNotifyFiles(String id);

    //ɾ���ù���ĵ����ļ�
    Integer deleteOneNotifyFiles(String id,String path);
    
    //���ظ�ǰ�����з�������Ŀ��Ϣ
    List<PublishProjectInfoObject> queryAllPublishProjectInfo(String pageNum,String pageSize);

    //���ط�������Ŀcount
    Integer getPublishProjectCount();

    //���ظ�ǰ�����з����Ĺ���
    List<NotifyObject> queryAllPublishNotifyInfo(String pageNum,String pageSize);

    //���ط�������Ŀcount
    Integer getPublishNotifyCount();
    
    
}