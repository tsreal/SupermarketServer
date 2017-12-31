package com.tgtiger.Bean;

import java.math.BigDecimal;
import java.util.Date;

//java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
//df.format(你要格式化的数字);
public class Product {
    //条形码
    private String barCode;
    //姓名
    private String name;
    //(7,2)商品进价价格
    private BigDecimal priceLowest;
    //(7,2)商品销售价格
    private BigDecimal priceSale;
    //(3,2)折扣
    private BigDecimal discount;
    //商品类型
    private String type;
    //商品过期日期
    private Date timeExpire;
    //生产日期
    private Date timeProduce;
    //供货商
    private String provicer;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceLowest() {
        return priceLowest;
    }

    public void setPriceLowest(BigDecimal priceLowest) {
        this.priceLowest = priceLowest;
    }

    public BigDecimal getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(BigDecimal priceSale) {
        this.priceSale = priceSale;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(Date timeExpire) {
        this.timeExpire = timeExpire;
    }

    public Date getTimeProduce() {
        return timeProduce;
    }

    public void setTimeProduce(Date timeProduce) {
        this.timeProduce = timeProduce;
    }

    public String getProvicer() {
        return provicer;
    }

    public void setProvicer(String provicer) {
        this.provicer = provicer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    //生产商
    private String producer;
    //商品销售状态
    private int state;


}
