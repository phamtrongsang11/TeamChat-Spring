package com.sangpt.teamchatspring.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.sangpt.teamchatspring.domain.dtos.DirectMessageDto.DirectMessageRequestDto;
import com.sangpt.teamchatspring.domain.dtos.DirectMessageDto.DirectMessageResponseDto;
import com.sangpt.teamchatspring.domain.dtos.MessageDto.MessageRequestDto;
import com.sangpt.teamchatspring.domain.dtos.MessageDto.MessageResponseDto;
import com.sangpt.teamchatspring.domain.entities.DirectMessage;
import com.sangpt.teamchatspring.domain.entities.Message;
import com.sangpt.teamchatspring.mappers.Mapper;
import com.sangpt.teamchatspring.services.DirectMessageService;
import com.sangpt.teamchatspring.services.MessageService;

@Controller
public class ChatMessageController {

    private SimpMessagingTemplate messagingTemplate;
    private Mapper<Message, MessageRequestDto, MessageResponseDto> messageMapper;
    private Mapper<DirectMessage, DirectMessageRequestDto, DirectMessageResponseDto> directMessageMapper;
    private MessageService messageService;
    private DirectMessageService directMessageService;

    public ChatMessageController(SimpMessagingTemplate messagingTemplate,
            Mapper<Message, MessageRequestDto, MessageResponseDto> messageMapper, MessageService messageService,
            Mapper<DirectMessage, DirectMessageRequestDto, DirectMessageResponseDto> directMessageMapper,
            DirectMessageService directMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageMapper = messageMapper;
        this.messageService = messageService;
        this.directMessageMapper = directMessageMapper;
        this.directMessageService = directMessageService;
    }

    @MessageMapping("/message.addMessage")
    public void addMessage(@Payload MessageRequestDto messageRequest) {
        Message message = messageMapper.requestToEntity(messageRequest);

        Message savedMessage = messageService.create(message);

        messagingTemplate.convertAndSend("/ReceiveMessage/" + messageRequest.getChannelId(), savedMessage);
    }

    @MessageMapping("/message.updateMessage")
    public void updateMessage(@Payload MessageRequestDto messageRequest) {
        Message message = messageMapper.requestToEntity(messageRequest);

        Message updatedMessage = messageService.patialUpdate(message.getId(), message);

        messagingTemplate.convertAndSend("/ReceiveUpdateMessage/" + messageRequest.getChannelId(), updatedMessage);
    }

    @MessageMapping("/message.deleteMessage")
    public void deleteMessage(@Payload MessageRequestDto messageRequest) {

        Message foundMessage = messageService.findOne(messageRequest.getId()).get();

        if (foundMessage == null || foundMessage.getDeleted() == true) {
            return;
        }
        messageRequest.setContent("This message has been deleted");
        messageRequest.setFileUrl(null);
        messageRequest.setDeleted(true);

        Message message = messageMapper.requestToEntity(messageRequest);
        Message updatedMessage = messageService.patialUpdate(message.getId(), message);

        messagingTemplate.convertAndSend("/ReceiveUpdateMessage/" + messageRequest.getChannelId(), updatedMessage);
    }

    @MessageMapping("/directMessage.addMessage")
    public void addDirectMessage(@Payload DirectMessageRequestDto directMessageRequest) {
        DirectMessage directMessage = directMessageMapper.requestToEntity(directMessageRequest);

        DirectMessage savedDirectMessage = directMessageService.create(directMessage);

        messagingTemplate.convertAndSend("/ReceiveMessage/" + directMessageRequest.getConversationId(),
                savedDirectMessage);
    }

    @MessageMapping("/directMessage.updateMessage")
    public void updateDirectMessage(@Payload DirectMessageRequestDto directMessageRequest) {
        DirectMessage directMessage = directMessageMapper.requestToEntity(directMessageRequest);

        DirectMessage updatedDirectMessage = directMessageService.patialUpdate(directMessage.getId(), directMessage);

        messagingTemplate.convertAndSend("/ReceiveUpdateMessage/" + directMessageRequest.getConversationId(),
                updatedDirectMessage);
    }

    @MessageMapping("/directMessage.deleteMessage")
    public void deleteDirectMessage(@Payload DirectMessageRequestDto directMessageRequest) {

        DirectMessage foundDirectMessage = directMessageService.findOne(directMessageRequest.getId()).get();

        if (foundDirectMessage == null || foundDirectMessage.getDeleted() == true) {
            return;
        }
        directMessageRequest.setContent("This message has been deleted");
        directMessageRequest.setFileUrl(null);
        directMessageRequest.setDeleted(true);

        DirectMessage directMessage = directMessageMapper.requestToEntity(directMessageRequest);
        DirectMessage updatedDirectMessage = directMessageService.patialUpdate(directMessage.getId(), directMessage);

        messagingTemplate.convertAndSend("/ReceiveUpdateMessage/" + directMessageRequest.getConversationId(),
                updatedDirectMessage);
    }
}
