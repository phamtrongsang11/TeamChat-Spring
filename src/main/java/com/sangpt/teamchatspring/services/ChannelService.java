package com.sangpt.teamchatspring.services;

import java.util.List;
import java.util.Optional;

import com.sangpt.teamchatspring.domain.entities.Channel;

public interface ChannelService {
    Channel create(Channel channel);

    Channel update(String id, Channel channel);

    List<Channel> findAll();

    Optional<Channel> findOne(String id);

    boolean isExist(String id);

    Channel patialUpdate(String id, Channel channel);

    void delete(String id);
}
