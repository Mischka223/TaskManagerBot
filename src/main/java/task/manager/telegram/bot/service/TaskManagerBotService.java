package task.manager.telegram.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import task.manager.telegram.bot.utils.MessageProcessor;

@Component
public class TaskManagerBotService extends TelegramLongPollingBot {
    private final MessageProcessor messageProcessor;

    @Autowired
    public TaskManagerBotService(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public String getBotUsername() {
        return "TaskManagerBot";
    }

    @Override
    public String getBotToken() {
        return "5414244410:AAEVVTT_QHZW_dpGRIzVlhPnAe_jOSXF7Lg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()){
            messageProcessor.processMessage(update);
        }

    }
}
