package com.tgtiger.Dao;

import com.tgtiger.Bean.Bill;
import com.tgtiger.Bean.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
    public boolean getBill(Bill rec) {


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
                        return false;
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
                        return false;
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
                    return false;
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
                return false;
            }
        }
        DBUtil.closeAll(rs, pstmt, conn);
        return true;

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
}
