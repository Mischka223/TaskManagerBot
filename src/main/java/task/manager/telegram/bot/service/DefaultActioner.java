package task.manager.telegram.bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import task.manager.telegram.bot.sender.MessageSender;
import task.manager.telegram.bot.utils.Action;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultActioner implements Actioner {

    private final MessageSender messageSender;

    public DefaultActioner(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void determineWork(Update update, Action action) {
        List<String> buttonNames = action.getActions()
                .stream()
                .map(Action::getActionText)
                .collect(Collectors.toList());
        messageSender.sendMessage(update.getMessage(), action.getActionResponse(),buttonNames);
    }
}
