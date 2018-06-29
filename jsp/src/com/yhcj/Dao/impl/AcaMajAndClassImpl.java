package com.yhcj.Dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.yhcj.enity.AcademyObject;
import com.yhcj.enity.ClassTableObject;
import com.yhcj.enity.MajorObject;
import com.yhcj.utils.DBUtil;

public class AcaMajAndClassImpl extends DBUtil implements com.yhcj.Dao.AcaMajAndClass {

	@Override
	public List<AcademyObject> findAllAca() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<AcademyObject> acaList = new ArrayList<AcademyObject>();
		try {
			conn = getConn();
			statement = conn.createStatement();
			//sql语句查询所有可以用的学院
			String sqlForAca = "select * from academy where status = '" + "可用';";
			System.out.println(sqlForAca);
			resultSet = statement.executeQuery(sqlForAca);
			if(resultSet != null) {
				while(resultSet.next()) {
					AcademyObject academyObj = new AcademyObject();
					academyObj.setAcademy(resultSet.getString("academy"));
					academyObj.setStatus(resultSet.getString("status"));
					acaList.add(academyObj);
				}
				return acaList;
			}else {
				// 学院信息为空
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			// 报错
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	@Override
	public List<MajorObject> findAllMajor(String academy) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<MajorObject> majorList = new ArrayList<MajorObject>();
		try {
			conn = getConn();
			statement = conn.createStatement();
			//sql语句查询所有可以用的学院
			String sqlFormajor = "select * from major where status = '" + "可用' and  academy = '" + academy +"';";
			System.out.println(sqlFormajor);
			resultSet = statement.executeQuery(sqlFormajor);
			if(resultSet != null) {
				while(resultSet.next()) {
					MajorObject majorObj = new MajorObject();
					majorObj.setAcademy(resultSet.getString("academy"));
					majorObj.setMajor(resultSet.getString("major"));
					majorObj.setStatus(resultSet.getString("status"));
					majorList.add(majorObj);
				}
				return majorList;
				
			}else {
				// 学院信息为空
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			// 报错
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

	@Override
	public List<ClassTableObject> findAllClass(String major) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<ClassTableObject> classList = new ArrayList<ClassTableObject>();
		try {
			conn = getConn();
			statement = conn.createStatement();
			//sql语句查询所有可以用的学院
			String sqlForclass = "select * from class_table where major = '" + major +"';";
			System.out.println(sqlForclass);
			resultSet = statement.executeQuery(sqlForclass);
			if(resultSet != null) {
				while(resultSet.next()) {
					ClassTableObject classObj = new ClassTableObject();
					classObj.setMajor(resultSet.getString("major"));
					classObj.set_class(resultSet.getString("_class"));
					classList.add(classObj);
				}
				return classList;
				
			}else {
				// 学院信息为空
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			// 报错
			return null;
		}
		finally{
			closeConn(resultSet,statement, conn);
		}
	}

}
