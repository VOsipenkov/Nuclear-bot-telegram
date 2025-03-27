package nuclear.bot.telegram.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "USER_NOTIFICATION_INFO")
@NoArgsConstructor
public class UserNotificationInfoEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "CHAT_ID")
    private String chatId;

    @Column(name = "IS_EVERY_MORNING")
    private Boolean isEveryMorning;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    public UserNotificationInfoEntity(String chatId, Boolean isEveryMorning, String userName, String firstName, String lastName) {
        this.chatId = chatId;
        this.isEveryMorning = isEveryMorning;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
