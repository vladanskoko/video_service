package com.springboot.videoservice.service;

import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.User;

import java.util.List;

public interface PlaylistService {
    Playlist savePlaylist(Playlist playlist);
    List<Playlist> getAllPlaylists();
    Playlist getPlaylistById(Long id);
    Playlist updatePlaylist(Playlist playlist, Long id);
    void deletePlaylist(Long id);
}
