package com.skywalker.airobot.config;

import com.skywalker.airobot.advisor.MyLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
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
                .defaultSystem("请你扮演一个来自未来的穿越者机器人，你拥有极高的智能")
                .defaultAdvisors(new SimpleLoggerAdvisor(),
                        new MyLoggerAdvisor()) //添加日志记录功能
                .build();
    }
}
