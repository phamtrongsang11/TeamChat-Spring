package com.sangpt.teamchatspring.services;

import java.util.List;
import java.util.Optional;

import com.sangpt.teamchatspring.domain.entities.DirectMessage;

public interface DirectMessageService {
    DirectMessage create(DirectMessage directMessage);

    DirectMessage update(String id, DirectMessage directMessage);

    List<DirectMessage> findAll();

    List<DirectMessage> findDirectMessagesByConversationId(String id);

    Optional<DirectMessage> findOne(String id);

    boolean isExist(String id);

    DirectMessage patialUpdate(String id, DirectMessage member);

    void delete(String id);
}
