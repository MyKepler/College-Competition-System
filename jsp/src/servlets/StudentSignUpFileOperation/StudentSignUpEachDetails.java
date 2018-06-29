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

//每个项目中报名人填写
@WebServlet("/StudentSignUpEachDetails")
public class StudentSignUpEachDetails extends HttpServlet {
	
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

        String projectId = request.getParameter("projectId");
        String userId = request.getParameter("userId");

        if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(projectId)){
        	
            SignUperObject suo = studentDao.StudentSignUpEachDetails(projectId,userId);
            
            if (suo!=null){
                result = new ResponseObject(200,"查询文件信息成功",suo);
                System.out.println(suo.getProjectId());
            }else {
                result = new ResponseObject(500,"查询文件详情信息失败");
            }
        }else
            result = new ResponseObject(500,"url参数没有传递过来");
       
        out.println(new GsonBuilder().create().toJson(result));
//        out.println("孔志鹏");
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doPost(request,response);
    }
}
