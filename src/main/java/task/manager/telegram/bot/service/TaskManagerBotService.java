package task.manager.telegram.bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskManagerBotService extends TelegramLongPollingBot {

    private static final String START_BUTTON = "/start";
    private static final String CREATE_SPRINT = "/створити спрінт";
    private static final String GET_SPRINTS = "/переглянути спрінти";

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
                sendMsg(message, "Ітак, пігнали складати твій план", List.of(CREATE_SPRINT));
            }
            if (message.getText().equals(CREATE_SPRINT)) {
                sendMsg(message, "Як називається ваш спрінт?");
            }
        }
    }

    private void sendMsg(Message message, String text) {
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

    private void sendMsg(Message message, String text, List<String> buttonsNames) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        sendMessage.enableMarkdown(true);
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
