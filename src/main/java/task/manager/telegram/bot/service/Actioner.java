package task.manager.telegram.bot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import task.manager.telegram.bot.utils.Action;

public interface Actioner {

    void determineWork(Update update, Action action);
}
