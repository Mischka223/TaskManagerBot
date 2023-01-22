package task.manager.telegram.bot.sender;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface MessageSender {
    void sendMessage(Message message, String response, List<String> buttonNames);

    void sendMessage(Message message, String response);

}
