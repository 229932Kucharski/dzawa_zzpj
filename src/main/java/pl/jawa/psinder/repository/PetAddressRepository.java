package pl.jawa.psinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jawa.psinder.entity.PetAddress;

@Repository
public interface PetAddressRepository extends JpaRepository<PetAddress, Long> {
}
