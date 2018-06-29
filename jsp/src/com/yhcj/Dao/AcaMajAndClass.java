package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.AcademyObject;
import com.yhcj.enity.ClassTableObject;
import com.yhcj.enity.MajorObject;

public interface AcaMajAndClass {
	
	/**
	 * 返回学院对象list
	 */
	List<AcademyObject> findAllAca();
	/**
	 * 返回专业对象
	 * 参数academy 对应的学院
	 */
	List<MajorObject> findAllMajor(String academy);
	/**
	 * 返回班级对象
	 * 参数major 对应专业
	 */
	List<ClassTableObject> findAllClass(String major);
}
