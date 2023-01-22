package task.manager.telegram.bot.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public enum SprintAction implements Action {
    CREATE_SPRINT("створити спрінт", "Введи ім'я спрінта", Collections.emptyList()),
    GET_ALL_SPRINT("переглянути спріти", "На даний момент у тебе немає спрінтів", List.of(CREATE_SPRINT)),
    SPRINT_CREATION("створення спрінта", "Ви створили спрінт", List.of(CREATE_SPRINT,GET_ALL_SPRINT));

    private final String actionText;
    private final String actionResponse;
    private final List<Action> actions;

    public String getActionResponse() {
        return this.actionResponse;
    }

    public String getActionText() {
        return this.actionText;
    }

    public List<Action> getActions(){
        return this.actions;
    }
    SprintAction(String actionText, String actionResponse, List<Action> actions) {
        this.actionText = actionText;
        this.actionResponse = actionResponse;
        this.actions = actions;
    }

    public static Optional<SprintAction> maybeSprintAction(String text) {
        return Arrays.stream(SprintAction.values())
                .filter(sprintAction -> sprintAction.actionText.equals(text))
                .findFirst();
    }
}
