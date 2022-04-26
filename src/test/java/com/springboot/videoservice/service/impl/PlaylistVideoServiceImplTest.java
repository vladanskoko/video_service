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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PlaylistVideoServiceImplTest {
    private PlaylistVideoService playlistVideoService;

    @Mock
    private PlaylistService playlistServiceMock;

    @Mock
    private VideoService videoServiceMock;

    @Mock
    private PlaylistVideoRepository playlistVideoRepositoryMock;

    private Video video1;
    private Video video2;
    private Video video3;
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
        video2 = new Video();
        video2.setId(2L);
        video2.setName("video2");
        video3 = new Video();
        video3.setId(3L);
        video3.setName("video3");
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
        verifyAll(1, 1, 1, 1, 0);
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
        verifyAll(1, 1, 1, 1, 0);
        assertEquals(playlistVideo, actualPlaylistVideo);
    }

    @Test
    void givenUnexistentPlaylistAndVideo_whenAddVideoToUnexistentPlaylist_thenThrowException() {
        //when
        Executable executable = () -> playlistVideoService.addVideoToPlaylist(invalidPlaylistId, 1L);

        //then
        verifyAll(0, 0, 0, 0, 0);
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void givenUnexistentVideoAndPlaylist_whenAddUnexistenVideoToPlaylist_thenThrowException() {
        //when
        Executable executable = () -> playlistVideoService.addVideoToPlaylist(1L, invalidVideoId);

        //then
        verifyAll(0, 0, 0, 0, 0);
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void givenPlaylistAndVideo_whenDeleteVideoFromPlaylist_thenVideoIsRemovedFromPlaylist() {
        //given
        PlaylistVideo testPV = new PlaylistVideo(playlist, video1, 1);
        testPVList.add(testPV);

        when(playlistVideoRepositoryMock.getPlaylistVideoByPlaylistAndVideo(eq(playlist), eq(video1))).thenReturn(testPV);

        doAnswer(invocation -> {
            PlaylistVideo arg0 = invocation.getArgument(0);

            //then
            verifyAll(1, 1, 1, 0, 1);
            assertEquals("video1", arg0.getVideo().getName());
            return arg0;
        }).when(playlistVideoRepositoryMock).delete(any(PlaylistVideo.class));

        //when
        playlistVideoService.removeVideoFromPlaylist(1L, 1L);
    }

    @Test
    void givenUnexistentPlaylistAndVideo_whenDeleteVideoFromUnexistentPlaylist_thenThrowException() {
        //when
        Executable executable = () -> playlistVideoService.removeVideoFromPlaylist(invalidPlaylistId,1L);

        //then
        verifyAll(0, 0, 0, 0, 0);
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void givenUnexistentVideoAndPlaylist_whenDeleteUnexistentVideoFromPlaylist_thenThrowException() {
        //when
        Executable executable = () -> playlistVideoService.removeVideoFromPlaylist(1L, invalidVideoId);

        //then
        verifyAll(0, 0, 0, 0, 0);
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void givenPlaylistWithVideos_whenSortVideosInPlaylist_thenReturnSortedPlaylistByOrderNumber() {
        //given
        PlaylistVideo playlistVideo1 = new PlaylistVideo(playlist, video1, 3);
        PlaylistVideo playlistVideo2 = new PlaylistVideo(playlist, video2, 1);
        PlaylistVideo playlistVideo3 = new PlaylistVideo(playlist, video3, 2);
        testPVList = new ArrayList<>(List.of(playlistVideo1, playlistVideo2, playlistVideo3));
        List<PlaylistVideo> actualPVList;

        when(playlistVideoRepositoryMock.getPlaylistVideosByPlaylist(eq(playlist))).thenReturn(testPVList);

        //when
        actualPVList = playlistVideoService.sortPlaylistVideos(1L);

        //then
        verifyAll(1, 0, 1, 0, 0);
        assertAll(
                () -> assertEquals("video2", actualPVList.get(0).getVideo().getName()),
                () -> assertEquals("video3", actualPVList.get(1).getVideo().getName()),
                () -> assertEquals("video1", actualPVList.get(2).getVideo().getName())
        );
    }

    @Test
    void givenUnexistentPlaylist_whenSortVideosInUnexistentPlaylist_thenThrowException() {
        //when
        Executable executable = () -> playlistVideoService.sortPlaylistVideos(invalidPlaylistId);

        //then
        assertThrows(ResourceNotFoundException.class, executable);
    }

    private void verifyAll(int playlistServiceMockNum,
                           int videoServiceMockNum,
                           int playlistVideoRepositoryMockNum,
                           int playlistVideoRepositoryMockSaveNum,
                           int playlistVideoRepositoryMockDeleteNum) {

        verify(playlistServiceMock, times(playlistServiceMockNum)).getPlaylistById(anyLong());
        verify(videoServiceMock, times(videoServiceMockNum)).getVideoById(anyLong());
        verify(playlistVideoRepositoryMock, times(playlistVideoRepositoryMockNum)).getPlaylistVideosByPlaylist(any());
        verify(playlistVideoRepositoryMock, times(playlistVideoRepositoryMockSaveNum)).save(any());
        verify(playlistVideoRepositoryMock, times(playlistVideoRepositoryMockDeleteNum)).delete(any());
    }
}