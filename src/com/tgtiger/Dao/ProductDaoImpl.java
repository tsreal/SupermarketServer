package com.tgtiger.Dao;

import com.tgtiger.Bean.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    @Override
    public boolean addProduct(Product product) {

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
            pstmt.setDate(7,new java.sql.Date(product.getTimeExpire().getTime()));
            //timeProduce
            pstmt.setDate(8,new java.sql.Date((product.getTimeProduce().getTime())));
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
            DBUtil.closeAll(rs,pstmt,conn);
        }
    }


    @Override
    public Product getProduct(String barcode) {
        String sql = "SELECT state,name,price_sale,discount FROM product WHERE bar_code = ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, barcode);
            rs = pstmt.executeQuery();
            rs.next();
            Product product = new Product();
            product.setState(rs.getInt(rs.getInt(1)));
            product.setBarCode(barcode);
            product.setName(rs.getString(2));
            product.setPriceSale(rs.getBigDecimal(3));
            product.setDiscount(rs.getBigDecimal(4));
//        product.setPriceLowest();
//        product.setTimeExpire();
//        product.setTimeProduce();
//        product.setType();
//        product.setProvicer();
//        product.setProducer();
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ProductDaoImpl.getProduct(String barcode)查询失败");
            return null;
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }

    }

    @Override
    public List<Product> getFullProduct() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setState(rs.getInt(rs.getInt(2)));
                product.setBarCode(rs.getString(3));
                product.setName(rs.getString(4));
                product.setPriceLowest(rs.getBigDecimal(5));
                product.setPriceSale(rs.getBigDecimal(6));
                product.setDiscount(rs.getBigDecimal(7));
                product.setTimeExpire(rs.getDate(8));
                product.setTimeProduce(rs.getDate(9));
                product.setType(rs.getString(10));
                product.setProvicer(rs.getString(11));
                product.setProducer(rs.getString(12));
                list.add(product);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getFullProduct() error!");
            return null;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
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
