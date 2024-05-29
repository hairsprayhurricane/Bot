import org.telegram.telegrambots.bots.TelegramLongPollingBot;
<<<<<<< HEAD
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
=======
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private InlineKeyboardButton next = InlineKeyboardButton.builder()
            .text("Next").callbackData("next")
            .build();

    private InlineKeyboardButton back = InlineKeyboardButton.builder()
            .text("Back").callbackData("back")
            .build();

    private InlineKeyboardButton url = InlineKeyboardButton.builder()
            .text("Tutorial")
            .url("https://core.telegram.org/bots/api")
            .build();
    private boolean screaming = false;
    private boolean whisper = false;

    private InlineKeyboardMarkup keyboardM1 = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(next)).build();;
    private InlineKeyboardMarkup keyboardM2 = InlineKeyboardMarkup.builder()
          .keyboardRow(List.of(back))
            .keyboardRow(List.of(url))
            .build();

>>>>>>> 627ed0e (Initial commit)

    @Override
    public String getBotUsername() {
        return "ParvumBot";
    }

    @Override
    public String getBotToken() {
        return "7304795454:AAGW8R4Vx9UjDKEiudakT9TS_4yZdFZ0kGA";
    }

<<<<<<< HEAD
=======
    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb){
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup(kb).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

>>>>>>> 627ed0e (Initial commit)
    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

<<<<<<< HEAD
        System.out.println("Id: " + id + " text: " + msg.getText() + " from: " + user);
        //sendText(id, msg.getText());
        copyMessage(user.getId(), msg.getMessageId());
=======

        if(screaming)                            //If we are screaming
            scream(id, update.getMessage());     //Call a custom method
        else if(whisper)                            //If we are screaming
            whisper(id, update.getMessage());
        else
            copyMessage(user.getId(), msg.getMessageId());
        System.out.println("Id: " + id + " text: " + msg.getText() + " from: " + user);


//        if(msg.isCommand()){
//            if(msg.getText().equals("/scream")) {        //If the command was /scream, we switch gears
//                whisper = false;
//                screaming = true;
//            }
//            else if (msg.getText().equals("/whisper")) {  //Otherwise, we return to normal
//                screaming = false;
//                whisper = true;
//            }
//            else if (msg.getText().equals("/off")) {
//                screaming = false;
//                whisper = false;
//            }
//        }
        var txt = msg.getText();
        if(msg.isCommand()) {
            if (txt.equals("/scream")) {
                whisper = false;
                screaming = true;
            }
            else if (txt.equals("/whisper")) {
                screaming = false;
                whisper = true;
            }
            else if (txt.equals("/off")) {
                screaming = false;
                whisper = false;
            }
            else if (txt.equals("/menu")) {
                sendMenu(id, "<b>Menu 1</b>", keyboardM1);
                sendMenu(id, "<b>Menu 2</b>", keyboardM2);
            }
        }
    }

    private void buttonTap(Long id, String queryId, String data, int msgId) throws TelegramApiException {

        EditMessageText newTxt = EditMessageText.builder()
                .chatId(id.toString())
                .messageId(msgId).text("").build();

        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
                .chatId(id.toString()).messageId(msgId).build();

        if(data.equals("next")) {
            newTxt.setText("MENU 2");
            newKb.setReplyMarkup(keyboardM2);
        } else if(data.equals("back")) {
            newTxt.setText("MENU 1");
            newKb.setReplyMarkup(keyboardM1);
        }

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(queryId).build();

        execute(close);
        execute(newTxt);
        execute(newKb);
    }

    private void scream(Long id, Message msg) {
        if(msg.hasText())
            sendText(id, msg.getText().toUpperCase());
        else
            copyMessage(id, msg.getMessageId());  //We can't really scream a sticker
    }
    private void whisper(Long id, Message msg) {
        if(msg.hasText())
            sendText(id, msg.getText().toLowerCase());
        else
            copyMessage(id, msg.getMessageId());  //We can't really scream a sticker
>>>>>>> 627ed0e (Initial commit)
    }

    public void copyMessage(Long who, Integer msgId){
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString())  //We copy from the user
                .chatId(who.toString())      //And send it back to him
                .messageId(msgId)            //Specifying what message
                .build();
        try {
            execute(cm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

}