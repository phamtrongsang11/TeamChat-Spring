package com.sangpt.teamchatspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.sangpt.teamchatspring.domain.entities.Message;
import com.sangpt.teamchatspring.repositories.ChannelRepository;
import com.sangpt.teamchatspring.repositories.MemberRepository;
import com.sangpt.teamchatspring.repositories.MessageRepository;
import com.sangpt.teamchatspring.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;
    private MemberRepository memberRepository;
    private ChannelRepository channelRepository;

    public MessageServiceImpl(MessageRepository messageRepository, MemberRepository memberRepository,
            ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.memberRepository = memberRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public Message create(Message message) {

        Optional.ofNullable(message.getMember())
                .ifPresent(member -> memberRepository.findById(member.getId()).ifPresentOrElse(message::setMember,
                        () -> message.setMember(null)));

        Optional.ofNullable(message.getChannel()).ifPresent(
                channel -> channelRepository.findById(channel.getId()).ifPresentOrElse(message::setChannel,
                        () -> message.setChannel(null)));

        return messageRepository.save(message);
    }

    @Override
    public Message update(String id, Message message) {

        return messageRepository.findById(id).map(existingMessage -> {
            Optional.ofNullable(message.getMember())
                    .ifPresent(member -> memberRepository.findById(member.getId()).ifPresentOrElse(message::setMember,
                            () -> message.setMember(existingMessage.getMember())));

            Optional.ofNullable(message.getChannel())
                    .ifPresent(
                            channel -> channelRepository.findById(channel.getId()).ifPresentOrElse(message::setChannel,
                                    () -> message.setChannel(existingMessage.getChannel())));
            return messageRepository.save(message);
        }).orElseThrow(() -> new RuntimeException("Message not found"));
    }

    @Override
    public List<Message> findAll() {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Message> findOne(String id) {
        return messageRepository.findById(id);
    }

    @Override
    public boolean isExist(String id) {
        return messageRepository.existsById(id);
    }

    @Override
    public Message patialUpdate(String id, Message message) {
        message.setId(id);

        return messageRepository.findById(id).map(existingMessage -> {
            Optional.ofNullable(message.getContent()).ifPresent(existingMessage::setContent);

            Optional.ofNullable(message.getFileUrl()).ifPresent(existingMessage::setFileUrl);

            Optional.ofNullable(message.getDeleted()).ifPresent(existingMessage::setDeleted);

            Optional.ofNullable(message.getMember())
                    .ifPresent(member -> memberRepository.findById(member.getId()).ifPresent(
                            existingMessage::setMember));

            Optional.ofNullable(message.getChannel())
                    .ifPresent(channel -> channelRepository.findById(channel.getId()).ifPresent(
                            existingMessage::setChannel));

            return messageRepository.save(existingMessage);
        }).orElseThrow(() -> new RuntimeException("Message not found"));
    }

    @Override
    public void delete(String id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> findMessagesByChannelId(String channelId) {
        return StreamSupport.stream(messageRepository.findMessageByChannelId(channelId).spliterator(), false)
                .collect(Collectors.toList());
    }
}
