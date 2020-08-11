package com.money.stocks.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalFormat {

    private DecimalFormat() {
        /*Noop*/
    }

    public static BigDecimal toBigDecimal(String percentValue) {
        if(!toNumber(percentValue).isBlank()) {
            return new BigDecimal(percentValue.replace(",", ".")
                    .replace("%", ""));
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal toBigDecimalBillionFormatter(String billionValue) {
        final String numericBillionValue = toNumber(billionValue);
        if(!numericBillionValue.isBlank()) {
            return new BigDecimal(numericBillionValue)
                    .divide(new BigDecimal(1_000_000_000), RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    private static String toNumber(String value){
        return value.replaceAll("[^0-9]", "");
    }

}
