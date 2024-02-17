package com.sangpt.teamchatspring.domain.dtos.ConversationDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationRequestDto {
    private String id;

    private String memberOneId;

    private String memberTwoId;

}
