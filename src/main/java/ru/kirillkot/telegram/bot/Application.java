package ru.kirillkot.telegram.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.kirillkot.telegram.bot.api.AmazingBot;
import ru.kirillkot.telegram.bot.service.AmazingBotService;
import ru.kirillkot.telegram.bot.service.LocalizationService;

import java.util.concurrent.ConcurrentHashMap;

public class Application {
    public static void main(String[] args) throws TelegramApiRequestException {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(new AmazingBot(
                new AmazingBotService(new LocalizationService()),
                new ConcurrentHashMap<>(),
                new LocalizationService()));
    }
}
