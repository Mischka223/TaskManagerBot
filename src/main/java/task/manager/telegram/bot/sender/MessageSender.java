package task.manager.telegram.bot.sender;

import task.manager.telegram.bot.model.MessageSettings;

public interface MessageSender {
    void sendMessage(MessageSettings messageSettings);

}
