package pl.jawa.psinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.jawa.psinder.entity.Connection;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query (value = "SELECT * FROM Connection WHERE owner_id = :ownerId", nativeQuery = true)
    List<Connection> findByOwnerId(@Param("ownerId") int ownerId);

    @Query (value = "SELECT * FROM Connection WHERE walker_id = :walkerId", nativeQuery = true)
    List<Connection> findByWalkerId(@Param("walkerId") int walkerId);

    @Query (value = "SELECT * FROM Connection WHERE status = :status", nativeQuery = true)
    List<Connection> findByStatus(@Param("status") String status);
}
