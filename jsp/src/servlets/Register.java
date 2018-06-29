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

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.LogAndReg;
import com.yhcj.Dao.impl.LogAndRegImpl;
import com.yhcj.enity.RegisterObject;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.UserObject;


/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		//user表格中的部分字段
		String studentId = request.getParameter("studentId");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		if ("false".equals(gender)) {
			gender = "男";
		}else {
			gender = "女";
		}
		String mail = request.getParameter("email");
		String tele = request.getParameter("tele");	
		//默认只能学生注册并且可用
		String identity = "学生";
		String state	 = "可用";
		RegisterObject register = new RegisterObject(studentId,name,password,gender,mail,tele,state,identity);

		//登陆注册对象
		LogAndReg LoginDao = new LogAndRegImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		//注册操作
		if(StringUtils.isNotBlank(studentId) && StringUtils.isNotBlank(name) &&
			StringUtils.isNotBlank(password) && StringUtils.isNotBlank(gender)&&
			StringUtils.isNotBlank(mail) && StringUtils.isNotBlank(tele)) {
			UserObject user = LoginDao.register(register);
			List<UserObject> list = new ArrayList<UserObject>();
			//System.out.print("user"+user);
			if(user != null) {
				//向cookie写对象
//				Cookie cookieId = new Cookie("userId",user.getUser_id());
//				Cookie cookieName = new Cookie("userName",user.getUser_name());
//				Cookie cookieType = new Cookie("userType",user.getUser_identity());
//				cookieId.setMaxAge( 60 * 60 * 24 );
//				cookieName.setMaxAge( 60 * 60 * 24 );
//				cookieType.setMaxAge( 60 * 60 * 24 ); 
//				response.addCookie(cookieId);
//				response.addCookie(cookieName);
//				response.addCookie(cookieType);
				list.add(user);
				result = new  ResponseObject(200,"注册成功!",list);
			}else{
				result = new ResponseObject(500,"用户信息错误!");
			}
		}else {
			result = new ResponseObject(500,"用户信息不完整!");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
