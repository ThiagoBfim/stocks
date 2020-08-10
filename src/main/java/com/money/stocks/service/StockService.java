package com.money.stocks.service;

import com.money.stocks.domain.Stock;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {

    public Optional<Stock> updateStock(String stockCod) {
        return Optional.empty();//TODO fazer.
    }
}
