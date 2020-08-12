package com.money.stocks.service.stock.search;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.service.ConnectionUtil;
import com.money.stocks.service.stock.StockSearch;
import com.money.stocks.util.DecimalFormat;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MeusDividendosSearch implements StockSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeusDividendosSearch.class);
    private static final String URI_STATUS_INVEST = "https://www.meusdividendos.com/acao/";

    private final ConnectionUtil connectionUtils;

    public MeusDividendosSearch(ConnectionUtil connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public TypeStockSearch getType() {
        return TypeStockSearch.MEUS_DIVIDENDOS;
    }

    @Override
    public Stock getStock(String stockCod) {
        try {
            Document doc = connectionUtils.getConnection(URI_STATUS_INVEST + stockCod);
            Element getInvestInfo = doc.getElementsByClass("table table-hover").get(0);
            final String dividendYield = getInvestInfo.getElementsByClass("converter-percentual").text();
            final String marketValue = getInvestInfo.getElementsByClass("abreviar-numero").get(1).text();

            return new Stock()
                    .setDividendYield(DecimalFormat.toBigDecimal(dividendYield)
                            .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.DOWN))
                    .setMarketValue(DecimalFormat.toBigDecimalBillionFormatter(marketValue))
                    .setPublicCod(stockCod);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

}
