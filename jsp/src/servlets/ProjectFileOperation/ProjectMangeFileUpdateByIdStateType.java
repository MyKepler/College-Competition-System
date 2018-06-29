package servlets.ProjectFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//更新特定id所有上传文件或者单个文件的state和type（path为*则为全部文件，path为可用地址则为单个文件）
@WebServlet("/ProjectMangeFileUpdateByIdStateType")
public class ProjectMangeFileUpdateByIdStateType extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");
        String state = request.getParameter("state");
        String type = request.getParameter("type");
        String path = request.getParameter("path");

        //登录项目管理
        Project ProjectDao = new ProjectImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        //更新
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(path) && StringUtils.isNotBlank(state) && StringUtils.isNotBlank(type)){
            Integer msg = ProjectDao.ProjectSignUpUpdateAllFilesStateAndType(id,state,type,path);
            if(msg<0){
                result = new ResponseObject(500,"更新失败！");
            }else
                result = new ResponseObject(200,"更新成功！");
        }else {
            result = new ResponseObject(500,"url地址错误！");
        }

        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
