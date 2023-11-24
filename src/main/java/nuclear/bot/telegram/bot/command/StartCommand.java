package nuclear.bot.telegram.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class StartCommand extends BotCommand {

    public StartCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        var answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(user.getFirstName()+ " " + user.getLastName() + " successfully subscribe to nuclear bot");
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
