package servlets.teacher;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Teacher;
import com.yhcj.Dao.impl.TeacherImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.TeacherObject;
/**
 * Servlet implementation class UpdateTea
 */
@WebServlet("/UpdateTea")
public class UpdateTea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTea() {
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
		String degree = request.getParameter("degree");
		String bachelor = request.getParameter("bachelor");
		String major = request.getParameter("major");
		//教师对象
		Teacher findAllTeaDao = new TeacherImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(sex) && StringUtils.isNotBlank(email)&& StringUtils.isNotBlank(phone)
				&& StringUtils.isNotBlank(status) && StringUtils.isNotBlank(userType) && StringUtils.isNotBlank(degree) 
				&& StringUtils.isNotBlank(bachelor) && StringUtils.isNotBlank(major)) {
			TeacherObject teaObj = new TeacherObject();
			teaObj.setUser_id(userId);
			teaObj.setUser_name(name);
			teaObj.setUser_sex(sex);
			teaObj.setUser_mail(email);
			teaObj.setUser_phone(phone);
			teaObj.setAccount_state(status);
			teaObj.setUser_identity(userType);
			teaObj.setTeacher_degree(degree);
			teaObj.setTeacher_bachelor(bachelor);
			teaObj.setTeacher_major(major);
			int update = findAllTeaDao.updateTea(teaObj);
			if(update != 0) {
				result = new  ResponseObject(200,"成功更新教师个人信息!");
			}
			else {
				result = new ResponseObject(500,"没有更新教师个人信息!");
			}
		}else {
			result = new ResponseObject(500,"url参数没有传递过来");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
