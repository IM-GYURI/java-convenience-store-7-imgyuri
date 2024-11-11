package store.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PurchaseItem;
import store.domain.Stock;

class PromotionDetailDtoTest {

    private final String productName = "상품1";
    private final int price = 1_000;
    private final int promotionStock = 10;
    private final int regularStock = 20;
    private final int quantity = 4;
    private final int buy = 2;
    private final int get = 1;

    private Product product;
    private PurchaseItem purchaseItem;
    private Promotion promotion;
    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock(promotionStock, regularStock);
        product = new Product(productName, price, stock, null);
        purchaseItem = new PurchaseItem(product, quantity);
    }

    @Test
    void 프로모션이_없을_경우_dto를_생성한다() {
        PromotionDetailDto dto = PromotionDetailDto.from(purchaseItem);

        assertEquals(0, dto.promotionQuantity());
        assertEquals(regularStock, dto.remainingQuantity());
    }

    @Test
    void 프로모션이_있을_경우_dto를_생성한다() {
        promotion = new Promotion("프로모션1", buy, get, LocalDate.of(2024, 1, 1)
                , LocalDate.of(2024, 12, 31));

        product = new Product(productName, price, stock, promotion);
        purchaseItem = new PurchaseItem(product, quantity);

        PromotionDetailDto dto = PromotionDetailDto.from(purchaseItem);

        assertEquals(promotionStock / (buy + get) * (buy + get), dto.promotionQuantity());
    }
}