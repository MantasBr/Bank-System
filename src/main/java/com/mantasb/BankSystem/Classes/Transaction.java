package com.mantasb.BankSystem.Classes;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

@Entity
public class Transaction {
    @Id
    private String transactionId;
    private String accountNumber;
    private Double amount;
    private String beneficiary;
    private LocalDate operation;
    private Currency currency;
    private String comment;

    public Transaction() {}

    public Transaction(String accountNumber, Double amount, String beneficiary, LocalDate operation, Currency currency, String comment) {
        this.transactionId = UUID.randomUUID().toString();
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.beneficiary = beneficiary;
        this.operation = operation;
        this.currency = currency;
        this.comment = comment;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public LocalDate getOperation() {
        return operation;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getComment() {
        return comment;
    }

    public String toString() {
        String[] stringArray = new String[] {this.accountNumber, Double.toString(this.amount), this.beneficiary, this.operation.toString(), this.currency.toString(), this.comment + "\n"};
        return String.join(",", stringArray);
    }
}
