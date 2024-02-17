package com.sangpt.teamchatspring.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangpt.teamchatspring.domain.dtos.MessageDto.MessageRequestDto;
import com.sangpt.teamchatspring.domain.dtos.MessageDto.MessageResponseDto;
import com.sangpt.teamchatspring.domain.entities.Message;
import com.sangpt.teamchatspring.mappers.Mapper;
import com.sangpt.teamchatspring.services.MessageService;

@RestController
@RequestMapping(path = "/api/messages")
public class MessageController {
    private Mapper<Message, MessageRequestDto, MessageResponseDto> messageMapper;
    private MessageService messageService;

    public MessageController(Mapper<Message, MessageRequestDto, MessageResponseDto> messageMapper,
            MessageService messageService) {
        this.messageMapper = messageMapper;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDto> createMessage(@RequestBody MessageRequestDto messageRequest) {
        Message message = messageMapper.requestToEntity(messageRequest);
        Message savedMessage = messageService.create(message);
        return new ResponseEntity<>(messageMapper.entityToResponse(savedMessage),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseDto>> listMessages() {
        List<Message> messages = messageService.findAll();
        return new ResponseEntity<>(messages.stream().map(messageMapper::entityToResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MessageResponseDto> getMessage(@PathVariable("id") String id) {
        Optional<Message> foundMessage = messageService.findOne(id);

        return foundMessage.map(message -> {
            MessageResponseDto messageDto = messageMapper.entityToResponse(message);

            return new ResponseEntity<>(messageDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MessageResponseDto> fullUpdateMessage(@PathVariable("id") String id,
            @RequestBody MessageRequestDto messageRequest) {
        if (!messageService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        messageRequest.setId(id);
        Message message = messageMapper.requestToEntity(messageRequest);
        Message updatedMessage = messageService.update(id, message);

        return new ResponseEntity<>(messageMapper.entityToResponse(updatedMessage),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<MessageResponseDto> partialUpdateMessage(@PathVariable("id") String id,
            @RequestBody MessageRequestDto messageRequest) {
        if (!messageService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Message message = messageMapper.requestToEntity(messageRequest);
        Message updatedMessage = messageService.patialUpdate(id, message);

        return new ResponseEntity<>(messageMapper.entityToResponse(updatedMessage),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteMessage(@PathVariable("id") String id) {
        messageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/channel")
    public ResponseEntity<List<MessageResponseDto>> findMessageByChannelId(
            @RequestParam("channelId") String channelId) {
        List<Message> messages = messageService.findMessagesByChannelId(channelId);
        return new ResponseEntity<>(messages.stream().map(messageMapper::entityToResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }

}
