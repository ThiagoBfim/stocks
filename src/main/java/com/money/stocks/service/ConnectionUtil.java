package com.money.stocks.service;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface ConnectionUtil {

    Document getConnection(String url) throws IOException;
}
