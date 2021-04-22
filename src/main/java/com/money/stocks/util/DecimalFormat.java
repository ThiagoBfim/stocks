package com.money.stocks.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class DecimalFormat {

    private DecimalFormat() {
        /*Noop*/
    }

    public static BigDecimal toBigDecimal(String percentValue) {
        if (!toNumber(percentValue).isBlank()) {
            return new BigDecimal(percentValue.replace(",", ".")
                    .replace("%", "").replace("R$", "").trim());
        }
        return BigDecimal.ZERO;
    }


    public static BigDecimal toBigDecimalBillionFormatter(String billionValue) {
        final String numericBillionValue = toNumber(billionValue);
        if (!toNumber(numericBillionValue).isBlank()) {
            var billion = new BigDecimal(1_000_000_000);
            var round = new BigDecimal(numericBillionValue).round(MathContext.DECIMAL64);
            float fraction = calculateFraction(round.longValue(), billion.longValue());
            return BigDecimal.valueOf(fraction).setScale(2, RoundingMode.DOWN);
        }
        return BigDecimal.ZERO;
    }

    public static float calculateFraction(long number, long divisor) {
        long truncate = (number * 10L + (divisor / 2L)) / divisor;
        return truncate * 0.10F;
    }

    private static String toNumber(String value) {
        return value.replaceAll("[^0-9]", "");
    }
}
