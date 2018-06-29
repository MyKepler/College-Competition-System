package com.yhcj.utils;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
监听上下文加载配置
 */
public class ListenerLoadConfig implements ServletContextListener {

   public static final long TOKEN_CHECKED_TIME=24*60*60*1000;

   @Override
   public void contextDestroyed(ServletContextEvent sce) {
   }
   @Override
   public void contextInitialized(ServletContextEvent event) {
       String path=event.getServletContext().getRealPath("/WEB-INF/config/db.properties");
       LoadDBconfig.load(path);
   }
}
