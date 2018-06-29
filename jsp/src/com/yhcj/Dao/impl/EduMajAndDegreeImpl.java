package com.yhcj.Dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.Dao.EduMajAndDegree;
import com.yhcj.enity.DegreeObject;
import com.yhcj.enity.EducationObject;
import com.yhcj.enity.MajorObject;
import com.yhcj.utils.DBUtil;

public class EduMajAndDegreeImpl extends DBUtil  implements EduMajAndDegree {

	@Override
	public List<EducationObject> findAllEdu() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<EducationObject> eduList = new ArrayList<EducationObject>();
		try {
			conn = getConn();
			statement = conn.createStatement();
			//sql����ѯ���п����õ�ѧԺ
			String sqlForEdu = "select * from education where status = '" + "����';";
			System.out.println(sqlForEdu);
			resultSet = statement.executeQuery(sqlForEdu);
			if(resultSet != null) {
				while(resultSet.next()) {
					EducationObject educationObj = new EducationObject();
					educationObj.setTeacher_bachelor(resultSet.getString("teacher_bachelor"));
					educationObj.setStatus(resultSet.getString("status"));
					eduList.add(educationObj);
				}
				return eduList;
			}else {
				// ѧ����ϢΪ��
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			// ����
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	@Override
	public List<DegreeObject> findAllDegree() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<DegreeObject> degreeList = new ArrayList<DegreeObject>();
		try {
			conn = getConn();
			statement = conn.createStatement();
			//sql����ѯ���п����õ�ѧԺ
			String sqlForDegree = "select * from degree where status = '" + "����';";
			System.out.println(sqlForDegree);
			resultSet = statement.executeQuery(sqlForDegree);
			if(resultSet != null) {
				while(resultSet.next()) {
					DegreeObject degreeObj = new DegreeObject();
					degreeObj.setTeacher_degree(resultSet.getString("teacher_degree"));
					degreeObj.setStatus(resultSet.getString("status"));
					degreeList.add(degreeObj);
				}
				return degreeList;
				
			}else {
				// ѧλ��ϢΪ��
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			// ����
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	@Override
	public List<MajorObject> findAllMajor() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<MajorObject> majorList = new ArrayList<MajorObject>();
		try {
			conn = getConn();
			statement = conn.createStatement();
			//sql����ѯ���п����õ�ѧԺ
			String sqlForMajor = "select * from major where status = '" + "����';";
			System.out.println(sqlForMajor);
			resultSet = statement.executeQuery(sqlForMajor);
			if(resultSet != null) {
				while(resultSet.next()) {
					MajorObject majorObj = new MajorObject();
					majorObj.setMajor(resultSet.getString("major"));
					majorObj.setStatus(resultSet.getString("status"));
					majorList.add(majorObj);
				}
				return majorList;
			}else {
				// רҵ��ϢΪ��
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			// ����
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

}
