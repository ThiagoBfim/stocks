package com.money.stocks.resource;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.repository.StockRepository;
import com.money.stocks.service.StockService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockRepository stockRepository;

    @MockBean
    private StockService stockService;

    @Test
    public void shouldReturnDividendYield() throws Exception {
        Stock stock = createStock();
        Mockito.when(stockRepository.findByPublicCodIgnoreCase("BIDI11"))
                .thenReturn(Optional.ofNullable(stock));

        String contentAsString = mockMvc.perform(get("/stocks/dividendYield/BIDI11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(contentAsString).isEqualTo("6.10");
    }

    @Test
    public void shouldReturnStockInfo() throws Exception {
        Stock stock = createStock();
        Mockito.when(stockRepository.findByPublicCodIgnoreCase("BIDI11"))
                .thenReturn(Optional.ofNullable(stock));

        mockMvc.perform(get("/stocks/BIDI11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("publicCod").value("bidi11"))
                .andExpect(jsonPath("dividendYield").value("6.1"))
                .andExpect(jsonPath("marketValue").value("100.7"))
                .andExpect(jsonPath("companyValue").value("10"))
                .andExpect(jsonPath("star").value("7.44"))
                .andExpect(jsonPath("dtLastUpdate").value("2020-04-01T01:01:00"));
    }

    @Test
    public void shouldUpdateStock() throws Exception {
        Stock stock = createStock();
        Mockito.when(stockService.updateStock("BIDI11", TypeStockSearch.STATUS_INVEST))
                .thenReturn(stock);

        mockMvc.perform(put("/stocks/BIDI11?typeSearch=STATUS_INVEST")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotUpdateStock() throws Exception {
        mockMvc.perform(put("/stocks/BIDI11?typeSearch=ABC")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotUpdateStockWhenStockIsWrong() throws Exception {
        String contentAsString = mockMvc.perform(put("/stocks/BIDI112")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(contentAsString).isEqualTo("Error when trying to update a stock of cod: BIDI112");
    }

    @Test
    public void shouldReturnTypeSearchs() throws Exception {
        String contentAsString = mockMvc.perform(get("/stocks/typeSearch")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               .andReturn()
               .getResponse()
               .getContentAsString();

        Assertions.assertThat(contentAsString).isEqualTo("[\"FUNDAMENTUS\",\"STATUS_INVEST\",\"MEUS_DIVIDENDOS\"]");
    }

    @Test
    public void shouldUpdateAllStocks() throws Exception {
        mockMvc.perform(put("/stocks/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnAllStocksIds() throws Exception {
        Mockito.when(stockRepository.findAll()).thenReturn(Collections.singletonList(createStock()));
        String contentAsString = mockMvc.perform(get("/stocks/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(contentAsString).isEqualTo("bidi11");
    }

    private Stock createStock() {
        return new Stock()
                .setDtLastUpdate(LocalDateTime.of(2020, Month.APRIL, 1, 1, 1))
                .setCompanyValue(BigDecimal.TEN)
                .setPublicCod("bidi11")
                .setStar(new BigDecimal("7.44"))
                .setMarketValue(new BigDecimal("100.7"))
                .setDividendYield(new BigDecimal("6.10"));
    }

}
