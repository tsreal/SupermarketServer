package com.tgtiger.Dao;

import com.tgtiger.Bean.DepositoryList;
import com.tgtiger.Bean.Product;

import java.util.List;

public interface ProductDao {
    //商品添加
    Product addProduct(Product product);

    //商品删除
    //boolean deleteProduct(String name);
    //商品购买扫码查询
    Product getProduct(String barcode);

    int productExist(String barcode);

    int nameExist(String name);


    List<Product> getFullProduct();

    List<DepositoryList.ListsEntity> getRemain();

    //生成条形码
    String genBarCodes();





}
