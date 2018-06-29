package servlets.notify;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.enity.NotifyFilesObject;
import com.yhcj.enity.ResponseObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//��������Ҫ���ص��ļ�
@WebServlet("/NotifyMangeDownload")
public class NotifyMangeDownload extends HttpServlet {
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");

        //��¼�������
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        if (StringUtils.isNotBlank(id)){
        		response.reset();
            response.setContentType("application/OCTET-STREAM");
            response.addHeader("Content-Disposition", "attachment; filename= download.zip");
            List<NotifyFilesObject> list = NotifyDao.queryAllNotifyFiles(id);
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zos.setMethod(ZipOutputStream.DEFLATED);//����ѹ������
            DataOutputStream os=null;
            //ѭ�����ļ�д��ѹ����
            for (NotifyFilesObject nfo:list){
                String filePath = nfo.getFilePath();
                String fileName = nfo.getFileName();
                String fileFullPath=this.getServletContext().getRealPath("/NotifyFileUpload") +"/"+ fileName;
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
