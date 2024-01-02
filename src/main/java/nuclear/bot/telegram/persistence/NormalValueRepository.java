package nuclear.bot.telegram.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NormalValueRepository extends JpaRepository<NormalValueEntity, UUID> {
    NormalValueEntity findNormalValueByParserAgentName(String parserAgentName);
}
