package store.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.PurchaseItem;
import store.domain.Stock;

class PurchasedDtoTest {

    private final String productName = "상품1";
    private final int price = 1000;
    private final int quantity = 3;
    private final int promotionStock = 10;
    private final int regularStock = 10;

    private Product product;
    private Stock stock;
    private PurchaseItem purchaseItem;

    @BeforeEach
    void setUp() {
        stock = new Stock(promotionStock, regularStock);
        product = new Product(productName, price, stock, null);
        purchaseItem = new PurchaseItem(product, quantity);
    }

    @Test
    void dto를_정상적으로_생성한다() {
        PurchasedDto dto = PurchasedDto.from(purchaseItem);

        assertEquals(price * quantity, dto.totalPrice());
    }
}