package task.manager.telegram.bot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import task.manager.telegram.bot.model.LastActionList;
import task.manager.telegram.bot.service.DefaultActioner;
import task.manager.telegram.bot.service.SprintActioner;

import java.util.Optional;

@Component
public class Parser {

    private final SprintActioner sprintActioner;
    private final DefaultActioner defaultActioner;
    private static final LastActionList lastActionList = LastActionList.getInstance();

    @Autowired
    public Parser(@Qualifier(value = "sprintActioner") SprintActioner sprintActioner,
                  @Qualifier(value = "defaultActioner") DefaultActioner defaultActioner) {
        this.sprintActioner = sprintActioner;
        this.defaultActioner = defaultActioner;
    }


    public void giveWork(Update update){
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        Optional<DefaultAction> maybeDefaultAction = DefaultAction.maybeDefaultAction(text);
        maybeDefaultAction.ifPresent(defaultAction -> defaultActioner.determineWork(update,defaultAction));

        if (!text.equals(SprintAction.GET_ALL_SPRINT.getActionText()) && lastActionList.getMaybeActionByChatId(chatId).isPresent()){
            sprintActioner.determineWork(update,SprintAction.SPRINT_CREATION);
        }

        Optional<SprintAction> sprintAction = SprintAction.maybeSprintAction(text);
        sprintAction.ifPresent(action -> sprintActioner.determineWork(update,action));
    }
}
