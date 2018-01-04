package com.tgtiger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Dao.MemberDaoImpl;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CheckVip extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        //{"memberNo":"3354234234324"}
        JSONObject json_rec = JSON.parseObject(IOUtils.toString(req.getInputStream(), "UTF-8"));
        JSONObject json = new JSONObject();


        String memberNo_rec = json_rec.getString("memberNo");
        //会员存在检测,1存在，0不存在，2检索失败
        int i = new MemberDaoImpl().checkMemNo(memberNo_rec);
        if (i == 1) {
            int j = new MemberDaoImpl().memExpire(memberNo_rec);
            //会员过期检测,1过期，0未过期，2检索失败
            if (j == 1) {
                json.put("info", "会员号与库中匹配成功,但已过期");
                json.put("task", false);
                json.put("status", 1);
            } else {
                json.put("info", "会员存在，可折扣");
                json.put("task", true);
                json.put("status", 0);
            }
        } else if (i == 0) {
            json.put("info", "会员不存在");
            json.put("task", false);
            json.put("status", 2);
        } else {
            json.put("info", "检索失败,检测codes");
            json.put("task", false);
            json.put("status", 3);
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }

}
