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
import com.yhcj.enity.ChangeInfoObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindChangeInfoByType
 */
@WebServlet("/FindChangeInfoByType")
public class FindChangeInfoByType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindChangeInfoByType() {
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
		String type = request.getParameter("type");
		System.out.print(userId);
		// ѧ������
		User findChangeInfoDao = new UserImpl();
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		ChangeInfoObject infoObj = new ChangeInfoObject();
		if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(type)) {
			infoObj = findChangeInfoDao.findUserMsgByType(userId, type);
			if(infoObj != null) {
				result = new  ResponseObject(200,"�ɹ������û���Ϣ!",infoObj);
			}
			else {
				result = new ResponseObject(500,"�����û���Ϣ����!");
			}
		}
		else {
			result = new ResponseObject(500,"url����û�д��ݹ���");
		}
		
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
