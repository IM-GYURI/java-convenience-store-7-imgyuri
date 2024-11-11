package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseItemTest {

    private final int promotionQuantity = 10;
    private final int regularQuantity = 10;
    private final int price = 1_000;
    private final int buy = 2;
    private final int get = 1;
    private final int quantity = 4;

    private Product product;
    private Stock stock;
    private Promotion promotion;
    private PurchaseItem purchaseItem;

    @BeforeEach
    void setUp() {
        stock = new Stock(promotionQuantity, regularQuantity);
        promotion = new Promotion("프로모션1", buy, get, LocalDate.of(2024, 1, 1)
                , LocalDate.of(2024, 12, 31));
        product = new Product("상품1", price, stock, promotion);

        purchaseItem = new PurchaseItem(product, quantity);
    }

    @Test
    void 추가구매가_필요한지_여부를_판단한다() {
        assertTrue(purchaseItem.needsAdditionalPurchase());
    }

    @Test
    void 추가구매_시_프로모션_재고를_넘어설_경우_추가구매를_권하지_않는다() {
        purchaseItem = new PurchaseItem(product, promotionQuantity + 1);

        assertFalse(purchaseItem.needsAdditionalPurchase());
    }

    @Test
    void 프로모션이_없는_상품은_추가구매를_권하지_않는다() {
        product = new Product("상품2", price, stock, null);
        purchaseItem = new PurchaseItem(product, quantity);

        assertFalse(purchaseItem.needsAdditionalPurchase());
    }

    @Test
    void 추가구매_시_수량이_변화하는지를_확인한다() {
        int additionalQuantity = (buy + get) - (quantity % (buy + get));

        purchaseItem.buyMore();

        assertEquals(additionalQuantity + quantity, purchaseItem.getQuantity());
    }
}