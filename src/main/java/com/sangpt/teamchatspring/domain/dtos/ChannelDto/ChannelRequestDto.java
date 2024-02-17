package com.sangpt.teamchatspring.domain.dtos.ChannelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelRequestDto {
    private String id;

    private String name;

    private String type;

    private String profileId;

    private String serverId;

}
