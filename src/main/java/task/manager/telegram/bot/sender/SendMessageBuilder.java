package task.manager.telegram.bot.sender;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class SendMessageBuilder {

    private final ButtonsService buttonsService;

    public SendMessageBuilder(ButtonsService buttonsService) {
        this.buttonsService = buttonsService;
    }

    public SendMessage getDefaultSendMessage(Message message, String text, List<String> buttonNames){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        System.out.println(sendMessage);
        buttonsService.setButtons(sendMessage,buttonNames);
    return sendMessage;
    }

    public SendMessage getDefaultSendMessage(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        sendMessage.enableMarkdown(true);
        System.out.println(sendMessage);
    return sendMessage;
    }
}
