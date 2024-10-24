package ru.averkiev.budget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.budget.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
