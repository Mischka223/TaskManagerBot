package task.manager.telegram.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import task.manager.telegram.bot.model.FinalMessage;
import task.manager.telegram.bot.utils.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskManagerBotService extends TelegramLongPollingBot {

    private static final String START_BUTTON = "/start";
    private final Parser parser;

    private final SprintService sprintService;

    @Autowired
    public TaskManagerBotService(Parser parser, SprintService sprintService) {
        this.parser = parser;
        this.sprintService = sprintService;
    }

    @Override
    public String getBotUsername() {
        return "TaskManagerBot";
    }

    @Override
    public String getBotToken() {
        return "5414244410:AAEVVTT_QHZW_dpGRIzVlhPnAe_jOSXF7Lg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && update.getMessage().hasText()) {
            if (message.getText().equals(START_BUTTON)) {
                sendMsg(message, "Ітак, пігнали складати твій план", List.of("створити спрінт", "всі спрінти"));
                return;
            }
            FinalMessage finalMessage = parser.giveWork(update);
            List<String> buttons = finalMessage.getButtons();

            if (buttons.isEmpty()) {
                sendMsg(finalMessage.getMessage(), finalMessage.getMessageText());
            } else {
                sendMsg(finalMessage.getMessage(), finalMessage.getMessageText(), buttons);
            }
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        try {
            execute(sendMessage);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text, List<String> buttonsNames) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        sendMessage.enableMarkdown(true);
        System.out.println(sendMessage);
        setButtons(sendMessage, buttonsNames);
        try {
            execute(sendMessage);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }


    private void setButtons(SendMessage sendMessage, List<String> buttonsNames) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();

        List<KeyboardButton> keyboardButtons = buttonsNames.stream()
                .map(KeyboardButton::new)
                .collect(Collectors.toList());

        keyboardRow.addAll(keyboardButtons);

        rows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
}
