package servlets.TeacherFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.Teacher;
import com.yhcj.Dao.impl.ProReviewImpl;
import com.yhcj.Dao.impl.TeacherImpl;
import com.yhcj.enity.ProjectReviewFileObject;
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

//��ѯ��Ŀ��ͬ����׶ε������ļ�
@WebServlet("/TeacherProjectReviewFileQueryByType")
public class TeacherProjectReviewFileQueryByType extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");
        String type = request.getParameter("type");

        //��¼��Ŀ�������
        Teacher teacherDao = new TeacherImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(type)){
            List<ProjectReviewFileObject> list = teacherDao.TeacherReviewQueryByTypeFiles(id,type);
            if(list!=null){
                result = new ResponseObject(200,"��ѯ�ļ���Ϣ�ɹ�",list);
            }else
                result = new ResponseObject(500,"��ѯ�ļ�������Ϣʧ��");
        }else
            result = new ResponseObject(500,"url����û�д��ݹ���");

        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
