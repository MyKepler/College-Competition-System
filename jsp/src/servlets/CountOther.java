package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.EndProject;
import com.yhcj.Dao.MidProject;
import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.ProjectInfo;
import com.yhcj.Dao.ProjectMember;
import com.yhcj.Dao.ReviewTask;
import com.yhcj.Dao.impl.EndProjectImpl;
import com.yhcj.Dao.impl.MidProjectImpl;
import com.yhcj.Dao.impl.ProReviewImpl;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.Dao.impl.ProjectInfoImpl;
import com.yhcj.Dao.impl.ProjectMemberImpl;
import com.yhcj.Dao.impl.ReviewTaskImpl;
import com.yhcj.enity.CountObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class CountOther
 */
@WebServlet("/CountOther")
public class CountOther extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CountOther() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				// TODO Auto-generated method stub
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
				response.setHeader("Access-Control-Allow-Credentials", "true");
				String userId = request.getParameter("userId");
				//�����
				PrintWriter out = response.getWriter();
				//������
				ResponseObject result = null;
				List<CountObject> numList = new ArrayList<CountObject>();
				/*------------------------------------------------��ʦ��----------------------------------------*/

				// ��Ŀ�������
				Project findAllProByTeaDao = new ProjectImpl();
				
				CountObject numTeaProObj = new CountObject();
				
				int proTeaNum = 0;
				proTeaNum = findAllProByTeaDao.getTeaCount(userId);
				numTeaProObj.setNum_name("��ʦ��Ŀ����");
				numTeaProObj.setNum(proTeaNum);
				
				numList.add(numTeaProObj);
				
				// ��Ŀ���ڶ���
				MidProject findMidProByTeaDao = new MidProjectImpl();
				CountObject numTeaMidProObj = new CountObject();
				int proTeaMidNum = 0;
				proTeaMidNum = findMidProByTeaDao.getTeaCount(userId);
				numTeaMidProObj.setNum_name("��ʦ��Ŀ����");
				numTeaMidProObj.setNum(proTeaMidNum);
				numList.add(numTeaMidProObj);
				
				// ��Ŀ�������
				EndProject findEndProByTeaDao = new EndProjectImpl();
				CountObject numTeaEndProObj = new CountObject();
				int proTeaEndNum = 0;
				proTeaEndNum = findEndProByTeaDao.getTeaCount(userId);
				numTeaEndProObj.setNum_name("��ʦ��Ŀ����");
				numTeaEndProObj.setNum(proTeaEndNum);
				numList.add(numTeaEndProObj);
				//��Ŀ��Ϣ����
				ProjectInfo findAllProInfoByTeaDao = new ProjectInfoImpl();
				CountObject numTeaProInfoObj = new CountObject();
				int proTeaProInfoNum = 0;
				proTeaProInfoNum = findAllProInfoByTeaDao.getTeaCount(userId);
				numTeaProInfoObj.setNum_name("��ʦ��Ŀ��Ϣ");
				numTeaProInfoObj.setNum(proTeaProInfoNum);
				numList.add(numTeaProInfoObj);
				//��Ŀ����������
				ProReview findAllProProRevByTeaDao = new ProReviewImpl();
				CountObject numTeaProRevObj = new CountObject();
				int proTeaProRevNum = 0;
				proTeaProRevNum = findAllProProRevByTeaDao.getTeaCount(userId);
				numTeaProRevObj.setNum_name("��ʦ��Ŀ������");
				numTeaProRevObj.setNum(proTeaProRevNum);
				numList.add(numTeaProRevObj);
				//��Ŀ��Ա����
				ProjectMember findAllProProMemByTeaDao = new ProjectMemberImpl();
				CountObject numTeaProMemObj = new CountObject();
				int proTeaProMemNum = 0;
				proTeaProMemNum = findAllProProMemByTeaDao.getTeaCount(userId);
				numTeaProMemObj.setNum_name("��ʦ��Ŀ��Ա");
				numTeaProMemObj.setNum(proTeaProMemNum);
				numList.add(numTeaProMemObj);

				//��Ŀ���Ϲ���
				ProjectInfo findAllProFileByTeaDao = new ProjectInfoImpl();
				CountObject numTeaProFileObj = new CountObject();
				int proTeaProFileNum = 0;
				proTeaProFileNum = findAllProFileByTeaDao.getProFileCount(userId);
				numTeaProFileObj.setNum_name("��ʦ��Ŀ����");
				numTeaProFileObj.setNum(proTeaProFileNum);
				numList.add(numTeaProFileObj);
				/*------------------------------------------------ѧ����----------------------------------------*/
				Project findAllStuProApplyDao=new ProjectImpl();
				CountObject numStuProApply=new CountObject();
				int stuproapplyNum = 0;
				stuproapplyNum = findAllStuProApplyDao.getCount(userId);
				System.out.print(stuproapplyNum);
				numStuProApply.setNum_name("ѧ������Ŀ����");
				numStuProApply.setNum(stuproapplyNum);
				numList.add(numStuProApply);
				
				MidProject findAllStuMidProDao=new MidProjectImpl();
				CountObject numStuMidPro=new CountObject();
				int stumidproNum = 0; 
				stumidproNum = findAllStuMidProDao.getCount(userId);
				numStuMidPro.setNum_name("ѧ������Ŀ����");
				numStuMidPro.setNum(stumidproNum);
				numList.add(numStuMidPro);
				
				EndProject findAllStuEndProDao=new EndProjectImpl();
				CountObject numStuEndPro=new CountObject();
				int stuendproNum = 0;
				stuendproNum = findAllStuEndProDao.getCount(userId);
				numStuEndPro.setNum_name("ѧ������Ŀ����");
				numStuEndPro.setNum(stuendproNum);
				numList.add(numStuEndPro);
				
				ProjectInfo findAllStuProInfoDao=new ProjectInfoImpl();
				CountObject numStuProInfo=new CountObject();
				int stuproinfoNum = 0;
				stuproinfoNum = findAllStuProInfoDao.getCount(userId);
				numStuProInfo.setNum_name("ѧ������Ŀ��Ϣ");
				numStuProInfo.setNum(stuproinfoNum);
				numList.add(numStuProInfo);
				
				ProReview findAllStuProReviewDao=new ProReviewImpl();
				CountObject numStuProReview=new CountObject();
				int stuproreviewNum = 0;
				stuproreviewNum = findAllStuProReviewDao.getCount(userId);
				numStuProReview.setNum_name("ѧ����������Ϣ");
				numStuProReview.setNum(stuproreviewNum);
				numList.add(numStuProReview);
				
				ProjectInfo findAllStuProManageDao=new ProjectInfoImpl();
				CountObject numStuProManage=new CountObject();
				int stupromanageNum = 0;
				stupromanageNum = findAllStuProManageDao.getCount(userId);
				numStuProManage.setNum_name("ѧ������Ŀ��Ϣ����");
				numStuProManage.setNum(stupromanageNum);
				numList.add(numStuProManage);
				/*------------------------------------------------ѧ����----------------------------------------*/
				
				//��Ŀ��Ա����
				ProjectMember findAllProProMemByStuDao = new ProjectMemberImpl();
				
				CountObject numStuProMemObj = new CountObject();
				
				int proStuProMemNum = 0;
				proStuProMemNum = findAllProProMemByStuDao.getStuCount(userId);
				numStuProMemObj.setNum_name("ѧ����Ŀ��Ա");
				numStuProMemObj.setNum(proStuProMemNum);
				
				numList.add(numStuProMemObj);
				
				ProjectInfo findAllStuFileManageDao=new ProjectInfoImpl();
				CountObject numStuFileManage=new CountObject();
				int stufilemanageNum = 0;
				stufilemanageNum = findAllStuFileManageDao.getCount(userId);
				numStuFileManage.setNum_name("ѧ������Ŀ�ļ�����");
				numStuFileManage.setNum(stufilemanageNum);
				numList.add(numStuFileManage);
				/*------------------------------------------------����ר�Ҷ�----------------------------------------*/

				//����ר�Ҷ�
				ReviewTask findReviewTaskDao=new ReviewTaskImpl();
				
				CountObject numReviewTask=new CountObject();
				
				int reviewtaskNum = 0;
				reviewtaskNum = findReviewTaskDao.getCount(userId);
				numReviewTask.setNum_name("���������");
				numReviewTask.setNum(reviewtaskNum);
				
				numList.add(numReviewTask);
				result = new  ResponseObject(200,"����!",numList);
				
				
				
				out.println(new GsonBuilder().create().toJson(result));
				out.flush();
				out.close();
	}

}
