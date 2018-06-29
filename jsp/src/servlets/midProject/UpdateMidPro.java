package servlets.midProject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.MidProject;
import com.yhcj.Dao.User;
import com.yhcj.Dao.impl.MidProjectImpl;
import com.yhcj.Dao.impl.UserImpl;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.UserObject;

@WebServlet("/UpdateMidPro")
public class UpdateMidPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMidPro() {
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
		String proName = request.getParameter("proName");
		String proStatus = request.getParameter("proStatus");
		String leader = request.getParameter("leader");
		String counselor = request.getParameter("counselor");
		System.out.println(leader);
	    User findUserByNameDao = new UserImpl();
		UserObject leaderObj = new UserObject();
		UserObject teaObj = new UserObject();
		String leaderId = null;
		String teaId = null;
		if(StringUtils.isNotBlank(leader) && StringUtils.isNotBlank(counselor)) {
			leaderObj = findUserByNameDao.findUserByName(leader);
			teaObj = findUserByNameDao.findUserByName(counselor);
            //System.out.println(teaObj.getUser_id());
			if(leaderObj != null && teaObj != null) {
				leaderId = leaderObj.getUser_id();
				teaId = teaObj.getUser_id();
				System.out.println(leaderId);
				System.out.println(teaId);
			}
		}

		// 项目对象
		MidProject proDao = new MidProjectImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		if(StringUtils.isNotBlank(proId) && StringUtils.isNotBlank(proName) && StringUtils.isNotBlank(proStatus)
				&& StringUtils.isNotBlank(leaderId) && StringUtils.isNotBlank(teaId)) {
			ProjectObject proObj = new ProjectObject();
			proObj.setPro_id(proId);
			proObj.setPro_name(proName);
			proObj.setPro_status(proStatus);
			proObj.setPro_principal_id(leaderId);
			proObj.setPro_teacher_id(teaId);
			int update = proDao.updateMidPro(proObj);
			if(update != 0) {
				result = new  ResponseObject(200,"成功更新项目中期信息!");
			}
			else {
				result = new ResponseObject(500,"没有更新项目中期信息!");
			}
		}else {
			result = new ResponseObject(500,"url参数没有传递过来");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
