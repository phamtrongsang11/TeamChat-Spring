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
import com.sangpt.teamchatspring.domain.entities.Member;
import com.sangpt.teamchatspring.mappers.Mapper;
import com.sangpt.teamchatspring.services.MemberService;

@RestController
@RequestMapping(path = "/api/members")
public class MemberController {
    private Mapper<Member, MemberRequestDto, MemberResponseDto> memberMapper;
    private MemberService memberService;

    public MemberController(Mapper<Member, MemberRequestDto, MemberResponseDto> memberMapper,
            MemberService memberService) {
        this.memberMapper = memberMapper;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto memberRequest) {
        Member member = memberMapper.requestToEntity(memberRequest);
        Member savedMember = memberService.create(member);
        return new ResponseEntity<>(memberMapper.entityToResponse(savedMember),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> listMembers() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members.stream().map(memberMapper::entityToResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable("id") String id) {
        Optional<Member> foundMember = memberService.findOne(id);

        return foundMember.map(member -> {
            MemberResponseDto memberDto = memberMapper.entityToResponse(member);

            return new ResponseEntity<>(memberDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MemberResponseDto> fullUpdateMember(@PathVariable("id") String id,
            @RequestBody MemberRequestDto memberRequest) {
        if (!memberService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        memberRequest.setId(id);
        Member member = memberMapper.requestToEntity(memberRequest);
        Member updatedMember = memberService.update(id, member);

        return new ResponseEntity<>(memberMapper.entityToResponse(updatedMember),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<MemberResponseDto> partialUpdateMember(@PathVariable("id") String id,
            @RequestBody MemberRequestDto memberRequest) {
        if (!memberService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Member member = memberMapper.requestToEntity(memberRequest);
        Member updatedMember = memberService.patialUpdate(id, member);

        return new ResponseEntity<>(memberMapper.entityToResponse(updatedMember),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteMember(@PathVariable("id") String id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/profile")
    public ResponseEntity deleteMemberByProfileId(@RequestParam("profileId") String profileId) {
        memberService.deleteMemberByProfileId(profileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "find")
    public ResponseEntity<MemberResponseDto> findByServerAndProfile(@RequestParam("serverId") String serverId,
            @RequestParam("profileId") String profileId) {
        Optional<Member> foundMember = memberService.findByServerAndProfile(serverId, profileId);

        return foundMember.map(member -> {
            MemberResponseDto memberDto = memberMapper.entityToResponse(member);

            return new ResponseEntity<>(memberDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
