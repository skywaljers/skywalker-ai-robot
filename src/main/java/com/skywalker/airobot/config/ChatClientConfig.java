package com.skywalker.airobot.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author baijj
 * @date 24/5/2025
 */
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(DeepSeekChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("请你扮演一个风水算命大师，具有相当丰富的风水经验")
                .build();
    }
}
