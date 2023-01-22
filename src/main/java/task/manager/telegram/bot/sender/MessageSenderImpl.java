package task.manager.telegram.bot.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import task.manager.telegram.bot.service.TaskManagerBotService;

import java.util.List;

@Component
public class MessageSenderImpl implements MessageSender{
    private final SendMessageBuilder sendMessageBuilder;
    private TaskManagerBotService taskManagerBotService;

    public MessageSenderImpl(SendMessageBuilder sendMessageBuilder) {
        this.sendMessageBuilder = sendMessageBuilder;
    }

    public void sendMessage(Message message, String response, List<String> buttonNames) {
        SendMessage defaultSendMessage = sendMessageBuilder.getDefaultSendMessage(message, response, buttonNames);
        try {
            taskManagerBotService.execute(defaultSendMessage);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }

    public void sendMessage(Message message, String response) {
        SendMessage defaultSendMessage = sendMessageBuilder.getDefaultSendMessage(message, response);
        try {
            taskManagerBotService.execute(defaultSendMessage);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }


    @Autowired
    private void setTaskManagerBotService(@Lazy TaskManagerBotService taskManagerBotService){
        this.taskManagerBotService = taskManagerBotService;
    }
}
