package nuclear.bot.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuclear.bot.telegram.persistence.AgentMessageEntity;
import nuclear.bot.telegram.persistence.AgentMessageRepository;
import nuclear.bot.telegram.persistence.NormalValueEntity;
import nuclear.bot.telegram.persistence.NormalValueRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnaliticService {
    private final AgentMessageRepository agentMessageRepository;
    private final NormalValueRepository normalValueRepository;

    /**
     * Method returns result for each agent
     */
    public Map<String, Boolean> prepareData() {
        var agentNames = normalValueRepository.findAll().stream()
                .map(NormalValueEntity::getParserAgentName).toList();
        log.info("Agent names from normalValueRepository {}", agentNames);

        var messageMap = agentMessageRepository.findAll().stream()
                .filter(message -> agentNames.contains(message.getParserAgentName()))
                .collect(Collectors.groupingBy(AgentMessageEntity::getParserAgentName));
        log.debug("Agent messages from db size {}, messages {}", messageMap.size(), messageMap);

        Map<String, Boolean> agentNameWithStatusMap = new TreeMap<>();
        messageMap.forEach((key, value) -> agentNameWithStatusMap.put(key, isNormalValuesInList(key, value)));

        log.info("Agent statuses {}", agentNameWithStatusMap);
        return agentNameWithStatusMap;
    }

    /**
     * By agentName check that received values are in normal deviation
     */
    private boolean isNormalValuesInList(String agentMessageName, List<AgentMessageEntity> agentMessageEntityList) {
        var normalValueString = normalValueRepository.findNormalValueByParserAgentName(agentMessageName);
        double normalValue = Double.parseDouble(normalValueString.getNormalValue());
        log.info("Found normal value {} for agent {}", normalValueString, agentMessageName);
        var result = agentMessageEntityList.stream()
                .filter(agentMessage -> Double.parseDouble(agentMessage.getMessage()) >= normalValue)
                .findFirst();
        result.ifPresent(agentMessageEntity -> log.info("Found alert message value {} for agent {}", agentMessageEntity, agentMessageName));
        if (result.isEmpty()) log.info("No alerts for agent {}", agentMessageName);
        return result.isPresent();
    }
}
