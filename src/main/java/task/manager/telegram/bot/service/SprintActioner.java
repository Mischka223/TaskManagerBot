package task.manager.telegram.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import task.manager.telegram.bot.model.MessageSettings;
import task.manager.telegram.bot.model.Sprint;
import task.manager.telegram.bot.sender.MessageSender;
import task.manager.telegram.bot.utils.Action;
import task.manager.telegram.bot.utils.SprintAction;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SprintActioner implements Actioner {
    private final SprintService sprintService;
    private final MessageSender messageSender;


    @Autowired
    public SprintActioner(SprintService sprintService, MessageSender messageSender) {
        this.sprintService = sprintService;
        this.messageSender = messageSender;
    }


    public void determineWork(Update update, Action action) {
        Message message = update.getMessage();
        List<String> buttonNames = action.getActions().stream().map(Action::getActionText).collect(Collectors.toList());
        List<String> messageButtonNames = action.getMessageActions()
                .stream()
                .map(Action::getActionText)
                .collect(Collectors.toList());

        MessageSettings messageSettings = new MessageSettings(message, action.getActionResponse(), buttonNames, messageButtonNames);

        if (action.equals(SprintAction.GET_ALL_SPRINT)) {
            getAllSprints(messageSettings);
            return;
        }

        if (action.equals(SprintAction.SPRINT_CREATION)) {
            createSprint(messageSettings);
            return;
        }

        if (action.equals(SprintAction.CREATE_SPRINT)) {
            createSprintClicked(messageSettings);
        }
    }


    private void getAllSprints(MessageSettings messageSettings) {
        Long chatId = messageSettings.getMessage().getChatId();
        List<Sprint> sprintsByChatId = sprintService.getSprintsByChatId(chatId);
        if (sprintsByChatId.isEmpty()) {
            messageSender.sendMessage(messageSettings);
            return;
        }
        MessageSettings messageSettingWithSprints = new MessageSettings(messageSettings, sprintsByChatId.toString());
        sprintsByChatId.forEach(sprint -> messageSender.sendMessage(messageSettingWithSprints));
    }

    private void createSprintClicked(MessageSettings messageSettings) {
        messageSender.sendMessage(messageSettings);
    }

    private void createSprint(MessageSettings messageSettings) {
        Long chatId = messageSettings.getMessage().getChatId();
        String sprintName = messageSettings.getMessage().getText();
        sprintService.createSprint(new Sprint(chatId, sprintName));
        messageSender.sendMessage(messageSettings);
    }
}
