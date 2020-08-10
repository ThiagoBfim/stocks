package com.money.stocks.service.search;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.service.StockSearch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FundamentusSearch implements StockSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundamentusSearch.class);
    private static final String URI_FUNDAMENTUS = "http://www.fundamentus.com.br/detalhes.php?papel=";

    @Override
    public TypeStockSearch getType() {
        return TypeStockSearch.FUNDAMENTUS;
    }

    @Override
    public Stock getStock(String stockCod) {
        try {
            Document doc = Jsoup.connect(URI_FUNDAMENTUS + stockCod).get();
            Elements data = doc.getElementsByClass("data");
            return new Stock()
                    .setDividendYield(new BigDecimal(getElementValue(data, 36).replace("%", "")))
                    .setMarketValue(getBillionValue(data, 10))
                    .setCompanyValue(getBillionValue(data, 13))
                    .setPublicCod(stockCod);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    private BigDecimal getBillionValue(Elements data, int i) {
        return new BigDecimal(getElementValue(data, i).replaceAll("\\.", "")).divide(new BigDecimal(1_000_000_000), RoundingMode.HALF_UP);
    }

    private String getElementValue(Elements data, int i) {
        return data.get(i).getElementsByClass("txt").text().replace(",", ".");
    }
}
