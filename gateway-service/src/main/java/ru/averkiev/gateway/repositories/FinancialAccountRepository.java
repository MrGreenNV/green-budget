package ru.averkiev.gateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.gateway.models.FinancialAccount;

@Repository
public interface FinancialAccountRepository extends JpaRepository<FinancialAccount, Long> {
}
