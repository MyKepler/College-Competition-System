package servlets.StudentSignUpFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Student;
import com.yhcj.Dao.impl.StudentImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.SignUperObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//ÿ����Ŀ�б�������д
@WebServlet("/StudentSignUpEachDetails")
public class StudentSignUpEachDetails extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //��¼ѧ������
        Student studentDao = new StudentImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        String projectId = request.getParameter("projectId");
        String userId = request.getParameter("userId");

        if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(projectId)){
        	
            SignUperObject suo = studentDao.StudentSignUpEachDetails(projectId,userId);
            
            if (suo!=null){
                result = new ResponseObject(200,"��ѯ�ļ���Ϣ�ɹ�",suo);
                System.out.println(suo.getProjectId());
            }else {
                result = new ResponseObject(500,"��ѯ�ļ�������Ϣʧ��");
            }
        }else
            result = new ResponseObject(500,"url����û�д��ݹ���");
       
        out.println(new GsonBuilder().create().toJson(result));
//        out.println("��־��");
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doPost(request,response);
    }
}
