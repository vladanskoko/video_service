package com.springboot.videoservice.service;

import com.springboot.videoservice.model.Video;

import java.util.List;

public interface VideoService {
    Video saveVideo(Video video);
    List<Video> getAllVideos();
    Video getVideoById(Long id);
    Video updateVideo(Video video, Long id);
    void deleteVideo(Long id);
}
