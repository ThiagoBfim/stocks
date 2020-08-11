package com.money.stocks.service.search;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ConnectionUtilsTest {

    @Test
    void testGetConnection() throws IOException {
        ConnectionUtils.getConnection("https://www.google.com.br");
    }
}
