package com.mantasb.BankSystem.Controllers;

import com.mantasb.BankSystem.Classes.Transaction;
import com.mantasb.BankSystem.Services.TransactionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/list")
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

    @PostMapping("/import")
    public void importTransactionsFromCSV(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.getContentType().equals("text/csv") == false)  {
            throw new IllegalArgumentException("Imported file should be in csv format.");
        } else {
            transactionService.importTransactions(file);
        }
    }
}
