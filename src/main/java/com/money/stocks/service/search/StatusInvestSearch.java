package com.money.stocks.service.search;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.service.StockSearch;
import com.money.stocks.util.DecimalFormat;
import org.jsoup.Jsoup;
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
    private static final String UA_PHONE = "Mozilla/5.0 (Linux; Android 4.3; Nexus 10 Build/JSS15Q) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Safari/537.36";
    private static final int TIME_OUT = 10 * 1000;

    @Override
    public TypeStockSearch getType() {
        return TypeStockSearch.STATUS_INVEST;
    }

    @Override
    public Stock getStock(String stockCod) {
        try {
            Jsoup.connect(URI_STATUS_INVEST+ stockCod)
                    .userAgent(UA_PHONE)
                    .timeout(TIME_OUT)
                    .ignoreContentType(true).get();
            Document doc = Jsoup.connect(URI_STATUS_INVEST + stockCod).get();
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
