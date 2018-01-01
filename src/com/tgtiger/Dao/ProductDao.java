package com.tgtiger.Dao;

import com.tgtiger.Bean.Product;

import java.util.List;

public interface ProductDao {
    //商品添加
    Product addProduct(Product product);

    //商品删除
    //boolean deleteProduct(String name);
    //商品购买扫码查询
    Product getProduct(String barcode);


    List<Product> getFullProduct();



    //生成条形码
    String genBarCodes();





}
