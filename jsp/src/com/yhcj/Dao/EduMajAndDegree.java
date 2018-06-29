package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.DegreeObject;
import com.yhcj.enity.EducationObject;
import com.yhcj.enity.MajorObject;

public interface EduMajAndDegree {
	
	/*
	 * 返回学历对象list
	 */
	List<EducationObject> findAllEdu();
	/*
	 * 返回学位对象list
	 */
	List<DegreeObject> findAllDegree();
	/*
	 * 返回所有专业对象list
	 */
	List<MajorObject> findAllMajor();

}
