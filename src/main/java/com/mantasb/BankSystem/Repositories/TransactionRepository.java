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

    List<Transaction> findByBeneficiaryAndOperationBetween(String accountNumber, LocalDate from, LocalDate to);
}
