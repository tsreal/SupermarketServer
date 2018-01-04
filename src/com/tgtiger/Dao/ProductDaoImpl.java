package com.tgtiger.Dao;

import com.tgtiger.Bean.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    @Override
    public Product addProduct(Product product) {

        product.setYes(0);

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
            String barcode = genBarCodes();
            product.setBarCode(barcode);
            pstmt.setString(2,barcode);
            //name
            pstmt.setString(3,product.getName());
            //priceLowest
            pstmt.setBigDecimal(4,new BigDecimal(product.getPriceLowest()));
            //priceSale
            pstmt.setBigDecimal(5,new BigDecimal(product.getPriceSale()));
            //discount
            pstmt.setBigDecimal(6,new BigDecimal(product.getDiscount()));
            //timeExpire
            //格式转换
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
            pstmt.setDate(7,new java.sql.Date(format.parse(product.getTimeExpire()).getTime()));
            //timeProduce
            pstmt.setDate(8,new java.sql.Date(format.parse(product.getTimeProduce()).getTime()));
            //type
            pstmt.setString(9,product.getType());
            //provider
            pstmt.setString(10,product.getProvider());
            //producer
            pstmt.setString(11,product.getProducer());
            pstmt.execute();
            product.setYes(1);
            return product;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            product.setYes(0);
            System.out.println("addProduct(Product product) error");
            return null;
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
            product.setPriceSale(String.valueOf(rs.getBigDecimal(3)));
            product.setDiscount(String.valueOf(rs.getBigDecimal(4)));
//        product.setPriceLowest();
//        product.setTimeExpire();
//        product.setTimeProduce();
//        product.setType();
//        product.setProvider();
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
    public int productExist(String barcode) {
        String sql = "SELECT count(*) FROM product WHERE bar_code = ?;";
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, barcode);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println("商品存在");
                return 1;
            } else {
                System.out.println("商品不存在，请检查输入的条形码");
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
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                product.setPriceLowest(String.valueOf(rs.getBigDecimal(5)));
                product.setPriceSale(String.valueOf(rs.getBigDecimal(6)));
                product.setDiscount(String.valueOf(rs.getBigDecimal(7)));
                product.setTimeExpire(format.format(rs.getDate(8)));
                product.setTimeProduce(format.format(rs.getDate(9)));
                product.setType(rs.getString(10));
                product.setProvider(rs.getString(11));
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
