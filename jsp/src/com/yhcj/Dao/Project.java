package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectApplyObject;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.RecruitmentObject;
import com.yhcj.enity.ProjectFileObject;


//项目申请接口
public interface Project {
	/**
	 * 返回项目总的数量
	 */
	int getCount();
	/**
	 * 返回的项目所有list
	 * 参数：pageSize  每页多少行数据
	 * 参数：pageNum 第几页
	 */
	List<ProjectApplyObject> findAllPro(String pageNum,String pageSize);
	/**
	 * 查找特定id的项目
	 */
	ProStuTeaAndRev findSpePro(String proId);
	/**
	 * 更新特定id的项目
	 * 参数：项目对象
	 */
	int updatePro(ProjectObject proObj);
	/**
	 * 改变项目状态的
	 * 项目申请中：状态就是申请 改变状态就变成中期
	 * 项目中期：状态就是中期 改变状态就变成结题
	 * 项目结题：状态就是结题
	 */
	boolean reProState(String proId,String state);
	
	/**
	 * 删除项目
	 */
	boolean delPro(String proId);

	/**
	 * 添加新项目
	 * 参数：项目对象
	 */
	boolean insertPro(ProjectObject proObj);
	/*
	 * 改变项目报名通知的状态，发布或下架报名通知
	 */
	boolean reRecState(RecruitmentObject recObj);
	
	int projectActNumber(String id);
	/*--------------------对项目管理文件的操作--------------------*/

	//上传文件，保存文件信息到服务器
	Integer ProjectSignUpUploadFiles(String id,String date,String state,String path,String type,String name);

	//特定id、state和type查询所有上传文件
	List<ProjectFileObject> ProjectSignUpQueryByStateAndTypeFiles(String id, String state, String type);

	//特定id和state查询所有上传文件
	List<ProjectFileObject> ProjectSignUpQueryByStateFiles(String id, String state);

	//特定id和type查询所有上传文件
	List<ProjectFileObject> ProjectSignUpQueryByTypeFiles(String id, String type);

	//特定id查询所有上传文件
	List<ProjectFileObject> ProjectSignUpQueryAllFiles(String id);

	//查询单个上传文件
	ProjectFileObject ProjectSignUpQueryOneFile(String id,String path);

	//删除特定id所有上传文件
	Integer ProjectSignUpDeleteAllFiles(String id);

	//删除特定id和state所有文件
	Integer ProjectSignUpDeleteByStateFiles(String id,String state);

	//删除特定id和file_type所有文件
	Integer ProjectSignUpDeleteByTypeFiles(String id,String type);

	//删除特定id、state和file_type所有文件
	Integer ProjectSignUpDeleteByStateAndTypeFiles(String id,String state,String type);

	//删除单个上传文件
	Integer ProjectSignUpDeleteOneFile(String id,String path);

	//更新特定id所有上传文件的state（path为*则为全部文件，path为可用地址则为单个文件）
	Integer ProjectSignUpUpdateAllFilesState(String id,String state,String path);

	//更新特定id所有上传文件的type（path为*则为全部文件，path为可用地址则为单个文件）
	Integer ProjectSignUpUpdateAllFilesType(String id,String type,String path);

	//更新特定id所有上传文件的state和type（path为*则为全部文件，path为可用地址则为单个文件）
	Integer ProjectSignUpUpdateAllFilesStateAndType(String id,String state,String type,String path);

	//更新特定id所有上传文件的submit_time（path为*则为全部文件，path为可用地址则为单个文件）
	Integer ProjectSignUpUpdateAllFilesSubmitTime(String id,String time,String path);

	
	/*-------------------------教师端-------------------------------------*/
	

	 // 列出该教师指导的项目（处于申请阶段，并且是可用的） 
	List<ProjectObject> findAllProInTeacher(String pageNum,String pageSize,String userId);
	 // 返回该教师现有指导项目（该项目处于申请阶段）总的数量
	int getTeaCount(String userId);
	/*-------------------------学生端-------------------------------------*/
	/**
	 * 返回项目总的数量
	 */
	int getCount(String userId);
	/**
	 * 返回的项目所有list
	 * 参数：pageSize  每页多少行数据
	 * 参数：pageNum 第几页
	 */
	List<ProStuTeaAndRev> findAllStuProApply(String pageNum,String pageSize,String userId);
	
}
