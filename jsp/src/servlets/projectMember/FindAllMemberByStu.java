package servlets.projectMember;

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
import com.yhcj.Dao.ProjectMember;
import com.yhcj.Dao.impl.ProjectMemberImpl;
import com.yhcj.enity.ProjectMemberObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindAllMemberByStu
 */
@WebServlet("/FindAllMemberByStu")
public class FindAllMemberByStu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindAllMemberByStu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
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
		String userId = request.getParameter("userId");
		
		ProjectMember findAllProMemberByStuDao = new ProjectMemberImpl();
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		List<ProjectMemberObject> proMemberList = new ArrayList<ProjectMemberObject>();
		
		if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize) && StringUtils.isNotBlank(userId)) {
			proMemberList = findAllProMemberByStuDao.findAllProInfoByStu(pageNum, pageSize, userId);
			if(proMemberList != null) {
				result = new  ResponseObject(200,"�ɹ�������Ŀ��Ա��Ϣ!",proMemberList);
			}
			else {
				result = new ResponseObject(500,"������Ŀ��Ա��Ϣ����!");
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
