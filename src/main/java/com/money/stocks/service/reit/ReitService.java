package com.money.stocks.service.reit;

import com.money.stocks.domain.Reit;
import com.money.stocks.domain.enuns.TypeReitSearch;
import com.money.stocks.repository.ReitRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReitService {

    private final ReitRepository reitRepository;

    private final List<ReitSearch> reitSearches;

    public ReitService(ReitRepository reitRepository, List<ReitSearch> reitSearches) {
        this.reitRepository = reitRepository;
        this.reitSearches = reitSearches;
    }

    public Optional<Reit> updateReit(String stockCod) {
        return Optional.ofNullable(updateReit(stockCod, TypeReitSearch.STATUS_INVEST));
    }

    public Reit updateReit(String stockCod, TypeReitSearch stockSearch) {
        return reitSearches.stream()
                .filter(s -> s.getType() == stockSearch)
                .findFirst()
                .map(s -> s.getReit(stockCod))
                .map(s -> s.setDtLastUpdate(LocalDateTime.now()))
                .map(s -> s.setId(reitRepository.findByPublicCodIgnoreCase(s.getPublicCod()).map(Reit::getId).orElse(null)))
                .map(reitRepository::save)
                .orElse(null);
    }

    public void updateAllReit(TypeReitSearch stockSearch) {
        reitRepository.findAll()
                .forEach(stock -> reitSearches.stream()
                        .filter(s -> s.getType() == stockSearch)
                        .findFirst()
                        .map(s -> s.getReit(stock.getPublicCod()))
                        .filter(this::isOldDate)
                        .map(s -> s.setDtLastUpdate(LocalDateTime.now()))
                        .map(s -> s.setId(stock.getId()))
                        .map(reitRepository::save));
    }

    private boolean isOldDate(Reit s) {
        return s.getDtLastUpdate() == null || s.getDtLastUpdate().plusDays(1).isBefore(LocalDateTime.now());
    }
}
