package servlets.projectAudit;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.ProjectAudit;
import com.yhcj.Dao.ProjectMember;
import com.yhcj.Dao.impl.ProjectAuditImpl;
import com.yhcj.Dao.impl.ProjectMemberImpl;
import com.yhcj.enity.ProjectAuditObject;
import com.yhcj.enity.ProjectMemberObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindProAuditById
 */
@WebServlet("/FindProAuditById")
public class FindProAuditById extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindProAuditById() {
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
		String recId = request.getParameter("recId");
		
		ProjectAudit findProAuditByIdDao = new ProjectAuditImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		ProjectAuditObject proAuditObj = new ProjectAuditObject();
		
		if(StringUtils.isNotBlank(recId)) {
			proAuditObj = findProAuditByIdDao.findProAuditById(recId);
			if(proAuditObj != null) {
				result = new  ResponseObject(200,"成功返回项目信息!",proAuditObj);
			}
			else {
				result = new ResponseObject(500,"返回项目信息错误!");
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
