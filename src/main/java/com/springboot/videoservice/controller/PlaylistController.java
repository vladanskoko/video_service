package com.springboot.videoservice.controller;

import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.model.Video;
import com.springboot.videoservice.service.PlaylistVideoService;
import com.springboot.videoservice.service.impl.PlaylistServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {
    private final PlaylistServiceImpl playlistService;
    private final PlaylistVideoService playlistVideoService;

    public PlaylistController(PlaylistServiceImpl playlistService, PlaylistVideoService playlistVideoService) {
        this.playlistService = playlistService;
        this.playlistVideoService = playlistVideoService;
    }

    @PostMapping
    public Playlist savePlaylist(@RequestBody Playlist playlist) {
        return playlistService.savePlaylist(playlist);
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("{id}")
    public Playlist getPlaylistById(@PathVariable("id") Long playlistId) {
        return playlistService.getPlaylistById(playlistId);
    }

    @PutMapping("{id}")
    public Playlist updatePlaylist(@RequestBody Playlist playlist, @PathVariable("id") Long playlistId) {
        return playlistService.updatePlaylist(playlist, playlistId);
    }

    @DeleteMapping("{id}")
    public void deletePlaylist(@PathVariable("id") Long playlistId) {
        playlistService.deletePlaylist(playlistId);
    }
}
