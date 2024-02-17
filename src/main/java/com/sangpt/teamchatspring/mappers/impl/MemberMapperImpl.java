package com.sangpt.teamchatspring.mappers.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.sangpt.teamchatspring.domain.dtos.MemberDto.MemberRequestDto;
import com.sangpt.teamchatspring.domain.dtos.MemberDto.MemberResponseDto;
import com.sangpt.teamchatspring.domain.entities.Member;
import com.sangpt.teamchatspring.mappers.Mapper;

@Component
public class MemberMapperImpl implements Mapper<Member, MemberRequestDto, MemberResponseDto> {
    private ModelMapper modelMapper;

    private MemberMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        PropertyMap<MemberRequestDto, Member> memberServerMap = new PropertyMap<MemberRequestDto, Member>() {
            protected void configure() {
                map().getServer().setId(source.getServerId());
            }
        };

        PropertyMap<MemberRequestDto, Member> memberProfileMap = new PropertyMap<MemberRequestDto, Member>() {
            protected void configure() {
                map().getProfile().setId(source.getProfileId());
            }
        };
        modelMapper.addMappings(memberServerMap);
        modelMapper.addMappings(memberProfileMap);
    }

    @Override
    public Member requestToEntity(MemberRequestDto memberRequest) {

        return modelMapper.map(memberRequest, Member.class);
    }

    @Override
    public MemberResponseDto entityToResponse(Member member) {

        return modelMapper.map(member, MemberResponseDto.class);
    }

}