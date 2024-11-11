package store.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

class MembershipCalculatorTest {

    private final BigDecimal discountRate = BigDecimal.valueOf(0.3);
    private final BigDecimal discountRoundingUnit = BigDecimal.valueOf(1_000);
    private final int maxDiscountPrice = 8_000;

    @Test
    void 가격이_10000원_일때_할인_금액은_3000원이다() {
        int currentPrice = 10_000;
        int discount = MembershipCalculator.calculateMembershipDiscount(currentPrice);

        BigDecimal expect = BigDecimal.valueOf(currentPrice).multiply(discountRate);
        expect = expect.divide(discountRoundingUnit, 0, RoundingMode.DOWN)
                .multiply(discountRoundingUnit);
        int result = expect.min(BigDecimal.valueOf(maxDiscountPrice)).intValue();

        assertEquals(result, discount);
    }

    @Test
    void 가격이_15000원_일때_할인_금액은_4000원이다() {
        int currentPrice = 15_000;
        int discount = MembershipCalculator.calculateMembershipDiscount(currentPrice);

        BigDecimal expect = BigDecimal.valueOf(currentPrice).multiply(discountRate);
        expect = expect.divide(discountRoundingUnit, 0, RoundingMode.DOWN)
                .multiply(discountRoundingUnit);
        int result = expect.min(BigDecimal.valueOf(maxDiscountPrice)).intValue();

        assertEquals(result, discount);
    }

    @Test
    void 가격이_30000원_일때_할인_금액은_8000원이다() {
        int currentPrice = 30_000;
        int discount = MembershipCalculator.calculateMembershipDiscount(currentPrice);

        assertEquals(maxDiscountPrice, discount);
    }

    @Test
    void 가격이_0원_일때_할인_금액은_0원이다() {
        int currentPrice = 0;
        int discount = MembershipCalculator.calculateMembershipDiscount(currentPrice);

        assertEquals(currentPrice, discount);
    }
}