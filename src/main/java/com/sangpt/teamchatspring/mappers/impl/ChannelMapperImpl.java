package com.sangpt.teamchatspring.mappers.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.sangpt.teamchatspring.domain.dtos.ChannelDto.ChannelRequestDto;
import com.sangpt.teamchatspring.domain.dtos.ChannelDto.ChannelResponseDto;
import com.sangpt.teamchatspring.domain.entities.Channel;
import com.sangpt.teamchatspring.mappers.Mapper;

@Component
public class ChannelMapperImpl implements Mapper<Channel, ChannelRequestDto, ChannelResponseDto> {
    private ModelMapper modelMapper;

    private ChannelMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        PropertyMap<ChannelRequestDto, Channel> channelProfileMap = new PropertyMap<ChannelRequestDto, Channel>() {
            protected void configure() {
                map().getProfile().setId(source.getProfileId());
            }
        };

        PropertyMap<ChannelRequestDto, Channel> channelServerMap = new PropertyMap<ChannelRequestDto, Channel>() {
            protected void configure() {
                map().getServer().setId(source.getServerId());
            }
        };
        modelMapper.addMappings(channelProfileMap);
        modelMapper.addMappings(channelServerMap);
    }

    @Override
    public Channel requestToEntity(ChannelRequestDto channelRequest) {

        return modelMapper.map(channelRequest, Channel.class);
    }

    @Override
    public ChannelResponseDto entityToResponse(Channel channel) {

        return modelMapper.map(channel, ChannelResponseDto.class);
    }

}