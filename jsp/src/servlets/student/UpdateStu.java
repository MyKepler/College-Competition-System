package servlets.student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Student;
import com.yhcj.Dao.impl.StudentImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.StudentObject;

/**
 * Servlet implementation class UpdateStu
 */
@WebServlet("/UpdateStu")
public class UpdateStu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateStu() {
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
		String academy = request.getParameter("academy");
		String major = request.getParameter("major");
		String classes = request.getParameter("classes");
		//学生对象
		Student findAllStuDao = new StudentImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(sex) && StringUtils.isNotBlank(email)&& StringUtils.isNotBlank(phone)
				&& StringUtils.isNotBlank(status) && StringUtils.isNotBlank(userType) && StringUtils.isNotBlank(academy) 
				&& StringUtils.isNotBlank(major) && StringUtils.isNotBlank(classes)) {
			StudentObject stuObj = new StudentObject();
			stuObj.setUser_id(userId);
			stuObj.setUser_name(name);
			stuObj.setUser_sex(sex);
			stuObj.setUser_mail(email);
			stuObj.setUser_phone(phone);
			stuObj.setAccount_state(status);
			stuObj.setUser_identity(userType);
			stuObj.setStudent_academy(academy);
			stuObj.setStudent_major(major);
			stuObj.setStudent_class(classes);
			int update = findAllStuDao.updateStu(stuObj);
			if(update != 0) {
				result = new  ResponseObject(200,"成功更新学生个人信息!");
			}
			else {
				result = new ResponseObject(500,"没有更新学生个人信息!");
			}
		}else {
			result = new ResponseObject(500,"url参数没有传递过来");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
		
	}

}
