package servlets.notify;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.enity.NotifyObject;
import com.yhcj.enity.ResponseObject;/*
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;*/

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//�½�����ʱ���ļ��ϴ���ͬʱҲ����������һ��Ψһid
@WebServlet("/NotifyMangeUpload")
public class NotifyMangeUpload extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
       
        
        //��¼�������
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //�����
        PrintWriter out = response.getWriter();
        //������
        ResponseObject result = null;
        String id = null;
        String fileDir = getServletContext().getRealPath("/NotifyFileUpload");

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
                System.out.println("getid"+id);
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
                    filename = name.substring(name.lastIndexOf("\\") + 1,
                            name.length());

                    address = fileDir + "//" + filename; // �����ϴ��ļ��ı����ַ
                    File saveFile = new File(address); // �����ļ������ַ�������ļ�
                    try {
                        formitem.write(saveFile); // ���ļ�д����
                        filepath="NotifyFileUpload/" + filename;
                        //���浽���ݿ�
                        System.out.println("���ݿ�id"+id);
                        i=NotifyDao.NotifyUploadFiles(id,filepath,filename);

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
        doPost(request,response);
    }
}
