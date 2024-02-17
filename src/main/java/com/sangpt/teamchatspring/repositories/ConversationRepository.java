package com.sangpt.teamchatspring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sangpt.teamchatspring.domain.entities.Conversation;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, String> {

}
