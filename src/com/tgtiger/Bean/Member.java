package com.tgtiger.Bean;

public class Member {
    //会员类，姓名手机非必要，会员号注册日期必须有
    private String name;
    private String memberNo;
    private String dateSignUp;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getDateSignUp() {
        return dateSignUp;
    }

    public void setDateSignUp(String dateSignUp) {
        this.dateSignUp = dateSignUp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
