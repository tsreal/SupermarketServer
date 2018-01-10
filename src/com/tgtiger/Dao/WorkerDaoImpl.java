package com.tgtiger.Dao;

import com.tgtiger.Bean.Worker;
import com.tgtiger.Bean.WorkerList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class WorkerDaoImpl implements WorkerDao {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;        //用户注册前帐号验证

    @Override
    public int usrExist(String phone) {

        String sql = "SELECT count(*) FROM worker WHERE phone = ?;";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println("号码与库中匹配成功");
                return 1;
            } else {
                System.out.println("库中未找到相同号码");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("检索用户失败");
            return 2;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    //level默认为2，代表收银员
    @Override
    public boolean addWorkers(Worker worker, int level) {
        //Worker(Phone,Password)
        String sql = "INSERT INTO worker(worker_number, phone, password, level, date_sign_up, name) VALUES(?,?,?,?,?,?);";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, genWorkerNo());
            pstmt.setString(2, worker.getPhone());
            pstmt.setString(3, worker.getPassword());
            pstmt.setInt(4, level);
            java.util.Date d = new java.util.Date();
            pstmt.setDate(5, new java.sql.Date(d.getTime()));
            pstmt.setString(6,worker.getName());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public String getPassword(String phone) {
        String sql = "SELECT password FROM worker WHERE phone = ?";
        String password;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            password = rs.getString(1);
            return password ;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getPassword(String phone) error");
            return null;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public Worker getWorkerInfo(String phone) {
        String password = null;
        String sql = "SELECT phone,name,level,worker_number,date_sign_up FROM worker WHERE phone = ?";
        try {
            Worker worker = new Worker();
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            worker.setPhone(rs.getString(1));
            worker.setName(rs.getString(2));
            worker.setLevel(rs.getInt(3));
            worker.setWorkerNo(rs.getString(4));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            worker.setDateSignUp(format.format(rs.getDate(5)));
            return worker;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public int getLevel(String phone) {
        int level = -1;
        String sql = "SELECT level from worker WHERE phone = ?";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            level = rs.getInt(1);
            System.out.println("权限查询成功");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("权限查询失败");
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return level;
    }

    @Override
    public String genWorkerNo() {
        int i1=0;
        for(int i=0;i<5;i++) {
            i1=i1*10+(int)( (Math.random()+0.1)*10);
        }
        if(i1>99999) {
            i1=i1/10;
        }
        return String.valueOf(i1);
    }

    @Override
    public List<WorkerList.WorkerlistsEntity> getAllWorker() {
        String sql = "SELECT worker_number,name,phone,date_sign_up,level FROM worker;";

        List<WorkerList.WorkerlistsEntity> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            while (rs.next()) {
                WorkerList.WorkerlistsEntity worker = new WorkerList.WorkerlistsEntity();
                worker.setPhone(rs.getString(3));
                worker.setWorkerNo(rs.getString(1));
                worker.setName(rs.getString(2));
                worker.setDateSignUp(format.format(rs.getDate(4)));
                worker.setLevel(rs.getInt(5));
                list.add(worker);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return list;
    }
}
