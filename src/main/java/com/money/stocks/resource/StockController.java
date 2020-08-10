package com.money.stocks.resource;

import com.money.stocks.domain.Stock;
import com.money.stocks.repository.StockRepository;
import com.money.stocks.service.StockService;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockRepository stockRepository;

    private final StockService stockService;

    public StockController(StockRepository stockRepository, StockService stockService) {
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    @GetMapping(value = "/dividendYield/{stockCod}")
    public ResponseEntity<BigDecimal> getStockDividendYield(@PathVariable String stockCod) {
        return stockRepository.findByPublicCod(stockCod)
                .map(Stock::getDividendYield)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping(value = "/{stockCod}")
    public ResponseEntity<Stock> getFullInfo(@PathVariable String stockCod) {
        return stockRepository.findByPublicCod(stockCod)
                .map(stock -> {
                    final Link selfLink = linkTo(
                            methodOn(StockController.class).updateStockInfo(stockCod))
                            .withSelfRel();

                    return stock.add(selfLink);
                })
                .or(() -> stockService.updateStock(stockCod))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{stockCod}")
    public ResponseEntity<Stock> updateStockInfo(@PathVariable String stockCod) {
        stockService.updateStock(stockCod);
        return stockRepository.findByPublicCod(stockCod)
                .map(stock -> {
                    final Link selfLink = linkTo(
                            methodOn(StockController.class).getFullInfo(stockCod))
                            .withSelfRel();

                    return stock.add(selfLink);
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
