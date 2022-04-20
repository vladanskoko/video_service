package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.exception.ResourceNotFoundException;
import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.model.Video;
import com.springboot.videoservice.repository.PlaylistVideoRepository;
import com.springboot.videoservice.service.PlaylistService;
import com.springboot.videoservice.service.PlaylistVideoService;
import com.springboot.videoservice.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlaylistVideoServiceImplTest {
    private PlaylistVideoService playlistVideoService;
    private PlaylistService playlistServiceMock;
    private VideoService videoServiceMock;
    private PlaylistVideoRepository playlistVideoRepositoryMock;
    private Video video;
    private Playlist playlist;


    @BeforeEach
    void setUp() {
        playlistServiceMock = mock(PlaylistService.class);
        playlistVideoRepositoryMock = mock(PlaylistVideoRepository.class);
        videoServiceMock = mock(VideoService.class);
        playlistVideoService = new PlaylistVideoServiceImpl(playlistVideoRepositoryMock, playlistServiceMock, videoServiceMock);

        video = new Video();
        video.setId(1L);
        video.setName("video1");
        playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("playlist1");
    }

    @Test
    void givenEmptyPlaylistAndVideo_whenAddVideoToPlaylist_thenReturnPlaylistVideoListWithAddedVideoAtBeggining() {
        //given
        List<PlaylistVideo> testPVList = new ArrayList<>();
        PlaylistVideo playlistVideo = new PlaylistVideo(playlist, video, 1);
        PlaylistVideo playlistVideoFromDB = new PlaylistVideo();

        when(playlistServiceMock.getPlaylistById(eq(1L))).thenReturn(playlist);
        when(videoServiceMock.getVideoById(eq(1L))).thenReturn(video);
        when(playlistVideoRepositoryMock.getPlaylistVideosByPlaylist(eq(playlist))).thenReturn(testPVList);
        when(playlistVideoRepositoryMock.save(eq(playlistVideo))).thenReturn(playlistVideo);

        //when
        PlaylistVideo actualPlaylistVideo = playlistVideoService.addVideoToPlaylist(1L, 1L);

        //then
        verifyAll();
        assertEquals(playlistVideo, actualPlaylistVideo);
    }

    @Test
    void givenPlaylistAndVideo_whenAddVideoToPlaylist_thenReturnPlaylistVideoListWithAddedVideoToTheEnd() {
        //given
        List<PlaylistVideo> testPVList = new ArrayList<>(List.of(new PlaylistVideo()));
        PlaylistVideo playlistVideo = new PlaylistVideo(playlist, video, 2);

        when(playlistServiceMock.getPlaylistById(eq(1L))).thenReturn(playlist);
        when(videoServiceMock.getVideoById(eq(1L))).thenReturn(video);
        when(playlistVideoRepositoryMock.getPlaylistVideosByPlaylist(eq(playlist))).thenReturn(testPVList);
        when(playlistVideoRepositoryMock.save(eq(playlistVideo))).thenReturn(playlistVideo);

        //when
        PlaylistVideo actualPlaylistVideo = playlistVideoService.addVideoToPlaylist(1L, 1L);

        //then
        verifyAll();
        assertEquals(playlistVideo, actualPlaylistVideo);
    }

    @Test
    void givenUnexistentPlaylistAndVideo_whenAddVideoToUnexistentPlaylist_thenThrowException() {
        //given
        when(playlistServiceMock.getPlaylistById(eq(222L))).thenThrow(ResourceNotFoundException.class);

        //when
        Executable executable = () -> playlistVideoService.addVideoToPlaylist(222L, 1L);

        //then
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void givenUnexistentVideoAndPlaylist_whenAddUnexistenVideoToPlaylist_thenThrowException() {
        //given
        when(videoServiceMock.getVideoById(eq(222L))).thenThrow(ResourceNotFoundException.class);

        //when
        Executable executable = () -> playlistVideoService.addVideoToPlaylist(1L, 222L);

        //then
        assertThrows(ResourceNotFoundException.class, executable);
    }

    private void verifyAll() {
        verify(playlistServiceMock, times(1)).getPlaylistById(anyLong());
        verify(videoServiceMock, times(1)).getVideoById(anyLong());
        verify(playlistVideoRepositoryMock, times(1)).getPlaylistVideosByPlaylist(any());
        verify(playlistVideoRepositoryMock, times(1)).save(any());
    }
}