package com.tgtiger.Bean;

import java.sql.Date;

public class Statement {
    //条形码
    private String barCode;
    //商品销售日期
    private Date date;
    //该商品正常销售价格
    private double priceSale;
    //该商品折扣价格
    private double priceDiscount;
    //会员购买数量
    private int vipNo;
    //普通人购买数量
    private int normalNo;
    //该商品当天销售总数量
    private int totalNo;
    //该商品毛收入
    private double income;
    /**
     * name : 雪碧
     */
    private String name;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(double priceSale) {
        this.priceSale = priceSale;
    }

    public double getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public int getVipNo() {
        return vipNo;
    }

    public void setVipNo(int vipNo) {
        this.vipNo = vipNo;
    }

    public int getNormalNo() {
        return normalNo;
    }

    public void setNormalNo(int normalNo) {
        this.normalNo = normalNo;
    }

    public int getTotalNo() {
        return totalNo;
    }

    public void setTotalNo(int totalNo) {
        this.totalNo = totalNo;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
