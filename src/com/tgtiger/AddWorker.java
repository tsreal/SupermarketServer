package com.tgtiger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Bean.Worker;
import com.tgtiger.Dao.WorkerDaoImpl;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddWorker extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JSONObject json = new JSONObject();



        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        Worker worker_rec = JSON.parseObject(receive, Worker.class);
        int j = new WorkerDaoImpl().usrExist(worker_rec.getPhone());
        if (j == 1) {
            json.put("info","该手机号已注册,添加失败");
            json.put("task", false);
            json.put("workerNo",new WorkerDaoImpl().getWorkerInfo(worker_rec.getPhone()).getWorkerNo());
            json.put("status",1);
        } else if (j == 0) {
            boolean i = new WorkerDaoImpl().addWorkers(worker_rec, worker_rec.getLevel());
            if (i) {
                json.put("task", true);
                json.put("info", "人员添加成功");
                json.put("workerNo", new WorkerDaoImpl().getWorkerInfo(worker_rec.getPhone()).getWorkerNo());
                json.put("status", 0);

            } else {
                json.put("task", false);
                json.put("status", 2);
                json.put("info", "addMember(*) error: 检查服务端");
            }
        } else {

            json.put("task", false);
            json.put("info", "usrExist() errord. 检索用户失败");
            json.put("status", 3);
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
