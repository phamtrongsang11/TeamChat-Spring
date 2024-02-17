package com.sangpt.teamchatspring.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sangpt.teamchatspring.domain.dtos.ChannelDto.ChannelRequestDto;
import com.sangpt.teamchatspring.domain.dtos.ChannelDto.ChannelResponseDto;
import com.sangpt.teamchatspring.domain.dtos.ChannelDto.LiveKitRequest;
import com.sangpt.teamchatspring.domain.entities.Channel;
import com.sangpt.teamchatspring.mappers.Mapper;
import com.sangpt.teamchatspring.services.ChannelService;

@RestController
@RequestMapping(path = "/api/channels")
public class ChannelController {
    private Mapper<Channel, ChannelRequestDto, ChannelResponseDto> channelMapper;
    private ChannelService channelService;

    public ChannelController(Mapper<Channel, ChannelRequestDto, ChannelResponseDto> channelMapper,
            ChannelService channelService) {
        this.channelMapper = channelMapper;
        this.channelService = channelService;
    }

    @PostMapping
    public ResponseEntity<ChannelResponseDto> createChannel(@RequestBody ChannelRequestDto channelRequest) {
        Channel channel = channelMapper.requestToEntity(channelRequest);
        Channel savedChannel = channelService.create(channel);
        return new ResponseEntity<>(channelMapper.entityToResponse(savedChannel),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ChannelResponseDto>> listChannels() {
        List<Channel> channels = channelService.findAll();
        return new ResponseEntity<>(channels.stream().map(channelMapper::entityToResponse).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ChannelResponseDto> getChannel(@PathVariable("id") String id) {
        Optional<Channel> foundChannel = channelService.findOne(id);

        return foundChannel.map(channel -> {
            ChannelResponseDto channelDto = channelMapper.entityToResponse(channel);

            return new ResponseEntity<>(channelDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ChannelResponseDto> fullUpdateChannel(@PathVariable("id") String id,
            @RequestBody ChannelRequestDto channelRequest) {
        if (!channelService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        channelRequest.setId(id);
        Channel channel = channelMapper.requestToEntity(channelRequest);
        Channel updatedChannel = channelService.update(id, channel);

        return new ResponseEntity<>(channelMapper.entityToResponse(updatedChannel),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ChannelResponseDto> partialUpdateChannel(@PathVariable("id") String id,
            @RequestBody ChannelRequestDto channelRequest) {
        if (!channelService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Channel channel = channelMapper.requestToEntity(channelRequest);
        Channel updatedChannel = channelService.patialUpdate(id, channel);

        return new ResponseEntity<>(channelMapper.entityToResponse(updatedChannel),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteChannel(@PathVariable("id") String id) {
        channelService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/token")
    public ResponseEntity<String> GetTokenLiveKit(@RequestBody LiveKitRequest liveKitRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
        requestData.add("room", liveKitRequest.getRoom());
        requestData.add("user", liveKitRequest.getUser());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestData, headers);

        String apiUrl = "http://localhost:8000/api/channels/token";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
                String.class);

        return responseEntity;

    }
}
