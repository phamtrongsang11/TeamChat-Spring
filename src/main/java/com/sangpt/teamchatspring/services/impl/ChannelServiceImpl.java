package com.sangpt.teamchatspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.sangpt.teamchatspring.domain.entities.Channel;
import com.sangpt.teamchatspring.repositories.ChannelRepository;
import com.sangpt.teamchatspring.repositories.ProfileRepository;
import com.sangpt.teamchatspring.repositories.ServerRepository;
import com.sangpt.teamchatspring.services.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {
    private ChannelRepository channelRepository;
    private ServerRepository serverRepository;
    private ProfileRepository profileRepository;

    public ChannelServiceImpl(ChannelRepository channelRepository, ServerRepository serverRepository,
            ProfileRepository profileRepository) {
        this.channelRepository = channelRepository;
        this.serverRepository = serverRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public Channel create(Channel channel) {
        if (channel.getProfile() != null)
            profileRepository.findById(channel.getProfile().getId()).ifPresentOrElse(channel::setProfile,
                    () -> channel.setProfile(null));

        if (channel.getServer() != null)
            serverRepository.findById(channel.getServer().getId()).ifPresentOrElse(channel::setServer,
                    () -> channel.setServer(null));

        return channelRepository.save(channel);
    }

    @Override
    public Channel update(String id, Channel channel) {

        return channelRepository.findById(id).map(existingChannel -> {
            if (channel.getProfile() != null)
                profileRepository.findById(channel.getProfile().getId()).ifPresentOrElse(channel::setProfile,
                        () -> channel.setProfile(existingChannel.getProfile()));

            if (channel.getServer() != null)
                serverRepository.findById(channel.getServer().getId()).ifPresentOrElse(channel::setServer,
                        () -> channel.setServer(existingChannel.getServer()));
            return channelRepository.save(channel);
        }).orElseThrow(() -> new RuntimeException("Channel not found"));
    }

    @Override
    public List<Channel> findAll() {
        return StreamSupport.stream(channelRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Channel> findOne(String id) {
        return channelRepository.findById(id);
    }

    @Override
    public boolean isExist(String id) {
        return channelRepository.existsById(id);
    }

    @Override
    public Channel patialUpdate(String id, Channel channel) {
        channel.setId(id);

        return channelRepository.findById(id).map(existingChannel -> {
            Optional.ofNullable(channel.getName()).ifPresent(existingChannel::setName);

            Optional.ofNullable(channel.getType()).ifPresent(existingChannel::setType);

            Optional.ofNullable(channel.getProfile())
                    .ifPresent(profile -> profileRepository.findById(profile.getId()).ifPresent(
                            existingChannel::setProfile));

            Optional.ofNullable(channel.getServer())
                    .ifPresent(server -> serverRepository.findById(server.getId()).ifPresent(
                            existingChannel::setServer));

            return channelRepository.save(existingChannel);
        }).orElseThrow(() -> new RuntimeException("Channel not found"));
    }

    @Override
    public void delete(String id) {
        channelRepository.deleteById(id);
    }
}
