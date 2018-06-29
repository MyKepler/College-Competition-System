package servlets.proReview;

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
import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.User;
import com.yhcj.Dao.impl.MidProjectImpl;
import com.yhcj.Dao.impl.ProReviewImpl;
import com.yhcj.Dao.impl.UserImpl;
import com.yhcj.enity.ProRevObject;
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.UserObject;

@WebServlet("/UpdateProReview")
public class UpdateProReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProReview() {
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
		String review_msg = request.getParameter("review_msg");
		String review_code = request.getParameter("review_code");
		String project_id = request.getParameter("project_id");
		String review_type = request.getParameter("review_type");
	   	// 项目对象
		ProReview proDao = new ProReviewImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		ProRevObject prorevObj  = new ProRevObject();
		if(StringUtils.isNotBlank(review_msg) && StringUtils.isNotBlank(review_code) && StringUtils.isNotBlank(project_id)
				&& StringUtils.isNotBlank(review_type)) {
			prorevObj.setReview_msg(review_msg);
			prorevObj.setReview_code(review_code);
			prorevObj.setProject_id(project_id);
			prorevObj.setReview_type(review_type);
			int update = proDao.updateProReview(prorevObj);
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
