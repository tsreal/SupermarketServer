package com.tgtiger.Dao;

import com.tgtiger.Bean.Member;

public interface MemberDao {
    int checkMemNo(String memberNo);

    int memExist(String phone);
    int memExpire(String memberNo);
    boolean addMember(Member member);
    Member getMemberInfo(String phone);

    String genMemberNo();
}
