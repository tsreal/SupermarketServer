package com.tgtiger.Dao;

import com.tgtiger.Bean.Worker;
import com.tgtiger.Bean.WorkerList;

import java.util.List;

public interface WorkerDao {
    int usrExist(String phone);

    boolean addWorkers(Worker worker, int level);

//    boolean addAdmins(Worker worker);

//    boolean updatePassword(String phone, String password);

//    ArrayList<Worker> getAllUsers(String expstationnum);

//    ArrayList<Worker> getAllAdmins(String expstationnum);

    String getPassword(String phone);

    Worker getWorkerInfo(String phone);

    int getLevel(String phone);

    String genWorkerNo();

    List<WorkerList.WorkerlistsEntity> getAllWorker();
}
