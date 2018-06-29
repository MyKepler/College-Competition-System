package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.EduMajAndDegree;
import com.yhcj.Dao.impl.EduMajAndDegreeImpl;
import com.yhcj.enity.EducationObject;
import com.yhcj.enity.MajorObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindAllMajorTeacher
 */
@WebServlet("/FindAllMajorTeacher")
public class FindAllMajorTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindAllMajorTeacher() {
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
		//ѧԺרҵ�༶����
		EduMajAndDegree findAllMajorDao = new EduMajAndDegreeImpl();
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		List<MajorObject> majorList = new ArrayList<MajorObject>();
		//���Ҳ���
		majorList = findAllMajorDao.findAllMajor();
		if(majorList != null) {
			result = new  ResponseObject(200,"�ɹ�����ѧԺ!",majorList);
		}
		else {
			result = new ResponseObject(500,"����ѧԺ����!");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
