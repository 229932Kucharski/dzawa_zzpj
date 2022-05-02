package pl.jawa.psinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jawa.psinder.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
