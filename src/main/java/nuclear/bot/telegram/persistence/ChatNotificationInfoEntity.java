package nuclear.bot.telegram.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "USER_NOTIFICATION_INFO")
@NoArgsConstructor
public class ChatNotificationInfoEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "CHAT_ID")
    private String chatId;

    @Column(name = "IS_EVERY_MORNING")
    private Boolean isEveryMorning;

    public ChatNotificationInfoEntity(String chatId, Boolean isEveryMorning) {
        this.chatId = chatId;
        this.isEveryMorning = isEveryMorning;
    }
}
