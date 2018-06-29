package servlets.notify;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

//公告基本信息修改
@WebServlet("/NotifyMangeEdit")
public class NotifyMangeEdit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //获取要修改的公告内容
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        Date time = Date.valueOf(request.getParameter("time"));
        String userName = request.getParameter("name");
        String introduction=request.getParameter("introduction");
        System.out.println(id);
        System.out.println(title);
        System.out.println(time);
        System.out.println(userName);
        System.out.println(introduction);

        //登录公告管理
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        if(StringUtils.isNotBlank("id") && StringUtils.isNotBlank("title") && time!=null && StringUtils.isNotBlank("name") && StringUtils.isNotBlank("introduction")){
           Integer msg = NotifyDao.editNotify(id,title,time,userName,introduction);
           if (msg<0){
               result = new ResponseObject(500,"修改公告信息错误");
           }else{
               result = new ResponseObject(200,"修改公告信息成功");
           }
        }else {
            result = new ResponseObject(500,"url参数没有传递过来");
        }

        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
