import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static String loadTgToken(String[] args) {
        try (Scanner sc = new Scanner(new FileInputStream(args[0]))) {
            return sc.next();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Token is not found");
        }
    }

    public static void main(String[] args) {
        final TelegramBot bot = new TelegramBot(loadTgToken(args));

        bot.setUpdatesListener(new UpdatesListener() {
            public int process(List<Update> list) {
                for (Update upd : list) {
                    System.out.println("Chat id: " + upd.message().chat().id());
                    bot.execute(new SendMessage(upd.message().chat().id(), upd.message().text()));
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }
}
