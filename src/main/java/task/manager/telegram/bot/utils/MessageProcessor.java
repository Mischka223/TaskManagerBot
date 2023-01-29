package task.manager.telegram.bot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import task.manager.telegram.bot.model.LastActionList;
import task.manager.telegram.bot.service.Actioner;

import java.util.Optional;

@Component
public class MessageProcessor {

    private final Actioner sprintActioner;
    private final Actioner defaultActioner;
    private static final LastActionList lastActionList = LastActionList.getInstance();

    @Autowired
    public MessageProcessor(@Qualifier(value = "sprintActioner") Actioner sprintActioner,
                            @Qualifier(value = "defaultActioner") Actioner defaultActioner) {
        this.sprintActioner = sprintActioner;
        this.defaultActioner = defaultActioner;
    }


    public void processMessage(Update update) {
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        Optional<DefaultAction> maybeDefaultAction = DefaultAction.maybeDefaultAction(text);
        maybeDefaultAction.ifPresent(defaultAction ->
                defaultActioner.determineWork(update, defaultAction));

        if (checkIsActionCreateSprint(chatId)) {
            sprintActioner.determineWork(update, SprintAction.SPRINT_CREATION);
        }
        lastActionList.addLastAction(chatId,text);

        Optional<SprintAction> sprintAction = SprintAction.maybeSprintAction(text);
        sprintAction.ifPresent(action -> sprintActioner.determineWork(update, action));
    }


    private boolean checkIsActionCreateSprint(long chatId) {
        return lastActionList.getMaybeActionByChatId(chatId)
                .filter(lastAction ->
                        lastAction.getActionName().equals(SprintAction.CREATE_SPRINT.getActionText()))
                .isPresent();
    }
}
