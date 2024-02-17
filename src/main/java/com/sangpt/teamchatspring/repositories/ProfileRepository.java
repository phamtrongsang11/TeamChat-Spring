package com.sangpt.teamchatspring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sangpt.teamchatspring.domain.entities.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, String> {

}
