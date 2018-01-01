package com.tgtiger.Dao;

import com.tgtiger.Bean.Bill;

import java.util.Date;

public interface StatementDao {
    boolean prodStatExist(String barcode);
    boolean getBill(Bill rec);
    int getNumber(String barcode, int isvip, Date date);

}
