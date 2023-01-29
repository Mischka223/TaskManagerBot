package task.manager.telegram.bot.utils;

import java.util.List;

public interface Action {
    String getActionText();
    String getActionResponse();
    List<Action> getActions();
    List<Action> getMessageActions();
}
