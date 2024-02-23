package nuclear.bot.telegram.bot.command;

import lombok.extern.slf4j.Slf4j;
import nuclear.bot.telegram.service.AnaliticService;
import nuclear.bot.telegram.service.ReportService;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Slf4j
public class CurrentStateCommand extends BotCommand {
    private final AnaliticService analyticService;
    private final ReportService reportService;

    public CurrentStateCommand(String commandIdentifier, String description,
                               AnaliticService analyticService, ReportService reportService) {
        super(commandIdentifier, description);
        this.analyticService = analyticService;
        this.reportService = reportService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Map<String, Boolean> agentNameWithStatusMap = analyticService.prepareData();
        var report = reportService.createReport(agentNameWithStatusMap);
        var sendMessage = SendMessage.builder()
                .chatId(chat.getId().toString())
                .text(report).build();
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Can't send message to telegram");
            throw new RuntimeException(e);
        }
    }
}
