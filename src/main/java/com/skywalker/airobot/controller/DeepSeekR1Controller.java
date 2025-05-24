package com.skywalker.airobot.controller;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author baijj
 * @date 24/5/2025
 * @Description: DeepSeek 聊天（R1 推理大模型）
 */
@RestController
@RequestMapping("/v1/ai")
public class DeepSeekR1Controller {

    @Resource
    private DeepSeekChatModel chatModel;

    @RequestMapping(value = "/generateStream",produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value = "message",defaultValue = "你好") String message) {
        //构建提示词
        Prompt prompt = new Prompt(new UserMessage(message));

        // 使用原子布尔值跟踪分隔线状态（每个请求独立）
        AtomicBoolean needSeparator = new AtomicBoolean(true);

        return chatModel.stream(prompt)
                .mapNotNull(chatResponse -> {
                    // 获取响应内容
                    DeepSeekAssistantMessage deepSeekAssistantMessage = (DeepSeekAssistantMessage) chatResponse.getResult().getOutput();
                    //推理内容
                    String reasoningContent = deepSeekAssistantMessage.getReasoningContent();
                    //推理结束后的正式回答
                    String text = deepSeekAssistantMessage.getText();
                    boolean isText = false;

                    String rawContent;
                    if (Objects.isNull(text)) {
                        rawContent = reasoningContent;
                    } else {
                        rawContent = text;
                        isText = true; // 标记为正式回答
                    }
                    //处理换行
                    String processed = StringUtils.isNotBlank(rawContent) ? rawContent.replace("\n", "<br/>") : rawContent;

                    //正式回答之前,添加一个分割线
                    if (isText && needSeparator.compareAndSet(true, false)) {
                        processed = "<hr>" + processed;
                    }
                    return processed;
                });
    }
}
