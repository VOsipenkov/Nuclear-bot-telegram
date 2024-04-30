package nuclear.bot.telegram.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuclear.bot.telegram.bot.command.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Component
public class Bot extends TelegramLongPollingCommandBot {

    private String botUserName;
    private final CurrentStateCommand currentStateCommand;
    private final EveryMorningCommand everyMorningCommand;

    public Bot(@Value("${bot.token}") String token,
               @Value("${bot.username}") String botUserName,
               CurrentStateCommand currentStateCommand,
               EveryMorningCommand everyMorningCommand) {
        super(token);
        this.botUserName = botUserName;
        this.currentStateCommand = currentStateCommand;
        this.everyMorningCommand = everyMorningCommand;
    }

    @PostConstruct
    public void registerCommands() {
        this.register(new StartCommand("start", "start"));
        this.register(new TimeCommand("time", "get current date time"));
        this.register(currentStateCommand);
        this.register(everyMorningCommand);
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        log.info("Получено сообщение {}", update.getMessage().getText());
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
        log.info("Получено сообщение {}", updates.get(0).getMessage().getText());
    }
}
