package ru.kirillkot.telegram.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kirillkot.telegram.bot.configuration.Commands;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AmazingBotService {

    private LocalizationService service;
    private Random random;

    public AmazingBotService(LocalizationService service, Random random) {
        this.service = service;
        this.random = random;
    }

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
            if (!user.getUserName().equals("kirill_kot")) {
                sendMessage.append(user.getUserName());
            } else {
                return msg;
            }
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

    private SendMessage collectSendMessage(Message message) {
        SendMessage msg = new SendMessage();
        msg.setChatId(message.getChatId());

        return msg;
    }
}
