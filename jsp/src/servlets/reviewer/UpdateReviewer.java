package servlets.reviewer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Reviewer;
import com.yhcj.Dao.impl.ReviewerImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.ReviewerObject;

/**
 * Servlet implementation class UpdateReviewer
 */
@WebServlet("/UpdateReviewer")
public class UpdateReviewer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateReviewer() {
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
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String status = request.getParameter("status");
		String userType = request.getParameter("userType");
		String education = request.getParameter("education");
		String degree = request.getParameter("degree");
		String major = request.getParameter("major");
		String companyName = request.getParameter("companyName");
		String companyPhone = request.getParameter("companyPhone");
		String companyLeader = request.getParameter("companyLeader");
		String companyAddress = request.getParameter("companyAddress");
		
		//学生对象
		Reviewer updateReviewerDao = new ReviewerImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(sex) && StringUtils.isNotBlank(email)
				&& StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(status) && StringUtils.isNotBlank(userType) 
				&& StringUtils.isNotBlank(education) && StringUtils.isNotBlank(degree) && StringUtils.isNotBlank(major)
				&& StringUtils.isNotBlank(companyName) && StringUtils.isNotBlank(companyPhone) && StringUtils.isNotBlank(companyLeader)
				&& StringUtils.isNotBlank(companyAddress)) {
			ReviewerObject reviewerObj = new ReviewerObject();
			reviewerObj.setUser_id(userId);
			reviewerObj.setUser_name(name);
			reviewerObj.setUser_sex(sex);
			reviewerObj.setUser_mail(email);
			reviewerObj.setUser_phone(phone);
			reviewerObj.setAccount_state(status);
			reviewerObj.setUser_identity(userType);
			
			reviewerObj.setTeacher_bachelor(education);
			reviewerObj.setTeacher_degree(degree);
			reviewerObj.setTeacher_major(major);
			
			reviewerObj.setCompany_name(companyName);
			reviewerObj.setCompany_phone(companyPhone);
			reviewerObj.setCompany_principal(companyLeader);
			reviewerObj.setCompany_address(companyAddress);
			int update = updateReviewerDao.updateReviewer(reviewerObj);
			if(update != 0) {
				result = new  ResponseObject(200,"成功更新专家个人信息!");
			}
			else {
				result = new ResponseObject(500,"没有更新专家个人信息!");
			}
		}else {
			result = new ResponseObject(500,"url参数没有传递过来");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
