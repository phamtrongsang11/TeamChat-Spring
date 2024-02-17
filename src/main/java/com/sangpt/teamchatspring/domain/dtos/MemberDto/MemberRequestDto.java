package com.sangpt.teamchatspring.domain.dtos.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {
    private String id;

    private String role;

    private String serverId;

    private String profileId;
}
