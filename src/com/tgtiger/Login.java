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

public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //字符流
        PrintWriter out = resp.getWriter();
        JSONObject json = new JSONObject();
        String receive = IOUtils.toString(req.getInputStream(),"UTF-8");
        Worker worker_rec = JSON.parseObject(receive, Worker.class);
        WorkerDao dao = new WorkerDaoImpl();

        if (dao.usrExist(worker_rec.getPhone())) {
            if (dao.getPassword(worker_rec.getPhone()).equals(worker_rec.getPassword())) {
                System.out.println("密码匹配成功");
                Worker worker = dao.getWorkerInfo(worker_rec.getPhone());
                json.put("name", worker.getName());
                json.put("level", worker.getLevel());
                json.put("workerNo", worker.getWorkerNo());
                json.put("info", "验证登录成功");
                json.put("task", true);
            } else {
                json.put("info", "请检查你的密码");
                json.put("task", false);
            }
        } else {
            json.put("info","帐号不存在,请先注册");
            json.put("task", false);
        }



        out.print(json.toString());
        out.flush();
        out.close();



    }
}
