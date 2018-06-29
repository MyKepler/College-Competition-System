package servlets.ProjectReviewFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.impl.ProReviewImpl;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.enity.ProjectFileObject;
import com.yhcj.enity.ProjectReviewFileObject;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@WebServlet("/ProjectReviewFileDownload")
public class ProjectReviewFileDownload extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");
        String type = request.getParameter("type");
        System.out.println("id"+id);
        System.out.println("type"+type);

        //��¼��Ŀ�������
        ProReview ProReviewDao = new ProReviewImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(type)){
            response.reset();
            response.setContentType("application/OCTET-STREAM");
            response.addHeader("Content-Disposition", "attachment; filename= download.zip");
            
            //����������Щ��
            List<ProjectReviewFileObject> list = ProReviewDao.ProjectReviewQueryByTypeFiles(id,type);

            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zos.setMethod(ZipOutputStream.DEFLATED);//����ѹ������
            DataOutputStream os=null;
            System.out.println(list);
            //ѭ�����ļ�д��ѹ����
            for (ProjectReviewFileObject prf:list){
                String filePath = prf.getFilePath();
                String fileName = prf.getFileName();
                String fileFullPath=this.getServletContext().getRealPath(filePath) ;
                System.out.println("filePath"+filePath);
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
