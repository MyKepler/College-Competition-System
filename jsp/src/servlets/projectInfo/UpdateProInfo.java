package servlets.projectInfo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.ProjectInfo;
import com.yhcj.Dao.User;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.Dao.impl.ProjectInfoImpl;
import com.yhcj.Dao.impl.UserImpl;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.UserObject;

/**
 * Servlet implementation class UpdateProInfo
 */
@WebServlet("/UpdateProInfo")
public class UpdateProInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
		String proName = request.getParameter("name");
		//String proStatus = request.getParameter("proStatus");
		String endTime = request.getParameter("endTime");
		String planNum = request.getParameter("planNum");
		String startTime = request.getParameter("startTime");
		String leader = request.getParameter("leader");
		String counselor = request.getParameter("counselor");
		//用户对象
		User findUserByNameDao = new UserImpl();
		UserObject leaderObj = new UserObject();
		UserObject teaObj = new UserObject();
		String leaderId = null;
		String teaId = null;
		if(StringUtils.isNotBlank(leader) && StringUtils.isNotBlank(counselor)) {
			leaderObj = findUserByNameDao.findUserByName(leader);
			teaObj = findUserByNameDao.findUserByName(counselor);
			if(leaderObj != null && teaObj != null) {
				leaderId = leaderObj.getUser_id();
				teaId = teaObj.getUser_id();
				System.out.println(leaderId);
				System.out.println(teaId);
			}
		}
		// 项目对象
		ProjectInfo proDao = new ProjectInfoImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		if(StringUtils.isNotBlank(proId) && StringUtils.isNotBlank(proName) 
				&& StringUtils.isNotBlank(endTime)&& StringUtils.isNotBlank(planNum)
				&& StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(leaderId) 
				&& StringUtils.isNotBlank(teaId)) {
			ProjectObject proObj = new ProjectObject();
			proObj.setPro_id(proId);
			proObj.setPro_name(proName);
			proObj.setFinish_year(endTime);
			proObj.setPlan_num(planNum);
			proObj.setStart_year(startTime);
			//proObj.setFinish_year(finish_year);
			proObj.setPro_principal_id(leaderId);
			proObj.setPro_teacher_id(teaId);
			int update = proDao.updateProInfo(proObj);
			System.out.println(update);
			if(update != 0) {
				result = new  ResponseObject(200,"成功更新项目信息!");
			}
			else {
				result = new ResponseObject(500,"没有更新项目信息!");
			}
		}else {
			result = new ResponseObject(500,"url参数没有传递过来");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
