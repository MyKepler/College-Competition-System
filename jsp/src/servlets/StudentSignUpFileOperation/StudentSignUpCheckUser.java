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

//确认输入的用户id和用户名存在于数据库中
@WebServlet("/StudentSignUpCheckUser")
public class StudentSignUpCheckUser extends HttpServlet {
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

        String userName = request.getParameter("userName");
        String userId = request.getParameter("userId");

        if(StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(userName)){
            Integer msg = studentDao.StudentSignUpCheckUser(userId,userName);
            if (msg<0)
                result = new ResponseObject(500,"身份验证错误");
            else
                result = new ResponseObject(200,"验证成功");
        }else
            result = new ResponseObject(500,"url地址错误");

        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
    }
}
