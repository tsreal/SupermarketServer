package com.tgtiger.Bean;

import java.sql.Date;

public class Worker {
    //员工表
    //员工号，手机号作为注册账号，密码，注册时期
    private String workerNo;
    private String name;
    private String password;
    private String phone;
    private Date dateSignUp;
    private int level;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkerNo() {
        return workerNo;
    }

    public void setWorkerNo(String workerNo) {
        this.workerNo = workerNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getDateSignUp() {
        return dateSignUp;
    }
    public void setDateSignUp(Date dateSignUp) {
        this.dateSignUp = dateSignUp;
    }
}
