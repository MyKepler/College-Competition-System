package servlets.ProjectFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.enity.ProjectFileObject;
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

//��Ŀ�������ص�id��typeɾ��ȫ���ļ�
@WebServlet("/ProjectMangeFileDeleteByType")
public class ProjectMangeFileDeleteByType extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");
        String type = request.getParameter("type");

        //��¼��Ŀ����
        Project ProjectDao = new ProjectImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        //��ɾ��ԭ�е��ϴ��ļ�
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(type)){
            List<ProjectFileObject> list = ProjectDao.ProjectSignUpQueryByTypeFiles(id,type);
            if(list!=null){
                for(ProjectFileObject psf:list){
                    String filePath=psf.getFilePath();
                    File file = new File(this.getServletContext().getRealPath(filePath));

                    if(file.exists()){
                        file.delete();
                    }
                }
            }
            Integer msg = ProjectDao.ProjectSignUpDeleteByTypeFiles(id,type);
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
