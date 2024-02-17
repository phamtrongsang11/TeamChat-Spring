package com.sangpt.teamchatspring.services;

import java.util.List;
import java.util.Optional;

import com.sangpt.teamchatspring.domain.entities.Profile;

public interface ProfileService {
    Profile createUpdate(Profile profile);

    List<Profile> findAll();

    Optional<Profile> findOne(String id);

    boolean isExist(String id);

    Profile patialUpdate(String id, Profile profile);

    void delete(String id);
}
