package com.money.stocks.service.search;

import org.assertj.core.api.Assertions;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ConnectionUtilsTest {

    @Test
    void testGetConnection() throws IOException {
        Document connection = ConnectionUtils.getConnection("https://www.google.com.br");
        Assertions.assertThat(connection).isNotNull();
    }
}
