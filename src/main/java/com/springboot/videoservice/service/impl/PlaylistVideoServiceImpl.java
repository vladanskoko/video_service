package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.model.Video;
import com.springboot.videoservice.repository.PlaylistVideoRepository;
import com.springboot.videoservice.service.PlaylistService;
import com.springboot.videoservice.service.PlaylistVideoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistVideoServiceImpl implements PlaylistVideoService {
    private PlaylistVideoRepository playlistVideoRepository;
    private PlaylistService playlistService;

    public PlaylistVideoServiceImpl(PlaylistVideoRepository playlistVideoRepository, PlaylistService playlistService) {
        this.playlistVideoRepository = playlistVideoRepository;
        this.playlistService = playlistService;
    }

    @Override
    public List<PlaylistVideo> sortPlaylistVideos(Playlist playlist) {
        return null;
    }

    @Override
    public PlaylistVideo addVideoToPlaylist(Long playlistId, Video video) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        PlaylistVideo playlistVideo = new PlaylistVideo(playlist, video, playlist.getPlaylistVideos().size() + 1);
        return playlistVideoRepository.save(playlistVideo);
    }

    @Override
    public List<PlaylistVideo> removeVideoFromPlaylist(Long playlistId, Video video) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        return null;
    }

    @Override
    public List<PlaylistVideo> changeOrderOfVideosInPlaylist(Long playlistId, Long videoId, Long orderNum) {
        return null;
    }
}
