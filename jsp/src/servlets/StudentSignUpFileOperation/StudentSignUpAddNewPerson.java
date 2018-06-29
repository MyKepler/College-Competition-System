package servlets.StudentSignUpFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Student;
import com.yhcj.Dao.impl.StudentImpl;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/StudentSignUpAddNewPerson")
public class StudentSignUpAddNewPerson extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String signUpId = request.getParameter("signUpId");
        String projectId = request.getParameter("projectId");
        
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");
        String time = request.getParameter("time");
        String state = request.getParameter("state");

        //��¼ѧ������
        Student studentDao = new StudentImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        if(StringUtils.isNotBlank(signUpId) && StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(userId)
                && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(email) && StringUtils.isNotBlank(phoneNumber)
                && StringUtils.isNotBlank(time)  && StringUtils.isNotBlank(state) ){
            Integer msg = studentDao.StudentSignUpAddNewPerson(signUpId,projectId,userId,userName,email,phoneNumber,time,state);
            if(msg<0){
                result = new ResponseObject(500,"�½�ʧ�ܣ�");
            }else
                result = new ResponseObject(200,"�½��ɹ���");
        }else
            result = new ResponseObject(500,"url��ַ����");
        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doPost(request,response);
    }
}
