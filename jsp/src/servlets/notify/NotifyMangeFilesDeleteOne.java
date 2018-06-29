package servlets.notify;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.enity.NotifyFilesObject;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringEscapeUtils;
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

//�����޸�ʱɾ�������ļ�
@WebServlet("/NotifyMangeFilesDeleteOne")
public class NotifyMangeFilesDeleteOne extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");
        String path = request.getParameter("path");

        //��¼�������
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(path)){
            NotifyFilesObject nfo = NotifyDao.queryOneNotifyFiles(id,path);
            if(nfo!=null){
            	    String fileName=nfo.getFileName();
                 File file = new File(this.getServletContext().getRealPath("/NotifyFileUpload") +"/"+ fileName);
                if(file.exists()){
                    file.delete();
                }
            }
            Integer msg = NotifyDao.deleteOneNotifyFiles(id,path);
            if(msg<0){
                result = new ResponseObject(500,"ɾ��ʧ�ܣ�");
            }else
                result = new ResponseObject(200,"ɾ���ɹ���");
        }else {
            result = new ResponseObject(500,"url��ַ����");
        }
        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
