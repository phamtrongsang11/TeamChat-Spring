package com.sangpt.teamchatspring.domain.dtos.DirectMessageDto;

import java.time.LocalDateTime;

import com.sangpt.teamchatspring.domain.entities.Conversation;
import com.sangpt.teamchatspring.domain.entities.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectMessageResponseDto {
    private String id;

    private String content;

    private String fileUrl;

    private Boolean deleted;

    private Member member;

    private Conversation conversation;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
