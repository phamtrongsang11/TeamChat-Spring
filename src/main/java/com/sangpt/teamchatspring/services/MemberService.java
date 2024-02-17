package com.sangpt.teamchatspring.services;

import java.util.List;
import java.util.Optional;

import com.sangpt.teamchatspring.domain.entities.Member;

public interface MemberService {
    Member create(Member member);

    Member update(String id, Member member);

    List<Member> findAll();

    Optional<Member> findOne(String id);

    Optional<Member> findByServerAndProfile(String serverId, String profileId);

    boolean isExist(String id);

    Member patialUpdate(String id, Member member);

    void delete(String id);

    void deleteMemberByProfileId(String id);

}
