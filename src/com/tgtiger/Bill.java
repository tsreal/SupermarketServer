package com.tgtiger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        Bill bills_rec = JSON.parseObject(receive, Bill.class);





        out.print("a");
        out.flush();
        out.close();

    }
}
