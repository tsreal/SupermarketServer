package com.tgtiger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Bean.Statement;
import com.tgtiger.Bean.StatementList;
import com.tgtiger.Dao.StatementDaoImpl;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GetAnalysis extends HttpServlet{
/*
SELECT bar_code,name,sum(number_vip) AS number_vip,sum(number_normal)
AS number_normal,sum(number_total) as number_total,sum(income) as income
from statement WHERE date BETWEEN '2017-1-1' AND '2018-1-1' GROUP BY bar_code,name;
apple 12 8 20 12.1
pen   2  3  5 12.1
eraser 3  0  3 12.1
------------------
apple 4  1  5 12.2
orange 3 3  6 12.2
-------------------
juice  2 11 13 12.3
eraser 1 1  2  12.3
apple  6 2  8  12.3
-------------------
1.日期2.group by name 3. sum
name  sum(vip) sum(normal)
apple 22       11


 */

//status,date (1,:2018-01-03)(2,month(year,month---month+1,day1,2018-1):2018-01-01 2018-02-01) (3,year(2018):2018-01-01 2019-01-01)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        JSONObject json = new JSONObject();


        JSONObject json_rec = JSON.parseObject(IOUtils.toString(req.getInputStream(), "UTF-8"));
        int status = json_rec.getInteger("status");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(json_rec.getString("date"));
            List<StatementList.ListsEntity> list = new StatementDaoImpl().getAnalysis(status, date);
            json.put("lists", list);
            json.put("status", 0);




        } catch (ParseException e) {
            e.printStackTrace();
            json.put("task", false);
            json.put("status", 1);
            json.put("info", "error");
        }

        out.print(json.toString());
        out.flush();
        out.close();


    }
}
