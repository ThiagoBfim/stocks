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
class StatusInvestSearchTest {

    private StatusInvestSearch statusInvestSearch;

    @BeforeEach
    void setUp() {
        statusInvestSearch = new StatusInvestSearch(mockConnection());
    }

    @Test
    void testGetType() {
        Assertions.assertThat(statusInvestSearch.getType()).isEqualTo(TypeStockSearch.STATUS_INVEST);
    }

    @Test
    void getStock() {
        Stock bidi11 = statusInvestSearch.getStock("BIDI11");
        Assertions.assertThat(bidi11.getMarketValue()).isEqualTo(new BigDecimal("5.09"));
        Assertions.assertThat(bidi11.getCompanyValue()).isEqualTo(new BigDecimal("5.09"));
        Assertions.assertThat(bidi11.getDividendYield()).isEqualTo(new BigDecimal("0.64"));
        Assertions.assertThat(bidi11.getDistributedEarnings()).isEqualTo(new BigDecimal("1.64"));
    }

    private ConnectionUtil mockConnection() {
        return url -> Jsoup.parse("<html>"
                + "<body class='table table-hover'>"
                + "<div title='Dividend Yield com base nos últimos 12 meses'>"
                + "       <p class='value'>0,64%</p>"
                + "</div>"
                + "<div title='Soma total de proventos distribuídos nos últimos 12 meses'>"
                + "       <p class='sub-value'>R$ 1,64%</p>"
                + "</div>"
                + "<div title='O valor da ação multiplicado pelo número de ações existentes'>"
                + "       <p class='value'>5.067.972.482</p>"
                + "</div>"
                + "<div title='Soma do valor de mercado das ações com a dívida líquida dessa empresa'>"
                + "       <p class='value'>5.064.750.482</p>"
                + "</div>"
                + "</body>" +
                "</html>");
    }
}
