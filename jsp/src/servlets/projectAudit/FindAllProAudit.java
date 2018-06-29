package servlets.projectAudit;

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
import com.yhcj.Dao.ProjectAudit;
import com.yhcj.Dao.ProjectMember;
import com.yhcj.Dao.impl.ProjectAuditImpl;
import com.yhcj.Dao.impl.ProjectMemberImpl;
import com.yhcj.enity.ProjectAuditObject;
import com.yhcj.enity.ProjectMemberObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindAllProAudit
 */
@WebServlet("/FindAllProAudit")
public class FindAllProAudit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindAllProAudit() {
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
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		ProjectAudit findAllProAudit = new ProjectAuditImpl();
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		List<ProjectAuditObject> proAuditList = new ArrayList<ProjectAuditObject>();
		
		if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize)) {
			proAuditList = findAllProAudit.findAllProAudit(pageNum, pageSize);
			if(proAuditList != null) {
				result = new  ResponseObject(200,"�ɹ�������Ŀ��Ϣ!",proAuditList);
			}
			else {
				result = new ResponseObject(500,"������Ŀ��Ϣ����!");
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
