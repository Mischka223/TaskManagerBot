package task.manager.telegram.bot.model;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class FinalMessage {
    private Message message;
    private String messageText;
    private List<String> buttons;


    public FinalMessage(Message message, String messageText, List<String> buttons) {
        this.message = message;
        this.messageText = messageText;
        this.buttons = buttons;
    }


    public Message getMessage() {
        return message;
    }

    public String getMessageText() {
        return messageText;
    }

    public List<String> getButtons() {
        return buttons;
    }
}
