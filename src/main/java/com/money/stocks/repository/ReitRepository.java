package com.money.stocks.repository;

import com.money.stocks.domain.Reit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReitRepository extends JpaRepository<Reit, Long> {

    Optional<Reit> findByPublicCodIgnoreCase(String reitCod);
}
