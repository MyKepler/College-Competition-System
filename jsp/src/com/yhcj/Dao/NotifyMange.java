package com.yhcj.Dao;
import com.yhcj.enity.NotifyObject;
import com.yhcj.enity.PublishProjectInfoObject;
import com.yhcj.enity.NotifyFilesObject;

import java.sql.Date;
import java.util.List;

public interface NotifyMange{
	// 获取公告数量
	int getCount();
    //查看一条新公告
    NotifyObject NotifyDetails(String id);

    //添加一条新的公告
    Integer addNewNotify(String id, String title, String state,
                              Date time,String userName,String introduction);
    //修改一条公告
    Integer editNotify(String id,String title,Date time,String userName,String introduction);

    //发布一条公告或者下架一条公告
    Integer publishNotify(String id,String state);

    //删除一条公告
    Integer deleteNotify(String id);

    //上传文件，保存文件信息到服务器
    Integer NotifyUploadFiles(String id,String path,String name);

    //初始化时加载所有公告
    List<NotifyObject> queryAllNotifyInfo(String pageNum,String pageSize);
    // 查询该公告的信息
    NotifyObject  queryAllNotify(String id);

    //查询该公告的所有上传文件
    List<NotifyFilesObject> queryAllNotifyFiles(String id);

    //查询该公告上的一个文件
    NotifyFilesObject queryOneNotifyFiles(String id,String path);

    //删除该公告的所有文件
    Integer deleteAllNotifyFiles(String id);

    //删除该公告的单个文件
    Integer deleteOneNotifyFiles(String id,String path);
    
    //返回给前端所有发布的项目信息
    List<PublishProjectInfoObject> queryAllPublishProjectInfo(String pageNum,String pageSize);

    //返回发布的项目count
    Integer getPublishProjectCount();

    //返回给前端所有发布的公告
    List<NotifyObject> queryAllPublishNotifyInfo(String pageNum,String pageSize);

    //返回发布的项目count
    Integer getPublishNotifyCount();
    
    
}