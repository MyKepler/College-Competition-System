package servlets.TeacherFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.Teacher;
import com.yhcj.Dao.impl.TeacherImpl;
import com.yhcj.enity.ProjectFileObject;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//������Ŀ���Ϲ����е��������в���
@WebServlet("/TeacherProjectAllFileDownload")
public class TeacherProjectAllFileDownload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");
        String state = request.getParameter("state");

        //��¼��Ŀ����
        Teacher teacherDao = new TeacherImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        if (StringUtils.isNotBlank(id)){
            response.reset();
            response.setContentType("application/OCTET-STREAM");
            response.addHeader("Content-Disposition", "attachment; filename= download.zip");

            //����������Щ��
            //List<ProjectFileObject> list = ProjectDao.ProjectSignUpQueryByStateFiles(id,state);
            List<ProjectFileObject> list = teacherDao.TeacherQueryByStateFiles(id,state);

            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zos.setMethod(ZipOutputStream.DEFLATED);//����ѹ������
            DataOutputStream os=null;
            //ѭ�����ļ�д��ѹ����
            for (ProjectFileObject pfo:list){
                String filePath = pfo.getFilePath();
                String fileName = pfo.getFileName();

                String fname="";
                String fileType="";
                if (fileName.lastIndexOf(".")!=-1){

                        fname=fileName.substring(0,fileName.lastIndexOf("."));
                        fileType=fileName.substring(fileName.lastIndexOf("."));
                        fileName=pfo.getFileType()+"����-" + fname + fileType;

                }

                String fileFullPath=this.getServletContext().getRealPath(filePath) ;
                File file = new File(fileFullPath);
                try{
                    zos.putNextEntry(new ZipEntry(fileName));
                    os = new DataOutputStream(zos);
                    InputStream is = new FileInputStream(file);
                    byte[] b =new byte[100];
                    int length=0;
                    while((length = is.read(b))!= -1){
                        os.write(b, 0, length);
                    }
                    is.close();
                    zos.closeEntry();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //�ر���
            try {
                os.flush();
                os.close();
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            result = new ResponseObject(500,"��ȡ����ʧ�ܣ�");
            out.println(new GsonBuilder().create().toJson(result));
            out.flush();
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
