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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChangeOrderOfVideosInPlaylistTest {
    private PlaylistVideoService playlistVideoService;

    @Mock
    private PlaylistVideoRepository playlistVideoRepositoryMock;

    @Mock
    private PlaylistService playlistServiceMock;

    @Mock
    private VideoService videoServiceMock;

    private Video video1;
    private Video video2;
    private Video video3;
    private Video video4;
    private Video video5;
    private Video video6;
    private Playlist playlist;
    private PlaylistVideo testPV1;
    private PlaylistVideo testPV2;
    private PlaylistVideo testPV3;
    private PlaylistVideo testPV4;
    private PlaylistVideo testPV5;
    private PlaylistVideo testPV6;
    private List<PlaylistVideo> testPVList;
    private final Long invalidPlaylistId = 111L;
    private final Long invalidVideoId = 222L;


    @BeforeEach
    void setUp() {
        playlistVideoService = new PlaylistVideoServiceImpl(playlistVideoRepositoryMock, playlistServiceMock, videoServiceMock);

        playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("playlist1");
        video1 = new Video();
        video1.setId(1L);
        video1.setName("video1");
        video2 = new Video();
        video2.setId(2L);
        video2.setName("video2");
        video3 = new Video();
        video3.setId(3L);
        video3.setName("video3");
        video4 = new Video();
        video4.setId(4L);
        video4.setName("video4");
        video5 = new Video();
        video5.setId(5L);
        video5.setName("video5");
        video6 = new Video();
        video6.setId(6L);
        video6.setName("video6");
        testPV1 = new PlaylistVideo(playlist, video1, 1);
        testPV2 = new PlaylistVideo(playlist, video2, 2);
        testPV3 = new PlaylistVideo(playlist, video3, 3);
        testPV4 = new PlaylistVideo(playlist, video4, 4);
        testPV5 = new PlaylistVideo(playlist, video5, 5);
        testPV6 = new PlaylistVideo(playlist, video6, 6);
        testPVList = new ArrayList<>();
        testPVList.add(testPV1);
        testPVList.add(testPV2);
        testPVList.add(testPV3);
        testPVList.add(testPV4);
        testPVList.add(testPV5);
        testPVList.add(testPV6);

        lenient().when(playlistServiceMock.getPlaylistById(eq(1L))).thenReturn(playlist);
        lenient().when(videoServiceMock.getVideoById(eq(3L))).thenReturn(video3);
        lenient().when(playlistVideoRepositoryMock.getPlaylistVideoByPlaylistAndVideo(eq(playlist), eq(video3))).thenReturn(testPV3);
        lenient().when(playlistVideoRepositoryMock.getPlaylistVideosByPlaylist(eq(playlist))).thenReturn(testPVList);
        lenient().when(playlistServiceMock.getPlaylistById(eq(invalidPlaylistId))).thenThrow(ResourceNotFoundException.class);
        lenient().when(videoServiceMock.getVideoById(eq(invalidVideoId))).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void givenPlaylistWithVideos_whenVideoOrderNumberIsSetOutOfRange_thenVideoWillBePlacedAtTheEnd() {
        //when
        List<PlaylistVideo> actualPVList = playlistVideoService.changeOrderOfVideosInPlaylist(1L, 3L, 6);

        //then
        verifyAll(1, 1, 1);
        assertAll(
                () -> assertEquals(1, actualPVList.get(0).getOrderNumber()),
                () -> assertEquals(2, actualPVList.get(1).getOrderNumber()),
                () -> assertEquals(6, actualPVList.get(2).getOrderNumber()),
                () -> assertEquals(3, actualPVList.get(3).getOrderNumber()),
                () -> assertEquals(4, actualPVList.get(4).getOrderNumber()),
                () -> assertEquals(5, actualPVList.get(5).getOrderNumber())
        );
    }

    @Test
    void givenPlaylistWithVideos_whenVideoOrderNumberIsSetToHigher_thenVideoWillBePlacedAtHigherPosition() {
        //when
        List<PlaylistVideo> actualPVList = playlistVideoService.changeOrderOfVideosInPlaylist(1L, 3L, 5);

        //then
        verifyAll(1, 1, 1);
        assertAll(
                () -> assertEquals(1, actualPVList.get(0).getOrderNumber()),
                () -> assertEquals(2, actualPVList.get(1).getOrderNumber()),
                () -> assertEquals(5, actualPVList.get(2).getOrderNumber()),
                () -> assertEquals(3, actualPVList.get(3).getOrderNumber()),
                () -> assertEquals(4, actualPVList.get(4).getOrderNumber()),
                () -> assertEquals(6, actualPVList.get(5).getOrderNumber())
        );
    }

    @Test
    void givenPlaylistWithVideos_whenVideoOrderNumberIsSetToLower_thenVideoWillBePlacedAtLowerPosition() {
        //when
        List<PlaylistVideo> actualPVList = playlistVideoService.changeOrderOfVideosInPlaylist(1L, 3L, 1);

        //then
        verifyAll(1, 1, 1);
        assertAll(
                () -> assertEquals(2, actualPVList.get(0).getOrderNumber()),
                () -> assertEquals(3, actualPVList.get(1).getOrderNumber()),
                () -> assertEquals(1, actualPVList.get(2).getOrderNumber()),
                () -> assertEquals(4, actualPVList.get(3).getOrderNumber()),
                () -> assertEquals(5, actualPVList.get(4).getOrderNumber()),
                () -> assertEquals(6, actualPVList.get(5).getOrderNumber())
        );
    }

    @Test
    void givenUnexistentPlaylistAndVideo_whenChangeOrderOfVideosInPlaylist_thenThrowException() {
        //when
        Executable executable = () -> playlistVideoService.changeOrderOfVideosInPlaylist(invalidPlaylistId, 3L, 4);

        //then
        verifyAll(0, 0, 0);
        assertThrows(ResourceNotFoundException.class, executable);
    }

    @Test
    void givenUnexistentVideoAndPlaylist_whenAddUnexistenVideoToPlaylist_thenThrowException() {
        //when
        Executable executable = () -> playlistVideoService.addVideoToPlaylist(1L, invalidVideoId);

        //then
        verifyAll(0, 0, 0);
        assertThrows(ResourceNotFoundException.class, executable);
    }

    private void verifyAll(int playlistServiceMockNum,
                           int videoServiceMockNum,
                           int playlistVideoRepositoryMockNum) {

        verify(playlistServiceMock, times(playlistServiceMockNum)).getPlaylistById(anyLong());
        verify(videoServiceMock, times(videoServiceMockNum)).getVideoById(anyLong());
        verify(playlistVideoRepositoryMock, times(playlistVideoRepositoryMockNum)).getPlaylistVideoByPlaylistAndVideo(any(), any());
        verify(playlistVideoRepositoryMock, times(playlistVideoRepositoryMockNum)).getPlaylistVideosByPlaylist(any());
    }
}