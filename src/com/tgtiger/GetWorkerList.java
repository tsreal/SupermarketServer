package com.tgtiger;

import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Bean.WorkerList;
import com.tgtiger.Dao.WorkerDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetWorkerList extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        JSONObject json = new JSONObject();



        List<WorkerList.WorkerlistsEntity> list = new WorkerDaoImpl().getAllWorker();
        json.put("workerlists", list);
        json.put("task", true);
        out.print(json.toString());
        out.flush();
        out.close();

    }
}
