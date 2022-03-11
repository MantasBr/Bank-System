package com.mantasb.BankSystem.Repositories;

import com.mantasb.BankSystem.Classes.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
