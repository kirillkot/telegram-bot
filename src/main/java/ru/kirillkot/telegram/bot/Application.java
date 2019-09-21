package ru.kirillkot.telegram.bot;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.kirillkot.telegram.bot.api.AmazingBot;
import ru.kirillkot.telegram.bot.client.NewsClient;
import ru.kirillkot.telegram.bot.service.AmazingBotService;
import ru.kirillkot.telegram.bot.service.LocalizationService;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Application {
    public static void main(String[] args) throws TelegramApiRequestException {
        ApiContextInitializer.init();

        NewsClient client = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logLevel(Logger.Level.FULL)
                .target(NewsClient.class, "https://newsapi.org");

        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(new AmazingBot(
                new AmazingBotService(new LocalizationService(), new Random(), client),
                new ConcurrentHashMap<>(),
                new LocalizationService()));
    }
}
