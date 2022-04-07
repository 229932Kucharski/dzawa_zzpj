package pl.jawa.psinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jawa.psinder.entity.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
}
