package com.sangpt.teamchatspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.sangpt.teamchatspring.domain.entities.Conversation;
import com.sangpt.teamchatspring.repositories.ConversationRepository;
import com.sangpt.teamchatspring.repositories.MemberRepository;
import com.sangpt.teamchatspring.services.ConversationService;

@Service
public class ConversationServiceImpl implements ConversationService {

    private ConversationRepository conversationRepository;
    private MemberRepository memberRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository, MemberRepository memberRepository) {
        this.conversationRepository = conversationRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Conversation create(Conversation conversation) {

        Optional.ofNullable(conversation.getMemberOne())
                .ifPresent(memberOne -> memberRepository.findById(memberOne.getId()).ifPresentOrElse(
                        conversation::setMemberOne,
                        () -> conversation.setMemberOne(null)));

        Optional.ofNullable(conversation.getMemberTwo())
                .ifPresent(memberTwo -> memberRepository.findById(memberTwo.getId()).ifPresentOrElse(
                        conversation::setMemberTwo,
                        () -> conversation.setMemberTwo(null)));

        return conversationRepository.save(conversation);
    }

    @Override
    public Conversation update(String id, Conversation conversation) {

        return conversationRepository.findById(id).map(existingConversation -> {
            Optional.ofNullable(conversation.getMemberOne())
                    .ifPresent(memberOne -> memberRepository.findById(memberOne.getId()).ifPresentOrElse(
                            conversation::setMemberOne,
                            () -> conversation.setMemberOne(existingConversation.getMemberOne())));

            Optional.ofNullable(conversation.getMemberTwo())
                    .ifPresent(memberTwo -> memberRepository.findById(memberTwo.getId()).ifPresentOrElse(
                            conversation::setMemberTwo,
                            () -> conversation.setMemberTwo(existingConversation.getMemberTwo())));

            return conversationRepository.save(conversation);
        }).orElseThrow(() -> new RuntimeException("Conversation not found"));
    }

    @Override
    public List<Conversation> findAll() {
        return StreamSupport.stream(conversationRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Conversation> findOne(String id) {
        return conversationRepository.findById(id);
    }

    @Override
    public boolean isExist(String id) {
        return conversationRepository.existsById(id);
    }

    @Override
    public Conversation patialUpdate(String id, Conversation conversation) {
        conversation.setId(id);

        return conversationRepository.findById(id).map(existingConversation -> {

            Optional.ofNullable(conversation.getMemberOne())
                    .ifPresent(memberOne -> memberRepository.findById(memberOne.getId()).ifPresent(
                            existingConversation::setMemberOne));

            Optional.ofNullable(conversation.getMemberTwo())
                    .ifPresent(memberTwo -> memberRepository.findById(memberTwo.getId()).ifPresent(
                            existingConversation::setMemberTwo));

            return conversationRepository.save(existingConversation);
        }).orElseThrow(() -> new RuntimeException("Conversation not found"));
    }

    @Override
    public void delete(String id) {
        conversationRepository.deleteById(id);
    }

    @Override
    public Conversation findConversationByMember(String memberOneId, String memberTwoId) {
        List<Conversation> conversations = findAll();

        return conversations.stream().filter(conversation -> {
            return (conversation.getMemberOne().getId().equals(memberOneId)
                    && conversation.getMemberTwo().getId().equals(memberTwoId))
                    || (conversation.getMemberOne().getId().equals(memberTwoId)
                            && conversation.getMemberTwo().getId().equals(memberOneId));
        }).findFirst().orElse(null);
    }

}
