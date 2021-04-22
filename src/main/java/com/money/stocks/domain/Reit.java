package com.money.stocks.domain;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_REIT")
public class Reit extends RepresentationModel<Reit> implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REIT")
    private Long id;

    @Column(name = "COD_REIT", unique = true)
    private String publicCod;

    @Column(name = "NU_DIVIDEND_YIELD")
    private BigDecimal dividendYield;

    @Column(name = "NU_MARKET_VALUE_BILLION")
    private BigDecimal marketValue;

    @Column(name = "NU_COMPANY_VALUE_BILLION")
    private BigDecimal companyValue;

    @Column(name = "NU_STAR")
    private BigDecimal star;

    @Column(name = "DT_LAST_UPDATE")
    private LocalDateTime dtLastUpdate;

    @Override
    public Long getId() {
        return id;
    }

    public Reit setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPublicCod() {
        return publicCod;
    }

    public Reit setPublicCod(String publicCod) {
        if (!publicCod.isBlank()) {
            this.publicCod = publicCod.toLowerCase();
        }
        return this;
    }

    public BigDecimal getDividendYield() {
        return dividendYield;
    }

    public Reit setDividendYield(BigDecimal dividendYield) {
        this.dividendYield = dividendYield;
        return this;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public Reit setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
        return this;
    }

    public BigDecimal getCompanyValue() {
        return companyValue;
    }

    public Reit setCompanyValue(BigDecimal companyValue) {
        this.companyValue = companyValue;
        return this;
    }

    public BigDecimal getStar() {
        return star;
    }

    public Reit setStar(BigDecimal star) {
        this.star = star;
        return this;
    }

    public LocalDateTime getDtLastUpdate() {
        return dtLastUpdate;
    }

    public Reit setDtLastUpdate(LocalDateTime dtLastUpdate) {
        this.dtLastUpdate = dtLastUpdate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var reit = (Reit) o;
        return Objects.equals(id, reit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
