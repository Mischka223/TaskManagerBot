package task.manager.telegram.bot.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum DefaultAction implements Action {
    START("/start", "Ітак, пігнали складати твій план",
            List.of(SprintAction.CREATE_SPRINT,SprintAction.GET_ALL_SPRINT));

    private final String actionText;
    private final String actionResponse;
    private final List<Action> actions;

    DefaultAction(String actionText, String actionResponse,List<Action> actions) {
        this.actionText = actionText;
        this.actionResponse = actionResponse;
        this.actions = actions;
    }

    public String getActionText(){
        return this.actionText;
    }

    public String getActionResponse(){
        return this.actionResponse;
    }

    public List<Action> getActions(){
        return this.actions;
    }

    public static Optional<DefaultAction> maybeDefaultAction(String text) {
        return Arrays.stream(DefaultAction.values())
                .filter(defaultAction -> defaultAction.actionText.equals(text))
                .findFirst();
    }
}
