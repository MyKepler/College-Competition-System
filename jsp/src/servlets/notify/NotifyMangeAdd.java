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

//这个id需要从/NotifyUpload中生成
@WebServlet("/NotifyMangeAdd")
public class NotifyMangeAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 获取要添加的新公告的信息
        // id由后端自动编号生成
        String id = request.getParameter("id");
        
        String title = request.getParameter("title");
        String state = request.getParameter("status");
        Date time = Date.valueOf(request.getParameter("time"));
        // userName缺少自动检查 用户不存在的情况没有考虑
        String userName = request.getParameter("publishName");
        String introduction=request.getParameter("introduce");

        System.out.println(id);
        System.out.println(title);
        System.out.println(state);
        System.out.println(userName);
        System.out.println(introduction);
        //登录公告管理
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        if(StringUtils.isNotBlank("id") && StringUtils.isNotBlank("title") && time!=null && StringUtils.isNotBlank("state") && StringUtils.isNotBlank("name") && StringUtils.isNotBlank("introduction")){
            Integer msg = NotifyDao.addNewNotify(id,title,state,time,userName,introduction);
            if(msg<0){
                result = new ResponseObject(500,"添加公告信息错误");
            }else
                result = new ResponseObject(200,"成功添加公告信息");
        }else
            result = new ResponseObject(500,"url参数没有传递过来");
        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
