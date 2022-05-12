package com.springboot.videoservice.controller;

import com.springboot.videoservice.model.Channel;
import com.springboot.videoservice.model.User;
import com.springboot.videoservice.service.impl.ChannelServiceImpl;
import com.springboot.videoservice.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
    private final ChannelServiceImpl channelService;
    private final UserServiceImpl userService;

    public ChannelController(ChannelServiceImpl channelService, UserServiceImpl userService) {
        this.channelService = channelService;
        this.userService = userService;
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

    @GetMapping("{channelId}/user/{userId}")
    public Channel getChannelByUser(@PathVariable("channelId") Long channelId, @PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);
        return channelService.getChannelByUser(user);
    }

    @PutMapping("{id}")
    public Channel updateChannel(@RequestBody Channel channel, @PathVariable("id") Long channelId) {
        return channelService.updateChannel(channel, channelId);
    }

    @PostMapping("{channelId}/playlist/{playlistId}")
    public void addPlaylistToChannel(@PathVariable("channelId") Long channelId, @PathVariable("playlistId") Long playlistId) {
        channelService.addPlaylistToChannel(channelId, playlistId);
    }

    @DeleteMapping("{id}")
    public void deleteChannel(@PathVariable("id") Long channelId) {
        channelService.deleteChannel(channelId);
    }
}
