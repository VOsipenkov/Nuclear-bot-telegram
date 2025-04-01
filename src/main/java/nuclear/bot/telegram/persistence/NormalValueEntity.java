package nuclear.bot.telegram.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Table(name = "normal_value")
@NoArgsConstructor
@AllArgsConstructor
public class NormalValueEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "parseragentname")
    private String parserAgentName;

    @Column(name = "normalvalue")
    private String normalValue;

    @Column(name="parseragenturl")
    private String parserAgentUrl;
}
