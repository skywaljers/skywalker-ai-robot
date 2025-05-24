package com.skywalker.airobot.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author baijj
 * @date 24/5/2025
 * @Description deepseek聊天
 */
@RestController
@RequestMapping("/ai")
public class DeepSeekChatController {

    @Resource
    private DeepSeekChatModel chatModel;

    /**
     * 普通对话
     */
    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message",defaultValue = "你好") String message) {
        //一次性返回结果
        return chatModel.call(message);
    }

    /**
     * 流式对话
     */
    @GetMapping(value = "/generateStream",produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value = "message",defaultValue = "你好") String message) {
        //构建提示词
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt)
                .mapNotNull(chatResponse -> chatResponse.getResult().getOutput().getText());
    }
}
