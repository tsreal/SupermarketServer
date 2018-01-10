package com.tgtiger.Dao;

import com.tgtiger.Bean.Member;
import com.tgtiger.Bean.MemberList;

import java.util.List;

public interface MemberDao {
    int checkMemNo(String memberNo);

    int memExist(String phone);
    int memExpire(String memberNo);
    boolean addMember(Member member);
    Member getMemberInfo(String phone);

    boolean updateMemberBill(Double total,String memberNo);

    List<MemberList.MemlistsEntity> getMemberList();
    String genMemberNo();
}
