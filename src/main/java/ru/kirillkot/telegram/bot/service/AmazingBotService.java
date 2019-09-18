package ru.kirillkot.telegram.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.kirillkot.telegram.bot.configuration.Commands;

public class AmazingBotService {

    private LocalizationService service;

    public AmazingBotService(LocalizationService service) {
        this.service = service;
    }

    public synchronized SendMessage help(Message message) {
        SendMessage msg = collectSendMessage(message);

        StringBuilder sendMessage = new StringBuilder();
        sendMessage.append(service.getString("help"));

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

    public synchronized SendMessage faggot(Message message) {
        SendMessage msg = collectSendMessage(message);
        return msg;
    }

    public synchronized SendMessage currencies(Message message) {
        SendMessage msg = collectSendMessage(message);
        return msg;
    }

    public synchronized SendMessage bestMember(Message message) {
        SendMessage msg = collectSendMessage(message);

        return msg;
    }

    public void explanation(Message message) {

    }

    private SendMessage collectSendMessage(Message message) {
        SendMessage msg = new SendMessage();
        msg.setChatId(message.getChatId());

        return msg;
    }
}
