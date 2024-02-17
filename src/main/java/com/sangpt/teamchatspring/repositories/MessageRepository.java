package com.sangpt.teamchatspring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sangpt.teamchatspring.domain.entities.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {
    @Query(value = "SELECT * from Message WHERE channel_id = :channelId ORDER BY created_at DESC", nativeQuery = true)
    Iterable<Message> findMessageByChannelId(String channelId);

}
