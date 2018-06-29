package servlets.notify;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.enity.NotifyFilesObject;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/NotifyMangeFilesDeleteAll")
public class NotifyMangeFilesDeleteAll extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");

        //登录公告管理
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        //先删除原有的上传文件
        if (StringUtils.isNotBlank(id)){
            List<NotifyFilesObject> list = NotifyDao.queryAllNotifyFiles(id);
            if(list!=null){
                for(NotifyFilesObject nfo:list){
                	    String fileName=nfo.getFileName();
                    File file = new File(this.getServletContext().getRealPath("/NotifyFileUpload") +"/"+ fileName);
                  
                    if(file.exists()){
                        file.delete();
                    }
                }
            }
            Integer msg = NotifyDao.deleteAllNotifyFiles(id);
            if(msg<0){
                result = new ResponseObject(500,"删除失败！");
            }else
                result = new ResponseObject(200,"删除成功！");
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
