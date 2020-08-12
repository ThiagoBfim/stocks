package com.money.stocks.service.reit.search;

import com.money.stocks.domain.Reit;
import com.money.stocks.domain.enuns.TypeReitSearch;
import com.money.stocks.service.ConnectionUtil;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class StatusInvestReitSearchTest {

    private StatusInvestReitSearch statusInvestReitSearch;

    @BeforeEach
    void setUp() {
        statusInvestReitSearch = new StatusInvestReitSearch(mockConnection());
    }

    @Test
    void testGetType() {
        Assertions.assertThat(statusInvestReitSearch.getType()).isEqualTo(TypeReitSearch.STATUS_INVEST);
    }

    @Test
    void getStock() {
        Reit xpml11 = statusInvestReitSearch.getReit("XPML11");
        Assertions.assertThat(xpml11.getMarketValue()).isEqualTo(new BigDecimal("2.00"));
        Assertions.assertThat(xpml11.getCompanyValue()).isEqualTo(new BigDecimal("2.10"));
        Assertions.assertThat(xpml11.getDividendYield()).isEqualTo(new BigDecimal("4.64"));
    }

    private ConnectionUtil mockConnection() {
        return url -> Jsoup.parse("<html>"
                + "<body class='table table-hover'>"
                + "<div title='Dividend Yield com base nos últimos 12 meses'>"
                + "       <p class='value'>4,64%</p>"
                + "</div>"
                + "<div class='top-info top-info-2 top-info-md-3 top-info-lg-n d-flex justify-between '>"
                + "       <p class='sub-value'>2.100.972.482</p>"
                + "       <p class='sub-value'>2.001.750.482</p>"
                + "</div>"
                + "</body>" +
                "</html>");
    }
}
