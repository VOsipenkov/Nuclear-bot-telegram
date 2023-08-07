package nuclear.bot.telegram.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TimeCommand extends BotCommand {

    public TimeCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        var sendMessage =  SendMessage.builder()
                .chatId(chat.getId().toString())
                .text(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ssXXX")))
                .build();
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
