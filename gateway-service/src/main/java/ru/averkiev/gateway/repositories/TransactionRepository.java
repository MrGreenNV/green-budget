package ru.averkiev.gateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.gateway.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
