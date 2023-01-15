package task.manager.telegram.bot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LastActionList {
    private List<Action> lastActions;

    public void addLastAction(long chatId, String lastAction){
        Optional<Action> actionByChatId = lastActions.stream().filter(action -> action.getChatId() == chatId).findFirst();
        Action newAction = new Action(chatId, lastAction);
        actionByChatId.ifPresentOrElse(action -> {
            System.out.println("IF LAST ACTION PRESENT FOR CHAT ID =" + action + "| NEW ACTION = " + newAction);
         lastActions.remove(action);
         lastActions.add(newAction);
        }, () -> {
            System.out.println("ACTION DOESN'T EXISTS FOR CHAT ID | NEW ACTION = " + newAction);
            lastActions.add(newAction);
        });
    }

    public Optional<Action> getMaybeActionByChatId(long chatId){
        return lastActions.stream().filter(action -> action.getChatId() == chatId).findFirst();
    }


    public LastActionList(){
        lastActions = new ArrayList<>();
    }
}
