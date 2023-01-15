package task.manager.telegram.bot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import task.manager.telegram.bot.model.FinalMessage;
import task.manager.telegram.bot.service.SprintActioner;

@Component
public class Parser {

    private SprintActioner sprintActioner;

    @Autowired
    public Parser(SprintActioner sprintActioner) {
        this.sprintActioner = sprintActioner;
    }


    public FinalMessage giveWork(Update update){
       return sprintActioner.determineWork(update);
    }

}
