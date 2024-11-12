package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PromotionTest {

    private final int buy = 2;
    private final int get = 1;

    private Promotion promotion;
    
    @BeforeEach
    void setUp() {
        promotion = new Promotion("프로모션1", buy, get, LocalDate.of(2024, 1, 1)
                , LocalDate.of(2025, 12, 31));
    }

    @Test
    void 프로모션_기준으로_추가_구매해야하는_수량을_계산한다() {
        int purchase = 8;
        int additionalPurchase = (buy + get) - (purchase % (buy + get));

        assertEquals(additionalPurchase, promotion.calculateAdditionalPurchase(purchase));
    }

    @Test
    void 프로모션_기준으로_추가_구매하지_않아도_되는_상황을_계산한다() {
        int purchase = 6;

        assertEquals(0, promotion.calculateAdditionalPurchase(purchase));
    }

    @Test
    void 프로모션_재고가_충분할_때_증정_상품의_수량을_계산한다() {
        int promotionStock = 10;
        int requestedQuantity = 8;
        int given = promotion.calculateFreeQuantity(requestedQuantity, promotionStock);
        int willGiven = requestedQuantity / (buy + get);

        assertEquals(willGiven, given);
    }

    @Test
    void 프로모션_재고가_부족할_때_증정_상품의_수량을_계산한다() {
        int promotionStock = 8;
        int requestedQuantity = 10;
        int given = promotion.calculateFreeQuantity(requestedQuantity, promotionStock);
        int willGiven = promotionStock / (buy + get);

        assertEquals(willGiven, given);
    }
}