package com.mantasb.BankSystem.Controllers;

import com.mantasb.BankSystem.Classes.Transaction;
import com.mantasb.BankSystem.Services.TransactionService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
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

    @GetMapping("/getBalance")
    public double getAccountBalance(@RequestParam String accountNumber, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        LocalDate startDate = from == null ? LocalDate.EPOCH : LocalDate.parse(from);
        LocalDate endDate = to == null ? LocalDate.now() : LocalDate.parse(to);
        return transactionService.getAccountBalance(accountNumber, startDate, endDate);
    }

    @PostMapping("/import")
    public void importTransactionsFromCSV(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.getContentType().equals("text/csv") == false)  {
            throw new IllegalArgumentException("Imported file should be in csv format.");
        } else {
            transactionService.importTransactions(file);
        }
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportTransactionsToCSV(@RequestParam(required = false) String from, @RequestParam(required = false) String to) throws IOException {
        LocalDate startDate = from == null ? LocalDate.EPOCH : LocalDate.parse(from);
        LocalDate endDate = to == null ? LocalDate.now() : LocalDate.parse(to);

        File csvFile = new File("exportedFile.csv");
        FileWriter fileWriter = new FileWriter(csvFile);
        String fileContent = transactionService.getTransactionsAsCSVString(transactionService.getTransactions(startDate, endDate));
        fileWriter.write(fileContent);
        fileWriter.close();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(csvFile));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exportedFile.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(csvFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
