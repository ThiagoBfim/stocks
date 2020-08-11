package com.money.stocks.resource;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.repository.StockRepository;
import com.money.stocks.service.StockService;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                .or(() -> stockService.updateStock(stockCod))
                .map(stockEntity -> {
                    final Link updateLink = linkTo(
                            methodOn(StockController.class).updateStockInfo(stockCod, TypeStockSearch.FUNDAMENTUS))
                            .withRel("update_date")
                            .withType("PUT");
                    stockEntity.add(updateLink);
                    final Link findTypes = linkTo(
                            methodOn(StockController.class).findTypesSearch())
                            .withRel("find_types")
                            .withType("GET");
                    stockEntity.add(findTypes);
                    return stockEntity;
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{stockCod}")
    public ResponseEntity<String> updateStockInfo(@PathVariable String stockCod,
                                                  @RequestParam(value = "typeSearch", required = false, defaultValue = "FUNDAMENTUS") TypeStockSearch typeSearch) {
        Stock stock = stockService.updateStock(stockCod, typeSearch);
        if (stock != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Ocorreu um erro ao tentar atualizar a ação: " + stockCod);
    }

    @GetMapping(value = "/typeSearch")
    public ResponseEntity<List<String>> findTypesSearch() {
        return ResponseEntity.ok(Stream.of(TypeStockSearch.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
    }

    @PutMapping
    public ResponseEntity<String> updateAllStockInfo(@RequestParam(value = "typeSearch", required = false, defaultValue = "FUNDAMENTUS") TypeStockSearch typeSearch) {
        stockService.updateAllStock(typeSearch);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<String> getAllStocksIds() {
        return ResponseEntity.ok(stockRepository.findAll()
                .stream()
                .map(Stock::getPublicCod)
                .collect(Collectors.joining(",")));
    }
}
