package com.springboot.videoservice.service;

import com.springboot.videoservice.model.Channel;
import com.springboot.videoservice.model.User;

import java.util.List;

public interface ChannelService {
    Channel saveChannel(Channel channel);
    List<Channel> getAllChannels();
    Channel getChannelById(Long id);
    Channel getChannelByUser(User user);
    Channel updateChannel(Channel channel, Long id);
    void addPlaylistToChannel(Long channelId, Long playlistId);
    void deleteChannel(Long id);
}
