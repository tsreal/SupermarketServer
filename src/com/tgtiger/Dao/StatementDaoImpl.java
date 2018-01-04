package com.tgtiger.Dao;

import com.tgtiger.Bean.Bill;
import com.tgtiger.Bean.Product;
import com.tgtiger.Bean.Statement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
    "bill":
    [
    {"barCode":"2343242314324","number":5},
    {"barCode":"1432434332443","number":1}
    ],
    "isVip":true
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


        String sql_update_depository = "UPDATE depository SET number_import=number_import-? WHERE bar_code=?";
        String sql_insert_statement = "INSERT INTO statement(bar_code,date,price_sale,price_discount,number_vip,number_normal) VALUES (?,?,?,?,?,?)";
        String sql_update_statement_vip = "UPDATE statement SET number_vip=? WHERE bar_code=? AND date = ?";
        String sql_update_statement_normal = "UPDATE statement SET number_normal=? WHERE  bar_code=? AND date=?";



        conn = DBUtil.getConnection();
        String barcode;
        int number;
        for (int i = 0; i < rec.getBills().size(); i++) {
            barcode = rec.getBills().get(i).getBarCode();
            number = rec.getBills().get(i).getNumber();
            Product product = new ProductDaoImpl().getProduct(barcode);
            Date date = new Date();
            if (prodStatExist(barcode)) {
                if (rec.isVip()) {
                    try {
                        pstmt = conn.prepareStatement(sql_update_statement_vip);
                        pstmt.setInt(1, number + getNumber(barcode, 1, date));
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
                        pstmt = conn.prepareStatement(sql_update_statement_normal);
                        pstmt.setInt(1, number + getNumber(barcode, 0, date));
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
                try {
                    pstmt = conn.prepareStatement(sql_insert_statement);
                    pstmt.setString(1, barcode);
                    pstmt.setDate(2, new java.sql.Date(date.getTime()));
                    pstmt.setBigDecimal(3, new BigDecimal(product.getPriceSale()));
                    pstmt.setBigDecimal(4, new BigDecimal(product.getDiscount()));
                    if (rec.isVip()) {
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


            if (updateIncome(barcode, date)) {
                System.out.println("商品销售表收入和数量更新成功。statement.income updates successfully.");
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

        } finally {
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
    public List<Statement> getAnalysis(int status, Date date) {
        //status 1 day 2018-01-01
        //status 2 month 2017-06-01
        //status 3 year 2017-01-01  2018-01-01
        String sql = "SELECT bar_code,name,sum(number_vip) AS number_vip," +
                "sum(number_normal) AS number_normal,sum(number_total) AS number_total," +
                "sum(income) AS income FROM statement WHERE date BETWEEN ? AND ? GROUP BY bar_code,name;";
        conn = DBUtil.getConnection();
        try {
            if (status == 1) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setDate(1,new java.sql.Date(date.getTime()));
                pstmt.setDate(2,new java.sql.Date(date.getTime()));

            } else if (status == 2) {
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
            List<Statement> list = new ArrayList<>();
            while (rs.next()) {

                Statement statement = new Statement();
                statement.setBarCode(rs.getString(1));
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
