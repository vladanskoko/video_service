package com.springboot.videoservice.repository;

import com.springboot.videoservice.model.PlaylistVideo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistVideoRepository extends CrudRepository<PlaylistVideo, PlaylistVideo.PlaylistVideoId> {
}
