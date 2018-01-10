package com.tgtiger.Bean;

import java.util.List;

public class DepositoryList {


    /**
     * lists : [{"importNo":13,"name":"hehe","importTime":"2017-1-1","productNo":12},{"importNo":13,"name":"hehe","importTime":"2017-1-1","productNo":12}]
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
         * importNo : 13
         * name : hehe
         * importTime : 2017-1-1
         * productNo : 12
         */
        private int importNo;
        private String name;
        private String importTime;
        private int productNo;

        public void setImportNo(int importNo) {
            this.importNo = importNo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setImportTime(String importTime) {
            this.importTime = importTime;
        }

        public void setProductNo(int productNo) {
            this.productNo = productNo;
        }

        public int getImportNo() {
            return importNo;
        }

        public String getName() {
            return name;
        }

        public String getImportTime() {
            return importTime;
        }

        public int getProductNo() {
            return productNo;
        }
    }
}
/*
 {
        "lists":
        [{"name":"hehe","importNo":13,"importTime":"2017-1-1","productNo":12},
        {"name":"hehe","importNo":13,"importTime":"2017-1-1","productNo":12}],
        "status":true

    }
 */
