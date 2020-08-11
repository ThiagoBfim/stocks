package com.money.stocks.service.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConnectionUtils implements ConnectionUtil {

    private static final String UA_PHONE = "Mozilla/5.0 (Linux; Android 4.3; Nexus 10 Build/JSS15Q) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Safari/537.36";
    private static final int TIME_OUT = 10 * 1000;

    @Override
    public Document getConnection(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent(UA_PHONE)
                .timeout(TIME_OUT)
                .ignoreContentType(true)
                .get();
    }
}
