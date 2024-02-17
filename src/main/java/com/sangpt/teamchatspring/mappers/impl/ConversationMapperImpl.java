package com.sangpt.teamchatspring.mappers.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.sangpt.teamchatspring.domain.dtos.ConversationDto.ConversationRequestDto;
import com.sangpt.teamchatspring.domain.dtos.ConversationDto.ConversationResponseDto;
import com.sangpt.teamchatspring.domain.entities.Conversation;
import com.sangpt.teamchatspring.mappers.Mapper;

@Component
public class ConversationMapperImpl implements Mapper<Conversation, ConversationRequestDto, ConversationResponseDto> {
    private ModelMapper modelMapper;

    private ConversationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        PropertyMap<ConversationRequestDto, Conversation> conversationMemberOneMap = new PropertyMap<ConversationRequestDto, Conversation>() {
            protected void configure() {
                map().getMemberOne().setId(source.getMemberOneId());
            }
        };
        PropertyMap<ConversationRequestDto, Conversation> conversationMemberTwoMap = new PropertyMap<ConversationRequestDto, Conversation>() {
            protected void configure() {
                map().getMemberTwo().setId(source.getMemberTwoId());
            }
        };
        modelMapper.addMappings(conversationMemberOneMap);
        modelMapper.addMappings(conversationMemberTwoMap);
    }

    @Override
    public Conversation requestToEntity(ConversationRequestDto conversationRequest) {

        return modelMapper.map(conversationRequest, Conversation.class);
    }

    @Override
    public ConversationResponseDto entityToResponse(Conversation conversation) {

        return modelMapper.map(conversation, ConversationResponseDto.class);
    }

}
