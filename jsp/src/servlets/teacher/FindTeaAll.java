package servlets.teacher;

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
import com.yhcj.Dao.Teacher;
import com.yhcj.Dao.impl.TeacherImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.TeacherObject;

/**
 * Servlet implementation class FindTeaAll
 */
@WebServlet("/FindTeaAll")
public class FindTeaAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindTeaAll() {
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
		// TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        // ѧ������
        Teacher findAllStuDao = new TeacherImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;
        List<TeacherObject> teaList = new ArrayList<TeacherObject>();
        if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize)) {
            teaList = findAllStuDao.findAllTea(pageNum,pageSize);
            if(teaList != null) {
                result = new  ResponseObject(200,"�ɹ����ؽ�ʦ��Ϣ!",teaList);
            }
            else {
                result = new ResponseObject(500,"���ؽ�ʦ��Ϣ����!");
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
