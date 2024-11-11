package store.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.dto.ParsedItemDto;

class PurchaseItemFactoryTest {

    private Products products;

    @BeforeEach
    void setUp() {
        products = new Products(Arrays.asList(
                new Product("상품1", 1000, null, null),
                new Product("상품2", 2000, null, null)
        ));
    }

    @Test
    void PurchaseItem을_정상적으로_생성한다() {
        ParsedItemDto parsedItemDto = new ParsedItemDto("상품1", 2);
        PurchaseItem purchaseItem = PurchaseItemFactory.createPurchaseItem(parsedItemDto, products);

        assertEquals("상품1", purchaseItem.getProduct().name());
    }

    @Test
    void 상품이_존재하지_않을_경우_예외를_발생시킨다() {
        ParsedItemDto parsedItemDto = new ParsedItemDto("존재하지 않는 상품", 2);

        assertThatThrownBy(() ->
                PurchaseItemFactory.createPurchaseItem(parsedItemDto, products))
                .isInstanceOf(IllegalArgumentException.class);
    }
}