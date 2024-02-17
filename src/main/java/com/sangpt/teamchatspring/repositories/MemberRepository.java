package com.sangpt.teamchatspring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sangpt.teamchatspring.domain.entities.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, String> {

    @Query(value = "DELETE FROM Member where profile_id = :profileId", nativeQuery = true)
    Iterable<Member> deleteMemberByProfileId(String profileId);

    @Query(value = "SELECT * from Member WHERE profile_id = :profileId AND server_id = :serverId", nativeQuery = true)
    Optional<Member> getMemberByServerAndProfile(String profileId, String serverId);

}
