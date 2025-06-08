package com.skywalker.airobot.controller;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author baijj
 * @date 30/5/2025
 */
@RestController
@RequestMapping("/v3/ai")
public class OllamaController {

    @Resource
    private OllamaChatModel chatModel;

    /**
     * 普通对话
     */
    @GetMapping("/generate")
    public Flux<String> generate(@RequestParam(value = "message",defaultValue = "你好") String message) {
        //构建提示词
        Prompt prompt = new Prompt(new UserMessage(message));

        return this.chatModel.stream(prompt)
                .mapNotNull(chatResponse -> {
                    String text = chatResponse.getResult().getOutput().getText();

                    return StringUtils.isNotBlank(text)?text.replace("\n", "<br>"):text;
                });
    }
}
