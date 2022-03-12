package com.mantasb.BankSystem.Util;

import com.mantasb.BankSystem.Classes.Transaction;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CSVManager {
    private final String DELIMITER = ",";

    public List<Transaction> importCSV(InputStream inputStream) {
        List <Transaction> importedTransactions = new ArrayList<>();

        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
              List<String> parsedLine = readLine(scanner.nextLine());
              String accountNumber = parsedLine.get(0);
              Double amount = Double.parseDouble(parsedLine.get(1));
              String beneficiary = parsedLine.get(2);
              LocalDate operation = LocalDate.parse(parsedLine.get(3));
              Currency currency = Currency.getInstance(parsedLine.get(4));
              String comment = parsedLine.size() == 5 ? "" : parsedLine.get(5);

              Transaction transaction = new Transaction(accountNumber, amount, beneficiary, operation, currency, comment);
              importedTransactions.add(transaction);
            }
        }
        return importedTransactions;
    }

    public List<String> readLine(String line){
        List<String> parsedLine = new ArrayList<>();

        try (Scanner lineScanner = new Scanner(line)) {
            lineScanner.useDelimiter(DELIMITER);

            while (lineScanner.hasNext()) {
                parsedLine.add(lineScanner.next());
            }
        }

        return parsedLine;
    }

    public String getCSVStringFromTransactions(List<Transaction> transactions) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Transaction transaction : transactions) {
            stringBuilder.append(transaction.toString());
        }

        return stringBuilder.toString();
    }
}
