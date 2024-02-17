package com.sangpt.teamchatspring.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangpt.teamchatspring.domain.dtos.MemberDto.MemberRequestDto;
import com.sangpt.teamchatspring.domain.dtos.MemberDto.MemberResponseDto;
import com.sangpt.teamchatspring.domain.dtos.ServerDto.ServerRequestDto;
import com.sangpt.teamchatspring.domain.dtos.ServerDto.ServerResponseDto;
import com.sangpt.teamchatspring.domain.entities.Member;
import com.sangpt.teamchatspring.domain.entities.Server;
import com.sangpt.teamchatspring.mappers.Mapper;
import com.sangpt.teamchatspring.services.ServerService;

@RestController
@RequestMapping(path = "/api/servers")
public class ServerController {
    private Mapper<Server, ServerRequestDto, ServerResponseDto> serverMapper;
    private Mapper<Member, MemberRequestDto, MemberResponseDto> memberMapper;
    private ServerService serverService;

    public ServerController(Mapper<Server, ServerRequestDto, ServerResponseDto> serverMapper,
            Mapper<Member, MemberRequestDto, MemberResponseDto> memberMapper,
            ServerService serverService) {
        this.serverMapper = serverMapper;
        this.memberMapper = memberMapper;
        this.serverService = serverService;
    }

    @PostMapping
    public ResponseEntity<ServerResponseDto> createServer(@RequestBody ServerRequestDto serverRequest) {
        Server server = serverMapper.requestToEntity(serverRequest);

        Server savedServer = serverService.create(server);

        return new ResponseEntity<>(serverMapper.entityToResponse(savedServer),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServerResponseDto>> listServers() {
        List<Server> servers = serverService.findAll();
        return new ResponseEntity<>(servers.stream().map(serverMapper::entityToResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ServerResponseDto> getServer(@PathVariable("id") String id) {
        Optional<Server> foundServer = serverService.findOne(id);

        return foundServer.map(server -> {
            ServerResponseDto serverDto = serverMapper.entityToResponse(server);

            return new ResponseEntity<>(serverDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ServerResponseDto> fullUpdateServer(@PathVariable("id") String id,
            @RequestBody ServerRequestDto serverRequest) {
        if (!serverService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        serverRequest.setId(id);
        Server server = serverMapper.requestToEntity(serverRequest);
        Server updatedServer = serverService.update(id, server);

        return new ResponseEntity<>(serverMapper.entityToResponse(updatedServer),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ServerResponseDto> partialUpdateServer(@PathVariable("id") String id,
            @RequestBody ServerRequestDto serverRequest) {
        if (!serverService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Server server = serverMapper.requestToEntity(serverRequest);
        Server updatedServer = serverService.patialUpdate(id, server);

        return new ResponseEntity<>(serverMapper.entityToResponse(updatedServer),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteServer(@PathVariable("id") String id) {
        serverService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/member")
    public ResponseEntity<List<ServerResponseDto>> findServersByMemberId(@RequestParam("memberId") String memberId) {
        List<Server> servers = serverService.findServersByMemberId(memberId);
        return new ResponseEntity<>(servers.stream().map(serverMapper::entityToResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(path = "/invite")
    public ResponseEntity<ServerResponseDto> findServersByInviteCode(
            @RequestParam("inviteCode") String inviteCode) {
        Optional<Server> foundServer = serverService.findServerByInviteCode(inviteCode);

        return foundServer.map(server -> {
            ServerResponseDto serverDto = serverMapper.entityToResponse(server);

            return new ResponseEntity<>(serverDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/invite")
    public ResponseEntity<ServerResponseDto> saveMemberByInviteCode(
            @RequestParam("inviteCode") String inviteCode, @RequestBody MemberRequestDto memberRequest) {

        Member member = memberMapper.requestToEntity(memberRequest);

        Optional<Server> foundServer = serverService.saveMemberByInviteCode(inviteCode, member);

        return foundServer.map(server -> {
            ServerResponseDto serverDto = serverMapper.entityToResponse(server);

            return new ResponseEntity<>(serverDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
