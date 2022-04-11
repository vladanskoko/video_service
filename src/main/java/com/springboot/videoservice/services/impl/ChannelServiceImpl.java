package com.springboot.videoservice.services.impl;

import com.springboot.videoservice.exceptions.ResourceNotFoundException;
import com.springboot.videoservice.models.Channel;
import com.springboot.videoservice.repositories.ChannelRepository;
import com.springboot.videoservice.services.ChannelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {
    private ChannelRepository channelRepository;

    public ChannelServiceImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel saveChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    @Override
    public Channel getChannelById(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Channel", "Id", id));
        return channel;
    }

    @Override
    public Channel updateChannel(Channel channel, Long id) {
        Channel existingChannel = channelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Channel", "Id", id));
        existingChannel.setName(channel.getName());
        return existingChannel;
    }

    @Override
    public void deleteChannel(Long id) {
        channelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Channel", "Id", id));
        channelRepository.deleteById(id);
    }
}
