package task.manager.telegram.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import task.manager.telegram.bot.model.LastActionList;
import task.manager.telegram.bot.model.Sprint;
import task.manager.telegram.bot.sender.MessageSender;
import task.manager.telegram.bot.utils.Action;
import task.manager.telegram.bot.utils.SprintAction;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SprintActioner implements Actioner {
    private static final LastActionList lastActionList = LastActionList.getInstance();
    private final SprintService sprintService;
    private final MessageSender messageSender;


    @Autowired
    public SprintActioner(SprintService sprintService, MessageSender messageSender) {
        this.sprintService = sprintService;
        this.messageSender = messageSender;
    }


    public void determineWork(Update update, Action action) {
        List<String> buttonNames = action.getActions().stream().map(Action::getActionText).collect(Collectors.toList());

        Message message = update.getMessage();
        Long chatId = message.getChatId();
        System.out.println(message.getText());

        if (action.equals(SprintAction.GET_ALL_SPRINT)) {
            getAllSprints(chatId, message, action, buttonNames);
            return;
        }

        if (action.equals(SprintAction.SPRINT_CREATION)) {
            createSprint(chatId, message, action, buttonNames);
            return;
        }

        if (action.equals(SprintAction.CREATE_SPRINT)) {
            createSprintClicked(chatId, message, action, buttonNames);
        }
    }


    private void getAllSprints(Long chatId, Message message, Action action, List<String> buttonNames) {
        lastActionList.addLastAction(chatId, SprintAction.GET_ALL_SPRINT.getActionText());
        List<Sprint> sprintsByChatId = sprintService.getSprintsByChatId(chatId);
        if (sprintsByChatId.isEmpty()) {
            messageSender.sendMessage(message, action.getActionResponse(), buttonNames);
            return;
        }
        messageSender.sendMessage(message, sprintsByChatId.toString(), buttonNames);
    }

    private void createSprintClicked(Long chatId, Message message, Action action, List<String> buttonNames) {
        lastActionList.addLastAction(chatId, SprintAction.CREATE_SPRINT.getActionText());
        messageSender.sendMessage(message, action.getActionResponse(), buttonNames);
    }

    private void createSprint(long chatId, Message message, Action action, List<String> buttonNames) {
                String sprintName = message.getText();
                sprintService.createSprint(new Sprint(chatId, sprintName));
                lastActionList.addLastAction(chatId, action.getActionText());
                messageSender.sendMessage(message, action.getActionResponse(), buttonNames);
    }
}
