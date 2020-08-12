package com.money.stocks.resource;

import com.money.stocks.domain.Reit;
import com.money.stocks.domain.enuns.TypeReitSearch;
import com.money.stocks.repository.ReitRepository;
import com.money.stocks.service.reit.ReitService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ReitControllerTest {

    private ReitRepository reitRepository = Mockito.mock(ReitRepository.class);

    private ReitService reitService = Mockito.mock(ReitService.class);

    private MockMvc mockMvc;

    private ReitController reitController = new ReitController(reitRepository, reitService);

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reitController)
                .setValidator(new MockValidator())
                .build();
    }

    @Test
    public void shouldReturnDividendYield() throws Exception {
        Reit reit = createReit();
        Mockito.when(reitRepository.findByPublicCodIgnoreCase("XPML11"))
                .thenReturn(Optional.ofNullable(reit));

        String contentAsString = mockMvc.perform(get("/reits/dividendYield/XPML11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(contentAsString).isEqualTo("6.10");
    }

    @Test
    public void shouldReturnReitInfo() throws Exception {
        Reit reit = createReit();
        Mockito.when(reitRepository.findByPublicCodIgnoreCase("XPML11"))
                .thenReturn(Optional.ofNullable(reit));

        mockMvc.perform(get("/reits/XPML11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("publicCod").value("xpml11"))
                .andExpect(jsonPath("dividendYield").value("6.1"))
                .andExpect(jsonPath("marketValue").value("100.7"))
                .andExpect(jsonPath("companyValue").value("10"))
                .andExpect(jsonPath("star").value("7.44"));
    }

    @Test
    public void shouldUpdateReit() throws Exception {
        Reit reit = createReit();
        Mockito.when(reitService.updateReit("XPML11", TypeReitSearch.STATUS_INVEST))
                .thenReturn(reit);

        mockMvc.perform(put("/reits/XPML11?typeSearch=STATUS_INVEST")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotUpdateReit() throws Exception {
        mockMvc.perform(put("/reits/XPML11?typeSearch=ABC")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotUpdateReitWhenReitIsWrong() throws Exception {
        String contentAsString = mockMvc.perform(put("/reits/XPML112")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(contentAsString).isEqualTo("Error when trying to update a Reit of cod: XPML112");
    }

    @Test
    public void shouldReturnTypeSearchs() throws Exception {
        String contentAsString = mockMvc.perform(get("/reits/typeSearch")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(contentAsString).isEqualTo("[\"STATUS_INVEST\"]");
    }

    @Test
    public void shouldUpdateAllReits() throws Exception {
        mockMvc.perform(put("/reits/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnAllReitsIds() throws Exception {
        Mockito.when(reitRepository.findAll()).thenReturn(Collections.singletonList(createReit()));
        String contentAsString = mockMvc.perform(get("/reits/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(contentAsString).isEqualTo("xpml11");
    }

    private Reit createReit() {
        return new Reit()
                .setCompanyValue(BigDecimal.TEN)
                .setPublicCod("xpml11")
                .setStar(new BigDecimal("7.44"))
                .setMarketValue(new BigDecimal("100.7"))
                .setDividendYield(new BigDecimal("6.10"));
    }

    private class MockValidator implements Validator {
        @Override
        public boolean supports(Class<?> aClass) {
            return false;
        }

        @Override
        public void validate(Object o, Errors errors) {

        }
    }

}
