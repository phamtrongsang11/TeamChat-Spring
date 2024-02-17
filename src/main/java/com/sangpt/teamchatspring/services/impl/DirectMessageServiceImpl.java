package com.sangpt.teamchatspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.sangpt.teamchatspring.domain.entities.DirectMessage;
import com.sangpt.teamchatspring.repositories.ConversationRepository;
import com.sangpt.teamchatspring.repositories.DirectMessageRepository;
import com.sangpt.teamchatspring.repositories.MemberRepository;
import com.sangpt.teamchatspring.services.DirectMessageService;

@Service
public class DirectMessageServiceImpl implements DirectMessageService {
    private DirectMessageRepository directMessageRepository;
    private MemberRepository memberRepository;
    private ConversationRepository conversationRepository;

    public DirectMessageServiceImpl(DirectMessageRepository directMessageRepository,
            MemberRepository memberRepository, ConversationRepository conversationRepository) {
        this.directMessageRepository = directMessageRepository;
        this.memberRepository = memberRepository;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public DirectMessage create(DirectMessage directMessage) {
        Optional.ofNullable(directMessage.getMember())
                .ifPresent(member -> memberRepository.findById(directMessage.getMember().getId()).ifPresentOrElse(
                        directMessage::setMember,
                        () -> directMessage.setMember(null)));

        Optional.ofNullable(directMessage.getConversation())
                .ifPresent(conversation -> conversationRepository.findById(conversation.getId()).ifPresentOrElse(
                        directMessage::setConversation,
                        () -> directMessage.setConversation(null)));

        return directMessageRepository.save(directMessage);
    }

    @Override
    public DirectMessage update(String id, DirectMessage directMessage) {

        return directMessageRepository.findById(id).map(existingDirectMessage -> {
            Optional.ofNullable(directMessage.getMember())
                    .ifPresent(member -> memberRepository.findById(member.getId()).ifPresentOrElse(
                            directMessage::setMember,
                            () -> directMessage.setMember(existingDirectMessage.getMember())));

            Optional.ofNullable(directMessage.getConversation())
                    .ifPresent(conversation -> conversationRepository.findById(conversation.getId()).ifPresentOrElse(
                            directMessage::setConversation,
                            () -> directMessage.setConversation(existingDirectMessage.getConversation())));
            return directMessageRepository.save(directMessage);
        }).orElseThrow(() -> new RuntimeException("Direct Message not found"));
    }

    @Override
    public List<DirectMessage> findAll() {
        return StreamSupport.stream(directMessageRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DirectMessage> findOne(String id) {
        return directMessageRepository.findById(id);
    }

    @Override
    public boolean isExist(String id) {
        return directMessageRepository.existsById(id);
    }

    @Override
    public DirectMessage patialUpdate(String id, DirectMessage directMessage) {
        directMessage.setId(id);

        return directMessageRepository.findById(id).map(existingDirectMessage -> {
            Optional.ofNullable(directMessage.getContent()).ifPresent(existingDirectMessage::setContent);

            Optional.ofNullable(directMessage.getFileUrl()).ifPresent(existingDirectMessage::setFileUrl);

            Optional.ofNullable(directMessage.getDeleted()).ifPresent(existingDirectMessage::setDeleted);

            Optional.ofNullable(directMessage.getMember())
                    .ifPresent(member -> memberRepository.findById(member.getId()).ifPresent(
                            existingDirectMessage::setMember));

            Optional.ofNullable(directMessage.getConversation())
                    .ifPresent(conversation -> conversationRepository.findById(conversation.getId()).ifPresent(
                            existingDirectMessage::setConversation));

            return directMessageRepository.save(existingDirectMessage);
        }).orElseThrow(() -> new RuntimeException("Direct Message not found"));
    }

    @Override
    public void delete(String id) {
        directMessageRepository.deleteById(id);
    }

    @Override
    public List<DirectMessage> findDirectMessagesByConversationId(String id) {
        return StreamSupport.stream(directMessageRepository.findDirectMessageByConversationId(id).spliterator(),
                false)
                .collect(Collectors.toList());

    }
}
