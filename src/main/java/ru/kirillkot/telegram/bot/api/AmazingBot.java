package ru.kirillkot.telegram.bot.api;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMembersCount;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kirillkot.telegram.bot.service.AmazingBotService;
import ru.kirillkot.telegram.bot.service.LocalizationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static ru.kirillkot.telegram.bot.configuration.BotConfiguration.token;
import static ru.kirillkot.telegram.bot.configuration.BotConfiguration.username;
import static ru.kirillkot.telegram.bot.configuration.Commands.*;

@RequiredArgsConstructor
public class AmazingBot extends TelegramLongPollingBot {

    private final AmazingBotService service;
    private final ConcurrentHashMap<Long, Set<User>> users;
    private final LocalizationService localizationService;

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
        String text = message.getText();

        if (text.startsWith(MAN)) {
            msg = service.explanation(message);
        }

        if (text.startsWith(NEWS)) {
            msg = service.news(message);
        }

        switch (text) {
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
                if (availableRecognizeMembers(message)) {
                    msg = service.faggot(message, users);
                } else {
                    msg = notAvailableIndicateMemberType(message, "faggotNotAvailable");
                }
                break;
            case BEST_MEMBER:
                if (availableRecognizeMembers(message)) {
                    msg = service.bestMember(message, users);
                } else {
                    msg = notAvailableIndicateMemberType(message, "bestMemberNotAvailable");
                }
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

        Long chatId = update.getMessage().getChatId();
        if (users.containsKey(chatId)) {
            users.get(chatId).add(user);
        } else {
            users.put(chatId, new HashSet<>(Collections.singleton(user)));
        }

        return update;
    }

    private boolean availableRecognizeMembers(Message message) {
        GetChatMembersCount membersCount = new GetChatMembersCount();
        membersCount.setChatId(message.getChatId());

        try {
            return users.get(message.getChatId()).size() == execute(membersCount) - 1;
        } catch (TelegramApiException e) {
            // todo throw exception
        }

        return false;
    }

    private SendMessage notAvailableIndicateMemberType(Message message, String notAvailableMessage) {
        SendMessage msg = new SendMessage();
        msg.setChatId(message.getChatId());
        msg.setText(localizationService.getString(notAvailableMessage));

        return msg;
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
