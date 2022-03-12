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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/getBalance")
    public Map<String, Double> getAccountBalance(@RequestParam String accountNumber, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        LocalDate startDate = from == null ? LocalDate.EPOCH : LocalDate.parse(from);
        LocalDate endDate = to == null ? LocalDate.now() : LocalDate.parse(to);

        List <Transaction> listOfAccountTransactions = transactionService.getAccountBalance(accountNumber, startDate, endDate);

        System.out.println(listOfAccountTransactions);

        Map<String, Double> map = new HashMap();

        for (Transaction transaction : listOfAccountTransactions) {
            String currencyName = transaction.getCurrency().getCurrencyCode();
            Double currentBalance = map.get(currencyName);
            if (currentBalance != null) {
                map.put(currencyName, currentBalance + transaction.getAmount());
            } else {
                map.put(currencyName, transaction.getAmount());
            }
        }

        return map;
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

        File csvFile = File.createTempFile("exportedFile", ".csv");
        csvFile.deleteOnExit();
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