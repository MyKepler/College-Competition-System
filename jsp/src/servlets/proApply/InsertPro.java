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
import com.yhcj.enity.ProjectObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class InsertPro
 */
@WebServlet("/InsertPro")
public class InsertPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertPro() {
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
		
		
		String proId = request.getParameter("proId");
		String proStatus = "申请";
		String proName = request.getParameter("proName");
		String counselor = request.getParameter("counselor"); 
		String leader = request.getParameter("leader");
		String proPlan = request.getParameter("proPlan");
		String startYear = request.getParameter("startYear");
		String finishYear = request.getParameter("finishYear");
		String proState = "可用";
		System.out.print("proId"+proId);
		
		
		// 项目对象
		Project proDao = new ProjectImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		if(StringUtils.isNotBlank(proId) &&StringUtils.isNotBlank(proName) && StringUtils.isNotBlank(proPlan)
				&& StringUtils.isNotBlank(startYear) && StringUtils.isNotBlank(leader) && StringUtils.isNotBlank(counselor)) {
			ProjectObject proObj = new ProjectObject();
			proObj.setPro_id(proId);
			proObj.setPro_status(proStatus);
			proObj.setPro_name(proName);
			proObj.setPro_teacher_id(counselor);
			proObj.setPro_principal_id(leader);
			proObj.setPlan_num(proPlan);
			proObj.setStart_year(startYear);
			proObj.setFinish_year(finishYear);
			proObj.setPro_state(proState);
			
			boolean update = proDao.insertPro(proObj);
			if(update) {
				result = new  ResponseObject(200,"成功插入学生个人信息!");
			}
			else {
				result = new ResponseObject(500,"没有插入学生个人信息!");
			}
		}else {
			result = new ResponseObject(500,"url参数没有传递过来");
		}
		out.println(new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(result));
		out.flush();
		out.close();
	}

}
