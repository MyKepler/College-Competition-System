package servlets.ProjectReviewFileOperation;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.ProReview;
import com.yhcj.Dao.Project;
import com.yhcj.Dao.impl.ProReviewImpl;
import com.yhcj.Dao.impl.ProjectImpl;
import com.yhcj.enity.ResponseObject;
/*import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;*/

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//��Ŀ�����ϴ��ļ�
@WebServlet("/ProjectReviewUploadFiles")
public class ProjectReviewUploadFiles extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //��¼��Ŀ�������
        ProReview ProReviewDao = new ProReviewImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;

        String id = null;
        String type = null;

        //�ļ��ϴ��ı����ַ
        String fileDir = getServletContext().getRealPath("/ProjectFileUpload");

        //���Ŀ¼�������򴴽�
        File uploadDir = new File(fileDir);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        System.out.println("fileDir: " + fileDir);

        // ָ���ϴ��ļ��ı����ַ
        String address = "";
        String filename=null;//�ļ���
        String filepath=null;

        Integer i = 0;
        if (ServletFileUpload.isMultipartContent(request)) { // �ж��Ƿ����ϴ��ļ�
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(20 * 1024 * 1024); // �����ڴ�������洢���ֽ���
            factory.setRepository(factory.getRepository()); // ���ô����ʱ�ļ���Ŀ¼
            ServletFileUpload upload = new ServletFileUpload(factory); // �����µ��ϴ��ļ����



            int size = 5 * 1024 * 1024; // ָ���ϴ��ļ��Ĵ�С
            List formlists = null; // ���������ϴ��ļ��ļ��϶���

            try {
                formlists = upload.parseRequest((RequestContext) request); // ��ȡ�ϴ��ļ�����
                Map param = new HashMap();
                for (Object object:formlists) {
                    FileItem fileItem = (FileItem) object;
                    if (fileItem.isFormField()){
                        param.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
                    }
                }
                id = (String) param.get("id");
                type = (String)param.get("type");
                System.out.println("getid"+id);
                System.out.println("type"+type);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            if(id.equals("yhcj")) {
                java.util.Date date=new java.util.Date();
                id=String.valueOf(date.getTime());
            }

            Iterator iter = formlists.iterator(); // ��ȡ�ϴ��ļ�������
            while (iter.hasNext()) {
                FileItem formitem = (FileItem) iter.next(); // ��ȡÿ���ϴ��ļ�
                if (!formitem.isFormField()) { // ���Բ����ϴ��ļ��ı���
                    String name = formitem.getName(); // ��ȡ�ϴ��ļ�������
                    if (formitem.getSize() > size) { // ����ϴ��ļ����ڹ涨���ϴ��ļ��Ĵ�С
                        break; // �˳�����
                    }
                    String adjunctsize = new Long(formitem.getSize()).toString(); // ��ȡ�ϴ��ļ��Ĵ�С
                    if ((name == null) || (name.equals(""))
                            && (adjunctsize.equals("0"))) // ����ϴ��ļ�Ϊ��
                        continue; // �˳�����
                    String exname;
                    filename = name.substring(name.lastIndexOf("\\")+1,name.length());
                    exname=filename;
                    String fname="";
                    String fileType="";
                    if (filename.lastIndexOf(".")!=-1){

                        Calendar cal = Calendar.getInstance();
                        String localdata = String.format("%4d-%02d-%02d",cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH));
                        String localtime=String.format("_%2d-%02d-%02d", cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));

                        fname=filename.substring(0,filename.lastIndexOf("."));
                        fileType=filename.substring(filename.lastIndexOf("."));
                        filename=fname+localdata+localtime+fileType;

                    }
                    address = fileDir + "//" + filename; // �����ϴ��ļ��ı����ַ
                    File saveFile = new File(address); // �����ļ������ַ�������ļ�
                    try {
                        formitem.write(saveFile); // ���ļ�д����
                        filepath="ProjectFileUpload/" + filename;
                        //���浽���ݿ�
                        System.out.println("���ݿ�id"+id);
                        i=ProReviewDao.ProjectReviewUploadFiles(id,filepath,type,exname);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if(i < 0){
            result = new ResponseObject(500,"����ʧ�ܣ�",id);
        }else if(i == 0) {
            result = new ResponseObject(200,"û���ļ���",id);
        }else {
            result = new ResponseObject(200,"����ɹ���",id);
        }

        //��ѷ��غ��id����ȥ���������id�½����棬���ű�id����һ��,����û���ļ�Ҳ�ǻ᷵��id��
        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
