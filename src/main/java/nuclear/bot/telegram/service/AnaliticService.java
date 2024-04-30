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

        var messageMap = agentMessageRepository.findAll().stream()
                .filter(message -> agentNames.contains(message.getParserAgentName()))
                .collect(Collectors.groupingBy(AgentMessageEntity::getParserAgentName));
        log.info("Agent messages from db {}", messageMap);

        Map<String, Boolean> agentNameWithStatusMap = new TreeMap<>();
        messageMap.forEach((key, value) -> {
            if (!isNormalValuesInList(key, value)) {
                log.info("Alert state for agent {}", key);
                agentNameWithStatusMap.put(key, false);
            } else {
                agentNameWithStatusMap.put(key, true);
            }
        });

        return agentNameWithStatusMap;
    }

    /**
     * By agentName check that received values are in normal deviation
     */
    private boolean isNormalValuesInList(String agentMessageName, List<AgentMessageEntity> agentMessageEntityList) {
        var normalValueString = normalValueRepository.findNormalValueByParserAgentName(agentMessageName);
        log.info("found normal value {} for agent {}", normalValueString, agentMessageName);
        double normalValue = Double.parseDouble(normalValueString.getNormalValue());

        var result = agentMessageEntityList.stream()
                .filter(agentMessage -> Double.parseDouble(agentMessage.getMessage()) >= normalValue)
                .findFirst();
        return result.isEmpty();
    }
}
