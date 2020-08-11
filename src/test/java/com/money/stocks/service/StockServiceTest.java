package com.money.stocks.service;

import com.money.stocks.domain.Stock;
import com.money.stocks.domain.enuns.TypeStockSearch;
import com.money.stocks.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    private List<StockSearch> stockSearchs = new ArrayList<>();
    private StockRepository stockRepository = Mockito.mock(StockRepository.class);

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    public void setUp() {
        stockSearchs.add(mockStockSearch());
        stockService = new StockService(stockRepository, stockSearchs);
    }

    @Test
    void shouldUpdateStock() {
        when(stockRepository.save(any())).thenReturn(new Stock());
        Optional<Stock> bidi11 = stockService.updateStock("bidi11");
        assertThat(bidi11.isPresent()).isTrue();
        verify(stockRepository).save(Mockito.any());
    }

    @Test
    void shouldUpdateAllStock() {
        stockService.updateAllStock(TypeStockSearch.STATUS_INVEST);
    }

    private StockSearch mockStockSearch() {
        return new StockSearch() {
            @Override
            public TypeStockSearch getType() {
                return TypeStockSearch.STATUS_INVEST;
            }

            @Override
            public Stock getStock(String stockCod) {
                return new Stock();
            }
        };
    }

}
