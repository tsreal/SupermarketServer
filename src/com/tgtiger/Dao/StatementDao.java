package com.tgtiger.Dao;

import com.tgtiger.Bean.Bill;
import com.tgtiger.Bean.Statement;

import java.util.Date;
import java.util.List;

public interface StatementDao {
    boolean prodStatExist(String barcode);
    int getBill(Bill rec);
    int getNumber(String barcode, int isvip, Date date);

    boolean updateIncome(String barcode, Date date);

    List<Statement> getAnalysis(int status, Date date);

}
