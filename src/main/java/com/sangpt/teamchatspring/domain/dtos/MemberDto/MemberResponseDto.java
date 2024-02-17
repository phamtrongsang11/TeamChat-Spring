package com.sangpt.teamchatspring.domain.dtos.MemberDto;

import java.time.LocalDateTime;
import java.util.List;

import com.sangpt.teamchatspring.domain.entities.Conversation;
import com.sangpt.teamchatspring.domain.entities.DirectMessage;
import com.sangpt.teamchatspring.domain.entities.Message;
import com.sangpt.teamchatspring.domain.entities.Profile;
import com.sangpt.teamchatspring.domain.entities.Server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    private String id;

    private String role;

    private Server server;

    private Profile profile;

    private List<Message> messages;

    private List<DirectMessage> directMessages;

    private List<Conversation> conversationsInitiated;

    private List<Conversation> conversationsReceived;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
