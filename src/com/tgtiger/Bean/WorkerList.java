package com.tgtiger.Bean;

import java.util.List;

public class WorkerList {

    /**
     * task : true
     * workerlists : [{"dateSignUp":"2017-08-01","workerNo":"4234234","phone":"18542341","level":2,"name":"2343242314324"},{"dateSignUp":"2017-08-01","workerNo":"4234234","phone":"18542341","level":2,"name":"2343242314324"}]
     */
    private boolean task;
    private List<WorkerlistsEntity> workerlists;

    public void setTask(boolean task) {
        this.task = task;
    }

    public void setWorkerlists(List<WorkerlistsEntity> workerlists) {
        this.workerlists = workerlists;
    }

    public boolean isTask() {
        return task;
    }

    public List<WorkerlistsEntity> getWorkerlists() {
        return workerlists;
    }

    public static class WorkerlistsEntity {
        /**
         * dateSignUp : 2017-08-01
         * workerNo : 4234234
         * phone : 18542341
         * level : 2
         * name : 2343242314324
         */
        private String dateSignUp;
        private String workerNo;
        private String phone;
        private int level;
        private String name;

        public void setDateSignUp(String dateSignUp) {
            this.dateSignUp = dateSignUp;
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
    }
}
