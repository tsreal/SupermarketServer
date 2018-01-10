package com.tgtiger.Bean;

import java.util.List;

public class MemberList {
    /**
     * task : true
     * memlists : [{"dateSignUp":"2017-08-01","memberNo":"4234234","phone":"18542341","expire":true,"name":"2343242314324","bill":"28.34"},{"dateSignUp":"2017-08-01","memberNo":"4234234","phone":"18542341","expire":true,"name":"2343242314324","bill":"28.34"}]
     */
    private boolean task;
    private List<MemlistsEntity> memlists;

    public void setTask(boolean task) {
        this.task = task;
    }

    public void setMemlists(List<MemlistsEntity> memlists) {
        this.memlists = memlists;
    }

    public boolean isTask() {
        return task;
    }

    public List<MemlistsEntity> getMemlists() {
        return memlists;
    }

    public static class MemlistsEntity {
        /**
         * dateSignUp : 2017-08-01
         * memberNo : 4234234
         * phone : 18542341
         * expire : true
         * name : 2343242314324
         * bill : 28.34
         */
        private String dateSignUp;
        private String memberNo;
        private String phone;
        private boolean expire;
        private String name;
        private String bill;

        public void setDateSignUp(String dateSignUp) {
            this.dateSignUp = dateSignUp;
        }

        public void setMemberNo(String memberNo) {
            this.memberNo = memberNo;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setExpire(boolean expire) {
            this.expire = expire;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBill(String bill) {
            this.bill = bill;
        }

        public String getDateSignUp() {
            return dateSignUp;
        }

        public String getMemberNo() {
            return memberNo;
        }

        public String getPhone() {
            return phone;
        }

        public boolean isExpire() {
            return expire;
        }

        public String getName() {
            return name;
        }

        public String getBill() {
            return bill;
        }
    }
    /*
    "memlists":
    [
    {"name":"2343242314324","phone":"18542341","memberNo":"4234234","dateSignUp":"2017-08-01","expire":true,"bill":"28.34"},
    {"name":"2343242314324","phone":"18542341","memberNo":"4234234","dateSignUp":"2017-08-01","expire":true,"bill":"28.34"}
    ],
    "task":true
     */

}
