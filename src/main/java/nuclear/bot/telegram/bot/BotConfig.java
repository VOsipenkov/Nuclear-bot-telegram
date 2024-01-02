package nuclear.bot.telegram.bot;

import nuclear.bot.telegram.bot.command.CurrentStateCommand;
import nuclear.bot.telegram.service.AnalyticService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ComponentScan("nuclear.bot.telegram.bot")
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBots(Bot bot) throws TelegramApiException {
        var bots = new TelegramBotsApi(DefaultBotSession.class);
        bots.registerBot(bot);
        return bots;
    }

    @Bean
    public CurrentStateCommand currentStateCommand(AnalyticService analyticService){
        return new CurrentStateCommand("/current", "current notification", analyticService);
    }
}
