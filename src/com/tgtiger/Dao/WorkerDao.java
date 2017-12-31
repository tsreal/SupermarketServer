package com.tgtiger.Dao;

import com.tgtiger.Bean.Worker;

public interface WorkerDao {
    boolean usrExist(String phone);

    boolean addWorkers(Worker worker, int level);

//    boolean addAdmins(Worker worker);

//    boolean updatePassword(String phone, String password);

//    ArrayList<Worker> getAllUsers(String expstationnum);

//    ArrayList<Worker> getAllAdmins(String expstationnum);

    String getPasswd(String phone);

    Worker getWorkerInfo(String phone);

    int getLevel(String phone);

    String genWorkerNo();
}
