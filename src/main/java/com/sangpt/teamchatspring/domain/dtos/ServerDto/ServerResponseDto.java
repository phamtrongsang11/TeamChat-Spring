package com.sangpt.teamchatspring.domain.dtos.ServerDto;

import java.time.LocalDateTime;
import java.util.List;

import com.sangpt.teamchatspring.domain.entities.Channel;
import com.sangpt.teamchatspring.domain.entities.Member;
import com.sangpt.teamchatspring.domain.entities.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerResponseDto {
    private String id;

    private String name;

    private String imageUrl;

    private String inviteCode;

    private Profile profile;

    private List<Member> members;

    private List<Channel> channels;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
