package servlets.ProjectReviewFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.impl.ProReviewImpl;
import com.yhcj.enity.ProjectReviewFileObject;
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

@WebServlet("/ProjectReviewFileUpdateByIdType")
public class ProjectReviewFileUpdateByIdType extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String path = request.getParameter("path");

        //��¼��Ŀ�������
        ProReview ProReviewDao = new ProReviewImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        //����
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(path) && StringUtils.isNotBlank(type)){
            Integer msg = ProReviewDao.ProjectReviewUpdateAllFilesType(id,type,path);
            if(msg<0){
                result = new ResponseObject(500,"����ʧ�ܣ�");
            }else
                result = new ResponseObject(200,"���³ɹ���");
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
