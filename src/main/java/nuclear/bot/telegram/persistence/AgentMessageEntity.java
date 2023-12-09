package nuclear.bot.telegram.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "AGENT_MESSAGE")
@NoArgsConstructor

public class AgentMessageEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Setter
    private String parserAgentName;

    @Setter
    private OffsetDateTime messageDateTime;

    @Setter
    private String message;
}
