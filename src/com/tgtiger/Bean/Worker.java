package com.tgtiger.Bean;

/*
{
"workerNo":"00001",
"name":"李晓明",
"password":"aaaaaa",
"phone":"18435181234",
"dateSignUp":"2017-12-25",
"level": 0
}
 */
public class Worker {
    /**
     * dateSignUp : 2017-12-25
     * password : aaaaaa
     * workerNo : 00001
     * phone : 18435181234
     * level : 2
     * name : 李晓明
     */
    //员工表
    //员工号，手机号作为注册账号，密码，注册时期

    private String dateSignUp;
    private String password;
    private String workerNo;
    private String phone;
    private int level;
    private String name;
    /**
     * yes : 0
     */
    private int yes;


    public void setDateSignUp(String dateSignUp) {
        this.dateSignUp = dateSignUp;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWorkerNo(String workerNo) {
        this.workerNo = workerNo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateSignUp() {
        return dateSignUp;
    }

    public String getPassword() {
        return password;
    }

    public String getWorkerNo() {
        return workerNo;
    }

    public String getPhone() {
        return phone;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getYes() {
        return yes;
    }


//    private String workerNo;
//    private String name;
//    private String password;
//    private String phone;
//    private Date dateSignUp;
//    private int level;
//
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getWorkerNo() {
//        return workerNo;
//    }
//
//    public void setWorkerNo(String workerNo) {
//        this.workerNo = workerNo;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//
//    public int getLevel() {
//        return level;
//    }
//
//    public void setLevel(int level) {
//        this.level = level;
//    }
//
//    public Date getDateSignUp() {
//        return dateSignUp;
//    }
//    public void setDateSignUp(Date dateSignUp) {
//        this.dateSignUp = dateSignUp;
//    }
}
