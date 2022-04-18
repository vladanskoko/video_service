package com.springboot.videoservice.service;

import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.model.Video;

import java.util.List;

public interface PlaylistVideoService {
    List<PlaylistVideo> sortPlaylistVideos(Playlist playlist);
    PlaylistVideo addVideoToPlaylist(Long playlistId, Video video);
    List<PlaylistVideo> removeVideoFromPlaylist(Long playlistId, Video video);
    List<PlaylistVideo> changeOrderOfVideosInPlaylist(Long playlistId, Long videoId, Long orderNum);
}
