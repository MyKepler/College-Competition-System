package servlets.projectInfo;

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
import com.yhcj.Dao.ProjectInfo;
import com.yhcj.Dao.impl.ProjectInfoImpl;
import com.yhcj.enity.ProjectInfoObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindAllProjectInfoByTea
 */
@WebServlet("/FindAllProjectInfoByTea")
public class FindAllProjectInfoByTea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindAllProjectInfoByTea() {
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
		String userId = request.getParameter("userId");
		//
		ProjectInfo findAllProInfoByTeaDao = new ProjectInfoImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		List<ProjectInfoObject> proInfoList = new ArrayList<ProjectInfoObject>();
		
		if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize) && StringUtils.isNotBlank(userId)) {
			proInfoList = findAllProInfoByTeaDao.findAllProInfoByTea(pageNum, pageSize, userId);
			if(proInfoList != null) {
				result = new  ResponseObject(200,"成功返回项目信息!",proInfoList);
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
