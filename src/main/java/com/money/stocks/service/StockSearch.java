package com.money.stocks.service;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;

public interface StockSearch {

    TypeStockSearch getType();

    Stock getStock(String stockCod);

}
