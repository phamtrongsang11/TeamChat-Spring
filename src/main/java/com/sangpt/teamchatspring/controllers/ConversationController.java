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
import org.springframework.web.bind.annotation.RestController;

import com.sangpt.teamchatspring.domain.dtos.ConversationDto.ConversationRequestDto;
import com.sangpt.teamchatspring.domain.dtos.ConversationDto.ConversationResponseDto;
import com.sangpt.teamchatspring.domain.entities.Conversation;
import com.sangpt.teamchatspring.mappers.Mapper;
import com.sangpt.teamchatspring.services.ConversationService;

@RestController
@RequestMapping(path = "/api/conversations")
public class ConversationController {
    private Mapper<Conversation, ConversationRequestDto, ConversationResponseDto> conversationMapper;
    private ConversationService conversationService;

    public ConversationController(
            Mapper<Conversation, ConversationRequestDto, ConversationResponseDto> conversationMapper,
            ConversationService conversationService) {
        this.conversationMapper = conversationMapper;
        this.conversationService = conversationService;
    }

    @PostMapping
    public ResponseEntity<ConversationResponseDto> createConversation(
            @RequestBody ConversationRequestDto conversationRequest) {
        Conversation conversation = conversationMapper.requestToEntity(conversationRequest);
        Conversation savedConversation = conversationService.create(conversation);
        return new ResponseEntity<>(conversationMapper.entityToResponse(savedConversation),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConversationResponseDto>> listConversations() {
        List<Conversation> conversations = conversationService.findAll();
        return new ResponseEntity<>(
                conversations.stream().map(conversationMapper::entityToResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ConversationResponseDto> getConversation(@PathVariable("id") String id) {
        Optional<Conversation> foundConversation = conversationService.findOne(id);

        return foundConversation.map(conversation -> {
            ConversationResponseDto conversationDto = conversationMapper.entityToResponse(conversation);

            return new ResponseEntity<>(conversationDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ConversationResponseDto> fullUpdateConversation(@PathVariable("id") String id,
            @RequestBody ConversationRequestDto conversationRequest) {
        if (!conversationService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        conversationRequest.setId(id);
        Conversation conversation = conversationMapper.requestToEntity(conversationRequest);
        Conversation updatedConversation = conversationService.update(id, conversation);

        return new ResponseEntity<>(conversationMapper.entityToResponse(updatedConversation),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ConversationResponseDto> partialUpdateConversation(@PathVariable("id") String id,
            @RequestBody ConversationRequestDto conversationRequest) {
        if (!conversationService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Conversation conversation = conversationMapper.requestToEntity(conversationRequest);
        Conversation updatedConversation = conversationService.patialUpdate(id, conversation);

        return new ResponseEntity<>(conversationMapper.entityToResponse(updatedConversation),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteDirectMessage(@PathVariable("id") String id) {
        conversationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/member")
    public ResponseEntity<ConversationResponseDto> getOrCreate(
            @RequestBody ConversationRequestDto conversationRequest) {
        Conversation conversation = conversationMapper.requestToEntity(conversationRequest);

        Conversation foundConversation = conversationService
                .findConversationByMember(conversationRequest.getMemberOneId(), conversationRequest.getMemberTwoId());

        if (foundConversation == null)
            foundConversation = conversationService.create(conversation);

        return new ResponseEntity<>(conversationMapper.entityToResponse(foundConversation),
                HttpStatus.CREATED);
    }
}
