package com.hanghae.video.controller;

import com.hanghae.video.dto.VideoPlayRequestDto;
import com.hanghae.video.dto.VideoRegistrationRequestDto;
import com.hanghae.video.dto.VideoResponseDto;
import com.hanghae.video.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoServiceImpl;

    public VideoController(VideoService videoServiceImpl) {
        this.videoServiceImpl = videoServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerVideo(@RequestBody VideoRegistrationRequestDto request) {
        videoServiceImpl.registerVideo(request);
        return ResponseEntity.ok("Video registered successfully");
    }

    @PostMapping("/play")
    public ResponseEntity<?> playVideo(@RequestBody VideoPlayRequestDto request) {
        videoServiceImpl.playVideo(request);
        return ResponseEntity.ok("Playback started");
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDto> getVideoById(@PathVariable Long id) {
        VideoResponseDto video = videoServiceImpl.getVideoById(id);
        return ResponseEntity.ok(video);
    }

    @GetMapping
    public ResponseEntity<List<VideoResponseDto>> getAllVideos() {
        List<VideoResponseDto> videos = videoServiceImpl.getAllVideos();
        return ResponseEntity.ok(videos);
    }
}

