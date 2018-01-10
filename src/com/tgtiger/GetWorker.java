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

public class GetWorker extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JSONObject json = new JSONObject();
        JSONObject json_rec = JSON.parseObject(IOUtils.toString(req.getInputStream(), "UTF-8"));
        String phone_rec = json_rec.getString("phone");
        int i = new WorkerDaoImpl().usrExist(phone_rec);
        //检测会员是否存在，1存在，0不存在，请检查输入,2检索失败
        if (i == 1) {
            Worker worker_send = new Worker();
            worker_send = new WorkerDaoImpl().getWorkerInfo(phone_rec);
            json.put("worker", worker_send);
            json.put("info", "Success!");
            json.put("task", true);
            json.put("status", 0);
        } else if (i == 0) {
            json.put("info", "The product doesn't exist. Please check input.");
            json.put("task", false);
            json.put("status",1);
        } else {
            json.put("info","The check procedure failed.");
            json.put("task", false);
            json.put("status",2);
        }


        out.print(json.toString());
        out.flush();
        out.close();
    }
}
