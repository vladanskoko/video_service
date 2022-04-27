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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SortVideosInPlaylistTest {
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
    private Playlist playlist;
    private List<PlaylistVideo> testPVList;
    private final Long invalidPlaylistId = 111L;

    @BeforeEach
    void setUp() {
        playlistVideoService = new PlaylistVideoServiceImpl(playlistVideoRepositoryMock, playlistServiceMock, videoServiceMock);

        video1 = new Video();
        video1.setId(1L);
        video1.setName("video1");
        video2 = new Video();
        video2.setId(1L);
        video2.setName("video2");
        video3 = new Video();
        video3.setId(3L);
        video3.setName("video3");
        playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("playlist1");
        testPVList = new ArrayList<>();

        lenient().when(playlistServiceMock.getPlaylistById(eq(1L))).thenReturn(playlist);
        lenient().when(playlistVideoRepositoryMock.getPlaylistVideosByPlaylist(eq(playlist))).thenReturn(testPVList);
        lenient().when(playlistServiceMock.getPlaylistById(eq(invalidPlaylistId))).thenThrow(ResourceNotFoundException.class);
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
        verifyAll(1, 1);
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
        verifyAll(0, 0);
        assertThrows(ResourceNotFoundException.class, executable);
    }

    private void verifyAll(int playlistServiceMockNum,
                           int playlistVideoRepositoryMockNum) {

        verify(playlistServiceMock, times(playlistServiceMockNum)).getPlaylistById(anyLong());
        verify(playlistVideoRepositoryMock, times(playlistVideoRepositoryMockNum)).getPlaylistVideosByPlaylist(any());
    }
}
