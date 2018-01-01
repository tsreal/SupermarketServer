package com.tgtiger.Bean;

//java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
//df.format(你要格式化的数字);
/*
product
{
"barCode":"100861008611",
"name":"小当家方便面",
"priceLowest":"0.30",
"priceSale":"0.50",
"discount":"0.95",
"type":"食品",
"timeExpire":"2018-3-6",
"timeProduce":"2017-3-6",
"provider":"太原食品批发厂",
"producer":"天津顶新集团",
"state": 0
}
 */
//public class Product {
//    //条形码
//    private String barCode;
//    //姓名
//    private String name;
//    //(7,2)商品进价价格
//    private BigDecimal priceLowest;
//    //(7,2)商品销售价格
//    private BigDecimal priceSale;
//    //(3,2)折扣
//    private BigDecimal discount;
//    //商品类型
//    private String type;
//    //商品过期日期
//    private Date timeExpire;
//    //生产日期
//    private Date timeProduce;
//    //供货商
//    private String provicer;
//    //生产商
//    private String producer;
//    //商品销售状态
//    private int state;
//
//    public String getBarCode() {
//        return barCode;
//    }
//
//    public void setBarCode(String barCode) {
//        this.barCode = barCode;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public BigDecimal getPriceLowest() {
//        return priceLowest;
//    }
//
//    public void setPriceLowest(BigDecimal priceLowest) {
//        this.priceLowest = priceLowest;
//    }
//
//    public BigDecimal getPriceSale() {
//        return priceSale;
//    }
//
//    public void setPriceSale(BigDecimal priceSale) {
//        this.priceSale = priceSale;
//    }
//
//    public BigDecimal getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(BigDecimal discount) {
//        this.discount = discount;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Date getTimeExpire() {
//        return timeExpire;
//    }
//
//    public void setTimeExpire(Date timeExpire) {
//        this.timeExpire = timeExpire;
//    }
//
//    public Date getTimeProduce() {
//        return timeProduce;
//    }
//
//    public void setTimeProduce(Date timeProduce) {
//        this.timeProduce = timeProduce;
//    }
//
//    public String getProvicer() {
//        return provicer;
//    }
//
//    public void setProvicer(String provicer) {
//        this.provicer = provicer;
//    }
//
//    public String getProducer() {
//        return producer;
//    }
//
//    public void setProducer(String producer) {
//        this.producer = producer;
//    }
//
//    public int getState() {
//        return state;
//    }
//
//    public void setState(int state) {
//        this.state = state;
//    }
//
//
//
//}
public class Product{

    /**
     * timeExpire : 2018-3-6
     * timeProduce : 2017-3-6
     * provider : 太原食品批发厂
     * name : 小当家方便面
     * discount : 0.95
     * producer : 天津顶新集团
     * priceLowest : 0.30
     * priceSale : 0.50
     * state : 0
     * type : 食品
     * barCode : 100861008611
     */

    private String timeExpire;
    private String timeProduce;
    private String provider;
    private String name;
    private String discount;
    private String producer;
    private String priceLowest;
    private String priceSale;
    private int state;
    private String type;
    private String barCode;
    /**
     * yes : 0
     */
    private int yes;

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public void setTimeProduce(String timeProduce) {
        this.timeProduce = timeProduce;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setPriceLowest(String priceLowest) {
        this.priceLowest = priceLowest;
    }

    public void setPriceSale(String priceSale) {
        this.priceSale = priceSale;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public String getTimeProduce() {
        return timeProduce;
    }

    public String getProvider() {
        return provider;
    }

    public String getName() {
        return name;
    }

    public String getDiscount() {
        return discount;
    }

    public String getProducer() {
        return producer;
    }

    public String getPriceLowest() {
        return priceLowest;
    }

    public String getPriceSale() {
        return priceSale;
    }

    public int getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getYes() {
        return yes;
    }
}
