package com.mantasb.BankSystem;

import com.mantasb.BankSystem.Controllers.TransactionController;
import com.mantasb.BankSystem.Repositories.TransactionRepository;
import com.mantasb.BankSystem.Services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

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
    void testAccountBalanceWithNoDateParams() throws Exception {
        when(transactionService.getAccountBalance("TEST_ID1", null, null)).thenReturn(200.2);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/transaction/getBalance")
                        .param("accountNumber", "TEST_ID1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        assert result.getResponse().getContentAsString().equals("200.2") == false;
    }
}
