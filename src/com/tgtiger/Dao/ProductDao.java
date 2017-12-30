package com.tgtiger.Dao;

import com.tgtiger.Bean.Product;

public interface ProductDao {
    //商品添加
    boolean addProduct(Product product);
    //商品删除
    //boolean deleteProduct(String name);
    //生成条形码
    String genBarCodes();





}
