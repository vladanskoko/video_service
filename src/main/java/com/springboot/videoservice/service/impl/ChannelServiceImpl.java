package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.exception.ResourceNotFoundException;
import com.springboot.videoservice.model.Channel;
import com.springboot.videoservice.repository.ChannelRepository;
import com.springboot.videoservice.service.ChannelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;

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
        Channel existingChannel = getChannelById(id);
        existingChannel.setName(channel.getName());
        return channelRepository.save(existingChannel);
    }

    @Override
    public void deleteChannel(Long id) {
        getChannelById(id);
        channelRepository.deleteById(id);
    }
}
