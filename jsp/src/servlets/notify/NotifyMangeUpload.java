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

//新建公告时的文件上传，同时也用它来生成一个唯一id
@WebServlet("/NotifyMangeUpload")
public class NotifyMangeUpload extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
       
        
        //登录公告管理
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;
        String id = null;
        String fileDir = getServletContext().getRealPath("/NotifyFileUpload");

        //如果目录不存在则创建
        File uploadDir = new File(fileDir);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        System.out.println("fileDir: " + fileDir);

        // 指定上传文件的保存地址
        String address = "";
        String filename=null;//文件名
        String filepath=null;
        Integer i = 0;
        if (ServletFileUpload.isMultipartContent(request)) { // 判断是否是上传文件
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(20 * 1024 * 1024); // 设置内存中允许存储的字节数
            factory.setRepository(factory.getRepository()); // 设置存放临时文件的目录
            ServletFileUpload upload = new ServletFileUpload(factory); // 创建新的上传文件句柄
            
            

            int size = 5 * 1024 * 1024; // 指定上传文件的大小
            List formlists = null; // 创建保存上传文件的集合对象

            try {
                formlists = upload.parseRequest((RequestContext) request); // 获取上传文件集合
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
        
            Iterator iter = formlists.iterator(); // 获取上传文件迭代器
            while (iter.hasNext()) {
                FileItem formitem = (FileItem) iter.next(); // 获取每个上传文件
                if (!formitem.isFormField()) { // 忽略不是上传文件的表单域
                    String name = formitem.getName(); // 获取上传文件的名称
                    if (formitem.getSize() > size) { // 如果上传文件大于规定的上传文件的大小
                        break; // 退出程序
                    }
                    String adjunctsize = new Long(formitem.getSize()).toString(); // 获取上传文件的大小
                    if ((name == null) || (name.equals(""))
                            && (adjunctsize.equals("0"))) // 如果上传文件为空
                        continue; // 退出程序
                    filename = name.substring(name.lastIndexOf("\\") + 1,
                            name.length());

                    address = fileDir + "//" + filename; // 创建上传文件的保存地址
                    File saveFile = new File(address); // 根据文件保存地址，创建文件
                    try {
                        formitem.write(saveFile); // 向文件写数据
                        filepath="NotifyFileUpload/" + filename;
                        //保存到数据库
                        System.out.println("数据库id"+id);
                        i=NotifyDao.NotifyUploadFiles(id,filepath,filename);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if(i < 0){
            result = new ResponseObject(500,"储存失败！",id);
        }else if(i == 0) {
        		result = new ResponseObject(200,"没有文件！",id);
        }else {
        		result = new ResponseObject(200,"储存成功！",id);
        }

        //请把返回后的id传回去这样用这个id新建公告，两张表id保持一致,就算没有文件也是会返回id的
        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
