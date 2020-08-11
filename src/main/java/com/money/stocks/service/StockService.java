package com.money.stocks.service;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockService {

    private final StockRepository stockRepository;

    private final List<StockSearch> stockSearchs;

    public StockService(StockRepository stockRepository, List<StockSearch> stockSearchs) {
        this.stockRepository = stockRepository;
        this.stockSearchs = stockSearchs;
    }

    public Optional<Stock> updateStock(String stockCod) {
        return Optional.ofNullable(updateStock(stockCod, TypeStockSearch.FUNDAMENTUS));
    }

    public Stock updateStock(String stockCod, TypeStockSearch stockSearch) {
        return stockSearchs.stream()
                .filter(s -> s.getType() == stockSearch)
                .findFirst()
                .map(s -> s.getStock(stockCod))
                .map(s -> s.setDtLastUpdate(LocalDateTime.now()))
                .map(s -> s.setId(stockRepository.findByPublicCod(s.getPublicCod()).map(Stock::getId).orElse(null)))
                .map(stockRepository::save)
                .orElse(null);
    }

    public void updateAllStock(TypeStockSearch stockSearch) {
        stockRepository.findAll()
                .forEach(stock -> stockSearchs.stream()
                        .filter(s -> s.getType() == stockSearch)
                        .findFirst()
                        .map(s -> s.getStock(stock.getPublicCod()))
                        .filter(s -> s.getDtLastUpdate().plusDays(1).isBefore(LocalDateTime.now()))
                        .map(s -> s.setDtLastUpdate(LocalDateTime.now()))
                        .map(s -> s.setId(stock.getId()))
                        .map(stockRepository::save));
    }
}
