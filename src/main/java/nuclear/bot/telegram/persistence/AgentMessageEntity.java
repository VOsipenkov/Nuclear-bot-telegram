package nuclear.bot.telegram.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "AGENT_MESSAGE")
@NoArgsConstructor
public class AgentMessageEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="parseragentname")
    private String parserAgentName;

    @Column(name="messagedatetime")
    private OffsetDateTime messageDateTime;

    @Column(name="message")
    private String message;
}
