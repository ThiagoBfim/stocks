package com.money.stocks.service.search;

import org.assertj.core.api.Assertions;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
public class ConnectionUtilsTest {

    @Autowired
    private ConnectionUtil connectionUtil;

    @Test
    void testGetConnection() throws IOException {
        Document connection = connectionUtil.getConnection("https://www.google.com.br");
        Assertions.assertThat(connection).isNotNull();
    }
}
