package com.tgtiger;

import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Bean.DepositoryList;
import com.tgtiger.Dao.ProductDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class importProduct extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        JSONObject json = new JSONObject();



        List<DepositoryList.ListsEntity> list = new ProductDaoImpl().getRemain();
        if (list != null) {
            json.put("lists", list);
            json.put("status", true);
        } else {
            json.put("status", false);
        }



        out.print(json.toString());
        out.flush();
        out.close();
    }
}
