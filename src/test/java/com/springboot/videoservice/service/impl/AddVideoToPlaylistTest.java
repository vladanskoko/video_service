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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AddVideoToPlaylistTest {
    private PlaylistVideoService playlistVideoService;

    @Mock
    private PlaylistVideoRepository playlistVideoRepositoryMock;

    @Mock
    private PlaylistService playlistServiceMock;

    @Mock
    private VideoService videoServiceMock;

    private Video video1;
    private Playlist playlist;
    private List<PlaylistVideo> testPVList;
    private final Long invalidPlaylistId = 111L;
    private final Long invalidVideoId = 222L;

    @BeforeEach
    void setUp() {
        playlistVideoService = new PlaylistVideoServiceImpl(playlistVideoRepositoryMock, playlistServiceMock, videoServiceMock);

        video1 = new Video();
        video1.setId(1L);
        video1.setName("video1");
        playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("playlist1");
        testPVList = new ArrayList<>();

        lenient().when(playlistServiceMock.getPlaylistById(eq(1L))).thenReturn(playlist);
        lenient().when(videoServiceMock.getVideoById(eq(1L))).thenReturn(video1);
        lenient().when(playlistVideoRepositoryMock.getPlaylistVideosByPlaylist(eq(playlist))).thenReturn(testPVList);
        lenient().when(playlistServiceMock.getPlaylistById(eq(invalidPlaylistId))).thenThrow(ResourceNotFoundException.class);
        lenient().when(videoServiceMock.getVideoById(eq(invalidVideoId))).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void givenEmptyPlaylistAndVideo_whenAddVideoToPlaylist_thenReturnPlaylistVideoListWithAddedVideoAtBeggining() {
        //given
        PlaylistVideo playlistVideo = new PlaylistVideo(playlist, video1, 1);

        when(playlistVideoRepositoryMock.save(eq(playlistVideo))).thenReturn(playlistVideo);

        //when
        PlaylistVideo actualPlaylistVideo = playlistVideoService.addVideoToPlaylist(1L, 1L);

        //then
        verifyAll(1, 1, 1, 1);
        assertEquals(playlistVideo, actualPlaylistVideo);
    }

    @Test
    void givenPlaylistAndVideo_whenAddVideoToPlaylist_thenReturnPlaylistVideoListWithAddedVideoToTheEnd() {
        //given
        testPVList.add(new PlaylistVideo());
        PlaylistVideo playlistVideo = new PlaylistVideo(playlist, video1, 2);

        when(playlistVideoRepositoryMock.save(eq(playlistVideo))).thenReturn(playlistVideo);

        //when
        PlaylistVideo actualPlaylistVideo = playlistVideoService.addVideoToPlaylist(1L, 1L);

        //then
        verifyAll(1, 1, 1, 1);
        assertEquals(playlistVideo, actualPlaylistVideo);
    }

    @Test
    void givenUnexistentPlaylistAndVideo_whenAddVideoToUnexistentPlaylist_thenThrowException() {
        //when
        Executable executable = () -> playlistVideoService.addVideoToPlaylist(invalidPlaylistId, 1L);

        //then
        verifyAll(0, 0, 0, 0);
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void givenUnexistentVideoAndPlaylist_whenAddUnexistenVideoToPlaylist_thenThrowException() {
        //when
        Executable executable = () -> playlistVideoService.addVideoToPlaylist(1L, invalidVideoId);

        //then
        verifyAll(0, 0, 0, 0);
        assertThrows(ResourceNotFoundException.class, executable);
    }

    private void verifyAll(int playlistServiceMockNum,
                           int videoServiceMockNum,
                           int playlistVideoRepositoryMockNum,
                           int playlistVideoRepositoryMockSaveNum) {

        verify(playlistServiceMock, times(playlistServiceMockNum)).getPlaylistById(anyLong());
        verify(videoServiceMock, times(videoServiceMockNum)).getVideoById(anyLong());
        verify(playlistVideoRepositoryMock, times(playlistVideoRepositoryMockNum)).getPlaylistVideosByPlaylist(any());
        verify(playlistVideoRepositoryMock, times(playlistVideoRepositoryMockSaveNum)).save(any());
    }
}
