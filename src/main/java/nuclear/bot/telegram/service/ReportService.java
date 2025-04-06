package nuclear.bot.telegram.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReportService {

    public String createReport(Map<String, Boolean> agentNameWithStatusMap) {
        StringBuilder result = new StringBuilder();
        agentNameWithStatusMap.forEach((k, v) -> result.append(new Report(k, v)));
        return result.toString();
    }

    @AllArgsConstructor
    static class Report {
        private String agentUrl;
        private Boolean status;

        @Override
        public String toString() {
            return agentUrl + "  status: " + (status ? "ALARM!!" : "OK") + System.lineSeparator();
        }
    }
}
