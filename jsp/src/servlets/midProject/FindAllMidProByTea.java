package servlets.midProject;

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
import com.yhcj.Dao.MidProject;
import com.yhcj.Dao.impl.MidProjectImpl;
import com.yhcj.enity.ProStuTeaAndRev;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindAllMidProByTea
 */
@WebServlet("/FindAllMidProByTea")
public class FindAllMidProByTea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindAllMidProByTea() {
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
		//学生对象
		MidProject findAllProByTeaDao = new MidProjectImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		List<ProStuTeaAndRev> proList = new ArrayList<ProStuTeaAndRev>();
		
		if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize) && StringUtils.isNotBlank(userId)) {
			proList = findAllProByTeaDao.findMidProByTea(pageNum, pageSize, userId);
//			System.out.println("proList"+proList);
			if(proList != null) {
				result = new  ResponseObject(200,"成功返回项目信息!",proList);
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
