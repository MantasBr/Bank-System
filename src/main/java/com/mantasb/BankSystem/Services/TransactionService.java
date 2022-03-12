package com.mantasb.BankSystem.Services;

import com.mantasb.BankSystem.Classes.Transaction;
import com.mantasb.BankSystem.Repositories.TransactionRepository;
import com.mantasb.BankSystem.Util.CSVManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CSVManager csvManager;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        this.csvManager = new CSVManager();
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactions(LocalDate from, LocalDate to) {
        return transactionRepository.findByOperationBetween(from, to);
    }

    public List<Transaction> getAccountBalance(String accountNumber, LocalDate from, LocalDate to) {
        return transactionRepository.findByBeneficiaryAndOperationBetween(accountNumber, from, to);
    }

    public void importTransactions(MultipartFile file) throws IOException {
        List<Transaction> importedTransactions = csvManager.importCSV(file.getInputStream());
        transactionRepository.saveAll(importedTransactions);
    }

    public String getTransactionsAsCSVString(List <Transaction> transactions) {
        return csvManager.getCSVStringFromTransactions(transactions);
    }

    public Transaction createTransaction(String accountNumber, Double amount, String beneficiary, LocalDate operation, Currency currency, String comment) {
        Transaction transaction = new Transaction(accountNumber, amount, beneficiary, operation, currency,  comment);

        transactionRepository.save(transaction);
        return transaction;
    }
}