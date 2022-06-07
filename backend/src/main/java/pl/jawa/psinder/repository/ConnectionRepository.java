package pl.jawa.psinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.jawa.psinder.entity.Connection;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query (value = "SELECT * FROM connection WHERE owner_id = :ownerId ORDER BY field(status, 'accepted', 'waiting', 'canceled')", nativeQuery = true)
    List<Connection> findByOwnerId(@Param("ownerId") long ownerId);

    @Query (value = "SELECT * FROM connection WHERE walker_id = :walkerId ORDER BY field(status, 'accepted', 'waiting', 'canceled')", nativeQuery = true)
    List<Connection> findByWalkerId(@Param("walkerId") long walkerId);

    @Query (value = "SELECT * FROM connection WHERE owner_id = :ownerId AND status = :status", nativeQuery = true)
    List<Connection> findByStatus(@Param("ownerId") long ownerId, @Param("status") String status);

    @Query (value = "SELECT connection.* " +
            "FROM connection " +
            "JOIN pet ON connection.pet_id = pet.id " +
            "JOIN petaddress ON connection.pet_id = petaddress.pet_id " +
            "WHERE walker_id = :walkerId AND " +
            "(lower(pet.name) LIKE CONCAT('%',:keyword,'%') OR lower(pet.race) LIKE CONCAT('%',:keyword,'%') OR lower(petaddress.city) LIKE CONCAT('%',:keyword,'%') OR lower(petaddress.street) LIKE CONCAT('%',:keyword,'%')) " +
            "ORDER BY field(status, 'accepted', 'waiting', 'canceled')", nativeQuery = true)
    List<Connection> findByWalkerIdWithKeyWord(@Param("walkerId") long walkerId, @Param("keyword") String keyword);

    @Query (value = "SELECT connection.* " +
            "FROM connection " +
            "JOIN pet ON connection.pet_id = pet.id " +
            "JOIN petaddress ON connection.pet_id = petaddress.pet_id " +
            "JOIN user ON connection.walker_id = user.id " +
            "WHERE owner_id = :ownerId AND " +
            "(lower(pet.name) LIKE CONCAT('%',:keyword,'%') OR lower(pet.race) LIKE CONCAT('%',:keyword,'%') OR lower(petaddress.city) LIKE CONCAT('%',:keyword,'%') OR lower(petaddress.street) LIKE CONCAT('%',:keyword,'%') OR lower(user.firstname) LIKE CONCAT('%',:keyword,'%') OR lower(user.lastname) LIKE CONCAT('%',:keyword,'%') OR lower(user.username) LIKE CONCAT('%',:keyword,'%')) " +
            "ORDER BY field(status, 'accepted', 'waiting', 'canceled')", nativeQuery = true)
    List<Connection> findByOwnerIdWithKeyWord(@Param("ownerId") long ownerId, @Param("keyword") String keyword);
}
