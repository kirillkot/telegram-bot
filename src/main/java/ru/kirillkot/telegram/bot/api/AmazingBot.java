package ru.kirillkot.telegram.bot.api;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kirillkot.telegram.bot.service.AmazingBotService;
import ru.kirillkot.telegram.bot.service.LocalizationService;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static ru.kirillkot.telegram.bot.configuration.BotConfiguration.token;
import static ru.kirillkot.telegram.bot.configuration.BotConfiguration.username;
import static ru.kirillkot.telegram.bot.configuration.Commands.BEST_MEMBER;
import static ru.kirillkot.telegram.bot.configuration.Commands.CURRENCIES;
import static ru.kirillkot.telegram.bot.configuration.Commands.FAGGOT;
import static ru.kirillkot.telegram.bot.configuration.Commands.HELP;
import static ru.kirillkot.telegram.bot.configuration.Commands.MAN;
import static ru.kirillkot.telegram.bot.configuration.Commands.WEATHER;

public class AmazingBot extends TelegramLongPollingBot {

    private AmazingBotService service;
    private ConcurrentHashMap<Integer, User> users;
    private LocalizationService localizationService;

    public AmazingBot(AmazingBotService service, ConcurrentHashMap<Integer, User> users, LocalizationService localizationService) {
        this.service = service;
        this.users = users;
        this.localizationService = localizationService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional.ofNullable(update)
                .filter(upd -> upd.getMessage() != null)
                .map(this::populateUsers)
                .map(Update::getMessage)
                .filter(message -> message.getText() != null)
                .filter(message -> message.getFrom() != null)
                .ifPresent(this::distributor);
    }

    private synchronized void distributor(Message message) {
        SendMessage msg = collectDefaultSendMessage(message);
        switch (message.getText()) {
            case HELP:
                msg = service.help(message);
                break;
            case CURRENCIES:
                msg = service.currencies(message);
                break;
            case WEATHER:
                msg = service.weather(message);
                break;
            case FAGGOT:
                msg = service.faggot(message);
                break;
            case BEST_MEMBER:
                msg = service.bestMember(message);
                break;
        }

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            // todo handle exception
        }
    }

    private SendMessage collectDefaultSendMessage(Message message) {
        SendMessage msg = new SendMessage();
        msg.setChatId(message.getChatId());
        msg.setText(localizationService.getString("commandNotFound"));

        return msg;
    }

    private Update populateUsers(Update update) {
        User user = update.getMessage().getFrom();
        if (user == null) {
            // todo throw exception
        }

        users.putIfAbsent(user.getId(), user);
        return update;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
