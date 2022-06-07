package pl.jawa.psinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import pl.jawa.psinder.entity.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT * FROM chat WHERE connection_id = :connectionId", nativeQuery = true)
    List<Chat> findChatsByConnectionId(@Param("connectionId") long connectionId);

    @Query(value = "SELECT * FROM chat WHERE id = :id", nativeQuery = true)
    Chat findById(@Param("id") long id);
}
