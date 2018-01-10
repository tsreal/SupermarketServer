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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JSONObject json = new JSONObject();
        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        JSONObject json_rec = JSON.parseObject(receive);
        Product product_receive = json_rec.getObject("product", Product.class);
        Product produ;
        if (new ProductDaoImpl().nameExist(product_receive.getName())==0) {
            json.put("info", "商品已存在,请检查输入");
            System.out.println(json.getString("info"));
        }else {
            if ((produ = new ProductDaoImpl().addProduct(product_receive)).getYes()==1) {
                json.put("barCode", produ.getBarCode());
                json.put("info","添加成功");
            } else {
                json.put("info","商品添加失败,请检查服务端");
                System.out.println(json.getString("info"));
            }
        }


        out.print(json.toString());
        out.flush();
        out.close();
    }
}

