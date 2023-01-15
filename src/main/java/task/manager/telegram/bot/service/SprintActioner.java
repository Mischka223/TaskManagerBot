package task.manager.telegram.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import task.manager.telegram.bot.model.FinalMessage;
import task.manager.telegram.bot.model.LastActionList;
import task.manager.telegram.bot.model.Sprint;

import java.util.List;

@Component
public class SprintActioner {
    private static final String CREATE_SPRINT = "створити спрінт";
    private static final String GET_SPRINTS = "переглянути спрінти";

    private final SprintService sprintService;
    private static final LastActionList lastActionList = new LastActionList();

    @Autowired
    public SprintActioner(SprintService sprintService) {
        this.sprintService = sprintService;
    }


    public FinalMessage determineWork(Update update) {
        Message message = update.getMessage();
        if (message != null && update.getMessage().hasText()) {
            Long chatId = message.getChatId();
            System.out.println(message.getText());
            if (message.getText().equals(GET_SPRINTS)) {
                lastActionList.addLastAction(chatId, GET_SPRINTS);
                List<Sprint> sprintsByChatId = sprintService.getSprintsByChatId(message.getChatId());
                if (sprintsByChatId.isEmpty()) {
                    return new FinalMessage(message, "На даний момент у вас немає спрінтів", List.of(CREATE_SPRINT));
                }
                return new FinalMessage(message, sprintsByChatId.toString(), List.of(CREATE_SPRINT));
            }

            lastActionList.getMaybeActionByChatId(chatId).ifPresent(action -> {
                if (action.getActionName().equals(CREATE_SPRINT)) {
                    String sprintName = message.getText();
                    sprintService.createSprint(new Sprint(chatId, sprintName));
                }
            });
            if (message.getText().equals(CREATE_SPRINT)) {
                lastActionList.addLastAction(chatId, CREATE_SPRINT);
                return new FinalMessage(message, "Введи ім'я спрінта", List.of(GET_SPRINTS));
            }
        }
       return new FinalMessage(message,"Ви створили спрінт",List.of(GET_SPRINTS));
    }
}
