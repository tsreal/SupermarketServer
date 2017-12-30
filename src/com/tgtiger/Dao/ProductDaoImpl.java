package com.tgtiger.Dao;

import com.tgtiger.Bean.Product;

import java.sql.*;

public class ProductDaoImpl implements ProductDao {

    @Override
    public boolean addProduct(Product product) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO product(state, bar_code, name, price_lowest, " +
                "price_sale, discount, time_expire, time_produce, type_product, " +
                "provide_dealer, producer) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            //state
            pstmt.setInt(1,product.getState());
            //barCode
            System.out.println("barcode:" + genBarCodes());
            pstmt.setString(2,genBarCodes());
            //name
            pstmt.setString(3,product.getName());
            //priceLowest
            pstmt.setBigDecimal(4,product.getPriceLowest());
            //priceSale
            pstmt.setBigDecimal(5,product.getPriceSale());
            //discount
            pstmt.setBigDecimal(6,product.getDiscount());
            //timeExpire
            pstmt.setDate(7,product.getTimeExpire());
            //timeProduce
            pstmt.setDate(8,product.getTimeProduce());
            //type
            pstmt.setString(9,product.getType());
            //provider
            pstmt.setString(10,product.getProvicer());
            //producer
            pstmt.setString(11,product.getProducer());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeAll(null,pstmt,conn);
        }
    }

    @Override
    public String genBarCodes() {
        String s1="690";
        int num1=0;
        for(int i=0;i<4;i++) {
            num1=num1*10+(int)(( Math.random()+0.1)*10);
        }
        if(num1>9999) {
            num1=num1/10;
        }
        String snum1=String.valueOf(num1);
        int num2=0;
        for(int i=0;i<5;i++) {
            num2=num2*10+(int)( (Math.random()+0.1)*10);
        }
        if(num2>99999) {
            num2=num2/10;
        }
        String snum2=String.valueOf(num2);
        String num=s1+snum1+snum2;

        char[] array=num.toCharArray();
        int sum1=0;
        for(int i=0;i<array.length;i+=2) {
            sum1=sum1+(array[i]-'0');
        }
        int sum2=0;
        for(int i=1;i<array.length;i+=2) {
            sum2=sum2+(array[i]-'0');
        }
        String s3=String.valueOf(10-((sum1+sum2*3)%10));
        if(s3.equals("10")) {
            s3="0";
        }
        return num+s3;
    }
}
