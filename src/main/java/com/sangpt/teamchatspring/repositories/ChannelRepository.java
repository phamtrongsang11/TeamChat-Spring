package com.sangpt.teamchatspring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sangpt.teamchatspring.domain.entities.Channel;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, String> {

}
