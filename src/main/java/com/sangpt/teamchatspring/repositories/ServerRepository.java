package com.sangpt.teamchatspring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sangpt.teamchatspring.domain.entities.Server;

@Repository
public interface ServerRepository extends CrudRepository<Server, String> {

    @Query(value = "SELECT * FROM Server WHERE invite_code = :inviteCode", nativeQuery = true)
    Optional<Server> findServerByInviteCode(String inviteCode);

}
