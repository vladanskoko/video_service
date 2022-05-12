package com.springboot.videoservice.repository;

import com.springboot.videoservice.model.Channel;
import com.springboot.videoservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel getChannelByUser(User user);
}
