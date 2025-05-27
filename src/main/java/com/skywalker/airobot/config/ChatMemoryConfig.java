package com.skywalker.airobot.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author baijj
 * @date 27/5/2025
 */
@Configuration
public class ChatMemoryConfig {

    /**
     * 记忆存储
     */
    @Resource
    private ChatMemoryRepository chatMemoryRepository;

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(50) //最大消息窗口为50，默认值为20
                .chatMemoryRepository(chatMemoryRepository)
                .build();
    }
}
