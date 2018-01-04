package com.tgtiger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Bean.Product;
import com.tgtiger.Dao.ProductDaoImpl;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetProduct extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JSONObject json = new JSONObject();

        JSONObject json_rec = JSON.parseObject(IOUtils.toString(req.getInputStream(), "UTF-8"));
        String barcode_rec = json_rec.getString("barCode");
        int i = new ProductDaoImpl().productExist(barcode_rec);
        //检测商品是否存在，1存在，0商品不存在，请检查输入的条形码,2检索失败
        if (i == 1) {
            Product product = new Product();
            product = new ProductDaoImpl().getProduct(barcode_rec);
            json.put("product", product);
            json.put("info", "商品查找成功");
            json.put("task", true);
            json.put("status", 0);
        } else if (i == 0) {
            json.put("info", "商品不存在,请检查输入的条形码");
            json.put("task", false);
            json.put("status",1);
        } else {
            json.put("info","检索失败");
            json.put("task", false);
            json.put("status",2);
        }


        out.print(json.toString());
        out.flush();
        out.close();

    }
}
