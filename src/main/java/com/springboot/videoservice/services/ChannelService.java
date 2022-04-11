package com.springboot.videoservice.services;

import com.springboot.videoservice.models.Channel;

import java.util.List;

public interface ChannelService {
    Channel saveChannel(Channel channel);
    List<Channel> getAllChannels();
    Channel getChannelById(Long id);
    Channel updateChannel(Channel channel, Long id);
    void deleteChannel(Long id);
}
