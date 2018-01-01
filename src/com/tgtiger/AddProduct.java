package com.tgtiger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Bean.Product;
import com.tgtiger.Dao.ProductDao;
import com.tgtiger.Dao.ProductDaoImpl;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddProduct extends HttpServlet {

    //添加商品
    //

    /*
{
//"barCode":"100861008611",
"name":"小当家方便面",
"priceLowest":"0.30",
"priceSale":"0.50",
"discount":"0.95",
"type":"食品",
"timeExpire":"2018-3-6",
"timeProduce":"2017-3-6",
"provider":"太原食品批发厂",
"producer":"天津顶新集团",
"state": 0
}
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        Product product_receive = JSON.parseObject(receive, Product.class);
        JSONObject json = new JSONObject();
        Product produ;
        if ((produ = (new ProductDaoImpl()).addProduct(product_receive)).getYes() == 1) {
            json.put("task", true);
            json.put("info", "商品添加成功");
            json.put("barCode", produ.getBarCode());
        } else {
            json.put("task", false);
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}

