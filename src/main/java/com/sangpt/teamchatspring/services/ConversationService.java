package com.sangpt.teamchatspring.services;

import java.util.List;
import java.util.Optional;

import com.sangpt.teamchatspring.domain.entities.Conversation;

public interface ConversationService {
    Conversation create(Conversation conversation);

    Conversation update(String id, Conversation conversation);

    List<Conversation> findAll();

    Optional<Conversation> findOne(String id);

    boolean isExist(String id);

    Conversation patialUpdate(String id, Conversation conversation);

    void delete(String id);

    Conversation findConversationByMember(String memberOneId, String memberTwoId);
}
