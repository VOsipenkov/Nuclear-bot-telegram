package nuclear.bot.telegram.bot;

import jakarta.annotation.PostConstruct;
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

    public Bot(@Value("${bot.token}") String token, @Value("${bot.username}") String botUserName) {
        super(token);
        this.botUserName = botUserName;
    }

    @PostConstruct
    public void registerCommands() {
        this.register(new StartCommand("start", "start"));
        this.register(new TimeCommand("time", "get current date time"));
        this.register(new AlarmOnlyPeriodCommand("/alarm", "alarm mode"));
        this.register(new EveryEveningPeriodCommand("/evening", "every evening notification"));
        this.register(new EveryMorningPeriodCommand("/morning", "every evening notification"));
        this.register(new EveryHourPeriodCommand("/hour", "every hour notification"));
        this.register(new CurrentStateCommand("/minute", "every minute notification"));
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
