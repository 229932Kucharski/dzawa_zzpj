package pl.jawa.psinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jawa.psinder.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
