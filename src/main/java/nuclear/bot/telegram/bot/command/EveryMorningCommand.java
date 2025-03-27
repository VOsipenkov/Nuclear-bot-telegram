package nuclear.bot.telegram.bot.command;

import lombok.extern.slf4j.Slf4j;
import nuclear.bot.telegram.persistence.UserNotificationInfoEntity;
import nuclear.bot.telegram.persistence.UserNotificationInfoRepository;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class EveryMorningCommand extends BotCommand {
    private final UserNotificationInfoRepository chatNotificationInfoRepository;

    public EveryMorningCommand(String commandIdentifier, String description,
                               UserNotificationInfoRepository chatNotificationInfoRepository) {
        super(commandIdentifier, description);
        this.chatNotificationInfoRepository = chatNotificationInfoRepository;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        // If found chat notification info - invert of notification value or just save new chat info
        chatNotificationInfoRepository.findFirstByChatId(chat.getId().toString())
                .ifPresentOrElse(
                        (chatInfo) -> {
                            chatInfo.setIsEveryMorning(!chatInfo.getIsEveryMorning());
                            chatNotificationInfoRepository.save(chatInfo);
                        },
                        () -> {
                            chatNotificationInfoRepository.save(
                                    new UserNotificationInfoEntity(chat.getId().toString(), true, user.getUserName(),
                                            user.getFirstName(), user.getLastName()));
                        });

        // Prepare message for chat
        var sendMessage = SendMessage.builder()
                .chatId(chat.getId().toString())
                .text("Every morning notification turn to " +
                        chatNotificationInfoRepository.findFirstByChatId(chat.getId().toString()).get().getIsEveryMorning())
                .build();

        // Send message to chat
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Can't send message to telegram {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
