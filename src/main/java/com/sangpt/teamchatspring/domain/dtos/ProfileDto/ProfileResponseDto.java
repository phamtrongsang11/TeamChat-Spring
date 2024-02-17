package com.sangpt.teamchatspring.domain.dtos.ProfileDto;

import java.time.LocalDateTime;
import java.util.List;

import com.sangpt.teamchatspring.domain.entities.Channel;
import com.sangpt.teamchatspring.domain.entities.Member;
import com.sangpt.teamchatspring.domain.entities.Server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponseDto {
    private String id;

    private String name;

    private String imageUrl;

    private String email;

    private List<Server> servers;

    private List<Member> members;

    private List<Channel> channels;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
