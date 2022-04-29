package com.springboot.videoservice.controller;

import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.service.PlaylistVideoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistVideoController {
    private final PlaylistVideoService playlistVideoService;

    public PlaylistVideoController(PlaylistVideoService playlistVideoService) {
        this.playlistVideoService = playlistVideoService;
    }

    @PostMapping("{playlistId}/video/{videoId}")
    public PlaylistVideo saveVideoToPlaylist(@PathVariable("playlistId") Long playlistId,
                                             @PathVariable("videoId") Long videoId ) {
        return playlistVideoService.addVideoToPlaylist(playlistId, videoId);
    }

    @DeleteMapping("{playlistId}/video/{videoId}")
    public void removeVideoFromPlaylist(@PathVariable("playlistId") Long playlistId,
                                        @PathVariable("videoId") Long videoId) {
        playlistVideoService.removeVideoFromPlaylist(playlistId, videoId);
    }

    @GetMapping("{playlistId}/sort")
    public List<PlaylistVideo> sortPlaylistVideos(@PathVariable("playlistId") Long playlistId) {
        return playlistVideoService.sortPlaylistVideos(playlistId);
    }

    @PutMapping("{playlistId}/video/{videoId}/order/{orderNum}")
    public List<PlaylistVideo> changeOrderOfVideosInPlaylist(@PathVariable("playlistId") Long playlistId,
                                                             @PathVariable("videoId") Long videoId,
                                                             @PathVariable("orderNum") Integer newOrderNum) {
        return playlistVideoService.changeOrderOfVideosInPlaylist(playlistId, videoId, newOrderNum);
    }
}
