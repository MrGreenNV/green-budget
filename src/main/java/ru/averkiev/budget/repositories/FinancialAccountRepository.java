package ru.averkiev.budget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.budget.models.FinancialAccount;

@Repository
public interface FinancialAccountRepository extends JpaRepository<FinancialAccount, Long> {
}
