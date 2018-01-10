package com.tgtiger.Dao;

import com.tgtiger.Bean.Bill;
import com.tgtiger.Bean.Product;
import com.tgtiger.Bean.Statement;
import com.tgtiger.Bean.StatementList;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatementDaoImpl implements StatementDao {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;


    /*


    {
    "bills":
    [
    {"barCode":"2343242314324","number":5},
    {"barCode":"1432434332443","number":1}
    ],
    "vip":true

    }
     */

    @Override
    public boolean prodStatExist(String barcode) {
        String sql = "SELECT count(*) FROM statement WHERE bar_code = ? AND date = ?";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, barcode);
            Date date = new Date();
            pstmt.setDate(2,new java.sql.Date(date.getTime()));
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println("该商品当日购买记录已创建");
                return true;
            } else {
                System.out.println("未找到该商品销售当天记录");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("检索用户失败");
            return false;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public int getBill(Bill rec) {


        String sql_update_depository = "UPDATE depository SET number_product=depository.number_product-? WHERE bar_code=?";
        String sql_insert_statement = "INSERT INTO statement(bar_code,date,price_sale,price_discount,number_vip,number_normal,name) VALUES (?,?,?,?,?,?,?)";
        String sql_update_statement_vip = "UPDATE statement SET number_vip=? WHERE bar_code=? AND date = ?";
        String sql_update_statement_normal = "UPDATE statement SET number_normal= ? WHERE  bar_code= ? AND date= ?";



//        conn = DBUtil.getConnection();
        String barcode;
        int number;
        String memberNo = rec.getMemberNo();
        System.out.println(rec.getBills().toString()+" size:"+rec.getBills().size());
        int flag=0;
        double total=0;
        for (int i = 0; i < rec.getBills().size(); i++) {
            barcode = rec.getBills().get(i).getBarCode();
            number = rec.getBills().get(i).getNumber();

            Product product = new ProductDaoImpl().getProduct(barcode);
            total += number*Double.valueOf(product.getPriceSale());
            Date date = new Date();
            if (prodStatExist(barcode)) {
                //if里面closeAll(rs,pstmt,conn)，故需要重新链接
                conn = DBUtil.getConnection();
                if (rec.isVip()) {
                    flag=1;
                    try {
                        int old = getNumber(barcode, 1, date);
                        conn =DBUtil.getConnection();
                        pstmt = conn.prepareStatement(sql_update_statement_vip);
                        pstmt.setInt(1, number + old);
                        pstmt.setString(2, barcode);
                        pstmt.setDate(3, new java.sql.Date(date.getTime()));
                        pstmt.execute();
                        System.out.println("某vip商品更新statement成功");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("error1: update statement vip error");
                        return 1;
                        //error 1:update statement vip error
                    }

                } else {
                    try {
                        int old = getNumber(barcode, 0, date);
                        conn =DBUtil.getConnection();
                        pstmt = conn.prepareStatement(sql_update_statement_normal);
                        pstmt.setInt(1, number + old);
                        pstmt.setString(2, barcode);
                        pstmt.setDate(3, new java.sql.Date(date.getTime()));
                        pstmt.execute();
                        System.out.println("某普通商品更新statement成功");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("error2: update statement normal error");
                        return 2;
                    }


                }
            } else {
                conn = DBUtil.getConnection();
                try {
                    System.out.println("test");
                    pstmt = conn.prepareStatement(sql_insert_statement);
                    pstmt.setString(1, barcode);
                    pstmt.setDate(2, new java.sql.Date(date.getTime()));
                    pstmt.setBigDecimal(3, new BigDecimal(product.getPriceSale()));
                    pstmt.setBigDecimal(4, new BigDecimal(Double.valueOf(product.getDiscount())*Double.valueOf(product.getPriceSale())));
                    pstmt.setString(7,product.getName());
                    if (rec.isVip()) {
                        flag=1;
                        pstmt.setInt(5, rec.getBills().get(i).getNumber());
                        pstmt.setInt(6, 0);
                        pstmt.execute();
                        System.out.println("某vip商品插入statement成功");
                    } else {
                        pstmt.setInt(5, 0);
                        pstmt.setInt(6, rec.getBills().get(i).getNumber());
                        pstmt.execute();
                        System.out.println("某普通商品插入成功");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("error3: insert statement error");
                    return 3;
                }
            }
            try {
                pstmt = conn.prepareStatement(sql_update_depository);
                pstmt.setInt(1, number);
                pstmt.setString(2, barcode);
                pstmt.execute();
                System.out.println("某商品库存更新成功");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error4: update depository error");
                return 4;
            }

            if (flag == 1) {
                if (new MemberDaoImpl().updateMemberBill(total, memberNo)) {
                    System.out.println("会员消费记录已存储");
                } else {
                    return 6;
                }
            }



            if (updateIncome(barcode, date)) {
                System.out.println("商品销售表收入和数量更新成功。");
            } else {
                return 5;
            }
        }
        DBUtil.closeAll(rs, pstmt, conn);
        return 0;
    }

    @Override
    public int getNumber(String barcode, int isvip, Date date) {
        String sql = "SELECT number_normal,number_vip FROM statement WHERE bar_code=? AND date=?";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, barcode);
            pstmt.setDate(2, new java.sql.Date(date.getTime()));
            rs = pstmt.executeQuery();
            rs.next();
            if (isvip == 1) {
                return rs.getInt(2);
            } else {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getNumber(*,*,*) error");
            return 0;
        }finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public boolean updateIncome(String barcode, Date date) {
        String sql = "UPDATE statement SET income=price_discount*number_vip+number_normal*price_sale,number_total=number_normal+number_vip WHERE bar_code=? AND date=?";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, barcode);
            pstmt.setDate(2,new java.sql.Date(date.getTime()));
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
    public List<StatementList.ListsEntity> getAnalysis(int status, Date date) {
        //status 1 day 2018-01-01
        //status 2 month 2017-06-01
        //status 3 year 2017-01-01  2018-01-01
        String sql = "SELECT price_sale,name,sum(number_vip) AS number_vip," +
                "sum(number_normal) AS number_normal,sum(number_total) AS number_total," +
                "sum(income) AS income FROM statement WHERE date BETWEEN ? AND ? GROUP BY bar_code,name,price_sale;";
        conn = DBUtil.getConnection();
        try {
            if (status == 2) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setDate(1,new java.sql.Date(date.getTime()));
                pstmt.setDate(2,new java.sql.Date(date.getTime()));

            } else if (status == 1) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setDate(1,new java.sql.Date(date.getTime()));
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.MONTH, 1);
                pstmt.setDate(2,new java.sql.Date(cal.getTime().getTime()));
            }else {
                pstmt = conn.prepareStatement(sql);
                pstmt.setDate(1,new java.sql.Date(date.getTime()));
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.YEAR, 1);
                pstmt.setDate(2, new java.sql.Date(cal.getTime().getTime()));
            }
            rs = pstmt.executeQuery();
            List<StatementList.ListsEntity> list = new ArrayList<>();
            while (rs.next()) {
//                Statement statement = new Statement();
                StatementList.ListsEntity statement = new StatementList.ListsEntity();
                statement.setPriceSale(rs.getDouble(1));
                statement.setName(rs.getString(2));
                statement.setVipNo(rs.getInt(3));
                statement.setNormalNo(rs.getInt(4));
                statement.setTotalNo(rs.getInt(5));
                statement.setIncome(rs.getDouble(6));
                list.add(statement);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error, getAnalysis(int status, Date date)");
            return null;
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }


    }
}
