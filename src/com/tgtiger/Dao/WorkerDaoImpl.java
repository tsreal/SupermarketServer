package com.tgtiger.Dao;

import com.tgtiger.Bean.Worker;

import java.sql.*;

public class WorkerDaoImpl implements WorkerDao {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;        //用户注册前帐号验证

    @Override
    public boolean usrExist(String phone) {

        String sql = "SELECT count(*) FROM worker WHERE phone = ?;";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println("号码与库中匹配成功");
                return true;
            } else {
                System.out.println("库中未找到相同号码");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("检索用户失败");
            return false;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public boolean addUsers(Worker worker) {
        //Worker(Phone,Password)
        String sql = "INSERT INTO worker(workerNo, phone, password, level, date_sign_up) VALUES(?,?,?,2,?);";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, genWorkerNo() );
            pstmt.setString(2, worker.getPhone());
            pstmt.setString(3, worker.getPassword());
            java.util.Date d = new java.util.Date();
            pstmt.setDate(4,new java.sql.Date(d.getTime()));
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addAdmins(Worker worker) {
        String sql = "INSERT INTO worker(workerNo, phone, password, level, date_sign_up) VALUES(?,?,?,1,?);";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, genWorkerNo());
            pstmt.setString(2, worker.getPhone());
            pstmt.setString(3, worker.getPassword());
            java.util.Date d = new java.util.Date();
            pstmt.setDate(4,new java.sql.Date(d.getTime()));
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getPasswd(String phone) {
        //返回-1说明未查询到
        String sql = "SELECT password FROM worker WHERE phone = ?";
        String password = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            password = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return password;
    }

    @Override
    public Worker getWorkerInfo(String phone) {
        String password = null;
        String sql = "SELECT phone,name,level,number_worker,date_sign_up FROM worker WHERE phone = ?";
        Worker worker = new Worker();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            worker.setPhone(rs.getString(1));
            worker.setName(rs.getString(2));
            worker.setLevel(rs.getInt(3));
            worker.setWorkerNo(rs.getString(4));
            worker.setDateSignUp(rs.getDate(5));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return worker;
    }

    @Override
    public int getLevel(String phone) {
        int level = -1;
        try {
            String sql = "SELECT level from worker WHERE phone = ?";
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
}
