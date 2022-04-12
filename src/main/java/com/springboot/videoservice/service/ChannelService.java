package com.springboot.videoservice.service;

import com.springboot.videoservice.model.Channel;

import java.util.List;

public interface ChannelService {
    Channel saveChannel(Channel channel);
    List<Channel> getAllChannels();
    Channel getChannelById(Long id);
    Channel updateChannel(Channel channel, Long id);
    void deleteChannel(Long id);
}
