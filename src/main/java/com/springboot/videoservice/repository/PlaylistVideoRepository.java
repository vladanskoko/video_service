package com.springboot.videoservice.repository;

import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.PlaylistVideo;
import com.springboot.videoservice.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideo, PlaylistVideo.PlaylistVideoId> {
    List<PlaylistVideo> getPlaylistVideosByPlaylist(Playlist playlist);
    PlaylistVideo getPlaylistVideoByPlaylistAndVideo(Playlist playlist, Video video);
}
