package servlets.proApply;

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
import com.yhcj.Dao.Project;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.enity.ProjectApplyObject;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindAllProject
 */
@WebServlet("/FindAllProject")
public class FindAllProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindAllProject() {
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
		//ѧ������
		Project findAllProDao = new ProjectImpl();
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		List<ProjectApplyObject> proList = new ArrayList<ProjectApplyObject>();
		
		if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize)) {
			proList = findAllProDao.findAllPro(pageNum,pageSize);
			if(proList != null) {
				result = new  ResponseObject(200,"�ɹ�������Ŀ��Ϣ!",proList);
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
