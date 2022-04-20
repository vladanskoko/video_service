package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.exception.ResourceNotFoundException;
import com.springboot.videoservice.model.Video;
import com.springboot.videoservice.repository.VideoRepository;
import com.springboot.videoservice.service.VideoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public Video saveVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public Video getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "Id", id));
        return video;
    }

    @Override
    public Video updateVideo(Video video, Long id) {
        Video existingVideo = getVideoById(id);
        existingVideo.setName(video.getName());
        return videoRepository.save(existingVideo);
    }

    @Override
    public void deleteVideo(Long id) {
        getVideoById(id);
        videoRepository.deleteById(id);
    }
}
