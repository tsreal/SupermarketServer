package com.tgtiger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Dao.StatementDaoImpl;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Bill extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        //
        /*
        {
            "bill":
            [
            {"barCode":"2343242314324","number":"5"},
            {"barCode":"1432434332443","number":"1"}
            ],
            "isVip":true

        }
         */

        JSONObject json = new JSONObject();
        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        System.out.println("receive:"+receive);
        com.tgtiger.Bean.Bill bills_rec = JSON.parseObject(receive, com.tgtiger.Bean.Bill.class);
        int i = new StatementDaoImpl().getBill(bills_rec);
        if (i == 0) {

            json.put("task", true);
            json.put("status", 0);
            json.put("info", "结账success!");
        } else if (i == 1) {
            json.put("task", false);
            json.put("status", 1);
            json.put("info","error1:更新vip订单错误");
        } else if (i == 2) {
            json.put("task", false);
            json.put("status", 2);
            json.put("info","error2: 更新普通订单错误");
        } else if (i == 3) {
            json.put("task", false);
            json.put("status", 3);
            json.put("info","error3: 插入订单错误");
        } else if (i == 4) {
            json.put("task", false);
            json.put("status", 4);
            json.put("info","error4: 更新仓库错误");

        } else if (i == 6) {
            json.put("task", false);
            json.put("status", 6);
            json.put("info", "会员更新账单失败！");
        } else {
            json.put("task", false);
            json.put("status", 5);
            json.put("info", "请检查代码");
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
