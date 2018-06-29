package servlets.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.User;
import com.yhcj.Dao.impl.UserImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.UserObject;

/**
 * Servlet implementation class UpdateChangeInfoByTea
 */
@WebServlet("/UpdateChangeInfoByTea")
public class UpdateChangeInfoByTea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateChangeInfoByTea() {
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
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String user_pwd = request.getParameter("user_pwd");
		//对象
		User updateUserByTeaDao = new UserImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		if( StringUtils.isNotBlank(email)&& StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(user_pwd)
				&& StringUtils.isNotBlank(userId)) {
			UserObject userObj = new UserObject();
			userObj.setUser_id(userId);
			userObj.setUser_mail(email);
			userObj.setUser_phone(phone);
			userObj.setUser_pwd(user_pwd);
			int update = updateUserByTeaDao.updateByTea(userObj);
			if(update != 0) {
				result = new  ResponseObject(200,"成功更新用户个人信息!");
			}
			else {
				result = new ResponseObject(500,"没有更新用户个人信息!");
			}
		}else {
			result = new ResponseObject(500,"url参数没有传递过来");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
