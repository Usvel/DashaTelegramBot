import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot extends TelegramLongPollingBot {

    private static final String TOKEN = "1930036735:AAFR-F1xz_PtwTSPQpWcVL46DaHt9ONTmCc";
    private static final String NAME = "/";

    public Bot(DefaultBotOptions options) {
        super(options);
    }

    public String getBotToken() {
        return TOKEN;
    }

    public String getBotUsername() {
        return NAME;
    }

    public void sendMessage(Message message, String text, boolean message_otvet) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        if (message_otvet) sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void showButton(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        //sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText("Я могу скинуть комлементы от Роди или поддержать тебя" +
                "\nИли можешь написать 'Что делаешь?' и родион ответит, а также спросит чем занята ты))");
        try {
            setButton(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendMessageList(Message message, List<String> list,boolean otvet) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        if (otvet) sendMessage.setReplyToMessageId(message.getMessageId());
        Random random = new Random();
        int random_slovo = random.nextInt(list.size());
        sendMessage.setText(list.get(random_slovo));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            long chat_id = update.getMessage().getChatId();
            switch (message.getText()) {
                case "/help": {
                    sendMessage(message, "Я бот преднозначиный для Даши, напиши /start" +
                            "\nИли спроси и Родион ответит 'Что делаешь?'", false);
                }
                break;
                case "/start": {
                    showButton(message);
                }
                break;
                case "/комплимент": {
                    sendMessageList(message, ListSlov.Compliments(), false);
                }
                break;
                case "/поддержка": {
                    sendMessageList(message, ListSlov.SlovaSupport(), false);
                }
                break;
                case "Что делаешь?": {
                    sendMessageList(message, ListSlov.SlovaRodion(), false);
                    sendMessage(message, "А ты?", true);
                }
                break;
//                default: {
//                    sendMessage(message, "Я бот преднозначиный для Даши, напиши /start" +
//                            "\nИли спроси и Родион ответит 'Что делаешь?'", true);
//                }
            }
        }
    }

    public void setButton(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList= new ArrayList<KeyboardRow>();
        KeyboardRow keyboardRow = new KeyboardRow();

        keyboardRow.add(new KeyboardButton("/комплимент"));
        keyboardRow.add(new KeyboardButton("/поддержка"));

        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
