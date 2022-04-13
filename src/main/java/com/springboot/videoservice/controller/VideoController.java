package com.springboot.videoservice.controller;

import com.springboot.videoservice.model.Video;
import com.springboot.videoservice.service.impl.VideoServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    private final VideoServiceImpl videoService;

    public VideoController(VideoServiceImpl videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public Video saveVideo(@RequestBody Video video) {
        return videoService.saveVideo(video);
    }

    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("{id}")
    public Video getVideoById(@PathVariable("id") Long id) {
        return  videoService.getVideoById(id);
    }

    @PutMapping("{id}")
    public Video updateVideo(@RequestBody Video video, @PathVariable("id") Long videoId) {
        return videoService.updateVideo(video, videoId);
    }

    @DeleteMapping("{id}")
    public void deleteVideo(@PathVariable("id") Long videoId) {
        videoService.deleteVideo(videoId);
    }
}
