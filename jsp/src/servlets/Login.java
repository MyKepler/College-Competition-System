package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.LogAndReg;
import com.yhcj.Dao.impl.LogAndRegImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.UserObject;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
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
		String id = request.getParameter("id");
		String password = request.getParameter("password");
	
		//登陆注册对象
		LogAndReg LoginDao = new LogAndRegImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		//登陆操作
		if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(password)) {
			UserObject user = LoginDao.login(id, password);
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
				result = new  ResponseObject(200,"登陆成功!",list);
			}else{
				result = new ResponseObject(500,"用户信息错误!");
			}
		}else {
			result = new ResponseObject(500,"用户名和密码不能为空!");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}
		
		
}


