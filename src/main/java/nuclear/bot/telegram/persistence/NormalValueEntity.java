package nuclear.bot.telegram.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Table(name = "normal_value")
@NoArgsConstructor
public class NormalValueEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Setter
    @Column(name = "parseragentname")
    private String parserAgentName;

    @Setter
    @Column(name = "normalvalue")
    private String normalValue;
}
