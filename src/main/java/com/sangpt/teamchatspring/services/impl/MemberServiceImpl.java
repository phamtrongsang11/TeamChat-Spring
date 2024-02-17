package com.sangpt.teamchatspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.sangpt.teamchatspring.domain.entities.Member;
import com.sangpt.teamchatspring.repositories.MemberRepository;
import com.sangpt.teamchatspring.repositories.ProfileRepository;
import com.sangpt.teamchatspring.repositories.ServerRepository;
import com.sangpt.teamchatspring.services.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;
    private ServerRepository serverRepository;
    private ProfileRepository profileRepository;

    public MemberServiceImpl(MemberRepository memberRepository, ServerRepository serverRepository,
            ProfileRepository profileRepository) {
        this.memberRepository = memberRepository;
        this.serverRepository = serverRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public Member create(Member member) {

        Optional.ofNullable(member.getServer()).ifPresent(
                server -> serverRepository.findById(server.getId()).ifPresentOrElse(member::setServer,
                        () -> member.setServer(null)));

        Optional.ofNullable(member.getProfile()).ifPresent(
                profile -> profileRepository.findById(profile.getId()).ifPresentOrElse(member::setProfile,
                        () -> member.setProfile(null)));

        return memberRepository.save(member);
    }

    @Override
    public Member update(String id, Member member) {

        return memberRepository.findById(id).map(existingMember -> {
            Optional.ofNullable(member.getServer())
                    .ifPresent(server -> serverRepository.findById(server.getId()).ifPresentOrElse(member::setServer,
                            () -> member.setServer(existingMember.getServer())));

            Optional.ofNullable(member.getProfile())
                    .ifPresent(
                            profile -> profileRepository.findById(profile.getId()).ifPresentOrElse(member::setProfile,
                                    () -> member.setProfile(existingMember.getProfile())));
            return memberRepository.save(member);
        }).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @Override
    public List<Member> findAll() {
        return StreamSupport.stream(memberRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Member> findOne(String id) {
        return memberRepository.findById(id);
    }

    @Override
    public boolean isExist(String id) {
        return memberRepository.existsById(id);
    }

    @Override
    public Member patialUpdate(String id, Member member) {
        member.setId(id);

        return memberRepository.findById(id).map(existingMember -> {
            Optional.ofNullable(member.getRole()).ifPresent(existingMember::setRole);

            Optional.ofNullable(member.getServer())
                    .ifPresent(server -> serverRepository.findById(server.getId()).ifPresent(
                            existingMember::setServer));

            Optional.ofNullable(member.getProfile())
                    .ifPresent(profile -> profileRepository.findById(profile.getId()).ifPresent(
                            existingMember::setProfile));

            return memberRepository.save(existingMember);
        }).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @Override
    public void delete(String id) {
        memberRepository.deleteById(id);
    }

    @Override
    public void deleteMemberByProfileId(String id) {
        memberRepository.deleteMemberByProfileId(id);
    }

    @Override
    public Optional<Member> findByServerAndProfile(String serverId, String profileId) {
        return memberRepository.getMemberByServerAndProfile(profileId, serverId);
    }
}
