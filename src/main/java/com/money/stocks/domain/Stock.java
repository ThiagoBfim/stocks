package com.money.stocks.domain;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "TB_STOCK")
public class Stock extends RepresentationModel<Stock> implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_STOCK", updatable = false, nullable = false)
    private Long id;

    @Column(name = "COD_STOCK", unique = true)
    private String publicCod;

    @Column(name = "NU_DIVIDEND_YIELD")
    private BigDecimal dividendYield;

    @Column(name = "NU_MARKET_VALUE_MILLION")
    private BigDecimal marketValue;

    @Column(name = "NU_STAR")
    private BigDecimal star;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicCod() {
        return publicCod;
    }

    public void setPublicCod(String publicCod) {
        this.publicCod = publicCod;
    }

    public BigDecimal getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(BigDecimal dividendYield) {
        this.dividendYield = dividendYield;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Stock stock = (Stock) o;
        return Objects.equals(id, stock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
