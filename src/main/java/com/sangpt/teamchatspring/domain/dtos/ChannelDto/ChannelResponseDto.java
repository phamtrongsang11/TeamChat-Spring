package com.sangpt.teamchatspring.domain.dtos.ChannelDto;

import java.time.LocalDateTime;
import java.util.List;

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
public class ChannelResponseDto {
    private String id;

    private String name;

    private String type;

    private Profile profile;

    private Server server;

    private List<Message> messages;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
