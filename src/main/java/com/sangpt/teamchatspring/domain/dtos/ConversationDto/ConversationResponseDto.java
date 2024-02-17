package com.sangpt.teamchatspring.domain.dtos.ConversationDto;

import java.time.LocalDateTime;
import java.util.List;

import com.sangpt.teamchatspring.domain.entities.DirectMessage;
import com.sangpt.teamchatspring.domain.entities.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationResponseDto {
    private String id;

    private Member MemberOne;

    private Member MemberTwo;

    private List<DirectMessage> directMessages;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
