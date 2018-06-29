package servlets.StudentSignUpFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Student;
import com.yhcj.Dao.impl.StudentImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.SignUpJoinProjectObject;
import com.yhcj.enity.SignUperObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


//��ѯ�����˲μӵ���Ŀ
@WebServlet("/StudentSignUpMyJoin")
public class StudentSignUpMyJoin extends HttpServlet {
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

        String userId = request.getParameter("userId");

        if(StringUtils.isNotBlank(userId) ){
            List<SignUpJoinProjectObject> list = studentDao.StudentSignUpMyJoin(userId);
            if (list!=null){
                result = new ResponseObject(200,"��ѯ�ļ���Ϣ�ɹ�",list);
            }else
                result = new ResponseObject(500,"��ѯ�ļ�������Ϣʧ��");
        }else
            result = new ResponseObject(500,"url����û�д��ݹ���");
        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doPost(request,response);
    }
}
