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

//�����ض�id�����ϴ��ļ����ߵ����ļ���state��type��pathΪ*��Ϊȫ���ļ���pathΪ���õ�ַ��Ϊ�����ļ���
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

        //��¼��Ŀ����
        Project ProjectDao = new ProjectImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        //����
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(path) && StringUtils.isNotBlank(state) && StringUtils.isNotBlank(type)){
            Integer msg = ProjectDao.ProjectSignUpUpdateAllFilesStateAndType(id,state,type,path);
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
