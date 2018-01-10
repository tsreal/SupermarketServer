package com.tgtiger.Bean;

import java.util.List;

public class StatementList {
    /**
     * lists : [{"income":28.34,"normalNo":23,"name":"hehe","totalNo":4235,"priceSale":13.3,"vipNo":35},{"income":28.34,"normalNo":23,"name":"hehe","totalNo":4235,"priceSale":13.3,"vipNo":35}]
     * status : true
     */
    private List<ListsEntity> lists;
    private boolean status;

    public void setLists(List<ListsEntity> lists) {
        this.lists = lists;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ListsEntity> getLists() {
        return lists;
    }

    public boolean isStatus() {
        return status;
    }

    public static class ListsEntity {
        /**
         * income : 28.34
         * normalNo : 23
         * name : hehe
         * totalNo : 4235
         * priceSale : 13.3
         * vipNo : 35
         */
        private double income;
        private int normalNo;
        private String name;
        private int totalNo;
        private double priceSale;
        private int vipNo;

        public void setIncome(double income) {
            this.income = income;
        }

        public void setNormalNo(int normalNo) {
            this.normalNo = normalNo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTotalNo(int totalNo) {
            this.totalNo = totalNo;
        }

        public void setPriceSale(double priceSale) {
            this.priceSale = priceSale;
        }

        public void setVipNo(int vipNo) {
            this.vipNo = vipNo;
        }

        public double getIncome() {
            return income;
        }

        public int getNormalNo() {
            return normalNo;
        }

        public String getName() {
            return name;
        }

        public int getTotalNo() {
            return totalNo;
        }

        public double getPriceSale() {
            return priceSale;
        }

        public int getVipNo() {
            return vipNo;
        }
    }
    /*
    {
        "lists":
        [{"name":"hehe","priceSale":13.3,"normalNo":23,"totalNo":4235,"vipNo":35,"income":28.34},
        {"name":"hehe","priceSale":13.3,"normalNo":23,"totalNo":4235,"vipNo":35,"income":28.34}],
        "status":true

    }
     */


}
