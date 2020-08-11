package com.money.stocks.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DecimalFormatTest {

    @Test
    void testToBigDecimal() {
        assertThat(DecimalFormat.toBigDecimal("11,5%")).isEqualTo(new BigDecimal("11.5"));
        assertThat(DecimalFormat.toBigDecimal("11,5")).isEqualTo(new BigDecimal("11.5"));
        assertThat(DecimalFormat.toBigDecimal("10,5")).isEqualTo(new BigDecimal("10.5"));
        assertThat(DecimalFormat.toBigDecimal("10,51")).isEqualTo(new BigDecimal("10.51"));
        assertThat(DecimalFormat.toBigDecimal("100")).isEqualTo(new BigDecimal("100"));
    }

    @Test
    void testToBigDecimalBillionFormatter() {
        assertThat(DecimalFormat.toBigDecimalBillionFormatter("110.555.444.000")).isEqualTo(new BigDecimal("110.59"));
        assertThat(DecimalFormat.toBigDecimalBillionFormatter("1.555.444.000")).isEqualTo(new BigDecimal("1.60"));
        assertThat(DecimalFormat.toBigDecimalBillionFormatter("1.900.444.000")).isEqualTo(new BigDecimal("1.89"));
        assertThat(DecimalFormat.toBigDecimalBillionFormatter("2.900444000")).isEqualTo(new BigDecimal("2.90"));
        assertThat(DecimalFormat.toBigDecimalBillionFormatter("8920444000")).isEqualTo(new BigDecimal("8.90"));
    }
}
