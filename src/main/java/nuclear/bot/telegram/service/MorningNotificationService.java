package nuclear.bot.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuclear.bot.telegram.bot.Bot;
import nuclear.bot.telegram.bot.command.CurrentStateCommand;
import nuclear.bot.telegram.persistence.ChatNotificationInfoEntity;
import nuclear.bot.telegram.persistence.ChatNotificationInfoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MorningNotificationService {
    private final ChatNotificationInfoRepository userNotificationInfoRepository;
    private final AnaliticService analiticService;
    private final ReportService reportService;
    private final Bot bot;

    /**
     * If already morning 9 a.m. - job will notify subscribed users
     */
    @Scheduled(cron = "0 0 9,15,19,22 * * *") // every day at 9:00 a.m. notification
//    @Scheduled(cron = "0 * * * * *") // every minute for debug
    public void everyMorningJob() {
        log.info("Every morning job notification job started");
        var agentNameWithStatusMap = analiticService.prepareData();
        var report = reportService.createReport(agentNameWithStatusMap);

        userNotificationInfoRepository.findAll().stream()
                .filter(ChatNotificationInfoEntity::getIsEveryMorning)
                .forEach(chatInfo -> {
                    var sendMessage = SendMessage.builder()
                            .chatId(chatInfo.getChatId())
                            .text(report).build();
                    try {
                        bot.execute(sendMessage);
                    } catch (TelegramApiException e) {
                        log.error("Can't send every morning message to telegram");
                        throw new RuntimeException(e);
                    }
                });
    }
}
