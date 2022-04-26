package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.model.Video;
import com.springboot.videoservice.repository.PlaylistVideoRepository;
import com.springboot.videoservice.service.PlaylistService;
import com.springboot.videoservice.service.PlaylistVideoService;
import com.springboot.videoservice.service.VideoService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PlaylistVideoServiceImpl implements PlaylistVideoService {
    private final PlaylistVideoRepository playlistVideoRepository;
    private final PlaylistService playlistService;
    private final VideoService videoService;

    public PlaylistVideoServiceImpl(PlaylistVideoRepository playlistVideoRepository, PlaylistService playlistService, VideoService videoService) {
        this.playlistVideoRepository = playlistVideoRepository;
        this.playlistService = playlistService;
        this.videoService = videoService;
    }

    @Override
    public List<PlaylistVideo> sortPlaylistVideos(Long playlistId) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        List<PlaylistVideo> playlistVideos = playlistVideoRepository.getPlaylistVideosByPlaylist(playlist);
        playlistVideos.sort(Comparator.comparingInt(PlaylistVideo::getOrderNumber));
        return playlistVideos;
    }

    @Override
    public PlaylistVideo addVideoToPlaylist(Long playlistId, Long videoId) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        Video video = videoService.getVideoById(videoId);
        List<PlaylistVideo> playlistVideos = playlistVideoRepository.getPlaylistVideosByPlaylist(playlist);
        PlaylistVideo playlistVideo = new PlaylistVideo(playlist, video, playlistVideos.size() + 1);
        return playlistVideoRepository.save(playlistVideo);
    }

    @Override
    public void removeVideoFromPlaylist(Long playlistId, Long videoId) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        Video video = videoService.getVideoById(videoId);
        List<PlaylistVideo> playlistVideos = playlistVideoRepository.getPlaylistVideosByPlaylist(playlist);
        PlaylistVideo playlistVideo = playlistVideoRepository.getPlaylistVideoByPlaylistAndVideo(playlist, video);
        Integer index = playlistVideo.getOrderNumber();
        playlistVideoRepository.delete(playlistVideo);

        for(PlaylistVideo pv: playlistVideos) {
            if(pv.getOrderNumber() > index) {
                pv.setOrderNumber(pv.getOrderNumber() - 1);
                playlistVideoRepository.save(pv);
            }
        }
    }

    @Override
    public List<PlaylistVideo> changeOrderOfVideosInPlaylist(Long playlistId, Long videoId, Long orderNum) {
        return null;
    }
}
