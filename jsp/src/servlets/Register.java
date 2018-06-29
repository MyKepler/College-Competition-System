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
		//user����еĲ����ֶ�
		String studentId = request.getParameter("studentId");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		if ("false".equals(gender)) {
			gender = "��";
		}else {
			gender = "Ů";
		}
		String mail = request.getParameter("email");
		String tele = request.getParameter("tele");	
		//Ĭ��ֻ��ѧ��ע�Ტ�ҿ���
		String identity = "ѧ��";
		String state	 = "����";
		RegisterObject register = new RegisterObject(studentId,name,password,gender,mail,tele,state,identity);

		//��½ע�����
		LogAndReg LoginDao = new LogAndRegImpl();
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		//ע�����
		if(StringUtils.isNotBlank(studentId) && StringUtils.isNotBlank(name) &&
			StringUtils.isNotBlank(password) && StringUtils.isNotBlank(gender)&&
			StringUtils.isNotBlank(mail) && StringUtils.isNotBlank(tele)) {
			UserObject user = LoginDao.register(register);
			List<UserObject> list = new ArrayList<UserObject>();
			//System.out.print("user"+user);
			if(user != null) {
				//��cookieд����
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
				result = new  ResponseObject(200,"ע��ɹ�!",list);
			}else{
				result = new ResponseObject(500,"�û���Ϣ����!");
			}
		}else {
			result = new ResponseObject(500,"�û���Ϣ������!");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
