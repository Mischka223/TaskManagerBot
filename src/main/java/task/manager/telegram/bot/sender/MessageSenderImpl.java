package task.manager.telegram.bot.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import task.manager.telegram.bot.model.MessageSettings;
import task.manager.telegram.bot.service.TaskManagerBotService;

@Component
public class MessageSenderImpl implements MessageSender{
    private final SendMessageBuilder sendMessageBuilder;
    private TaskManagerBotService taskManagerBotService;

    public MessageSenderImpl(SendMessageBuilder sendMessageBuilder) {
        this.sendMessageBuilder = sendMessageBuilder;
    }

    public void sendMessage(MessageSettings messageSettings) {
        SendMessage sendMessage = sendMessageBuilder.getDefaultSendMessage(messageSettings);
        try {
            taskManagerBotService.execute(sendMessage);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }


    @Autowired
    private void setTaskManagerBotService(@Lazy TaskManagerBotService taskManagerBotService){
        this.taskManagerBotService = taskManagerBotService;
    }
}
