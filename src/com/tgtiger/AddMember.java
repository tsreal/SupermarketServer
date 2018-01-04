package com.tgtiger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tgtiger.Bean.Member;
import com.tgtiger.Dao.MemberDaoImpl;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddMember extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JSONObject json = new JSONObject();



        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        Member member_rec = JSON.parseObject(receive, Member.class);
        int j = new MemberDaoImpl().memExist(member_rec.getPhone());
        if (j == 1) {
            json.put("info","该手机号已注册会员号,添加失败");
            json.put("task", false);
            json.put("memberNo",new MemberDaoImpl().getMemberInfo(member_rec.getPhone()).getMemberNo());
            json.put("status",1);
        } else if (j == 0) {
            boolean i = new MemberDaoImpl().addMember(member_rec);
            if (i) {
                json.put("task", true);
                json.put("info", "会员添加成功");
                json.put("memberNo", new MemberDaoImpl().getMemberInfo(member_rec.getPhone()).getMemberNo());
                json.put("status", 0);

            } else {
                json.put("task", false);
                json.put("status", 2);
                json.put("info", "addMember(*) error: 检查服务端");
            }
        } else {
            json.put("task", false);
            json.put("info", "memExist() error, check codes.");
            json.put("status", 2);
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
