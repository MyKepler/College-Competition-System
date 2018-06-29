package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.SignUpEnableProjectObject;
import com.yhcj.enity.SignUpJoinProjectObject;
import com.yhcj.enity.SignUperObject;
import com.yhcj.enity.StudentObject;
import com.yhcj.enity.StudentSignUpFileObject;

public interface Student {
	/**
	 * 返回学生的总共的条数
	 */
	int getCount();
	/**
	 * 返回的学生所有list
	 * 参数：pageSize  每页多少行数据
	 * 参数：pageNum 第几页
	 */
	List<StudentObject> findAllStu(String pageNum,String pageSize);
	/**
	 * 查找特定id的学生
	 */
	StudentObject findSpeStu(String userId);
	/**
	 * 删除特定id的学生
	 * 没有返回任何数据，前端做的只是跳转到表格页面
	 */
	boolean delStu(String userId);
	/**
	 * 更新特定用户的学生
	 * 参数：学生对象
	 */
	int updateStu(StudentObject stuObj);
	/**
	 * 重置用户密码
	 */
	boolean rePassword(String userId);
	/**
	 * 重置用户状态
	 */
	boolean reStates(String userId,String state);
	
	/*--------------------对学生项目报名文件的操作--------------------*/

	//上传文件，保存文件信息到服务器
	Integer StudentSignUpUploadFiles(String id,String path,String name);

	//查询所有上传文件
	List<StudentSignUpFileObject> StudentSignUpQueryAllFiles(String id);

	//查询单个文件
	StudentSignUpFileObject StudentSignUpQueryOneFile(String id,String path);

	//删除所有上传文件
	Integer StudentSignUpDeleteAllFiles(String id);

	//删除单个文件
	Integer StudentSignUpDeleteOneFile(String id,String path);
	//添加新的报名人
	Integer StudentSignUpAddNewPerson(String signUpId,String projectId,String userId,String userName,String email,
									  String phoneNumber,String time,String state);


	//查询新的报名人的id和name是否能匹配到正确的用户
	Integer StudentSignUpCheckUser(String userId,String userName);

	//查询所有审核人数未满并且项目为申请状态的所有项目
	List<SignUpEnableProjectObject> StudentSignUpQueryAllEnableProjects();

	//查询特定项目id项目的具体信息
	SignUpEnableProjectObject StudentSignUpSelectOneEnableProjects(String id);

	//查询报名人参加的项目
	List<SignUpJoinProjectObject> StudentSignUpMyJoin(String userId);

	//特定project_id 和 特定用户id 的填写的信息
	SignUperObject StudentSignUpEachDetails(String projectId,String userId);
}
