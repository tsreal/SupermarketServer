package com.tgtiger.Bean;

import java.util.List;

/*
{
    "bill":
    [
    {"barCode":"2343242314324","number":5},
    {"barCode":"1432434332443","number":1}
    ],
    "isVip":true

}
*/
public class Bill {
    /**
     * bills : [{"number":5,"barCode":"2343242314324"},{"number":1,"barCode":"1432434332443"}]
     * vip : true
     */
    private List<BillsEntity> bills;
    private boolean vip;
    /**
     * memberNo : 13234134
     */
    private String memberNo;


    public void setBills(List<BillsEntity> bills) {
        this.bills = bills;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public List<BillsEntity> getBills() {
        return bills;
    }

    public boolean isVip() {
        return vip;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public class BillsEntity {
        /**
         * number : 5
         * barCode : 2343242314324
         */
        private int number;
        private String barCode;

        public void setNumber(int number) {
            this.number = number;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public int getNumber() {
            return number;
        }

        public String getBarCode() {
            return barCode;
        }
    }




}
