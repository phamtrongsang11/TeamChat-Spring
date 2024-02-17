package com.sangpt.teamchatspring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sangpt.teamchatspring.domain.entities.DirectMessage;

@Repository
public interface DirectMessageRepository extends CrudRepository<DirectMessage, String> {

    @Query(value = "SELECT * FROM DirectMessage WHERE conversation_id = :conversationId ORDER BY created_at DESC", nativeQuery = true)
    Iterable<DirectMessage> findDirectMessageByConversationId(String conversationId);

}
