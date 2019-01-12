package io.github.Chase22.simplexchat.telegram;

import lombok.extern.slf4j.Slf4j;
import name.maratik.spring.telegram.annotation.EnableTelegramBot;
import name.maratik.spring.telegram.config.TelegramBotBuilder;
import name.maratik.spring.telegram.config.TelegramBotType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@EnableTelegramBot
public class BotConfig {
    @Bean
    public TelegramBotType telegramBotType() {
        return TelegramBotType.LONG_POLLING;
    }

    @Bean
    public TelegramBotBuilder telegramBotBuilder(
            @Value("${BOT_TOKEN}") String botToken, @Value("${BOT_USERNAME}") String botUsername) {
        log.info("Bot registered");

        if (!botToken.matches("\\d{9}:[\\w-]{35}")) {
            log.error("Invalid bot token: {}", botToken);
            throw new ValidationException("Invalid bot token");
        }

        if (!botUsername.toLowerCase().matches("\\w+bot")) {
            log.error("Invalid bot name: {}", botUsername);
            throw new ValidationException("Invalid bot name");
        }

        return new TelegramBotBuilder()
                .username(botUsername)
                .token(botToken);
    }
}
