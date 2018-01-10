package com.tgtiger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Bean.Worker;
import com.tgtiger.Dao.WorkerDao;
import com.tgtiger.Dao.WorkerDaoImpl;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SignUp extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String receive = IOUtils.toString(req.getInputStream(),"UTF-8");
        Worker worker_rec = JSON.parseObject(receive, Worker.class);
        WorkerDao workerDao = new WorkerDaoImpl();
        JSONObject json = new JSONObject();
        System.out.println("addWorker...");
        if (workerDao.usrExist(worker_rec.getPhone()) == 1) {
            json.put("info", "该手机号已注册.请重新输入新手机号.");
            json.put("task", false);
            json.put("status", 1);

        }else{
            if (worker_rec.getLevel() == 2) {
                System.out.println("准备添加普通用户");
                if (workerDao.addWorkers(worker_rec, 2)) {

                    System.out.println("用户添加成功!");
                    json.put("info", "用户添加成功!");
                    json.put("task", true);
                } else {
                    System.out.println("用户添加失败!");
                    json.put("info", "用户添加失败,检查服务器");
                    json.put("task", false);
                    json.put("status", 2);
                }
            } else if (worker_rec.getLevel() == 1) {
                System.out.println("准备添加管理员");
                if (workerDao.addWorkers(worker_rec,1)) {
                    System.out.println("管理员添加成功!");
                    json.put("info", "管理员添加成功!");
                    json.put("task", true);
                } else {
                    System.out.println("管理员添加失败!");
                    json.put("info", "管理员添加失败,请检查服务器");
                    json.put("status", 2);
                    json.put("task", false);
                }
            } else {
                System.out.println("Error,提交参数错误.");
                json.put("info", "提交参数错误,请检查提交level是否正确");
                json.put("task", false);
                json.put("status", 3);
            }
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
