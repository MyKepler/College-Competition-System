package servlets.notify;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;
import com.yhcj.Dao.NotifyMange;
import com.yhcj.Dao.impl.NotifyMangeImpl;
import com.yhcj.enity.NotifyFilesObject;
import com.yhcj.enity.NotifyObject;
import com.yhcj.enity.ResponseObject;

/**
 * Servlet implementation class NotifyManageSpeInfo
 */
@WebServlet("/NotifyManageSpeInfo")
public class NotifyManageSpeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotifyManageSpeInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String id = request.getParameter("id");

        //登录公告管理
        NotifyMange NotifyDao = new NotifyMangeImpl();
        //输出流
        PrintWriter out = response.getWriter();
        //返回体
        ResponseObject result = null;

        if(StringUtils.isNotBlank(id)){
            NotifyObject obj = NotifyDao.queryAllNotify(id);
            if(obj!=null){
                result = new ResponseObject(200,"查看公告详情信息成功",obj);
            }else
                result = new ResponseObject(500,"查看公告详情信息失败");
        }else
            result = new ResponseObject(500,"url参数没有传递过来");

        out.println(new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(result));
        out.flush();
        out.close();
	}

}
