package com.tgtiger.Bean;

public class User {
    //员工表
    //员工号，手机号作为注册账号，密码，注册时期
    private String workerNo;
    private String password;
    private String phone;
    private String dateSignUp;

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

    public String getDateSignUp() {
        return dateSignUp;
    }

    public void setDateSignUp(String dateSignUp) {
        this.dateSignUp = dateSignUp;
    }
}
