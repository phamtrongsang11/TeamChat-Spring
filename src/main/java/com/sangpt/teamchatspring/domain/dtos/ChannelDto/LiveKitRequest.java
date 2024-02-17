package com.sangpt.teamchatspring.domain.dtos.ChannelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveKitRequest {
    private String room;
    private String user;
}
