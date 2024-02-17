package com.sangpt.teamchatspring.domain.dtos.ProfileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequestDto {
    private String id;
    private String email;
    private String imageUrl;
    private String name;

}
