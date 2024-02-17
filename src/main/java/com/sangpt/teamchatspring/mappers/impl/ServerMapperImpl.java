package com.sangpt.teamchatspring.mappers.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.sangpt.teamchatspring.domain.dtos.ServerDto.ServerRequestDto;
import com.sangpt.teamchatspring.domain.dtos.ServerDto.ServerResponseDto;
import com.sangpt.teamchatspring.domain.entities.Server;
import com.sangpt.teamchatspring.mappers.Mapper;

@Component
public class ServerMapperImpl implements Mapper<Server, ServerRequestDto, ServerResponseDto> {
    private ModelMapper modelMapper;

    private ServerMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        PropertyMap<ServerRequestDto, Server> serverMap = new PropertyMap<ServerRequestDto, Server>() {
            protected void configure() {
                map().getProfile().setId(source.getProfileId());
            }
        };
        modelMapper.addMappings(serverMap);
    }

    @Override
    public Server requestToEntity(ServerRequestDto serverRequest) {

        return modelMapper.map(serverRequest, Server.class);
    }

    @Override
    public ServerResponseDto entityToResponse(Server server) {

        return modelMapper.map(server, ServerResponseDto.class);
    }

}