package com.sangpt.teamchatspring.domain.dtos.MessageDto;

import java.time.LocalDateTime;

import com.sangpt.teamchatspring.domain.entities.Channel;
import com.sangpt.teamchatspring.domain.entities.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponseDto {
    private String id;

    private String content;

    private String fileUrl;

    private Boolean deleted;

    private Member member;

    private Channel channel;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
