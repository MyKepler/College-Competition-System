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
import com.yhcj.Dao.impl.ProjectAuditImpl;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class ReProAuditState
 */
@WebServlet("/ReProAuditState")
public class ReProAuditState extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReProAuditState() {
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
		String recId = request.getParameter("recId");
		// ��Ŀ����
		ProjectAudit reProAuditSateDao = new ProjectAuditImpl();
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		
		if(StringUtils.isNotBlank(recId)&&StringUtils.isNotBlank(proId)&&StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(state)) {
			boolean isReState = reProAuditSateDao.ReState(recId, state, proId, userId);
			if(isReState) {
				result = new  ResponseObject(200,"��Ŀ��Ա����״̬�ɹ�!");
			}
			else {
				result = new ResponseObject(500,"��Ŀ��Ա����״̬ʧ��!");
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
