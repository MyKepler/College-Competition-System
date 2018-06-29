package servlets.StudentSignUpFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Student;
import com.yhcj.Dao.impl.StudentImpl;
import com.yhcj.enity.ResponseObject;
import com.yhcj.enity.SignUpEnableProjectObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/StudentSignUpSelectOneEnableProjects")
public class StudentSignUpSelectOneEnableProjects extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //登录学生报名
        Student studentDao = new StudentImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        String id = request.getParameter("id");
//        System.out.println(id);
        if(StringUtils.isNotBlank(id)){
            SignUpEnableProjectObject spo = studentDao.StudentSignUpSelectOneEnableProjects(id);
            if (spo!=null){
                result = new ResponseObject(200,"查询项目信息成功",spo);
            }else
                result = new ResponseObject(500,"查询项目信息失败");
        }else
            result = new ResponseObject(500,"url地址错误");
        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }
}
