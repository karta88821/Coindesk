package com.danielliao.coindesk_api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.danielliao.coindesk_api.currency.Currency;
import com.danielliao.coindesk_api.currency.CurrencyAddBody;
import com.danielliao.coindesk_api.currency.CurrencyRow;
import com.danielliao.coindesk_api.currency.CurrencyService;
import com.danielliao.coindesk_api.currency.CurrencyUpdateBody;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurrencyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CurrencyService currencyService;

    private Currency mockCurrency;

    @BeforeAll
    public void setup() {
        // Add a currency into DB
        mockCurrency = new Currency();
        mockCurrency.setName("USD");
        mockCurrency.setChineseName("美金");
        mockCurrency.setRateFloat(28999.2894f);
        mockCurrency.setUpdatedTime(new Date());

        currencyService.saveCurrency(mockCurrency);

        // Add another currency with the name "GBP" which is used for testing the delete api
        Currency anotherCurrency = new Currency();
        anotherCurrency.setName("EUR");
        anotherCurrency.setChineseName("歐元");
        anotherCurrency.setRateFloat(28249.5417f);
        anotherCurrency.setUpdatedTime(new Date());

        currencyService.saveCurrency(anotherCurrency);
    }

    @BeforeEach
    public void fetchCurrency() {
        Optional<CurrencyRow> res = currencyService.getCurrencyByName("USD");
        if (res.isPresent()) {
            mockCurrency = new Currency();
            mockCurrency.setName(res.get().getName());
            mockCurrency.setChineseName(res.get().getChineseName());
            mockCurrency.setRateFloat(res.get().getRateFloat());
            mockCurrency.setUpdatedTime(res.get().getUpdatedTime());
        }
    }

    // 查詢幣別
    // Path variables: 
    //   1. name: 所要查詢的幣別
    @Test
    public void testGetCurrency() throws Exception {

        String name = mockCurrency.getName();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        mockMvc.perform(get("/api/currencies/{name}", name))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.name").value(mockCurrency.getName()))
        .andExpect(jsonPath("$.data.chineseName").value(mockCurrency.getChineseName()))
        .andExpect(jsonPath("$.data.rateFloat").value(mockCurrency.getRateFloat()))
        .andExpect(jsonPath("$.data.updatedTime").value(format.format(mockCurrency.getUpdatedTime())))
        .andDo(print());
    }

    // 新增幣別
    // Body: 
    //   1. name: 幣別的名稱
    //   2. chineseName: 幣別的中文名稱
    //   3. rateFloat: 幣別的匯率
    @Test
    public void testAddCurrency() throws Exception {
        CurrencyAddBody currency = new CurrencyAddBody();
        currency.setName("GBP");
        currency.setChineseName("英鎊");
        currency.setRateFloat(24231.5742f);

        mockMvc.perform(post("/api/currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(currency)))
        .andExpect(status().isCreated())
        .andDo(print());
    }

    // 修改幣別
    // Path variables: 
    //   1. name: 所要修改的幣別
    // Body: 
    //   1. rateFloat: 幣別的匯率
    @Test
    public void testUpdateCurrency() throws Exception {

        String name = mockCurrency.getName();

        Float newRateFloat = 27999.2894f;

        CurrencyUpdateBody currency = new CurrencyUpdateBody();
        currency.setRateFloat(newRateFloat);

        mockMvc.perform(patch("/api/currencies/{name}", name)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(currency)))
        .andExpect(status().isOk())
        .andDo(print());
    }

    // 刪除幣別
    // Path variables: 
    //   1. name: 所要刪除的幣別
    @Test
    public void testDeleteCurrency() throws Exception {
        String name = "EUR";

        mockMvc.perform(delete("/api/currencies/{name}", name))
        .andExpect(status().isOk())
        .andDo(print());
    }
}
