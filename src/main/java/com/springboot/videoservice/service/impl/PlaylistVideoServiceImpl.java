package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.exception.ResourceNotFoundException;
import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.model.Video;
import com.springboot.videoservice.repository.PlaylistRepository;
import com.springboot.videoservice.repository.PlaylistVideoRepository;
import com.springboot.videoservice.service.PlaylistVideoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistVideoServiceImpl implements PlaylistVideoService {
    private PlaylistVideoRepository playlistVideoRepository;
    private PlaylistRepository playlistRepository;

    public PlaylistVideoServiceImpl(PlaylistVideoRepository playlistVideoRepository, PlaylistRepository playlistRepository) {
        this.playlistVideoRepository = playlistVideoRepository;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<PlaylistVideo> sortPlaylistVideos(Playlist playlist) {
        return null;
    }

    @Override
    public PlaylistVideo addVideoToPlaylist(Long playlistId, Video video) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "Id", playlistId));
        PlaylistVideo playlistVideo = new PlaylistVideo(playlist, video, playlist.getPlaylistVideos().size() + 1);
        return playlistVideoRepository.save(playlistVideo);
    }

    @Override
    public List<PlaylistVideo> removeVideoFromPlaylist(Long playlistId, Video video) {
        return null;
    }

    @Override
    public List<PlaylistVideo> changeOrderOfVideosInPlaylist(Long playlistId, Long videoId, Long orderNum) {
        return null;
    }
}
