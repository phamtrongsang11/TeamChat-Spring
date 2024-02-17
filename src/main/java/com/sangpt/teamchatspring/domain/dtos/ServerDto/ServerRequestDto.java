package com.sangpt.teamchatspring.domain.dtos.ServerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerRequestDto {
    private String id;

    private String name;

    private String imageUrl;

    private String inviteCode;

    private String profileId;

}
