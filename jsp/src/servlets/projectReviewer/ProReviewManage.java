package servlets.projectReviewer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.ProjectReviewer;
import com.yhcj.Dao.User;
import com.yhcj.Dao.impl.ProjectReviewerImpl;
import com.yhcj.Dao.impl.UserImpl;
import com.yhcj.enity.ProAndReviewObject;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.UserObject;

/**
 * Servlet implementation class ProReviewManage
 */
@WebServlet("/ProReviewManage")
public class ProReviewManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProReviewManage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
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
		String proId = request.getParameter("proId");
		String reviewType = request.getParameter("review_type");
		String userId = request.getParameter("userId");
		
		//��Ŀ�������
		ProjectReviewer findReviewMsgDao = new ProjectReviewerImpl();
	
		//��Ŀ�������
		ProjectReviewer ReviewMsgDao = new ProjectReviewerImpl();
		
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		if(StringUtils.isNotBlank(proId) && StringUtils.isNotBlank(reviewType) 
				&& StringUtils.isNotBlank(userId)) {
			ProAndReviewObject proAndRevObj = new ProAndReviewObject();
			proAndRevObj.setProject_id(proId);
			proAndRevObj.setReview_type(reviewType);
			proAndRevObj.setReview_user_id(userId);
			int isUpdate = findReviewMsgDao.isUpdate(proId, reviewType);
			if(isUpdate == 0) {
				int insert = ReviewMsgDao.insertProReview(proAndRevObj);
				if(insert != 0) {
					result = new  ResponseObject(200,"�ɹ�������Ŀ������Ϣ!");
				}
				else {
					result = new ResponseObject(500,"û�и�����Ŀ������Ϣ!");
				}
			}
			else {
				int update = ReviewMsgDao.UpdateProreview(proAndRevObj);
				if(update != 0) {
					result = new  ResponseObject(200,"�ɹ�������Ŀ������Ϣ!");
				}else {
					result = new ResponseObject(500,"û�и�����Ŀ������Ϣ!");
				}
			}
			
		}else {
			result = new ResponseObject(500,"url����û�д��ݹ���");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
		
		
		
		

	}

}
