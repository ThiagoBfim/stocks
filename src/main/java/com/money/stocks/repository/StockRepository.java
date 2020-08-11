package com.money.stocks.repository;

import com.money.stocks.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByPublicCodIgnoreCase(String stockCod);
}
