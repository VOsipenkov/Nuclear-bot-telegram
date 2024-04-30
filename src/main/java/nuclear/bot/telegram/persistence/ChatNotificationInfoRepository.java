package nuclear.bot.telegram.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatNotificationInfoRepository extends JpaRepository<ChatNotificationInfoEntity,UUID> {
    Optional<ChatNotificationInfoEntity> findFirstByChatId(String chatId);
}
