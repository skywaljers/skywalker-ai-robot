package com.skywalker.airobot.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author baijj
 * @date 24/5/2025
 * @Description 基于chat Client客户端
 */
@RestController
@RequestMapping("/v2/ai")
public class ChatClientController {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message",defaultValue = "你是谁") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping(value = "/generateStream",produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value = "message",defaultValue = "你是谁") String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
