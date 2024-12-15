package ru.averkiev.gateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.gateway.models.TransactionCategory;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
    boolean existsUserByName(String name);
}
