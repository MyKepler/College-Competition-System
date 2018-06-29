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

//修改公告时的文件上传
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

        //登录公告管理
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;



        String fileDir = getServletContext().getRealPath("/NotifyFileUpload");
        // 指定上传文件的保存地址
        //如果目录不存在则创建
        File uploadDir = new File(fileDir);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        String address = "";
        String filename=null;//文件名
        String filepath=null;


        int i=0;

        if (ServletFileUpload.isMultipartContent(request)) { // 判断是否是上传文件
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(20 * 1024 * 1024); // 设置内存中允许存储的字节数
            factory.setRepository(factory.getRepository()); // 设置存放临时文件的目录
            ServletFileUpload upload = new ServletFileUpload(factory); // 创建新的上传文件句柄

            int size = 5 * 1024 * 1024; // 指定上传文件的大小
            List formlists = null; // 创建保存上传文件的集合对象

            try {
                formlists = upload.parseRequest((RequestContext) request); // 获取上传文件集合
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            Iterator iter = formlists.iterator(); // 获取上传文件迭代器
            Iterator iterId = formlists.iterator(); //获取id值的迭代器

            while (iterId.hasNext()){
                FileItem formitem = (FileItem) iterId.next(); // 获取每个上传文件
                if (!formitem.isFormField()){}
                else {
                    String fieldName=formitem.getFieldName();

                    //表单名要是id
                    //获取传入的id
                    System.out.println("表单名称："+fieldName);
                    if(fieldName.equals("id")){
                        id=formitem.getString("UTF-8");
                    }

                }
            }

            while (iter.hasNext()) {
                FileItem formitem = (FileItem) iter.next(); // 获取每个上传文件
                if (!formitem.isFormField()){
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

                    address = fileDir + "\\" + filename; // 创建上传文件的保存地址
                    File saveFile = new File(address); // 根据文件保存地址，创建文件
                    try {
                        formitem.write(saveFile); // 向文件写数据
                        filepath="upload/" + filename;

                        //保存到数据库
                        i=NotifyDao.NotifyUploadFiles(id,filepath,filename);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if(i<0){
            result = new ResponseObject(500,"修改文件错误");
        }else
            result = new ResponseObject(200,"修改文件成功");
        if(id==null){
            result = new ResponseObject(500,"参数获取失败");
        }

        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
