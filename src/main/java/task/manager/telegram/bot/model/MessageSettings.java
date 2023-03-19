package task.manager.telegram.bot.model;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.List;

public class MessageSettings {
    private final Message message;
    private final String response;
    private final List<String> buttonNames;
    private List<String> messageButtonNames;


    public MessageSettings(
            Message message, String response, List<String> buttonNames, List<String> messageButtonNames) {
        this.message = message;
        this.response = response;
        this.buttonNames = buttonNames;
        this.messageButtonNames = messageButtonNames;
    }

    public MessageSettings(Message message, String response, List<String> buttonNames) {
        this.message = message;
        this.response = response;
        this.buttonNames = buttonNames;
    }

    public MessageSettings(MessageSettings messageSettings, String response) {
        this.message = messageSettings.getMessage();
        this.response = response;
        this.buttonNames = messageSettings.getButtonNames();
        this.messageButtonNames = Collections.emptyList();
    }

    public List<String> getButtonNames() {
        return buttonNames;
    }

    public List<String> getMessageButtonNames() {
        return messageButtonNames;
    }

    public Message getMessage() {
        return message;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "MessageSettings{" +
                "message=" + message +
                ", response='" + response + '\'' +
                ", buttonNames=" + buttonNames +
                ", messageButtonNames=" + messageButtonNames +
                '}';
    }
}
