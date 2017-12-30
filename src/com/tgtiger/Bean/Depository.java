package com.tgtiger.Bean;

import java.sql.Date;

public class Depository {
    //条形码
    private String barCode;
    //仓库号
    private int depoNo;
    //商品数量
    private int productNo;
    //默认进货数量
    private int importNo;
    //进货时间
    private Date importDate;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getDepoNo() {
        return depoNo;
    }

    public void setDepoNo(int depoNo) {
        this.depoNo = depoNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public int getImportNo() {
        return importNo;
    }

    public void setImportNo(int importNo) {
        this.importNo = importNo;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
}
