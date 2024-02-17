package com.sangpt.teamchatspring.mappers.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.sangpt.teamchatspring.domain.dtos.MessageDto.MessageRequestDto;
import com.sangpt.teamchatspring.domain.dtos.MessageDto.MessageResponseDto;
import com.sangpt.teamchatspring.domain.entities.Message;
import com.sangpt.teamchatspring.mappers.Mapper;

@Component
public class MessageMapperImpl implements Mapper<Message, MessageRequestDto, MessageResponseDto> {
    private ModelMapper modelMapper;

    private MessageMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        PropertyMap<MessageRequestDto, Message> messageMemberMap = new PropertyMap<MessageRequestDto, Message>() {
            protected void configure() {
                map().getMember().setId(source.getMemberId());
            }
        };
        PropertyMap<MessageRequestDto, Message> messageChannelMap = new PropertyMap<MessageRequestDto, Message>() {
            protected void configure() {
                map().getChannel().setId(source.getChannelId());
            }
        };

        modelMapper.addMappings(messageMemberMap);
        modelMapper.addMappings(messageChannelMap);
    }

    @Override
    public Message requestToEntity(MessageRequestDto messageRequest) {

        return modelMapper.map(messageRequest, Message.class);
    }

    @Override
    public MessageResponseDto entityToResponse(Message message) {

        return modelMapper.map(message, MessageResponseDto.class);
    }

}