package servlets.ProjectFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.enity.NotifyFilesObject;
import com.yhcj.enity.ProjectFileObject;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//项目管理中特点id查询全部文件
@WebServlet("/ProjectMangeFileQueryAll")
public class ProjectMangeFileQueryAll extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");

        //登录项目管理
        Project ProjectDao = new ProjectImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        if(StringUtils.isNotBlank(id)){
            List<ProjectFileObject> list = ProjectDao.ProjectSignUpQueryAllFiles(id);
            if(list!=null){
                result = new ResponseObject(200,"查询文件信息成功",list);
            }else
                result = new ResponseObject(500,"查询文件详情信息失败");
        }else
            result = new ResponseObject(500,"url参数没有传递过来");

        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
