package ru.kirillkot.telegram.bot.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kirillkot.telegram.bot.client.NewsClient;
import ru.kirillkot.telegram.bot.client.dto.ArticlesDto;
import ru.kirillkot.telegram.bot.client.dto.TopHeadlinesDto;
import ru.kirillkot.telegram.bot.configuration.Commands;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static ru.kirillkot.telegram.bot.configuration.BotConfiguration.apiKey;

@RequiredArgsConstructor
public class AmazingBotService {

    private final LocalizationService service;
    private final Random random;
    private final NewsClient client;

    public synchronized SendMessage help(Message message) {
        SendMessage msg = collectSendMessage(message);

        StringBuilder sendMessage = new StringBuilder();
        sendMessage.append(service.getString("helpBot"));

        for (String command : Commands.commands) {
            sendMessage.append(command);
            sendMessage.append("\n");
        }

        sendMessage.append(service.getString("explanation"));
        msg.setText(sendMessage.toString());

        return msg;
    }

    public synchronized SendMessage weather(Message message) {
        SendMessage msg = collectSendMessage(message);
        return msg;
    }

    public synchronized SendMessage faggot(Message message, ConcurrentHashMap<Long, Set<User>> users) {
        SendMessage msg = collectSendMessage(message);
        StringBuilder sendMessage = new StringBuilder();

        Object[] usersFromChat = users.get(message.getChatId()).toArray();
        User user = (User) usersFromChat[random.nextInt(usersFromChat.length)];

        sendMessage.append(service.getString("wonder"));
        sendMessage.append(service.getString("secretSource"));

        if (user.getUserName() != null) {
            sendMessage.append(user.getUserName());
        } else {
            sendMessage.append(user.getFirstName()).append(" ").append(user.getLastName());
        }

        msg.setText(sendMessage.toString());
        return msg;
    }

    public synchronized SendMessage currencies(Message message) {
        SendMessage msg = collectSendMessage(message);
        return msg;
    }

    public synchronized SendMessage bestMember(Message message, ConcurrentHashMap<Long, Set<User>> users) {
        SendMessage msg = collectSendMessage(message);

        return msg;
    }

    public synchronized SendMessage explanation(Message message) {
        SendMessage msg = collectSendMessage(message);
        String command = message.getText().split(" ")[1];
        if (!Commands.commands.contains("/" + command)) {
            // todo throw exception
            msg.setText(service.getString("commandNotFound"));
            return msg;
        }

        msg.setText(service.getString(command));
        return msg;
    }

    public SendMessage news(Message message) {
        SendMessage msg = collectSendMessage(message);
        TopHeadlinesDto topHeadlinesDto = client.topHeadlines(collectTopHeadlinersFilter(message));

        StringBuilder sendMessage = new StringBuilder();
        for (ArticlesDto article : topHeadlinesDto.getArticles()) {
            appendIfNotNull(article.getSource().getName(), sendMessage);
            sendMessage.append("\n");
            appendIfNotNull(article.getTitle(), sendMessage);
            sendMessage.append("\n");
            appendIfNotNull(article.getUrl(), sendMessage);
            sendMessage.append("\n");
            sendMessage.append("\n");
        }

        msg.setText(sendMessage.toString());

        return msg;
    }

    private SendMessage collectSendMessage(Message message) {
        SendMessage msg = new SendMessage();
        msg.setChatId(message.getChatId());

        return msg;
    }

    // todo refactor for several parameters
    private Map<String, Object> collectTopHeadlinersFilter(Message message) {
        ArrayList<String> command = Lists.newArrayList(message.getText().split(" "));
        Map<String, Object> filter = new HashMap<String, Object>() {{
            put("apiKey", apiKey);
        }};

        if (command.get(1) != null) {
            filter.put("country", command.get(1));
        }

        return filter;
    }

    private void appendIfNotNull(String text, StringBuilder builder) {
        Optional.ofNullable(text)
                .ifPresent(builder::append);
    }
}
