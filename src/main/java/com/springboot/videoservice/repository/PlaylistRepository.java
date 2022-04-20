package com.springboot.videoservice.repository;

import com.springboot.videoservice.model.Playlist;
import com.springboot.videoservice.model.PlaylistVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
