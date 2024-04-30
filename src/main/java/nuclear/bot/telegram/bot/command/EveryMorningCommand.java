package nuclear.bot.telegram.bot.command;

import lombok.extern.slf4j.Slf4j;
import nuclear.bot.telegram.persistence.ChatNotificationInfoEntity;
import nuclear.bot.telegram.persistence.ChatNotificationInfoRepository;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class EveryMorningCommand extends BotCommand {
    private final ChatNotificationInfoRepository chatNotificationInfoRepository;

    public EveryMorningCommand(String commandIdentifier, String description,
                               ChatNotificationInfoRepository chatNotificationInfoRepository) {
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
                                    new ChatNotificationInfoEntity(chat.getId().toString(), true));
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
            log.error("Can't send message to telegram");
            throw new RuntimeException(e);
        }
    }
}
