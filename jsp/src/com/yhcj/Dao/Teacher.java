package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProjectFileObject;
import com.yhcj.enity.ProjectReviewFileObject;
import com.yhcj.enity.TeacherObject;

public interface Teacher {
	 /*
     * 返回的教师所有list
     * 参数：pageSize  每页多少行数据
     */
    int getCount();
    List<TeacherObject> findAllTea(String pageNum, String pageSize);
    
    TeacherObject findSpeTea(String userId);	//查找特定id的教师
    boolean delTea(String userId);			//删除特定id的教师
    int updateTea(TeacherObject teaObj);	//更新特定用户的教师
    boolean rePassword(String userId);		//重置用户密码
    boolean reStates(String userId,String state);//重置用户状态

    /*--------------------对教师项目管理文件的操作--------------------*/

    //上传文件，保存文件信息到服务器
    Integer TeacherUploadFiles(String id,String date,String state,String path,String type,String name);

    //查询单个上传文件
    ProjectFileObject TeacherQueryOneFile(String id,String path);

    //特定id、state和type查询所有上传文件
    List<ProjectFileObject> TeacherQueryByStateAndTypeFiles(String id, String state, String type);

    //特定id和state查询所有上传文件
    List<ProjectFileObject> TeacherQueryByStateFiles(String id, String state);

    //删除特定id、state和type所有文件
    Integer TeacherDeleteByTypeFiles(String id,String state,String type);

    //删除单个上传文件
    Integer TeacherDeleteOneFile(String id,String path);

    //删除特定id所有上传文件
    Integer TeacherDeleteAllFiles(String id);

    //特定id和type查询所有上传文件
    List<ProjectReviewFileObject> TeacherReviewQueryByTypeFiles(String id, String type);


}
