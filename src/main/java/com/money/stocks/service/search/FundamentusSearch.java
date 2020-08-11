package com.money.stocks.service.search;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.service.StockSearch;
import com.money.stocks.util.DecimalFormat;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class FundamentusSearch implements StockSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundamentusSearch.class);
    private static final String URI_FUNDAMENTUS = "https://www.fundamentus.com.br/detalhes.php?papel=";

    @Override
    public TypeStockSearch getType() {
        return TypeStockSearch.FUNDAMENTUS;
    }

    @Override
    public Stock getStock(String stockCod) {
        try {
            Document doc = ConnectionUtils.getConnection(URI_FUNDAMENTUS + stockCod);
            Elements data = doc.getElementsByClass("data");
            return new Stock()
                    .setDividendYield(DecimalFormat.toBigDecimal(getElementValue(data, 36)))
                    .setMarketValue(getBillionValue(data, 10))
                    .setCompanyValue(getBillionValue(data, 12))
                    .setPublicCod(stockCod);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    private BigDecimal getBillionValue(Elements data, int i) {
        return DecimalFormat.toBigDecimalBillionFormatter(getElementValue(data, i));
    }

    private String getElementValue(Elements data, int i) {
        return data.get(i).getElementsByClass("txt").text().replace(",", ".");
    }
}
