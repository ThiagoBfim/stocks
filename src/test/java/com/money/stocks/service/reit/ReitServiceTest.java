package com.money.stocks.service.reit;

import com.money.stocks.domain.Reit;
import com.money.stocks.domain.enuns.TypeReitSearch;
import com.money.stocks.repository.ReitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReitServiceTest {

    private List<ReitSearch> stockSearchs = new ArrayList<>();
    private ReitRepository stockRepository = Mockito.mock(ReitRepository.class);

    @InjectMocks
    private ReitService stockService;

    @BeforeEach
    public void setUp() {
        stockSearchs.add(mockReitSearch());
        stockService = new ReitService(stockRepository, stockSearchs);
    }

    @Test
    void shouldUpdateReit() {
        when(stockRepository.save(any())).thenReturn(new Reit());
        Optional<Reit> xml11 = stockService.updateReit("xml11");
        assertThat(xml11).isPresent();
        verify(stockRepository).save(Mockito.any());
    }

    @Test
    void shouldNotUpdateReit() {
        Reit xml11 = stockService.updateReit("xml11", TypeReitSearch.STATUS_INVEST);
        assertThat(xml11).isNull();
    }

    @Test
    void shouldUpdateAllReit() {
        when(stockRepository.findAll()).thenReturn(Collections.singletonList(new Reit().setPublicCod("XML11")));
        stockService.updateAllReit(TypeReitSearch.STATUS_INVEST);
        verify(stockRepository).save(Mockito.any());
    }

    private ReitSearch mockReitSearch() {
        return new ReitSearch() {
            @Override
            public TypeReitSearch getType() {
                return TypeReitSearch.STATUS_INVEST;
            }

            @Override
            public Reit getReit(String stockCod) {
                return new Reit().setPublicCod("XML11");
            }
        };
    }

}
