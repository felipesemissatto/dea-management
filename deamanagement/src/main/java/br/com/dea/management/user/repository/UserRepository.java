package br.com.dea.management.user.repository;

import br.com.dea.management.user.domain.User;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findById(Long id);
    public Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User WHERE name = :name")
    public Optional<User> findByName(String name);

    @Query("SELECT u FROM User WHERE linkedin = :linkedin")
    public Optional<User> findByLinkedin(String linkedin);
}
