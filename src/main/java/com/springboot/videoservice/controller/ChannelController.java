package com.springboot.videoservice.controller;

import com.springboot.videoservice.model.Channel;
import com.springboot.videoservice.service.impl.ChannelServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
    private final ChannelServiceImpl channelService;

    public ChannelController(ChannelServiceImpl channelService) {
        this.channelService = channelService;
    }

    @PostMapping()
    public Channel saveChannel(@RequestBody Channel channel) {
        return channelService.saveChannel(channel);
    }

    @GetMapping()
    public List<Channel> getAllChannels() {
        return channelService.getAllChannels();
    }

    @GetMapping("{id}")
    public Channel getChannelById(@PathVariable("id") Long channelId) {
        return channelService.getChannelById(channelId);
    }

    @PutMapping("{id}")
    public Channel updateChannel(@RequestBody Channel channel, @PathVariable("id") Long channelId) {
        return channelService.updateChannel(channel, channelId);
    }

    @DeleteMapping("{id}")
    public void deleteChannel(@PathVariable("id") Long channelId) {
        channelService.deleteChannel(channelId);
    }
}
