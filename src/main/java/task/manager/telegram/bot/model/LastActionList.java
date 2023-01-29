package task.manager.telegram.bot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LastActionList {
    private static final LastActionList lastActionList = new LastActionList();
    private final List<Action> lastActions;

    public void addLastAction(long chatId, String lastAction){
        Optional<Action> actionByChatId = lastActions.stream().filter(action -> action.getChatId() == chatId).findFirst();
        Action newAction = new Action(chatId, lastAction);
        actionByChatId.ifPresentOrElse(action -> {
         lastActions.remove(action);
         lastActions.add(newAction);
        }, () -> {
            lastActions.add(newAction);
        });
    }

    public Optional<Action> getMaybeActionByChatId(long chatId){
        return lastActions.stream().filter(action -> action.getChatId() == chatId).findFirst();
    }


    private LastActionList(){
        this.lastActions = new ArrayList<>();
    }

    public static LastActionList getInstance(){
        return lastActionList;
    }

}
