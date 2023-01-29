package task.manager.telegram.bot.service;

import org.springframework.stereotype.Service;
import task.manager.telegram.bot.model.Sprint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SprintService {

    private static final List<Sprint> sprints = new ArrayList<>();


    public void createSprint(Sprint sprint){
        sprints.add(sprint);
    }

    public List<Sprint> getSprintsByChatId(long chatId){
        return sprints.stream()
                .filter(sprint -> sprint.getChatId() == chatId)
                .collect(Collectors.toList());
    }
}
