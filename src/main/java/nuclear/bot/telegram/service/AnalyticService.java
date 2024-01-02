package nuclear.bot.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuclear.bot.telegram.persistence.AgentMessageEntity;
import nuclear.bot.telegram.persistence.AgentMessageRepository;
import nuclear.bot.telegram.persistence.NormalValueEntity;
import nuclear.bot.telegram.persistence.NormalValueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnalyticService {
    private final AgentMessageRepository agentMessageRepository;
    private final NormalValueRepository normalValueRepository;

    public boolean isNormalStateOfData() {
        var agentNames = normalValueRepository.findAll().stream()
                .map(NormalValueEntity::getParserAgentName).toList();

        var messageMap = agentMessageRepository.findAll().stream()
                .filter(message -> agentNames.contains(message.getParserAgentName()))
                .collect(Collectors.groupingBy(AgentMessageEntity::getParserAgentName));
        log.info("messageMap {}", messageMap);

        AtomicBoolean isAlertState = new AtomicBoolean(false);
        messageMap.forEach((key, value) -> {
            if (!isNormalValuesInList(key, value)) {
                log.info("ALERT STATE!!");
                log.info("Agent {} detect deviation", key);
                isAlertState.set(true);
            }
        });

        return !isAlertState.get();
    }

    private boolean isNormalValuesInList(String agentMessageName, List<AgentMessageEntity> agentMessageEntityList) {
        var normalValueString = normalValueRepository.findNormalValueByParserAgentName(agentMessageName);
        log.info("normalValueString {}", normalValueString);
        double normalValue = Double.parseDouble(normalValueString.getNormalValue());

        var result = agentMessageEntityList.stream()
                .filter(agentMessage -> Double.parseDouble(agentMessage.getMessage()) >= normalValue)
                .findFirst();
        return result.isEmpty();
    }
}
