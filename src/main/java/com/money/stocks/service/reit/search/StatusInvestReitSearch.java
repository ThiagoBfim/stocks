package com.money.stocks.service.reit.search;

import com.money.stocks.domain.Reit;
import com.money.stocks.domain.enuns.TypeReitSearch;
import com.money.stocks.service.ConnectionUtil;
import com.money.stocks.service.reit.ReitSearch;
import com.money.stocks.util.DecimalFormat;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class StatusInvestReitSearch implements ReitSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusInvestReitSearch.class);
    private static final String URI_STATUS_INVEST = "https://statusinvest.com.br/fundos-imobiliarios/";

    private final ConnectionUtil connectionUtils;

    public StatusInvestReitSearch(ConnectionUtil connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public TypeReitSearch getType() {
        return TypeReitSearch.STATUS_INVEST;
    }

    @Override
    public Reit getReit(String reitCod) {
        try {
            Document doc = connectionUtils.getConnection(URI_STATUS_INVEST + reitCod);

            final String dividendYield = doc.getElementsByAttributeValue("title", "Dividend Yield com base nos últimos 12 meses").get(0)
                    .getElementsByClass("value").text();
            final Element element = doc.getElementsByClass("top-info top-info-2 top-info-md-3 top-info-lg-n d-flex justify-between ")
                    .get(0);
            final String companyValue = element.getElementsByClass("sub-value").get(0).text();
            final String marketValue = element.getElementsByClass("sub-value").get(1).text();

            return new Reit()
                    .setDividendYield(DecimalFormat.toBigDecimal(dividendYield))
                    .setMarketValue(getBillionValue(marketValue))
                    .setCompanyValue(getBillionValue(companyValue))
                    .setPublicCod(reitCod);
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
