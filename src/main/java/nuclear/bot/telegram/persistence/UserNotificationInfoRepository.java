package nuclear.bot.telegram.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserNotificationInfoRepository extends JpaRepository<UserNotificationInfoEntity,UUID> {
    Optional<UserNotificationInfoEntity> findFirstByChatId(String chatId);
}
