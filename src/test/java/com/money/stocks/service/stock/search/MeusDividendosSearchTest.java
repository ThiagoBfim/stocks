package com.money.stocks.service.stock.search;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.service.ConnectionUtil;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class MeusDividendosSearchTest {

    private MeusDividendosSearch meusDividendosSearch;

    @BeforeEach
    void setUp() {
        meusDividendosSearch = new MeusDividendosSearch(mockConnection());
    }

    @Test
    void testGetType() {
        Assertions.assertThat(meusDividendosSearch.getType()).isEqualTo(TypeStockSearch.MEUS_DIVIDENDOS);
    }

    @Test
    void getStock() {
        Stock bidi11 = meusDividendosSearch.getStock("BIDI11");
        Assertions.assertThat(bidi11.getMarketValue()).isEqualTo(new BigDecimal("12.69"));
        Assertions.assertThat(bidi11.getDividendYield()).isEqualTo(new BigDecimal("0.49"));
    }

    private ConnectionUtil mockConnection() {
        return url -> Jsoup.parse("<html>"
                + "<body class='table table-hover'>"
                + "<p class='converter-percentual'>0.004933221596374731</p>"
                + "<p class='abreviar-numero'>123</p>"
                + "<p class='abreviar-numero'>12.700.000.000</p>"
                + "</body>" +
                "</html>");
    }
}
