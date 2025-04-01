package nuclear.bot.telegram;

import nuclear.bot.telegram.persistence.AgentMessageEntity;
import nuclear.bot.telegram.persistence.AgentMessageRepository;
import nuclear.bot.telegram.persistence.NormalValueEntity;
import nuclear.bot.telegram.persistence.NormalValueRepository;
import nuclear.bot.telegram.service.AnaliticService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnaliticServiceTest {

    @InjectMocks
    AnaliticService analiticService;

    @Mock AgentMessageRepository agentMessageRepository;
    @Mock NormalValueRepository normalValueRepository;

    @Test
    public void prepareDataTest(){
        when(normalValueRepository.findAll()).thenReturn(List.of(
                new NormalValueEntity(UUID.randomUUID(), "www.saveecobot.com", "10", "https://www.saveecobot.com/radiation-maps"),
                new NormalValueEntity(UUID.randomUUID(), "www.radon.ru", "20", "https://www.radon.ru/online-map/?city=6")));
        when(normalValueRepository.findNormalValueByParserAgentName("www.saveecobot.com")).thenReturn(new NormalValueEntity(UUID.randomUUID(), "www.saveecobot.com", "10", "https://www.saveecobot.com/radiation-maps"));
        when(normalValueRepository.findNormalValueByParserAgentName("www.radon.ru")).thenReturn(new NormalValueEntity(UUID.randomUUID(), "www.radon.ru", "20", "https://www.radon.ru/online-map/?city=6"));
        when(agentMessageRepository.findAll()).thenReturn(List.of(
                new AgentMessageEntity(UUID.randomUUID(), "www.saveecobot.com", OffsetDateTime.now(), "1"),
                new AgentMessageEntity(UUID.randomUUID(), "www.saveecobot.com", OffsetDateTime.now(), "2"),
                new AgentMessageEntity(UUID.randomUUID(),"www.not-existing", OffsetDateTime.now(), "3"),
                new AgentMessageEntity(UUID.randomUUID(),"www.radon.ru", OffsetDateTime.now(), "400")
        ));


        var resultMap = analiticService.prepareData();

        Assertions.assertEquals(2, resultMap.size());
        Assertions.assertFalse(resultMap.get("www.saveecobot.com"));
        Assertions.assertTrue(resultMap.get("www.radon.ru"));
        verify(normalValueRepository, times(1)).findAll();
        verify(normalValueRepository, times(2)).findNormalValueByParserAgentName(anyString());
        verify(agentMessageRepository, times(1)).findAll();
    }
}
