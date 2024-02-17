package com.sangpt.teamchatspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.sangpt.teamchatspring.domain.entities.Channel;
import com.sangpt.teamchatspring.domain.entities.Member;
import com.sangpt.teamchatspring.domain.entities.Profile;
import com.sangpt.teamchatspring.domain.entities.Server;
import com.sangpt.teamchatspring.domain.entities.enums.ChannelType;
import com.sangpt.teamchatspring.domain.entities.enums.MemberRole;
import com.sangpt.teamchatspring.repositories.ChannelRepository;
import com.sangpt.teamchatspring.repositories.MemberRepository;
import com.sangpt.teamchatspring.repositories.ProfileRepository;
import com.sangpt.teamchatspring.repositories.ServerRepository;
import com.sangpt.teamchatspring.services.ServerService;

@Service
public class ServerServiceImpl implements ServerService {

    private ServerRepository serverRepository;
    private ProfileRepository profileRepository;
    private MemberRepository memberRepository;
    private ChannelRepository channelRepository;

    public ServerServiceImpl(ServerRepository serverRepository, ProfileRepository profileRepository,
            MemberRepository memberRepository, ChannelRepository channelRepository) {
        this.serverRepository = serverRepository;
        this.profileRepository = profileRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public Server create(Server server) {

        Optional.ofNullable(server.getProfile())
                .ifPresent(profile -> profileRepository.findById(profile.getId()).ifPresentOrElse(foundProfile -> {
                    server.setProfile(foundProfile);
                },
                        () -> server.setProfile(null)));

        Server savedServer = serverRepository.save(server);

        if (savedServer != null) {

            Member member = Member.builder().role(MemberRole.ADMIN).profile(savedServer.getProfile())
                    .server(savedServer).build();

            memberRepository.save(member);

            Channel channel = Channel.builder().name("general").type(ChannelType.TEXT).profile(savedServer.getProfile())
                    .server(savedServer).build();

            channelRepository.save(channel);
        }

        Server foundServer = findOne(savedServer.getId()).get();

        return foundServer;
    }

    @Override
    public Server update(String id, Server server) {

        return serverRepository.findById(id).map(existingServer -> {
            Optional.ofNullable(server.getProfile()).ifPresent(
                    profile -> profileRepository.findById(profile.getId()).ifPresentOrElse(server::setProfile,
                            () -> server.setProfile(existingServer.getProfile())));
            return serverRepository.save(server);
        }).orElseThrow(() -> new RuntimeException("Server not found"));
    }

    @Override
    public List<Server> findAll() {
        return StreamSupport.stream(serverRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Server> findOne(String id) {
        return serverRepository.findById(id);
    }

    @Override
    public boolean isExist(String id) {
        return serverRepository.existsById(id);
    }

    @Override
    public Server patialUpdate(String id, Server server) {
        server.setId(id);

        return serverRepository.findById(id).map(existingServer -> {
            Optional.ofNullable(server.getName()).ifPresent(existingServer::setName);

            Optional.ofNullable(server.getImageUrl()).ifPresent(existingServer::setImageUrl);

            Optional.ofNullable(server.getInviteCode()).ifPresent(existingServer::setInviteCode);

            Optional.ofNullable(server.getProfile())
                    .ifPresent(profile -> profileRepository.findById(profile.getId()).ifPresent(
                            existingServer::setProfile));

            return serverRepository.save(existingServer);
        }).orElseThrow(() -> new RuntimeException("Server not found"));
    }

    @Override
    public void delete(String id) {
        serverRepository.deleteById(id);
    }

    @Override
    public List<Server> findServersByMemberId(String memberId) {
        List<Server> servers = findAll();

        return servers.stream().filter(server -> {
            if (server.getMembers().size() < 0)
                return false;

            return server.getMembers().stream()
                    .filter(member -> member.getProfile().getId().equals(memberId))
                    .count() > 0;

        }).collect(Collectors.toList());

    }

    @Override
    public Optional<Server> findServerByInviteCode(String inviteCode) {
        return serverRepository.findServerByInviteCode(inviteCode);
    }

    @Override
    public Optional<Server> saveMemberByInviteCode(String inviteCode, Member member) {
        Optional<Server> foundServer = findServerByInviteCode(inviteCode);

        foundServer.ifPresent(server -> {
            Optional<Profile> foundProfile = profileRepository.findById(member.getProfile().getId());
            foundProfile.ifPresent(profile -> {

                member.setServer(server);
                member.setProfile(profile);

                memberRepository.save(member);
            });
        });

        return foundServer;
    }
}
