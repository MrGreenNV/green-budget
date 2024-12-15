package ru.averkiev.gateway.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.gateway.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(final String email);

    boolean existsUserByUsername(String username);

    Page<User> findAll(final Pageable pageRequest);
}
