package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.model.Video;
import com.springboot.videoservice.repository.PlaylistVideoRepository;
import com.springboot.videoservice.service.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistVideoServiceImplTest {
    private PlaylistVideoServiceImpl playlistVideoService;
    private PlaylistService playlistServiceMock;
    private PlaylistVideoRepository playlistVideoRepositoryMock;

    @BeforeEach
    void setUp() {
        playlistServiceMock = Mockito.mock(PlaylistService.class);
        playlistVideoRepositoryMock = Mockito.mock(PlaylistVideoRepository.class);
        playlistVideoService = new PlaylistVideoServiceImpl(playlistVideoRepositoryMock, playlistServiceMock);
    }

    @Test
    void sortPlaylistVideos() {
    }

    @Test
    void addVideoToPlaylist_Test() {
        //given
        Video testVideo = new Video("v1");
        Playlist testPlaylist = new Playlist(1L, "p1");
        int testSize = 1;
        Playlist playlistMock = Mockito.mock(Playlist.class);
        List<PlaylistVideo> testPVList = new ArrayList<PlaylistVideo>(List.of(new PlaylistVideo()));
        PlaylistVideo pvMock = Mockito.mock(PlaylistVideo.class);

        Mockito.when(playlistServiceMock.getPlaylistById(1L)).thenReturn(testPlaylist);
        Mockito.when(playlistMock.getPlaylistVideos()).thenReturn(testPVList);
        Mockito.when(playlistMock.getPlaylistVideos().size()).thenReturn(testSize);
        PlaylistVideo expectedPlaylistVideo = new PlaylistVideo(testPlaylist, testVideo, 1);
        Mockito.when(playlistVideoRepositoryMock.save(new PlaylistVideo(testPlaylist, testVideo,2)))
                .thenReturn(expectedPlaylistVideo);

        //when
        PlaylistVideo actualPlaylistVideo = playlistVideoService.addVideoToPlaylist(1L, testVideo);

        //then
        assertEquals(expectedPlaylistVideo, actualPlaylistVideo);
    }

    @Test
    void removeVideoFromPlaylist() {
    }

    @Test
    void changeOrderOfVideosInPlaylist() {
    }
}