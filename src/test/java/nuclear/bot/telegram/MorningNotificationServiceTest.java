package nuclear.bot.telegram;

import nuclear.bot.telegram.bot.Bot;
import nuclear.bot.telegram.persistence.UserNotificationInfoRepository;
import nuclear.bot.telegram.service.AnaliticService;
import nuclear.bot.telegram.service.MorningNotificationService;
import nuclear.bot.telegram.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MorningNotificationServiceTest {

    @InjectMocks
    MorningNotificationService morningNotificationService;

    @Mock
    UserNotificationInfoRepository userNotificationInfoRepository;
    @Mock AnaliticService analiticService;
    @Mock ReportService reportService;
    @Mock Bot bot;

    @Test
    public void test(){

    }
}
