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
import com.yhcj.Dao.Reviewer;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.Dao.impl.ProjectInfoImpl;
import com.yhcj.Dao.impl.ReviewerImpl;
import com.yhcj.enity.ProAndReviewObject;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.ReviewerObject;

/**
 * Servlet implementation class FindRevMsgInPro
 */
@WebServlet("/FindRevMsgInPro")
public class FindRevMsgInPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindRevMsgInPro() {
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
		String proStatus = request.getParameter("proStatus");

		ProjectInfo findRevMsgDao = new ProjectInfoImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		ProAndReviewObject reviewObj = new ProAndReviewObject();
		if(StringUtils.isNotBlank(proId) && StringUtils.isNotBlank(proId)) {
			reviewObj = findRevMsgDao.findRevMsgInProInfo(proId, proStatus);
			if(reviewObj != null) {
				result = new  ResponseObject(200,"成功返回评审信息!",reviewObj);
			}
			else {
				result = new ResponseObject(500,"返回评审信息错误!");
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
