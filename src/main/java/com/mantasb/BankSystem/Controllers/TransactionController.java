package com.mantasb.BankSystem.Controllers;

import com.mantasb.BankSystem.Classes.Transaction;
import com.mantasb.BankSystem.Services.TransactionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportTransactionsToCSV(String param) throws IOException {
        File csvFile = new File("exportedFile.csv");
        FileWriter fileWriter = new FileWriter(csvFile);
        String fileContent = transactionService.getTransactionsAsCSVString(transactionService.getTransactions());
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
