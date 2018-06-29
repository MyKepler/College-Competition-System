package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProRevObject;
import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.ProjectReviewFileObject;

public interface ProReview {
	/**
	 * 返回项目总的数量
	 */
	int getCount();
	/**
	 * 返回的项目所有list
	 * 参数：pageSize  每页多少行数据
	 * 参数：pageNum 第几页
	 */
	List<ProStuTeaAndRev> findProReview(String pageNum,String pageSize);
	/**
	 * 查找特定id的中期项目
	 */
	ProStuTeaAndRev findSpeProReview(String proId);
	/**
	 * 
	 */
	ProRevObject findRevbytime(String proId,String proStatus) ;
	/**
	 * 更新特定id的项目
	 * 参数：项目对象
	 */
	int updateProReview(ProRevObject prorevObj);
	/**
	 * 改变项目状态的
	 * 项目申请中：状态就是未开始/开始
	 * 项目中期：状态就是开始/中期
	 * 项目结题：状态就是中期/结题
	 */
	/**
	 * 删除项目
	 */
	boolean delProReview(String proId);

	/*--------------------对项目评审管理文件的操作--------------------*/

	//上传文件，保存文件信息到服务器
	Integer ProjectReviewUploadFiles(String id,String path,String type,String name);

	//特定id和type查询所有上传文件
	List<ProjectReviewFileObject> ProjectReviewQueryByTypeFiles(String id, String type);

	//特定id查询所有上传文件
	List<ProjectReviewFileObject> ProjectReviewQueryAllFiles(String id);

	//查询单个上传文件
	ProjectReviewFileObject ProjectReviewQueryOneFile(String id,String path);

	//删除特定id所有上传文件
	Integer ProjectReviewDeleteAllFiles(String id);

	//删除特定id和file_type所有文件
	Integer ProjectReviewDeleteByTypeFiles(String id,String type);

	//删除单个上传文件
	Integer ProjectReviewDeleteOneFile(String id,String path);

	//更新特定id所有上传文件的type（path为*则为全部文件，path为可用地址则为单个文件）
	Integer ProjectReviewUpdateAllFilesType(String id,String type,String path);

    /*-----------------------------教师端----------------------------------*/
	
	/*
	 * 查找该教师指导的所有项目的评审结果信息(只显示可用的)
	 */
	List<ProStuTeaAndRev> findProReviewByTea(String pageNum,String pageSize,String userId);
	
	/*
	 * 统计出该教师指导的所有项目的评审结果信息(只显示可用的)的总数
	 */
	int getTeaCount(String userId);
	/*-----------------------------学生端----------------------------------*/
	/**
	 * 返回项目总的数量
	 */
	int getCount(String userId);
	/**
	 * 返回的项目所有list
	 * 参数：pageSize  每页多少行数据
	 * 参数：pageNum 第几页
	 */
	List<ProStuTeaAndRev> findAllStuProReview(String pageNum,String pageSize,String userId);
}
