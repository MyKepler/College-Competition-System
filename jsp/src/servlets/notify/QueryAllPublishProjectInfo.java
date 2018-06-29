package servlets.notify;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.enity.PublishProjectInfoObject;
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

@WebServlet("/QueryAllPublishProjectInfo")
public class QueryAllPublishProjectInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");

        //登录公告管理
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        if (StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize)){
            List<PublishProjectInfoObject> list = NotifyDao.queryAllPublishProjectInfo(pageNum,pageSize);
            if (list!=null)
                result = new ResponseObject(200,"成功返回发布公告信息",list);
            else
                result = new ResponseObject(500,"返回失败");
        }else
            result = new ResponseObject(500,"url地址获取失败");



        out.println(new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
