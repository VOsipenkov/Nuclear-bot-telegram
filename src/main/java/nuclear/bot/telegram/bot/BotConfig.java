package nuclear.bot.telegram.bot;

import nuclear.bot.telegram.bot.command.CurrentStateCommand;
import nuclear.bot.telegram.bot.command.EveryMorningCommand;
import nuclear.bot.telegram.persistence.ChatNotificationInfoRepository;
import nuclear.bot.telegram.service.AnaliticService;
import nuclear.bot.telegram.service.ReportService;
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
    public CurrentStateCommand currentStateCommand(AnaliticService analyticService, ReportService reportService) {
        return new CurrentStateCommand("/current", "current notification",
                analyticService, reportService);
    }

    @Bean
    public EveryMorningCommand everyMorningCommand(ChatNotificationInfoRepository chatNotificationInfoRepository) {
        return new EveryMorningCommand("/morning", "morning notification",
                chatNotificationInfoRepository);
    }
}
