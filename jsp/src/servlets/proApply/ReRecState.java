package servlets.proApply;

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
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.enity.RecruitmentObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class ReRecState
 */
@WebServlet("/ReRecState")
public class ReRecState extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReRecState() {
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
		String state = request.getParameter("state");
		String publish_user = request.getParameter("publish_user");
		String publish_time = request.getParameter("publish_time");
		String end_time = request.getParameter("end_time");
		// ��Ŀ����
		Project reRecSateDao = new ProjectImpl();
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		
		if(StringUtils.isNotBlank(proId)&&StringUtils.isNotBlank(state)&&StringUtils.isNotBlank(publish_user)) {
			RecruitmentObject recObj = new RecruitmentObject();
			recObj.setId(proId);
			recObj.setPublish_user(publish_user);
			recObj.setPublish_time(publish_time);
			recObj.setEnd_time(end_time);
			recObj.setState(state);
			boolean isReState = reRecSateDao.reRecState(recObj);
			if(isReState) {
				result = new  ResponseObject(200,"��Ŀ֪ͨ���³ɹ�!");
			}
			else {
				result = new ResponseObject(500,"��Ŀ֪ͨ����ʧ��!");
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
