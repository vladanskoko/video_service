package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.exception.ResourceNotFoundException;
import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.repository.PlaylistRepository;
import com.springboot.videoservice.service.PlaylistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public Playlist savePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", "Id", id));
        return playlist;
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist, Long id) {
        Playlist existingPlaylist = getPlaylistById(id);
        existingPlaylist.setName(playlist.getName());
        return playlistRepository.save(existingPlaylist);
    }

    @Override
    public void deletePlaylist(Long id) {
        getPlaylistById(id);
        playlistRepository.deleteById(id);
    }
}
