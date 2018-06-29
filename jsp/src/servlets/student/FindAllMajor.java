package servlets.student;

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
import com.yhcj.Dao.AcaMajAndClass;
import com.yhcj.Dao.impl.AcaMajAndClassImpl;
import com.yhcj.enity.MajorObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindAllMajor
 */
@WebServlet("/FindAllMajor")
public class FindAllMajor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindAllMajor() {
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
		String academy = request.getParameter("academy");
		//学院专业班级对象
		AcaMajAndClass findAllMajDao = new AcaMajAndClassImpl();
		//输出流
		PrintWriter out = response.getWriter();
		//返回体
		ResponseObject result = null;
		
		if(StringUtils.isNotBlank(academy)) {
			List<MajorObject> majorList = new ArrayList<MajorObject>();
			majorList = findAllMajDao.findAllMajor(academy);
			if(majorList != null) {
				result = new  ResponseObject(200,"返回专业信息成功!",majorList);
			}	
			else {
				result = new ResponseObject(500,"专业信息为空!");
			}
		}
		else {
			result = new ResponseObject(500,"学院信息不能为空!");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}
}
