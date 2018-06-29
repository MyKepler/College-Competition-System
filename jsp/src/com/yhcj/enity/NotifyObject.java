package com.yhcj.enity;

import java.sql.Date;
import java.util.List;

public class NotifyObject {
    String id;
    String title;
    String state;
    Date time;
    String userName;
    String introduction;
    List<NotifyFilesObject> notifyFilesObj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

	public List<NotifyFilesObject> getNotifyFilesObj() {
		return notifyFilesObj;
	}

	public void setNotifyFilesObj(List<NotifyFilesObject> notifyFilesObj) {
		this.notifyFilesObj = notifyFilesObj;
	}

    
}
