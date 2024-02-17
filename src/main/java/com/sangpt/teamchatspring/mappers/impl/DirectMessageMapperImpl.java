package com.sangpt.teamchatspring.mappers.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.sangpt.teamchatspring.domain.dtos.DirectMessageDto.DirectMessageRequestDto;
import com.sangpt.teamchatspring.domain.dtos.DirectMessageDto.DirectMessageResponseDto;
import com.sangpt.teamchatspring.domain.entities.DirectMessage;
import com.sangpt.teamchatspring.mappers.Mapper;

@Component
public class DirectMessageMapperImpl
        implements Mapper<DirectMessage, DirectMessageRequestDto, DirectMessageResponseDto> {
    private ModelMapper modelMapper;

    private DirectMessageMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        PropertyMap<DirectMessageRequestDto, DirectMessage> directMessageMemberMap = new PropertyMap<DirectMessageRequestDto, DirectMessage>() {
            protected void configure() {
                map().getMember().setId(source.getMemberId());
            }
        };

        PropertyMap<DirectMessageRequestDto, DirectMessage> directMessageConversationMap = new PropertyMap<DirectMessageRequestDto, DirectMessage>() {
            protected void configure() {
                map().getConversation().setId(source.getConversationId());
            }
        };

        modelMapper.addMappings(directMessageMemberMap);
        modelMapper.addMappings(directMessageConversationMap);
    }

    @Override
    public DirectMessage requestToEntity(DirectMessageRequestDto directMessageRequest) {

        return modelMapper.map(directMessageRequest, DirectMessage.class);
    }

    @Override
    public DirectMessageResponseDto entityToResponse(DirectMessage directMessage) {

        return modelMapper.map(directMessage, DirectMessageResponseDto.class);
    }

}