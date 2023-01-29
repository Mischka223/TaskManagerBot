package task.manager.telegram.bot.sender;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import task.manager.telegram.bot.model.MessageSettings;
import task.manager.telegram.bot.service.ButtonsService;

import java.util.List;

@Component
public class SendMessageBuilder {

    private final ButtonsService buttonsService;

    public SendMessageBuilder(ButtonsService buttonsService) {
        this.buttonsService = buttonsService;
    }

    public SendMessage getDefaultSendMessage(MessageSettings messageSettings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(messageSettings.getMessage().getChatId()));
        sendMessage.setText(messageSettings.getResponse());
        buttonsService.setDefaultButtons(sendMessage,messageSettings.getButtonNames());
        if (messageSettings.getMessageButtonNames() != null){
            addMessageButtons(sendMessage,messageSettings.getMessageButtonNames());
        }
    return sendMessage;
    }

    public void addMessageButtons(SendMessage sendMessage, List<String> messageButtonNames) {
        sendMessage.enableMarkdown(true);
        buttonsService.setButtonsForMessage(sendMessage,messageButtonNames);
    }
}
