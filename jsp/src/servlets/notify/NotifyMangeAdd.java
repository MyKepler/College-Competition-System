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

//���id��Ҫ��/NotifyUpload������
@WebServlet("/NotifyMangeAdd")
public class NotifyMangeAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // ��ȡҪ��ӵ��¹������Ϣ
        // id�ɺ���Զ��������
        String id = request.getParameter("id");
        
        String title = request.getParameter("title");
        String state = request.getParameter("status");
        Date time = Date.valueOf(request.getParameter("time"));
        // userNameȱ���Զ���� �û������ڵ����û�п���
        String userName = request.getParameter("publishName");
        String introduction=request.getParameter("introduce");

        System.out.println(id);
        System.out.println(title);
        System.out.println(state);
        System.out.println(userName);
        System.out.println(introduction);
        //��¼�������
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        if(StringUtils.isNotBlank("id") && StringUtils.isNotBlank("title") && time!=null && StringUtils.isNotBlank("state") && StringUtils.isNotBlank("name") && StringUtils.isNotBlank("introduction")){
            Integer msg = NotifyDao.addNewNotify(id,title,state,time,userName,introduction);
            if(msg<0){
                result = new ResponseObject(500,"��ӹ�����Ϣ����");
            }else
                result = new ResponseObject(200,"�ɹ���ӹ�����Ϣ");
        }else
            result = new ResponseObject(500,"url����û�д��ݹ���");
        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
