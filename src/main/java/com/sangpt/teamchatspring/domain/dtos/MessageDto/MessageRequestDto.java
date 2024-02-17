package com.sangpt.teamchatspring.domain.dtos.MessageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequestDto {
    private String id;

    private String content;

    private String fileUrl;

    private Boolean deleted;

    private String memberId;

    private String channelId;

}
