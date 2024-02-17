package com.sangpt.teamchatspring.services;

import java.util.List;
import java.util.Optional;

import com.sangpt.teamchatspring.domain.entities.Member;
import com.sangpt.teamchatspring.domain.entities.Server;

public interface ServerService {
    Server create(Server server);

    Server update(String id, Server server);

    List<Server> findAll();

    List<Server> findServersByMemberId(String memberId);

    Optional<Server> findServerByInviteCode(String inviteCode);

    Optional<Server> findOne(String id);

    boolean isExist(String id);

    Server patialUpdate(String id, Server server);

    Optional<Server> saveMemberByInviteCode(String inviteCode, Member member);

    void delete(String id);
}
