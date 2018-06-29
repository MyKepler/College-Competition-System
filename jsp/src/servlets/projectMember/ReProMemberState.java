package servlets.projectMember;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;

import com.yhcj.Dao.ProjectMember;

import com.yhcj.Dao.impl.ProjectMemberImpl;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class ReProMemberState
 */
@WebServlet("/ReProMemberState")
public class ReProMemberState extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReProMemberState() {
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
		String userId = request.getParameter("userId");
		String state = request.getParameter("state");
		// 项目对象
		ProjectMember reMemberSateDao = new ProjectMemberImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		
		if(StringUtils.isNotBlank(proId)&&StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(state)) {
			boolean isReState = reMemberSateDao.reStates(proId, userId, state);
			if(isReState) {
				result = new  ResponseObject(200,"项目成员更新状态成功!");
			}
			else {
				result = new ResponseObject(500,"项目成员更新状态失败!");
			}
		}
		else {
			result = new ResponseObject(500,"url参数没有传递过来");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
