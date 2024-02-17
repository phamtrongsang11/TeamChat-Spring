package com.sangpt.teamchatspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.sangpt.teamchatspring.domain.entities.Profile;
import com.sangpt.teamchatspring.repositories.ProfileRepository;
import com.sangpt.teamchatspring.services.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

    private ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile createUpdate(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public List<Profile> findAll() {
        return StreamSupport.stream(profileRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Profile> findOne(String id) {
        return profileRepository.findById(id);
    }

    @Override
    public boolean isExist(String id) {
        return profileRepository.existsById(id);
    }

    @Override
    public Profile patialUpdate(String id, Profile profile) {
        profile.setId(id);

        return profileRepository.findById(id).map(existingProfile -> {
            Optional.ofNullable(profile.getName()).ifPresent(existingProfile::setName);

            Optional.ofNullable(profile.getEmail()).ifPresent(existingProfile::setEmail);

            Optional.ofNullable(profile.getImageUrl()).ifPresent(existingProfile::setImageUrl);

            return profileRepository.save(existingProfile);
        }).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Override
    public void delete(String id) {
        profileRepository.deleteById(id);
    }

}
