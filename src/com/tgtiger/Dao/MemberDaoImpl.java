package com.tgtiger.Dao;

import com.tgtiger.Bean.Member;
import com.tgtiger.Bean.MemberList;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public int checkMemNo(String memberNo) {
        String sql = "SELECT count(*) FROM member WHERE number_member = ?;";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberNo);
            rs = pstmt.executeQuery();
                    rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println("会员号与库中匹配成功");
                return 1;
            } else {
                System.out.println("会员不存在");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("检索失败");
            return 2;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public int memExist(String phone) {
        String sql = "SELECT count(*) FROM member WHERE phone = ?;";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println("库中已有该手机号");
                return 1;
            } else {
                System.out.println("库中未找到相同号码,可添加");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("检索库失败");
            return 2;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public int memExpire(String memberNo) {
        String sql = "SELECT expire FROM member WHERE number_member = ?;";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberNo);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getBoolean(1)) {
                System.out.println("会员已过期");
                return 1;
            } else {
                System.out.println("会员未过期，可享受折扣");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("检索库失败");
            return 2;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public boolean addMember(Member member) {
        String sql = "INSERT INTO member(number_member, name, phone, date_sign_up,expire) VALUES(?,?,?,?,FALSE );";

        String memberNo = genMemberNo();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberNo);
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getPhone());
            java.util.Date d = new java.util.Date();
            pstmt.setDate(4, new java.sql.Date(d.getTime()));
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
    }

    @Override
    public Member getMemberInfo(String phone) {
        String sql = "SELECT phone,number_member,name,date_sign_up,expire,bill FROM member WHERE phone = ?";
        try {
            Member member = new Member();
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            member.setPhone(rs.getString(1));
            member.setMemberNo(rs.getString(2));
            member.setName(rs.getString(3));
            member.setExpire(rs.getBoolean(5));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            member.setDateSignUp(format.format(rs.getDate(4)));
            member.setBill(String.valueOf(rs.getBigDecimal(6)));
            return member;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public boolean updateMemberBill(Double total,String memberNo) {
        String sql = "UPDATE member SET bill=bill+? WHERE number_member=?";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setBigDecimal(1, new BigDecimal(total));
            pstmt.setString(2,memberNo);
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
    }

    @Override
    public List<MemberList.MemlistsEntity> getMemberList() {
        String sql = "SELECT number_member,name,phone,date_sign_up,expire,bill FROM member;";

        List<MemberList.MemlistsEntity> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            while (rs.next()) {
                MemberList.MemlistsEntity member = new MemberList.MemlistsEntity();
                member.setPhone(rs.getString(3));
                member.setMemberNo(rs.getString(1));
                member.setName(rs.getString(2));
                member.setDateSignUp(format.format(rs.getDate(4)));
                member.setExpire(rs.getBoolean(5));
                member.setBill(String.valueOf(rs.getBigDecimal(6)));
                list.add(member);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return list;
    }

    @Override
    public String genMemberNo() {
        int i = 0;
        String sql1="select * from auto_ins;";
        String sql2="UPDATE auto_ins SET id := id+1 LIMIT 1;";
        String expnum = "";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql1);
            rs = pstmt.executeQuery();
            rs.next();
            i = rs.getInt("id");
            String autoIns = "00000" + i;
            String expnum2 = autoIns.substring(autoIns.length() - 6);
            String expnum1 = String.valueOf(System.currentTimeMillis());
            expnum1 = expnum1.substring(7, expnum1.length());
            expnum = expnum1 + expnum2;
            pstmt = conn.prepareStatement(sql2);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return expnum;
    }
}
