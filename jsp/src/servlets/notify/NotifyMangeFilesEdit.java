package servlets.notify;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.enity.NotifyFilesObject;
import com.yhcj.enity.ResponseObject;/*
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;*/
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

//�޸Ĺ���ʱ���ļ��ϴ�
@WebServlet("/NotifyMangeFilesEdit")
public class NotifyMangeFilesEdit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = null;

        //��¼�������
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;



        String fileDir = getServletContext().getRealPath("/NotifyFileUpload");
        // ָ���ϴ��ļ��ı����ַ
        //���Ŀ¼�������򴴽�
        File uploadDir = new File(fileDir);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        String address = "";
        String filename=null;//�ļ���
        String filepath=null;


        int i=0;

        if (ServletFileUpload.isMultipartContent(request)) { // �ж��Ƿ����ϴ��ļ�
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(20 * 1024 * 1024); // �����ڴ�������洢���ֽ���
            factory.setRepository(factory.getRepository()); // ���ô����ʱ�ļ���Ŀ¼
            ServletFileUpload upload = new ServletFileUpload(factory); // �����µ��ϴ��ļ����

            int size = 5 * 1024 * 1024; // ָ���ϴ��ļ��Ĵ�С
            List formlists = null; // ���������ϴ��ļ��ļ��϶���

            try {
                formlists = upload.parseRequest((RequestContext) request); // ��ȡ�ϴ��ļ�����
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            Iterator iter = formlists.iterator(); // ��ȡ�ϴ��ļ�������
            Iterator iterId = formlists.iterator(); //��ȡidֵ�ĵ�����

            while (iterId.hasNext()){
                FileItem formitem = (FileItem) iterId.next(); // ��ȡÿ���ϴ��ļ�
                if (!formitem.isFormField()){}
                else {
                    String fieldName=formitem.getFieldName();

                    //����Ҫ��id
                    //��ȡ�����id
                    System.out.println("�����ƣ�"+fieldName);
                    if(fieldName.equals("id")){
                        id=formitem.getString("UTF-8");
                    }

                }
            }

            while (iter.hasNext()) {
                FileItem formitem = (FileItem) iter.next(); // ��ȡÿ���ϴ��ļ�
                if (!formitem.isFormField()){
                    String name = formitem.getName(); // ��ȡ�ϴ��ļ�������
                    if (formitem.getSize() > size) { // ����ϴ��ļ����ڹ涨���ϴ��ļ��Ĵ�С
                        break; // �˳�����
                    }
                    String adjunctsize = new Long(formitem.getSize()).toString(); // ��ȡ�ϴ��ļ��Ĵ�С
                    if ((name == null) || (name.equals(""))
                            && (adjunctsize.equals("0"))) // ����ϴ��ļ�Ϊ��
                        continue; // �˳�����
                    filename = name.substring(name.lastIndexOf("\\") + 1,
                            name.length());

                    address = fileDir + "\\" + filename; // �����ϴ��ļ��ı����ַ
                    File saveFile = new File(address); // �����ļ������ַ�������ļ�
                    try {
                        formitem.write(saveFile); // ���ļ�д����
                        filepath="upload/" + filename;

                        //���浽���ݿ�
                        i=NotifyDao.NotifyUploadFiles(id,filepath,filename);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if(i<0){
            result = new ResponseObject(500,"�޸��ļ�����");
        }else
            result = new ResponseObject(200,"�޸��ļ��ɹ�");
        if(id==null){
            result = new ResponseObject(500,"������ȡʧ��");
        }

        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
