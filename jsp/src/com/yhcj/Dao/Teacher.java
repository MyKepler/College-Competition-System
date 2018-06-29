package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProjectFileObject;
import com.yhcj.enity.ProjectReviewFileObject;
import com.yhcj.enity.TeacherObject;

public interface Teacher {
	 /*
     * ���صĽ�ʦ����list
     * ������pageSize  ÿҳ����������
     */
    int getCount();
    List<TeacherObject> findAllTea(String pageNum, String pageSize);
    
    TeacherObject findSpeTea(String userId);	//�����ض�id�Ľ�ʦ
    boolean delTea(String userId);			//ɾ���ض�id�Ľ�ʦ
    int updateTea(TeacherObject teaObj);	//�����ض��û��Ľ�ʦ
    boolean rePassword(String userId);		//�����û�����
    boolean reStates(String userId,String state);//�����û�״̬

    /*--------------------�Խ�ʦ��Ŀ�����ļ��Ĳ���--------------------*/

    //�ϴ��ļ��������ļ���Ϣ��������
    Integer TeacherUploadFiles(String id,String date,String state,String path,String type,String name);

    //��ѯ�����ϴ��ļ�
    ProjectFileObject TeacherQueryOneFile(String id,String path);

    //�ض�id��state��type��ѯ�����ϴ��ļ�
    List<ProjectFileObject> TeacherQueryByStateAndTypeFiles(String id, String state, String type);

    //�ض�id��state��ѯ�����ϴ��ļ�
    List<ProjectFileObject> TeacherQueryByStateFiles(String id, String state);

    //ɾ���ض�id��state��type�����ļ�
    Integer TeacherDeleteByTypeFiles(String id,String state,String type);

    //ɾ�������ϴ��ļ�
    Integer TeacherDeleteOneFile(String id,String path);

    //ɾ���ض�id�����ϴ��ļ�
    Integer TeacherDeleteAllFiles(String id);

    //�ض�id��type��ѯ�����ϴ��ļ�
    List<ProjectReviewFileObject> TeacherReviewQueryByTypeFiles(String id, String type);


}
