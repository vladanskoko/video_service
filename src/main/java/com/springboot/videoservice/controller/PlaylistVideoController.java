package com.springboot.videoservice.controller;

import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.service.PlaylistVideoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlistvideo")
public class PlaylistVideoController {
    private final PlaylistVideoService playlistVideoService;

    public PlaylistVideoController(PlaylistVideoService playlistVideoService) {
        this.playlistVideoService = playlistVideoService;
    }

    @PostMapping("{playlistId}/{videoId}")
    public PlaylistVideo saveVideoToPlaylist(@PathVariable("playlistId") Long playlistId,
                                             @PathVariable("videoId") Long videoId ) {
        return playlistVideoService.addVideoToPlaylist(playlistId, videoId);
    }

    @DeleteMapping("{playlistId}/{videoId}")
    public void removeVideoFromPlaylist(@PathVariable("playlistId") Long playlistId,
                                        @PathVariable("videoId") Long videoId) {
        playlistVideoService.removeVideoFromPlaylist(playlistId, videoId);
    }

    @GetMapping("{playlistId}")
    public List<PlaylistVideo> sortPlaylistVideos(@PathVariable("playlistId") Long playlistId) {
        return playlistVideoService.sortPlaylistVideos(playlistId);
    }
}
