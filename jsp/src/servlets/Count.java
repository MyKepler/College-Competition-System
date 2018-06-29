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
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.ProjectAudit;
import com.yhcj.Dao.ProjectInfo;
import com.yhcj.Dao.ProjectMember;
import com.yhcj.Dao.ProjectReviewer;
import com.yhcj.Dao.ReviewTask;
import com.yhcj.Dao.Reviewer;
import com.yhcj.Dao.Student;
import com.yhcj.Dao.Teacher;
import com.yhcj.Dao.User;
import com.yhcj.Dao.impl.EndProjectImpl;
import com.yhcj.Dao.impl.MidProjectImpl;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.Dao.impl.ProReviewImpl;
import com.yhcj.Dao.impl.ProjectAuditImpl;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.Dao.impl.ProjectInfoImpl;
import com.yhcj.Dao.impl.ProjectMemberImpl;
import com.yhcj.Dao.impl.ProjectReviewerImpl;
import com.yhcj.Dao.impl.ReviewTaskImpl;
import com.yhcj.Dao.impl.ReviewerImpl;
import com.yhcj.Dao.impl.StudentImpl;
import com.yhcj.Dao.impl.TeacherImpl;
import com.yhcj.Dao.impl.UserImpl;
import com.yhcj.enity.CountObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class Count
 */
@WebServlet("/Count")
public class Count extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Count() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		//ǰ��
		
		NotifyMange notifyPubDao = new NotifyMangeImpl();
		CountObject notifyPubObj = new CountObject();
		int notifyPubNum = 0;
		notifyPubNum = notifyPubDao.getPublishProjectCount();
		notifyPubObj.setNum_name("������Ŀ");
		notifyPubObj.setNum(notifyPubNum);
		
		NotifyMange proPubDao = new NotifyMangeImpl();
		CountObject proPubObj = new CountObject();
		int proPubNum = 0;
		proPubNum = proPubDao.getPublishNotifyCount();
		proPubObj.setNum_name("��������");
		proPubObj.setNum(proPubNum);
		
		
		
	// ����Ա�˵�
		// �������
		NotifyMange findAllNotDao = new NotifyMangeImpl();
		
		// ��Ŀ����
		Project findAllProDao = new ProjectImpl();
		// ��Ŀ������˶���
		ProjectAudit findAllProAuditDao = new ProjectAuditImpl();
		// ��Ŀ��Ϣ����
		ProjectInfo findAllProInfoDao = new ProjectInfoImpl();
		// ��Ŀ���ڶ���
		MidProject findAllProMidDao = new MidProjectImpl();
		// ��Ŀ�������
		EndProject findAllEndProDao=new EndProjectImpl();
		// ��Ŀ�������
		ProReview findAllProRevDao = new ProReviewImpl();
		// ��Ŀ��Ա����
		ProjectMember findAllProMemDao = new ProjectMemberImpl();
		
		// ��Ŀ����������
		ProjectReviewer findAllProReviewerDao = new ProjectReviewerImpl();
				
		// �û�����
		User findAllUserDao = new UserImpl();
		// ѧ������
		Student findAllStuDao = new StudentImpl();
		// ��ʦ����
		Teacher findAllTeaDao = new TeacherImpl();
		// ����ר�Ҷ���
		Reviewer findAllRevDao = new ReviewerImpl();
	// ����ר�Ҷ�
		//�����
		ReviewTask findReviewTaskDao=new ReviewTaskImpl();

		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		List<CountObject> numList = new ArrayList<CountObject>();
		// ����Ա��
		CountObject numNotObj = new CountObject();
		
		CountObject numUserObj = new CountObject();
		CountObject numStuObj = new CountObject();
		CountObject numTeaObj = new CountObject();
		CountObject numRevObj = new CountObject();
		
		CountObject numProObj = new CountObject();
		CountObject numProInfoObj = new CountObject();
		CountObject numProMemObj = new CountObject();
		CountObject numProMidObj = new CountObject();
		CountObject numEndProObj = new CountObject();
		CountObject numProRevObj = new CountObject();
		CountObject numProAuditObj = new CountObject();
		
		CountObject numProReviewerObj = new CountObject();
		
		// ����ר�Ҷ�
		CountObject numReviewTask=new CountObject();
		
		// ����Ա��
		int notNum = 0;
		notNum = findAllNotDao.getCount();
		numNotObj.setNum_name("����");
		numNotObj.setNum(notNum);
		
		int proNum = 0;
		proNum = findAllProDao.getCount();
		numProObj.setNum_name("��Ŀ����");
		numProObj.setNum(proNum);
		int midNum = 0;
		midNum = findAllProMidDao.getCount();
		numProMidObj.setNum_name("��Ŀ����");
		numProMidObj.setNum(midNum);
		int endproNum = 0;
		endproNum = findAllEndProDao.getCount();
		numEndProObj.setNum_name("��Ŀ����");
		numEndProObj.setNum(endproNum);
		int proRivNum = 0;
		proRivNum = findAllProRevDao.getCount();
		numProRevObj.setNum_name("��Ŀ������");
		numProRevObj.setNum(proRivNum);
		int proInfoNum = 0;
		proInfoNum = findAllProInfoDao.getCount();
		numProInfoObj.setNum_name("��Ŀ��Ϣ");
		numProInfoObj.setNum(proInfoNum);
		int proMemNum = 0;
		proMemNum = findAllProMemDao.getCount();
		numProMemObj.setNum_name("��Ŀ��Ա");
		numProMemObj.setNum(proMemNum);
		int proAuditNum = 0;
		proAuditNum = findAllProAuditDao.getCount();
		numProAuditObj.setNum_name("��Ŀ�������");
		numProAuditObj.setNum(proAuditNum);
		
		int proReviewerNum = 0;
		proReviewerNum = findAllProReviewerDao.getCount();
		numProReviewerObj.setNum_name("����ר�ҹ���");
		numProReviewerObj.setNum(proReviewerNum);
		
		int userNum = 0;
		userNum = findAllUserDao.getCount();
		numUserObj.setNum_name("�û�");
		numUserObj.setNum(userNum);
		int stuNum = 0;
		stuNum = findAllStuDao.getCount();
		numStuObj.setNum_name("ѧ��");
		numStuObj.setNum(stuNum);
		int teaNum = 0;
		teaNum = findAllTeaDao.getCount();
		numTeaObj.setNum_name("��ʦ");
		numTeaObj.setNum(teaNum);
		int revNum = 0;
		revNum = findAllRevDao.getCount();
		numRevObj.setNum_name("����ר��");
		numRevObj.setNum(revNum);
		
		
		// ����ר�Ҷ�
		String userId = request.getParameter("userId");
		int reviewtaskNum = 0;
		reviewtaskNum = findReviewTaskDao.getCount(userId);
		numReviewTask.setNum_name("���������");
		numReviewTask.setNum(reviewtaskNum);
		
		numList.add(numNotObj);
		
		numList.add(numProObj);
		numList.add(numProMidObj);
		numList.add(numEndProObj);
		numList.add(numProInfoObj);
		numList.add(numProMemObj);
		numList.add(numProRevObj);
		numList.add(numProAuditObj);
		
		numList.add(numProReviewerObj);
		
		numList.add(numUserObj);
		numList.add(numStuObj);
		numList.add(numTeaObj);
		numList.add(numRevObj);
	
		numList.add(numReviewTask);
		
		numList.add(notifyPubObj);
		numList.add(proPubObj);
		
		result = new  ResponseObject(200,"����!",numList);
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
