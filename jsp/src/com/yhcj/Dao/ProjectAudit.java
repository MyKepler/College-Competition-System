package com.yhcj.Dao;

import java.util.List;

import com.yhcj.enity.ProjectAuditObject;

public interface ProjectAudit {

	/*
	 * ��ѯ��������
	 */
	int getCount();
	
	/*
	 * �г����д���˵ı�����
	 */
	List<ProjectAuditObject> findAllProAudit(String pageNum, String pageSize);
	
	/*
	 * �г�ָ����Ŀ�뱨���ߵ���Ϣ
	 */
	ProjectAuditObject findProAuditById(String recId);
	
	/*
	 * ��ˣ��ı�recruitment_sign_up״̬,��˳ɹ�������ݵ�project_student����
	 */
	
	boolean ReState(String recId, String state,String proId,String userId);
	
	
}
