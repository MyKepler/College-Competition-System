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
import com.yhcj.enity.ClassTableObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class FindAllClass
 */
@WebServlet("/FindAllClass")
public class FindAllClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindAllClass() {
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
		String major = request.getParameter("major");
		//ѧԺרҵ�༶����
		AcaMajAndClass findAllClassDao = new AcaMajAndClassImpl();
		//�����
		PrintWriter out = response.getWriter();
		//������
		ResponseObject result = null;
		if(StringUtils.isNotBlank(major)) {
			List<ClassTableObject> classList = new ArrayList<ClassTableObject>();
			classList = findAllClassDao.findAllClass(major);
			if(classList != null) {
				result = new  ResponseObject(200,"���ذ༶��Ϣ�ɹ�!",classList);
			}	
			else {
				result = new ResponseObject(500,"�༶��ϢΪ��!");
			}
		}
		else {
			result = new ResponseObject(500,"ѧԺ��Ϣ����Ϊ��!");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
	}

}
