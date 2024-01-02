package nuclear.bot.telegram.bot.command;

import lombok.extern.slf4j.Slf4j;
import nuclear.bot.telegram.service.AnalyticService;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class CurrentStateCommand extends BotCommand {
    private final AnalyticService analyticService;
    private static final String OK = "Radiation value is ok..";
    private static final String ALARM = "Alarm! Radiation value is danger! ";

    public CurrentStateCommand(String commandIdentifier, String description, AnalyticService analyticService) {
        super(commandIdentifier, description);
        this.analyticService = analyticService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        boolean isNormal = analyticService.isNormalStateOfData();
        var sendMessage = SendMessage.builder()
                .chatId(chat.getId().toString())
                .text(isNormal? OK : ALARM)
                .build();
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
