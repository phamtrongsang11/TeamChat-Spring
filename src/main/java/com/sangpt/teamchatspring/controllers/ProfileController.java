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
import org.springframework.web.bind.annotation.RestController;

import com.sangpt.teamchatspring.domain.dtos.ProfileDto.ProfileRequestDto;
import com.sangpt.teamchatspring.domain.dtos.ProfileDto.ProfileResponseDto;
import com.sangpt.teamchatspring.domain.entities.Profile;
import com.sangpt.teamchatspring.mappers.Mapper;
import com.sangpt.teamchatspring.services.ProfileService;

@RestController
@RequestMapping(path = "/api/profiles")
public class ProfileController {
    private Mapper<Profile, ProfileRequestDto, ProfileResponseDto> profileMapper;
    private ProfileService profileService;

    public ProfileController(Mapper<Profile, ProfileRequestDto, ProfileResponseDto> profileMapper,
            ProfileService profileService) {
        this.profileMapper = profileMapper;
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDto> createProfile(@RequestBody ProfileRequestDto profileRequest) {
        Profile profile = profileMapper.requestToEntity(profileRequest);

        Profile savedProfile = profileService.createUpdate(profile);

        return new ResponseEntity<>(profileMapper.entityToResponse(savedProfile), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProfileResponseDto> listProfiles() {
        List<Profile> profiles = profileService.findAll();

        return profiles.stream().map(profileMapper::entityToResponse).collect(Collectors.toList());

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable("id") String id) {
        Optional<Profile> foundProfile = profileService.findOne(id);

        return foundProfile.map(profile -> {
            ProfileResponseDto profileDto = profileMapper.entityToResponse(profile);
            return new ResponseEntity<>(profileDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProfileResponseDto> fullUpdateProfile(@PathVariable("id") String id,
            @RequestBody ProfileRequestDto profileRequest) {
        if (!profileService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        profileRequest.setId(id);
        Profile profile = profileMapper.requestToEntity(profileRequest);

        Profile updatedProfile = profileService.createUpdate(profile);

        return new ResponseEntity<>(profileMapper.entityToResponse(updatedProfile), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ProfileResponseDto> partialUpdate(@PathVariable("id") String id,
            @RequestBody ProfileRequestDto profileRequest) {
        if (!profileService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Profile profile = profileMapper.requestToEntity(profileRequest);

        Profile updatedProfile = profileService.patialUpdate(id, profile);

        return new ResponseEntity<>(profileMapper.entityToResponse(updatedProfile), HttpStatus.OK);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletePrfile(@PathVariable("id") String id) {
        profileService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
