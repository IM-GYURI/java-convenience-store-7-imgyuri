package store.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MembershipCalculator {

    private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.3);
    private static final BigDecimal DISCOUNT_ROUNDING_UNIT = BigDecimal.valueOf(1_000);
    private static final int MAX_DISCOUNT_PRICE = 8_000;

    public static int calculateMembershipDiscount(int totalPrice) {
        BigDecimal discount = BigDecimal.valueOf(totalPrice).multiply(DISCOUNT_RATE);
        discount = discount.divide(DISCOUNT_ROUNDING_UNIT, 0, RoundingMode.DOWN)
                .multiply(DISCOUNT_ROUNDING_UNIT);
        return discount.min(BigDecimal.valueOf(MAX_DISCOUNT_PRICE)).intValue();
    }
}
