package com.mantasb.BankSystem;

import com.mantasb.BankSystem.Controllers.TransactionController;
import com.mantasb.BankSystem.Repositories.TransactionRepository;
import com.mantasb.BankSystem.Services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionController transactionController;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    void testGetBalanceWithNoParams() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/transaction/getBalance")).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testAccountBalanceWithNoDateParams() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/transaction/getBalance")
                        .param("accountNumber", "TEST_ID1"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
