package com.springboot.videoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@Data
@AllArgsConstructor
public class PlaylistVideo {
    @EmbeddedId
    private PlaylistVideoId playlistVideoId;

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @MapsId("videoId")
    @JoinColumn(name = "video_id")
    private Video video;

    private Integer orderNumber;
    public PlaylistVideo() {

    }
    public PlaylistVideo(Playlist playlist, Video video, Integer orderNumber) {
        this.playlist = playlist;
        this.video = video;
        this.orderNumber = orderNumber;
        this.playlistVideoId = new PlaylistVideoId(playlist, video);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistVideo)) return false;
        PlaylistVideo that = (PlaylistVideo) o;
        return getPlaylistVideoId().equals(that.getPlaylistVideoId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaylistVideoId());
    }

    @Override
    public String toString() {
        return "PlaylistVideo{" +
                "playlist=" + playlist.getId() +
                ", video=" + video.getId() +
                ", orderNumber=" + orderNumber +
                '}';
    }

    @Embeddable
    @Data
    public static class PlaylistVideoId implements Serializable {
        private Long playlistId;
        private Long videoId;

        public PlaylistVideoId() {

        }

        public PlaylistVideoId(Playlist playlist, Video video) {
            playlistId = playlist.getId();
            videoId = video.getId();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PlaylistVideoId)) return false;
            PlaylistVideoId that = (PlaylistVideoId) o;
            return playlistId.equals(that.playlistId) && videoId.equals(that.videoId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(playlistId, videoId);
        }
    }
}

