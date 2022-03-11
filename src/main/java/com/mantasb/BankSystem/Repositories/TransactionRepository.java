package com.mantasb.BankSystem.Repositories;

import com.mantasb.BankSystem.Classes.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByOperationBetween(LocalDate from, LocalDate to);

    @Query("SELECT sum(t.amount) FROM Transaction t WHERE t.beneficiary = ?1 AND t.operation >= ?2 AND t.operation <= ?3")
    Double calculateAccountBalance(String accountNumber, LocalDate from, LocalDate to);
}
