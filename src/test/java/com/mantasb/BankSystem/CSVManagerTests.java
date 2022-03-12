package com.mantasb.BankSystem;

import com.mantasb.BankSystem.Classes.Transaction;
import com.mantasb.BankSystem.Util.CSVManager;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CSVManagerTests {
    CSVManager csvManager = new CSVManager();

    @Test
    public void testStringConverter() {
        String mockString = "100,10.5,200,2022-03-10,EUR,\n" +
                "300,20.4,400,2022-03-11,USD,Test";

        List mockList = new ArrayList<>();
        mockList.add(new Transaction("100", 10.5, "200", LocalDate.of(2022, 3, 10), Currency.getInstance("EUR"), ""));
        mockList.add(new Transaction("300", 20.4, "400", LocalDate.of(2022, 3, 11), Currency.getInstance("USD"), "Test"));

        assert csvManager.getCSVStringFromTransactions(mockList).equals(mockString) == false;
    }

    @Test
    public void testFileImport() throws IOException {
        String mockString = "100,10.5,200,2022-03-10,EUR,\n" +
                "300,20.4,400,2022-03-11,USD,Test";

        File csvFile = File.createTempFile("testFile", ".csv");
        csvFile.deleteOnExit();
        PrintWriter printWriter = new PrintWriter(csvFile);
        printWriter.write(mockString);
        printWriter.close();

        List<Transaction> importedTransactions = csvManager.importCSV(new FileInputStream(csvFile));

        assert csvManager.getCSVStringFromTransactions(importedTransactions).equals(mockString) == false;
    }
}
