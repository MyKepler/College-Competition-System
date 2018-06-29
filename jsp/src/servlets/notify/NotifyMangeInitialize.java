package servlets.notify;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.enity.NotifyObject;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//��ʼ���������
@WebServlet("/NotifyMange")
public class NotifyMangeInitialize extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");

        //��¼�������
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        List<NotifyObject> list = new ArrayList<NotifyObject>();

        if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize)){
            list=NotifyDao.queryAllNotifyInfo(pageNum,pageSize);
            if (list!=null){
                result = new  ResponseObject(200,"���سɹ�!",list);
            }else {
                result = new ResponseObject(500,"����ʧ�ܣ�",list);
            }

        }else{
            result = new ResponseObject(500,"��ȡ����ʧ�ܣ�",list);
        }
        out.println(new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
