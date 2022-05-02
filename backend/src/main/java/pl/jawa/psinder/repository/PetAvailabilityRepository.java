package pl.jawa.psinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jawa.psinder.entity.PetAvailability;

@Repository
public interface PetAvailabilityRepository extends JpaRepository<PetAvailability, Long> {
}
