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

import com.sangpt.teamchatspring.domain.dtos.DirectMessageDto.DirectMessageRequestDto;
import com.sangpt.teamchatspring.domain.dtos.DirectMessageDto.DirectMessageResponseDto;
import com.sangpt.teamchatspring.domain.entities.DirectMessage;
import com.sangpt.teamchatspring.mappers.Mapper;
import com.sangpt.teamchatspring.services.DirectMessageService;

@RestController
@RequestMapping(path = "/api/directmessages")
public class DirecMessageController {
    private Mapper<DirectMessage, DirectMessageRequestDto, DirectMessageResponseDto> directMessageMapper;
    private DirectMessageService directMessageService;

    public DirecMessageController(
            Mapper<DirectMessage, DirectMessageRequestDto, DirectMessageResponseDto> directMessageMapper,
            DirectMessageService directMessageService) {
        this.directMessageMapper = directMessageMapper;
        this.directMessageService = directMessageService;
    }

    @PostMapping
    public ResponseEntity<DirectMessageResponseDto> createMessage(
            @RequestBody DirectMessageRequestDto directMessageRequest) {
        DirectMessage directMessage = directMessageMapper.requestToEntity(directMessageRequest);
        DirectMessage savedDirectMessage = directMessageService.create(directMessage);
        return new ResponseEntity<>(directMessageMapper.entityToResponse(savedDirectMessage),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DirectMessageResponseDto>> listMessages() {
        List<DirectMessage> directMessages = directMessageService.findAll();
        return new ResponseEntity<>(
                directMessages.stream().map(directMessageMapper::entityToResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DirectMessageResponseDto> getMessage(@PathVariable("id") String id) {
        Optional<DirectMessage> foundDirectMessage = directMessageService.findOne(id);

        return foundDirectMessage.map(directMessage -> {
            DirectMessageResponseDto directMessageDto = directMessageMapper.entityToResponse(directMessage);

            return new ResponseEntity<>(directMessageDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DirectMessageResponseDto> fullUpdateDirectMessage(@PathVariable("id") String id,
            @RequestBody DirectMessageRequestDto directMessageRequest) {
        if (!directMessageService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        directMessageRequest.setId(id);
        DirectMessage directMessage = directMessageMapper.requestToEntity(directMessageRequest);
        DirectMessage updatedDirectMessage = directMessageService.update(id, directMessage);

        return new ResponseEntity<>(directMessageMapper.entityToResponse(updatedDirectMessage),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<DirectMessageResponseDto> partialUpdateDirectMessage(@PathVariable("id") String id,
            @RequestBody DirectMessageRequestDto directMessageRequest) {
        if (!directMessageService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DirectMessage directMessage = directMessageMapper.requestToEntity(directMessageRequest);
        DirectMessage updatedDirectMessage = directMessageService.patialUpdate(id, directMessage);

        return new ResponseEntity<>(directMessageMapper.entityToResponse(updatedDirectMessage),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteDirectMessage(@PathVariable("id") String id) {
        directMessageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/conversation")
    public ResponseEntity<List<DirectMessageResponseDto>> findDirectMessageByConversationId(
            @RequestParam("conversationId") String conversationId) {
        List<DirectMessage> directMessages = directMessageService.findDirectMessagesByConversationId(conversationId);
        return new ResponseEntity<>(
                directMessages.stream().map(directMessageMapper::entityToResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
