package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.DegreeObject;
import com.yhcj.enity.EducationObject;
import com.yhcj.enity.MajorObject;

public interface EduMajAndDegree {
	
	/*
	 * ����ѧ������list
	 */
	List<EducationObject> findAllEdu();
	/*
	 * ����ѧλ����list
	 */
	List<DegreeObject> findAllDegree();
	/*
	 * ��������רҵ����list
	 */
	List<MajorObject> findAllMajor();

}
