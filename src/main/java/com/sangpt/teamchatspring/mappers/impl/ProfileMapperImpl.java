package com.sangpt.teamchatspring.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.sangpt.teamchatspring.domain.dtos.ProfileDto.ProfileRequestDto;
import com.sangpt.teamchatspring.domain.dtos.ProfileDto.ProfileResponseDto;
import com.sangpt.teamchatspring.domain.entities.Profile;
import com.sangpt.teamchatspring.mappers.Mapper;

@Component
public class ProfileMapperImpl implements Mapper<Profile, ProfileRequestDto, ProfileResponseDto> {
    private ModelMapper modelMapper;

    public ProfileMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Profile requestToEntity(ProfileRequestDto profileRequest) {
        return modelMapper.map(profileRequest, Profile.class);
    }

    @Override
    public ProfileResponseDto entityToResponse(Profile profile) {
        return modelMapper.map(profile, ProfileResponseDto.class);
    }

}