package com.sangpt.teamchatspring.services;

import java.util.List;
import java.util.Optional;

import com.sangpt.teamchatspring.domain.entities.Message;

public interface MessageService {
    Message create(Message message);

    Message update(String id, Message message);

    List<Message> findAll();

    List<Message> findMessagesByChannelId(String channelId);

    Optional<Message> findOne(String id);

    boolean isExist(String id);

    Message patialUpdate(String id, Message message);

    void delete(String id);
}
