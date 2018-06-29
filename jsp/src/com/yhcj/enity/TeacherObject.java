package com.yhcj.enity;

public class TeacherObject {
    // 用户所有信息
    public String user_id;
    public String user_name;
    public String user_pwd;
    public String user_sex;
    public String user_mail;
    public String user_phone;
    public String account_state;
    public String user_identity;

    //教师特有信息
    public String teacher_degree;
    public String teacher_bachelor;
    public String teacher_major;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getAccount_state() {
        return account_state;
    }

    public void setAccount_state(String account_state) {
        this.account_state = account_state;
    }

    public String getUser_identity() {
        return user_identity;
    }

    public void setUser_identity(String user_identity) {
        this.user_identity = user_identity;
    }

    public String getTeacher_degree() {
        return teacher_degree;
    }

    public void setTeacher_degree(String teacher_degree) {
        this.teacher_degree = teacher_degree;
    }

    public String getTeacher_bachelor() {
        return teacher_bachelor;
    }

    public void setTeacher_bachelor(String teacher_bachelor) {
        this.teacher_bachelor = teacher_bachelor;
    }

    public String getTeacher_major() {
        return teacher_major;
    }

    public void setTeacher_major(String teacher_major) {
        this.teacher_major = teacher_major;
    }
}
