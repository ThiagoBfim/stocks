package com.money.stocks.service.stock.search;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.service.ConnectionUtil;
import com.money.stocks.service.stock.StockSearch;
import com.money.stocks.util.DecimalFormat;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class StatusInvestSearch implements StockSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusInvestSearch.class);
    private static final String URI_STATUS_INVEST = "https://statusinvest.com.br/acoes/";

    private final ConnectionUtil connectionUtils;

    public StatusInvestSearch(ConnectionUtil connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public TypeStockSearch getType() {
        return TypeStockSearch.STATUS_INVEST;
    }

    @Override
    public Stock getStock(String stockCod) {
        try {
            Document doc = connectionUtils.getConnection(URI_STATUS_INVEST + stockCod);
            final String dividendYield = getValue(doc, "Dividend Yield com base nos últimos 12 meses");
            final String marketValue = getValue(doc, "O valor da ação multiplicado pelo número de ações existentes");
            final String companyValue = getValue(doc, "Soma do valor de mercado das ações com a dívida líquida dessa empresa");

            return new Stock()
                    .setDividendYield(DecimalFormat.toBigDecimal(dividendYield))
                    .setMarketValue(getBillionValue(marketValue))
                    .setCompanyValue(getBillionValue(companyValue))
                    .setPublicCod(stockCod);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    private String getValue(Document doc, String s) {
        return doc.getElementsByAttributeValue("title", s)
                .get(0).getElementsByClass("value").text();
    }

    private BigDecimal getBillionValue(String value) {
        return DecimalFormat.toBigDecimalBillionFormatter(value);
    }

}
