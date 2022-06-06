package pl.jawa.psinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.jawa.psinder.entity.Rating;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value = "SELECT * FROM rating WHERE id = :id", nativeQuery = true)
    Rating findById(@Param("id") long id);

    @Query(value = "SELECT * FROM rating WHERE to_user_id = :user_Id", nativeQuery = true)
    List<Rating> findByToUserId(@Param("user_Id") long user_Id);

    @Query(value = "SELECT * FROM rating WHERE from_user_id = :user_Id", nativeQuery = true)
    List<Rating> findByFromUserId(@Param("user_Id") long user_Id);

    @Query(value = "SELECT * FROM rating WHERE rating >= :rate", nativeQuery = true)
    List<Rating> findBetterOrExactRates(@Param("rate") int rate);

    @Query(value = "SELECT * FROM rating WHERE rating <= :rate", nativeQuery = true)
    List<Rating> findWorseOrExactRates(@Param("rate") int rate);


}
